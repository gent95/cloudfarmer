/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.jctl.cloud.manager.region.dao;

import com.jctl.cloud.common.persistence.CrudDao;
import com.jctl.cloud.common.persistence.annotation.MyBatisDao;
import com.jctl.cloud.manager.region.entity.Region;

/**
 * 地区DAO接口
 * @author Lewkay
 * @version 2017-03-17
 */
@MyBatisDao
public interface RegionDao extends CrudDao<Region> {

    Region selectByName(Region search);
}