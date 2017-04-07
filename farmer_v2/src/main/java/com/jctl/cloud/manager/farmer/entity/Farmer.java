/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.jctl.cloud.manager.farmer.entity;

import org.hibernate.validator.constraints.Length;
import com.jctl.cloud.modules.sys.entity.User;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jctl.cloud.common.persistence.DataEntity;

/**
 * 农场Entity
 * @author lewkay
 * @version 2017-02-25
 */
public class Farmer extends DataEntity<Farmer> {
	
	private static final long serialVersionUID = 1L;
	private String farmerNum;		// 农场编号
	private String name;		// 农场名称
	private String addr;		// 农场地址
	private Double area;         //农场面积
	private String plantVariety;//种植植物种类名称
	private User user;		// 所有人
	private String usedId;//管理人员编号
	private String usedName;//管理人员名称
	private String orderNum;		// 排序
	private String useFlag;		// 是否可用
	private Date addTime;		// 架设时间
	private String addUserId;		// 添加人
	private Date updateTime;		// 修改时间
	private String updateUserId;		// 修改人
	private Integer relayNumber;//中继数量
	private Integer farmlandNumber;//农田数量
	private String remarks;//备注

	public String getUsedId() {
		return usedId;
	}

	public void setUsedId(String usedId) {
		this.usedId = usedId;
	}

	public String getUsedName() {
		return usedName;
	}

	public void setUsedName(String usedName) {
		this.usedName = usedName;
	}

	public String getPlantVariety() {
		return plantVariety;
	}

	public void setPlantVariety(String plantVariety) {
		this.plantVariety = plantVariety;
	}

	@Override
	public String getRemarks() {
		return remarks;
	}

	@Override
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public Integer getRelayNumber() {
		return relayNumber;
	}

	public void setRelayNumber(Integer relayNumber) {
		this.relayNumber = relayNumber;
	}

	public Integer getFarmlandNumber() {
		return farmlandNumber;
	}

	public void setFarmlandNumber(Integer farmlandNumber) {
		this.farmlandNumber = farmlandNumber;
	}


	public Farmer() {
		super();
	}

	public Farmer(String id){
		super(id);
	}
	@Length(message="农场编号必须为字母数字下划线")
	public String getFarmerNum() {
		return farmerNum;
	}

	public void setFarmerNum(String farmerNum) {
		this.farmerNum = farmerNum;
	}
	
	@Length(min=0, max=64, message="农场名称长度必须介于 0 和 64 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getArea() {
		return area;
	}


	public void setArea(Double area) {
		this.area = area;
	}

	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	@Length(min=0, max=10, message="排序长度必须介于 0 和 10 之间")
	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	
	@Length(min=0, max=1, message="是否可用长度必须介于 0 和 1 之间")
	public String getUseFlag() {
		return useFlag;
	}

	public void setUseFlag(String useFlag) {
		this.useFlag = useFlag;
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