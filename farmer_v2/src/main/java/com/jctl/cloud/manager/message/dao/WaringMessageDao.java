/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.jctl.cloud.manager.message.dao;

import com.jctl.cloud.common.persistence.CrudDao;
import com.jctl.cloud.common.persistence.annotation.MyBatisDao;
import com.jctl.cloud.manager.message.entity.WaringMessage;

/**
 * 报警信息DAO接口
 * @author kay
 * @version 2017-03-07
 */
@MyBatisDao
public interface WaringMessageDao extends CrudDao<WaringMessage> {
	
}