/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.jctl.cloud.manager.nodedatatype.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jctl.cloud.common.persistence.Page;
import com.jctl.cloud.common.service.CrudService;
import com.jctl.cloud.manager.nodedatatype.entity.NodeDataType;
import com.jctl.cloud.manager.nodedatatype.dao.NodeDataTypeDao;

/**
 * 节点数据类型管理Service
 * @author ll
 * @version 2017-02-25
 */
@Service
@Transactional(readOnly = true)
public class NodeDataTypeService extends CrudService<NodeDataTypeDao, NodeDataType> {

	public NodeDataType get(String id) {
		return super.get(id);
	}
	
	public List<NodeDataType> findList(NodeDataType nodeDataType) {
		return super.findList(nodeDataType);
	}
	
	public Page<NodeDataType> findPage(Page<NodeDataType> page, NodeDataType nodeDataType) {
		return super.findPage(page, nodeDataType);
	}
	
	@Transactional(readOnly = false)
	public void save(NodeDataType nodeDataType) {
		super.save(nodeDataType);
	}
	
	@Transactional(readOnly = false)
	public void delete(NodeDataType nodeDataType) {
		super.delete(nodeDataType);
	}
	
}