/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.jctl.cloud.manager.helpmessage.dao;

import com.jctl.cloud.common.persistence.CrudDao;
import com.jctl.cloud.common.persistence.annotation.MyBatisDao;
import com.jctl.cloud.manager.helpmessage.entity.HelpMessage;

/**
 * 帮助信息DAO接口
 * @author 刘凯
 * @version 2017-03-23
 */
@MyBatisDao
public interface HelpMessageDao extends CrudDao<HelpMessage> {
	
}