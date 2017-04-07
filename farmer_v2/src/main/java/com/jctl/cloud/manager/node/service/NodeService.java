/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.jctl.cloud.manager.node.service;

import java.util.Date;
import java.util.List;

import com.jctl.cloud.mina.entity.ResultSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jctl.cloud.common.persistence.Page;
import com.jctl.cloud.common.service.CrudService;
import com.jctl.cloud.manager.node.entity.Node;
import com.jctl.cloud.manager.node.dao.NodeDao;

/**
 * 节点管理Service
 *
 * @author ll
 * @version 2017-02-25
 */
@Service
@Transactional(readOnly = true)
public class NodeService extends CrudService<NodeDao, Node> {

    @Autowired
    private NodeDao nodeDao;

    public Node get(String id) {
        return super.get(id);
    }

    public List<Node> findList(Node node) {
        return super.findList(node);
    }

    public Page<Node> findPage(Page<Node> page, Node node) {
        return super.findPage(page, node);
    }



    @Transactional(readOnly = false)
    public void save(Node node) {
        super.save(node);
    }

    @Transactional(readOnly = false)
    public void delete(Node node) {
        super.delete(node);
    }


    @Transactional(readOnly = false)
   //自定义的注解
    public void saveOrUpdate(ResultSet resultSet,String relayId) {
        List<String> clintMacs = resultSet.getGatewayResultSet().getClientMacList();
        if (clintMacs == null) {
            return;
        }
        for (String clintMac : clintMacs) {
            Node temp = nodeDao.getByNodeMac(clintMac);
            if(temp==null){
                Node newNode = new Node(clintMac,relayId,new Date());
                save(newNode);
            }
        }
    }
    public List<Node> findAllByNum(Node node){
        return nodeDao.findAllByNum(node);
    }

    public Node getByNodeMac(String nodeNum){
      return   nodeDao.getByNodeMac(nodeNum);
    }


    public Integer getNodeNum(String relayId) {
        return nodeDao.getNodeNum(relayId);
    }

    public List<Node> findRefreshList() {
        return nodeDao.findRefreshList();
    }

    public Node getByNodeNum(String nodeNum){return nodeDao.getByNodeNum(nodeNum);};
    public  Integer getNodeNumberByfarmlandId(String farmlandId){
        return nodeDao.getNodeNumberByfarmlandId(farmlandId);
    }

    public List<Node> selectAllNodeByUserId(String id) {

      return nodeDao.selectAllNodeByUserId( id);

    }
}