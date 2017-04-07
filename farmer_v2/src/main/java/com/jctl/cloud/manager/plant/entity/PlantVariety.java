/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.jctl.cloud.manager.plant.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jctl.cloud.common.persistence.DataEntity;

/**
 * 作物管理Entity
 * @author ll
 * @version 2017-02-25
 */
public class PlantVariety extends DataEntity<PlantVariety> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 名称
	private Date addTime;		// 添加时间
	private String addUserId;		// 添加人
	private Date updateTime;		// 修改时间
	private String updateUserId;		// 修改人
	
	public PlantVariety() {
		super();
	}

	public PlantVariety(String id){
		super(id);
	}

	@Length(min=0, max=64, message="名称长度必须介于 0 和 64 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	
	@Length(min=0, max=64, message="添加人长度必须介于 0 和 64 之间")
	public String getAddUserId() {
		return addUserId;
	}

	public void setAddUserId(String addUserId) {
		this.addUserId = addUserId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	@Length(min=0, max=64, message="修改人长度必须介于 0 和 64 之间")
	public String getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}
	
}