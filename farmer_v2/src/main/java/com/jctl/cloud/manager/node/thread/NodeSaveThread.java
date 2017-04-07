package com.jctl.cloud.manager.node.thread;

import com.jctl.cloud.common.utils.SpringContextHolder;
import com.jctl.cloud.manager.node.service.NodeService;
import com.jctl.cloud.manager.relay.entity.Relay;
import com.jctl.cloud.manager.relay.service.RelayService;
import com.jctl.cloud.mina.entity.ResultSet;

/**
 * Created by Administrator on 2017/3/10.
 */
public class NodeSaveThread extends Thread {

    private ResultSet resultSet;
    private String relayMac;

    private NodeService nodeService = SpringContextHolder.getBean(NodeService.class);

    private RelayService relayService = SpringContextHolder.getBean(RelayService.class);


    public NodeSaveThread() {

    }

    public NodeSaveThread(ResultSet resultSet, String relayMac) {
        this.resultSet = resultSet;
        this.relayMac = relayMac;
    }

    public void run(){

        try {
            Thread.sleep(3000L);
            Relay relay = relayService.getByMac(relayMac);
            nodeService.saveOrUpdate(resultSet,relay.getId());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
