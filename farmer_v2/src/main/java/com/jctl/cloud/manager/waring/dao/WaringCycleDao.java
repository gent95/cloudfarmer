/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.jctl.cloud.manager.waring.dao;

import com.jctl.cloud.common.persistence.CrudDao;
import com.jctl.cloud.common.persistence.annotation.MyBatisDao;
import com.jctl.cloud.manager.waring.entity.WaringCycle;

/**
 * 异常采集DAO接口
 * @author lewkay
 * @version 2017-03-07
 */
@MyBatisDao
public interface WaringCycleDao extends CrudDao<WaringCycle> {
	
}