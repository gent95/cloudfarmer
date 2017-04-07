/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.jctl.cloud.manager.timingstrategy.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jctl.cloud.common.persistence.DataEntity;

/**
 * 节点定时任务Entity
 * @author gent
 * @version 2017-03-02
 */
public class NodeCollectionCycle extends DataEntity<NodeCollectionCycle> {
	
	private static final long serialVersionUID = 1L;
	private String nodeId;		// 节点id
	private String cycleTime;		// 循环策略
	private String cycleOff;		// 关闭策略
	private String cycleOn;		// 开启策略
	private Date addTime;		// 添加时间
	private Long addUserId;		// 添加人
	private Date updateTime;		// 修改时间
	private Long updateUserId;		// 修改人
	
	public NodeCollectionCycle() {
		super();
	}

	public NodeCollectionCycle(String id){
		super(id);
	}

	@Length(min=0, max=255, message="节点id长度必须介于 0 和 255 之间")
	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}
	
	@Length(min=0, max=255, message="循环策略长度必须介于 0 和 255 之间")
	public String getCycleTime() {
		return cycleTime;
	}

	public void setCycleTime(String cycleTime) {
		this.cycleTime = cycleTime;
	}
	
	@Length(min=0, max=255, message="关闭策略长度必须介于 0 和 255 之间")
	public String getCycleOff() {
		return cycleOff;
	}

	public void setCycleOff(String cycleOff) {
		this.cycleOff = cycleOff;
	}
	
	@Length(min=0, max=255, message="开启策略长度必须介于 0 和 255 之间")
	public String getCycleOn() {
		return cycleOn;
	}

	public void setCycleOn(String cycleOn) {
		this.cycleOn = cycleOn;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	
	public Long getAddUserId() {
		return addUserId;
	}

	public void setAddUserId(Long addUserId) {
		this.addUserId = addUserId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	public Long getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(Long updateUserId) {
		this.updateUserId = updateUserId;
	}
	
}