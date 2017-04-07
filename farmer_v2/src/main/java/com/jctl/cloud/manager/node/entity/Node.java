/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.jctl.cloud.manager.node.entity;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Length;
import com.jctl.cloud.modules.sys.entity.User;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jctl.cloud.common.persistence.DataEntity;

/**
 * 节点管理Entity
 * @author ll
 * @version 2017-02-25
 */
public class Node extends DataEntity<Node> {
	
	private static final long serialVersionUID = 1L;
	private String nodeNum;		// 节点编号
	private String type;		// 设备类型(待确认 默认0)
	private User user;		// 所有人
	private String usedId;		// 使用人
	private String usedName; //使用人名称
	private String openFlag;		// 开关控制状态 1开 0关 默认关闭
	private String degree;		// 电机控制状态 (0-100表示开启程度，默认为0)
	private String exceptionFlag;		// 节点固有状态(0-异常；1-正常；2-失联；默认为1)
	private String warningFlag;		// 节点监测数据状态(0-报警；1-正常；默认为1)
	private String controlType;		// 节点控制方式(1-手动；2-定时；3-自动；默认为1)',
	private Long relayId;		// 所属中继(默认为-1，代表未被中继绑定)'
	private String relayName; //所属中继编号
	private String cycle;		// 数据采集周期(存储的时候单位为
	private String addUserId;		// 添加人
	private Date addTime;		// 添加时间
	private String updateUserId;		// 修改人
	private Date updateTime;		// 修改时间
	private String farmlandId;//所属农田（大棚）编号
	private String farmlandName;//所属农田（大棚）名称
	private String power;//电量
	private String hangingDevice;//下挂设备

	public String getHangingDevice() {
		return hangingDevice;
	}

	public void setHangingDevice(String hangingDevice) {
		this.hangingDevice = hangingDevice;
	}

	public String getPower() {
		return power;
	}

	public void setPower(String power) {
		this.power = power;
	}

	public String getRelayName() {
		return relayName;
	}

	public void setRelayName(String relayName) {
		this.relayName = relayName;
	}

	public String getFarmlandId() {
		return farmlandId;
	}

	public void setFarmlandId(String farmlandId) {
		this.farmlandId = farmlandId;
	}

	public String getFarmlandName() {
		return farmlandName;
	}

	public void setFarmlandName(String farmlandName) {
		this.farmlandName = farmlandName;
	}

	public String getUsedName() {
		return usedName;
	}

	public void setUsedName(String usedName) {
		this.usedName = usedName;
	}

	public Node() {
		super();
	}

	public Node(String id){
		super(id);
	}

	public Node(String nodeNum,String relayId,Date date){
		this.relayId = Long.valueOf(relayId);
		this.nodeNum =nodeNum;
		this.addTime=date;
	}

	@Length(min=0, max=10, message="节点编号长度必须介于 0 和 10 之间")
	public String getNodeNum() {
		return nodeNum;
	}

	public void setNodeNum(String nodeNum) {
		this.nodeNum = nodeNum;
	}
	
	@Length(min=0, max=1, message="设备类型(待确认 默认0)长度必须介于 0 和 1 之间")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Length(min=0, max=64, message="使用人长度必须介于 0 和 64 之间")
	public String getUsedId() {
		return usedId;
	}

	public void setUsedId(String usedId) {
		this.usedId = usedId;
	}
	
	@Length(min=0, max=1, message="开关控制状态 1开 0关 默认关闭长度必须介于 0 和 1 之间")
	public String getOpenFlag() {
		return openFlag;
	}

	public void setOpenFlag(String openFlag) {
		this.openFlag = openFlag;
	}
	
	@Length(min=0, max=10, message="电机控制状态 (0-100表示开启程度，默认为0)长度必须介于 0 和 10 之间")
	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}
	
	@Length(min=0, max=1, message="节点固有状态(0-异常；1-正常；2-失联；默认为1)长度必须介于 0 和 1 之间")
	public String getExceptionFlag() {
		return exceptionFlag;
	}

	public void setExceptionFlag(String exceptionFlag) {
		this.exceptionFlag = exceptionFlag;
	}
	
	@Length(min=0, max=1, message="节点监测数据状态(0-报警；1-正常；默认为1)长度必须介于 0 和 1 之间")
	public String getWarningFlag() {
		return warningFlag;
	}

	public void setWarningFlag(String warningFlag) {
		this.warningFlag = warningFlag;
	}
	
	@Length(min=0, max=1, message="节点控制方式(1-手动；2-定时；3-自动；默认为1)',长度必须介于 0 和 1 之间")
	public String getControlType() {
		return controlType;
	}

	public void setControlType(String controlType) {
		this.controlType = controlType;
	}

	public Long getRelayId() {
		return relayId;
	}

	public void setRelayId(Long relayId) {
		this.relayId = relayId;
	}

	@Length(min=0, max=10, message="数据采集周期(存储的时候单位为长度必须介于 0 和 10 之间")
	public String getCycle() {
		return cycle;
	}

	public void setCycle(String cycle) {
		this.cycle = cycle;
	}
	
	@Length(min=0, max=64, message="添加人长度必须介于 0 和 64 之间")
	public String getAddUserId() {
		return addUserId;
	}

	public void setAddUserId(String addUserId) {
		this.addUserId = addUserId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	
	@Length(min=0, max=64, message="修改人长度必须介于 0 和 64 之间")
	public String getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
}