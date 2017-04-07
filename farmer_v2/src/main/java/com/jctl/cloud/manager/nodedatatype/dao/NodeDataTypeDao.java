/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.jctl.cloud.manager.nodedatatype.dao;

import com.jctl.cloud.common.persistence.CrudDao;
import com.jctl.cloud.common.persistence.annotation.MyBatisDao;
import com.jctl.cloud.manager.nodedatatype.entity.NodeDataType;

/**
 * 节点数据类型管理DAO接口
 * @author ll
 * @version 2017-02-25
 */
@MyBatisDao
public interface NodeDataTypeDao extends CrudDao<NodeDataType> {
	
}