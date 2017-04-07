/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.jctl.cloud.manager.waring.service;

import java.util.List;

import com.jctl.cloud.common.annotation.waring.WaringMessagePush;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jctl.cloud.common.persistence.Page;
import com.jctl.cloud.common.service.CrudService;
import com.jctl.cloud.manager.waring.entity.WaringCycle;
import com.jctl.cloud.manager.waring.dao.WaringCycleDao;

/**
 * 异常采集Service
 * @author lewkay
 * @version 2017-03-07
 */
@Service
@Transactional(readOnly = true)
public class WaringCycleService extends CrudService<WaringCycleDao, WaringCycle> {

	public WaringCycleService() {
	}

//	@WaringMessagePush(description = "描述信息,测试用的")
	public WaringCycle get(String id) {
		return super.get(id);
	}

//	@WaringMessagePush(description = "描述信息,测试用的")
	public List<WaringCycle> findList(WaringCycle waringCycle) {
		return super.findList(waringCycle);
	}

//	@WaringMessagePush(description = "描述信息,测试用的")
	@Override
	public Page<WaringCycle> findPage(Page<WaringCycle> page, WaringCycle waringCycle) {
		return super.findPage(page, waringCycle);
	}


//	@WaringMessagePush(description = "描述信息,测试用的")
	@Transactional(readOnly = false)
	public void save(WaringCycle waringCycle) {
		super.save(waringCycle);
	}


//	@WaringMessagePush(description = "描述信息,测试用的")
	@Transactional(readOnly = false)
	public void delete(WaringCycle waringCycle) {
		super.delete(waringCycle);
	}
	
}