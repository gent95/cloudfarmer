/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.jctl.cloud.modules.cms.dao;

import com.jctl.cloud.common.persistence.annotation.MyBatisDao;
import com.jctl.cloud.modules.cms.entity.ArticleData;
import com.jctl.cloud.common.persistence.CrudDao;

/**
 * 文章DAO接口
 * @author ThinkGem
 * @version 2013-8-23
 */
@MyBatisDao
public interface ArticleDataDao extends CrudDao<ArticleData> {
	
}
