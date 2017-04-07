/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.jctl.cloud.manager.timingstrategy.dao;

import com.jctl.cloud.common.persistence.CrudDao;
import com.jctl.cloud.common.persistence.annotation.MyBatisDao;
import com.jctl.cloud.manager.timingstrategy.entity.NodeCollectionCycle;

/**
 * 节点定时任务DAO接口
 * @author gent
 * @version 2017-03-02
 */
@MyBatisDao
public interface NodeCollectionCycleDao extends CrudDao<NodeCollectionCycle> {
    NodeCollectionCycle findByNodeId(NodeCollectionCycle nodeCollectionCycle);
    int deleteByNodeId(NodeCollectionCycle nodeCollectionCycle);
}