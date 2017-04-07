/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.jctl.cloud.manager.timingstrategy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jctl.cloud.common.persistence.Page;
import com.jctl.cloud.common.service.CrudService;
import com.jctl.cloud.manager.timingstrategy.entity.NodeCollectionCycle;
import com.jctl.cloud.manager.timingstrategy.dao.NodeCollectionCycleDao;

/**
 * 节点定时任务Service
 * @author gent
 * @version 2017-03-02
 */
@Service
@Transactional(readOnly = true)
public class NodeCollectionCycleService extends CrudService<NodeCollectionCycleDao, NodeCollectionCycle> {
	@Autowired
	private NodeCollectionCycleDao nodeCollectionCycleDao;

	public NodeCollectionCycle get(String id) {
		return super.get(id);
	}
	
	public List<NodeCollectionCycle> findList(NodeCollectionCycle nodeCollectionCycle) {
		return super.findList(nodeCollectionCycle);
	}
	
	public Page<NodeCollectionCycle> findPage(Page<NodeCollectionCycle> page, NodeCollectionCycle nodeCollectionCycle) {
		return super.findPage(page, nodeCollectionCycle);
	}
	
	@Transactional(readOnly = false)
	public void save(NodeCollectionCycle nodeCollectionCycle) {
		super.save(nodeCollectionCycle);
	}
	
	@Transactional(readOnly = false)
	public void delete(NodeCollectionCycle nodeCollectionCycle) {
		super.delete(nodeCollectionCycle);
	}

	public NodeCollectionCycle findByNodeId(NodeCollectionCycle nodeCollectionCycle){ return nodeCollectionCycleDao.findByNodeId(nodeCollectionCycle);}
	@Transactional(readOnly = false)
	public int deleteByNodeId(NodeCollectionCycle nodeCollectionCycle){return  nodeCollectionCycleDao.deleteByNodeId(nodeCollectionCycle);}
}