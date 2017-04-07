/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.jctl.cloud.modules.gen.dao;

import com.jctl.cloud.common.persistence.annotation.MyBatisDao;
import com.jctl.cloud.modules.gen.entity.GenScheme;
import com.jctl.cloud.common.persistence.CrudDao;

/**
 * 生成方案DAO接口
 * @author ThinkGem
 * @version 2013-10-15
 */
@MyBatisDao
public interface GenSchemeDao extends CrudDao<GenScheme> {
	
}
