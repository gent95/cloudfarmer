package com.jctl.cloud.mina.server;

import com.jctl.cloud.common.utils.SpringContextHolder;
import com.jctl.cloud.common.utils.TimeUtils;
import com.jctl.cloud.manager.relay.service.RelayService;
import com.jctl.cloud.mina.core.SerialPort;
import com.jctl.cloud.mina.main.Main;
import com.jctl.cloud.mina.entity.IoSessionEntity;
import com.jctl.cloud.mina.entity.ResultSet;
import com.jctl.cloud.mina.utils.cache.IoSessionCacheManager;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.net.InetSocketAddress;
import java.util.Date;

/**
 * 暂时不插入到数据库   后面要插入
 */
@Service
public class MinaLongConnServerHandler extends IoHandlerAdapter {
    // 作为日志打印用
    private final static org.slf4j.Logger log = LoggerFactory.getLogger(MinaLongConnServerHandler.class);

    public static String nodeOrders;

    private RelayService relayService = SpringContextHolder.getBean(RelayService.class);


    private final String REFRESH_RELAY_ORDER = "Gateway:+\n";

    private String resultOrderStr;

//    private IDataService dataService = new DataServiceImpl();


    /**
     * TCP 连接建立后立即执行的
     *
     * @param session
     */
    @Override
    public void sessionOpened(IoSession session) {
        InetSocketAddress remoteAddress = (InetSocketAddress) session.getRemoteAddress();
        String clientIp = remoteAddress.getAddress().getHostAddress();
        log.info("接收到设备连接信息：当前设备信息：ip=" + clientIp + "，Session ID=" + session.getId());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        //发送获取Gateway:的指令
//        if (nodeOrders != null) {
//            session.write(nodeOrders);
//        }
        if (nodeOrders == null) {
            session.write(new String(Main.sendGatewayOrders()).trim());
        }
        log.info("-->Gateway:命令已经发送至设备，设备Ip为:" + clientIp);
    }


    //接收的数据之后执行发送
    @Override
    public void messageReceived(IoSession session, Object message) {
        log.info("接收到来自设备:" + ((InetSocketAddress) session.getRemoteAddress()).getAddress().getHostAddress() + "的信息" + "信息内容：" + message);
//        System.out.println(message.toString().equals("DT"));
        resultOrderStr = message.toString();
        //hashCode缓存动态修改的
        if (resultOrderStr.equals("DT")) {
            IoSessionCacheManager.changeHashCodeCacheMap(session);
        }
        //如果返回的信息为空，停止解析
        if (resultOrderStr == null || resultOrderStr.equals("") || resultOrderStr.trim().length() < 7) {
            return;
        }
        //如果返回的字符串为非协议的命令信息，停止解析
        if (!resultOrderStr.trim().substring(0, 7).equals("Client:") && !resultOrderStr.trim().substring(0, 8).equals("Gateway:")) {
            return;
        }
        //解析信息
        byte[] data = resultOrderStr.trim().getBytes();
        ResultSet resultSet = new SerialPort().comReadData(data, data.length);
        //设备信息维护
        if (resultSet.getGatewayResultSet() != null) {
            IoSessionCacheManager.addIoSessionCache(resultSet,session);
//            addIoSessionCache(resultSet,session);
        }
        relayService.saveOrUpdate(resultSet);
    }



    @Override
    public void messageSent(IoSession session, Object message) {
        log.info("服务端发消息到客户端" + message);
        if (nodeOrders != null) {
            session.write(message);
        }
    }

    // 当连接空闲时触发此方法.
    @Override
    public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
        log.info(new Date() + "-->空闲消息，连接来自SESSION ID 为：" + " " + String.valueOf(session.getId()));
    }



    /**
     * 客户端连接被关闭
     *
     * @param session
     * @throws Exception
     */
    @Override
    public void sessionClosed(IoSession session) throws Exception {
        try {
            /*将spring容器中的缓存去除*/
            InetSocketAddress remoteAddress = (InetSocketAddress) session.getRemoteAddress();
            String clientIp = remoteAddress.getAddress().getHostAddress();
            log.info(new Date() + "-->与" + clientIp + "的连接中断，清除它的缓存记录 " + String.valueOf(session.getId()));
            super.sessionClosed(session);
        } catch (Exception e) {
            throw e;
        }

    }

    // 当接口中其他方法抛出异常未被捕获时触发此方法
    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        cause.printStackTrace();
        super.exceptionCaught(session, cause);
    }

}
