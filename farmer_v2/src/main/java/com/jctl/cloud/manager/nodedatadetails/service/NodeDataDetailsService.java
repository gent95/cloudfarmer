/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.jctl.cloud.manager.nodedatadetails.service;

import java.util.List;

import com.jctl.cloud.common.annotation.waring.WaringMessagePush;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jctl.cloud.common.persistence.Page;
import com.jctl.cloud.common.service.CrudService;
import com.jctl.cloud.manager.nodedatadetails.entity.NodeDataDetails;
import com.jctl.cloud.manager.nodedatadetails.dao.NodeDataDetailsDao;

/**
 * 节点数据详情管理Service
 *
 * @author ll
 * @version 2017-02-27
 */
@Service
@Transactional(readOnly = true)
public class NodeDataDetailsService extends CrudService<NodeDataDetailsDao, NodeDataDetails> {

    @Autowired
    private NodeDataDetailsDao nodeDataDetailsDao;

    public NodeDataDetails get(String id) {
        NodeDataDetails nodeDataDetails = super.get(id);
        return nodeDataDetails;
    }

    public List<NodeDataDetails> findList(NodeDataDetails nodeDataDetails) {
        return super.findList(nodeDataDetails);
    }

    public Page<NodeDataDetails> findPage(Page<NodeDataDetails> page, NodeDataDetails nodeDataDetails) {
        return super.findPage(page, nodeDataDetails);
    }


    //	AOP判断是否超警告范围
    @WaringMessagePush(description = "预警判断")
    @Transactional(readOnly = false)
    public void save(NodeDataDetails nodeDataDetails) {
        super.save(nodeDataDetails);
    }

    @Transactional(readOnly = false)
    public void delete(NodeDataDetails nodeDataDetails) {
        super.delete(nodeDataDetails);
    }

    public List<NodeDataDetails> fetchLastData(NodeDataDetails nodeDataDetails) {
        return nodeDataDetailsDao.fetchLastData(nodeDataDetails);
    }

    public Page<NodeDataDetails> findNodeDataDetails(Page<NodeDataDetails> page, NodeDataDetails nodeDataDetails) {
        // 生成数据权限过滤条件（dsf为dataScopeFilter的简写，在xml中使用 ${sqlMap.dsf}调用权限SQL）
        nodeDataDetails.getSqlMap().put("dsf", dataScopeFilter(nodeDataDetails.getCurrentUser(), "o", "a"));
        // 设置分页参数
        nodeDataDetails.setPage(page);
        // 执行分页查询
        page.setList(nodeDataDetailsDao.findList(nodeDataDetails));
        return page;
    }
}