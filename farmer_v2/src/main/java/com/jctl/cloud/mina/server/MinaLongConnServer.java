package com.jctl.cloud.mina.server;

import com.jctl.cloud.common.config.Global;
import com.jctl.cloud.mina.entity.ClientSessions;
import com.jctl.cloud.mina.entity.IoSessionEntity;
import com.jctl.cloud.mina.entity.OpenCloseVO;
import com.jctl.cloud.mina.entity.OrdersQueueVO;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.apache.mina.util.CopyOnWriteMap;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public class MinaLongConnServer {

    // 作为日志打印用
    private final static org.slf4j.Logger log = LoggerFactory.getLogger(MinaLongConnServer.class);
    /**
     * 10分钟后超时
     */
    private static final int IDELTIMEOUT = 600;

    public static List<ClientSessions> sessionList = new CopyOnWriteArrayList<ClientSessions>();

    public static volatile long INDEX = 0;

    public static List<OrdersQueueVO> ordersQueueVOList = new CopyOnWriteArrayList<OrdersQueueVO>();

    public static Map<String, OrdersQueueVO> ordersLogMap = new CopyOnWriteMap<String, OrdersQueueVO>();//Key是所发的命令

    public static List<OpenCloseVO> openCloseNodeResponseList = new CopyOnWriteArrayList<OpenCloseVO>();

    public static Map<String, IoSessionEntity> sessionMap;//实际的存储iosession

    public static Map<Integer, IoSessionEntity> sessionMapBackups;//在内存中备份的 永不清除的

    public static Map<Integer,IoSessionEntity> hashCodeMap;//依据连接信息保存的hashcode


    private String serverPort = Global.getConfig("serverPort");

    //打开服务
    public void start() {
        IoAcceptor acceptor = new NioSocketAcceptor();
        acceptor.getSessionConfig().setReadBufferSize(2048);
        acceptor.getSessionConfig().setIdleTime(IdleStatus.READER_IDLE, IDELTIMEOUT);
        acceptor.getFilterChain().addLast("codec",
                new ProtocolCodecFilter( new TextLineCodecFactory(
                        Charset.forName("UTF-8"),
                        "\r\n",
                        "\r\n")));

        // 设置消息处理类（创建、关闭Session，可读可写等等，继承自接口IoHandler）
        acceptor.setHandler(new MinaLongConnServerHandler());
        try {
            //增加缓存数据
            sessionMapBackups= new HashMap<>(100);
            hashCodeMap= new HashMap<>(100);
            sessionMap = new HashMap<>(100);
            acceptor.bind(new InetSocketAddress(Integer.valueOf(serverPort)));
            log.info("监听服务器已经启动，当前监听端口为 "+serverPort);
        } catch (Exception e) {
            log.info("监听服务器启动失败");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        new MinaLongConnServer().start();
    }

    public static Map<String, IoSessionEntity> getSessionMap() {
        return sessionMap;
    }

    public static void setSessionMap(Map<String, IoSessionEntity> sessionMap) {
        MinaLongConnServer.sessionMap = sessionMap;
    }
}
