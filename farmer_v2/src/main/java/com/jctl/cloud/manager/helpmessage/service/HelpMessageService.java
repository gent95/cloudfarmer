/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.jctl.cloud.manager.helpmessage.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jctl.cloud.common.persistence.Page;
import com.jctl.cloud.common.service.CrudService;
import com.jctl.cloud.manager.helpmessage.entity.HelpMessage;
import com.jctl.cloud.manager.helpmessage.dao.HelpMessageDao;

/**
 * 帮助信息Service
 * @author 刘凯
 * @version 2017-03-23
 */
@Service
@Transactional(readOnly = true)
public class HelpMessageService extends CrudService<HelpMessageDao, HelpMessage> {

	public HelpMessage get(String id) {
		return super.get(id);
	}
	
	public List<HelpMessage> findList(HelpMessage helpMessage) {
		return super.findList(helpMessage);
	}
	
	public Page<HelpMessage> findPage(Page<HelpMessage> page, HelpMessage helpMessage) {
		return super.findPage(page, helpMessage);
	}
	
	@Transactional(readOnly = false)
	public void save(HelpMessage helpMessage) {
		super.save(helpMessage);
	}
	
	@Transactional(readOnly = false)
	public void delete(HelpMessage helpMessage) {
		super.delete(helpMessage);
	}
	
}