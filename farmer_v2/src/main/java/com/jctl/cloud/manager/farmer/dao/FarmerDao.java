/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.jctl.cloud.manager.farmer.dao;

import com.jctl.cloud.common.persistence.CrudDao;
import com.jctl.cloud.common.persistence.annotation.MyBatisDao;
import com.jctl.cloud.manager.farmer.entity.Farmer;

/**
 * 农场DAO接口
 * @author lewkay
 * @version 2017-02-25
 */
@MyBatisDao
public interface FarmerDao extends CrudDao<Farmer> {
	
}