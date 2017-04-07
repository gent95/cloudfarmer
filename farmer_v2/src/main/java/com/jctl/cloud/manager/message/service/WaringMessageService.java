/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.jctl.cloud.manager.message.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jctl.cloud.common.persistence.Page;
import com.jctl.cloud.common.service.CrudService;
import com.jctl.cloud.manager.message.entity.WaringMessage;
import com.jctl.cloud.manager.message.dao.WaringMessageDao;

/**
 * 报警信息Service
 * @author kay
 * @version 2017-03-07
 */
@Service
@Transactional(readOnly = true)
public class WaringMessageService extends CrudService<WaringMessageDao, WaringMessage> {

	public WaringMessage get(String id) {
		return super.get(id);
	}
	
	public List<WaringMessage> findList(WaringMessage waringMessage) {
		return super.findList(waringMessage);
	}
	
	public Page<WaringMessage> findPage(Page<WaringMessage> page, WaringMessage waringMessage) {
		return super.findPage(page, waringMessage);
	}
	
	@Transactional(readOnly = false)
	public void save(WaringMessage waringMessage) {
		super.save(waringMessage);
	}
	
	@Transactional(readOnly = false)
	public void delete(WaringMessage waringMessage) {
		super.delete(waringMessage);
	}


	
}