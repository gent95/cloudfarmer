/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.jctl.cloud.manager.nodedatadetails.dao;

import com.jctl.cloud.common.persistence.CrudDao;
import com.jctl.cloud.common.persistence.annotation.MyBatisDao;
import com.jctl.cloud.manager.nodedatadetails.entity.NodeDataDetails;

import java.util.List;

/**
 * 节点数据详情管理DAO接口
 * @author ll
 * @version 2017-02-27
 */
@MyBatisDao
public interface NodeDataDetailsDao extends CrudDao<NodeDataDetails> {

    List<NodeDataDetails> fetchLastData(NodeDataDetails details);

}