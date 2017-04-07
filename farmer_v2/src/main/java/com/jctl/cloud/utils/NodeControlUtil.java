package com.jctl.cloud.utils;

import com.jctl.cloud.common.utils.SpringContextHolder;
import com.jctl.cloud.exception.mina.IoSessionIsNullException;
import com.jctl.cloud.exception.mina.NodeNoBindingException;
import com.jctl.cloud.manager.node.entity.Node;
import com.jctl.cloud.manager.node.service.NodeService;
import com.jctl.cloud.manager.relay.entity.Relay;
import com.jctl.cloud.manager.relay.service.RelayService;
import com.jctl.cloud.mina.main.Main;
import com.jctl.cloud.mina.entity.IoSessionEntity;
import com.jctl.cloud.mina.server.MinaLongConnServer;
import org.apache.mina.core.session.IoSession;
import org.aspectj.lang.reflect.NoSuchPointcutException;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 操作节点工具类
 * Created by lewKay on 2017/2/27.
 */

@Service
public class NodeControlUtil {
    // 作为日志打印用
    private final static org.slf4j.Logger log = LoggerFactory.getLogger(NodeControlUtil.class);
    //
    private NodeService nodeService = SpringContextHolder.getBean(NodeService.class);

    private RelayService relayService = SpringContextHolder.getBean(RelayService.class);

    //获取缓存的ioSession
    private Map<String, IoSessionEntity> ioSessions;

    private final String REFRESH_RELAY_ORDER = "Gateway:+\n";

    private Node orderNode;//节点

    private Relay orderRelay;

    private String orderRelayMac;

    private String orderNodeMac;

    private IoSession orderIoSession;

    /**
     * 根据当前节点刷新节点信息
     *
     * @return
     */
    public void refreshNodeByNodeId(Node node) throws IoSessionIsNullException, NodeNoBindingException {
        refreshProperty(node);
        char[] order = Main.sendCilentOrdersGetData(orderNodeMac, orderRelayMac);
        String orderString = new String(order);
        orderIoSession.write(orderString);
        log.debug("已经发送命令到：" + orderRelayMac + "命令内容为：" + order.toString().trim());
    }

    /**
     * 关闭节点
     *
     * @param node
     */
    public void closeNode(Node node) throws IoSessionIsNullException, NodeNoBindingException {
        refreshProperty(node);
        char[] order = Main.sendCilentOrdersClose(orderNodeMac, orderRelayMac);
        String orderString = new String(order);
        orderIoSession.write(orderString);
    }

    /**
     * 打开节点
     *
     * @param node
     */
    public void openNode(Node node) throws IoSessionIsNullException, NodeNoBindingException {
        refreshProperty(node);
        char[] order = Main.sendCilentOrdersOpen(orderNodeMac, orderRelayMac);
        String orderString = new String(order);
        orderIoSession.write(orderString);
    }

    /**
     * 刷新关联的中继
     *
     * @param node
     */
    public void refreshRealyByNode(Node node) throws IoSessionIsNullException, NodeNoBindingException {
        refreshProperty(node);
        char[] order = Main.sendCilentOrdersOpen(orderNodeMac, orderRelayMac);
        String orderString = new String(order);
        orderIoSession.write(orderString);
    }

    /**
     * 单独刷新中继
     *
     * @param relayMac
     */
    public void refreshRelay(String relayMac) throws IoSessionIsNullException {
        orderIoSession = ioSessions.get(relayMac).getIoSession();
        if (orderIoSession == null) {
            throw new IoSessionIsNullException("The current device is offline or not connected!");
        }
        orderIoSession.write(REFRESH_RELAY_ORDER);
    }


    /**
     * 断开链接的中继
     *
     * @param
     */
    public void disConnect(String relayMac) {
        orderIoSession = ioSessions.get(relayMac).getIoSession();
        orderIoSession.close(true);
        ioSessions.remove(relayMac);
    }


    //刷新当前对象属性
    public synchronized void refreshProperty(Node node) throws IoSessionIsNullException, NodeNoBindingException {
        ioSessions = MinaLongConnServer.sessionMap;

        orderNode = nodeService.get(node.getId());
        if (orderNode == null) {
            throw new NullPointerException("Node is not exit！");
        }
        orderNodeMac = orderNode.getNodeNum();
        if (orderNode.getRelayId() == null || orderNode.getRelayId().toString().equals("")) {
            throw new NodeNoBindingException("This node does not bind any relay!");
        }
        orderRelay = relayService.get(orderNode.getRelayId().toString());
        if (orderRelay == null) {
            throw new NodeNoBindingException("Bound relay deleted！");
        }
        orderRelayMac = orderRelay.getRelayNum();
        try {
            orderIoSession = ioSessions.get(orderRelayMac).getIoSession();
        } catch (Exception e) {

        }
        if (orderIoSession == null) {
            throw new IoSessionIsNullException("relay：" + orderRelayMac + "offline or not connected!");
        }
    }


}
