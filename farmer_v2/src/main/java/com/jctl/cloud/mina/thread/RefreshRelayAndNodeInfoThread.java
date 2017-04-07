package com.jctl.cloud.mina.thread;

import com.jctl.cloud.common.utils.SpringContextHolder;
import com.jctl.cloud.exception.mina.IoSessionIsNullException;
import com.jctl.cloud.exception.mina.NodeNoBindingException;
import com.jctl.cloud.manager.node.entity.Node;
import com.jctl.cloud.manager.node.service.NodeService;
import com.jctl.cloud.manager.relay.entity.Relay;
import com.jctl.cloud.manager.relay.service.RelayService;
import com.jctl.cloud.utils.NodeControlUtil;
import java.util.List;

/**
 * 刷新设备+节点线程
 * Created by Administrator on 2017/2/28.
 */
public class RefreshRelayAndNodeInfoThread implements Runnable {


    private NodeService nodeService = SpringContextHolder.getBean(NodeService.class);

    private RelayService relayService = SpringContextHolder.getBean(RelayService.class);

    private NodeControlUtil nodeControlUtil=SpringContextHolder.getBean(NodeControlUtil.class);

    public void run() {

        //刷新中繼

        List<Relay> relays =relayService.findList(new Relay());
        for(Relay relay:relays){
            try {
                nodeControlUtil.refreshRelay(relay.getRelayNum());
                Thread.sleep(1000);
            } catch (IoSessionIsNullException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //刷新節點
        List<Node> list = nodeService.findList(new Node());
        for(Node node:list){
            try {
                nodeControlUtil.refreshNodeByNodeId(node);
                System.out.println("刷新节点信息，刷新节点为:"+node.getNodeNum());
                Thread.sleep(1000);
            } catch (IoSessionIsNullException e) {
                e.printStackTrace();
            } catch (NodeNoBindingException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
