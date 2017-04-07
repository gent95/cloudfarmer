/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.jctl.cloud.manager.message.entity;

import org.hibernate.validator.constraints.Length;

import com.jctl.cloud.common.persistence.DataEntity;

import java.util.Date;

/**
 * 报警信息Entity
 * @author kay
 * @version 2017-03-07
 */
public class WaringMessage extends DataEntity<WaringMessage> {
	
	private static final long serialVersionUID = 1L;
	private String nodeNum;		// 节点编号
	private String message;		// 报警信息



	public WaringMessage() {
		super();
	}

	public WaringMessage(String id){
		super(id);
	}

	@Length(min=0, max=64, message="节点编号长度必须介于 0 和 64 之间")
	public String getNodeNum() {
		return nodeNum;
	}

	public void setNodeNum(String nodeNum) {
		this.nodeNum = nodeNum;
	}
	
	@Length(min=0, max=255, message="报警信息长度必须介于 0 和 255 之间")
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}