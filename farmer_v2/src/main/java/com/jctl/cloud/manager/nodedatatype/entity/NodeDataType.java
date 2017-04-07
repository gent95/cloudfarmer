/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.jctl.cloud.manager.nodedatatype.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jctl.cloud.common.persistence.DataEntity;

/**
 * 节点数据类型管理Entity
 * @author ll
 * @version 2017-02-25
 */
public class NodeDataType extends DataEntity<NodeDataType> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 名称
	private String useFlag;		// 是否使用
	private String delFalg;		// 是否删除
	private Date addTime;		// 添加时间
	private String addUserId;		// 添加人
	private Date updateTime;		// 修改时间
	private String updateUserId;		// 修改人
	
	public NodeDataType() {
		super();
	}

	public NodeDataType(String id){
		super(id);
	}

	@Length(min=0, max=64, message="名称长度必须介于 0 和 64 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=1, message="是否使用长度必须介于 0 和 1 之间")
	public String getUseFlag() {
		return useFlag;
	}

	public void setUseFlag(String useFlag) {
		this.useFlag = useFlag;
	}
	
	@Length(min=0, max=1, message="是否删除长度必须介于 0 和 1 之间")
	public String getDelFalg() {
		return delFalg;
	}

	public void setDelFalg(String delFalg) {
		this.delFalg = delFalg;
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