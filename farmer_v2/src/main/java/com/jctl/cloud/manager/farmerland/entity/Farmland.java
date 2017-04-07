/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.jctl.cloud.manager.farmerland.entity;

import com.jctl.cloud.manager.farmer.entity.Farmer;
import com.jctl.cloud.manager.relay.entity.Relay;
import org.hibernate.validator.constraints.Length;
import com.jctl.cloud.modules.sys.entity.User;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jctl.cloud.common.persistence.DataEntity;

/**
 * 农田(大棚)Entity
 * @author kay
 * @version 2017-02-25
 */
public class Farmland extends DataEntity<Farmland> {
	
	private static final long serialVersionUID = 1L;
	private String farmlandNum;		// 农田编号
	private String alias;		// 别名
	private String landType;		// 农田类型
	private String plantVaritety; //种植植物种类名称
	private User user;		// 所有人农田的主权拥有者
	private String usedId;		// 使用人农田的当前使用者id
	private String usedName; //使用人农田的当前使用者名称
	private Date assigneTime;		// 分配时间
	private Farmer farmer;		// 所属农场
	private Double area;		//面积
	//private Long realyId;		// 所属中继
	private String addr; //位置
	private Relay relay;// 所属中继
	private String orderNum;		// 排序
	private String useFlag;		// 是否可用
	private String addUserId;		// 添加人
	private Date addTime;		// 添加时间
	private String updateUserId;		// 修改人
	private Date updateTime;		// 修改时间
	private Integer nodeNumber; //节点数量
	private Integer cameraNumber;//摄像头数量
	private String remarks;//备注

	public String getPlantVaritety() {
		return plantVaritety;
	}

	public void setPlantVaritety(String plantVaritety) {
		this.plantVaritety = plantVaritety;
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

	public Integer getNodeNumber() {
		return nodeNumber;
	}

	public void setNodeNumber(Integer nodeNumber) {
		this.nodeNumber = nodeNumber;
	}

	public Integer getCameraNumber() {
		return cameraNumber;
	}

	public void setCameraNumber(Integer cameraNumber) {
		this.cameraNumber = cameraNumber;
	}

	public Farmland() {
		super();
	}

	public Farmland(String id){
		super(id);
	}

	@Length(min=0, max=10, message="农田编号长度必须介于 0 和 10 之间")
	public String getFarmlandNum() {
		return farmlandNum;
	}

	public void setFarmlandNum(String farmlandNum) {
		this.farmlandNum = farmlandNum;
	}
	
	@Length(min=0, max=255, message="别名长度必须介于 0 和 255 之间")
	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}
	
	@Length(min=0, max=1, message="农田类型长度必须介于 0 和 1 之间")
	public String getLandType() {
		return landType;
	}

	public void setLandType(String landType) {
		this.landType = landType;
	}

	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	@Length(min=0, max=64, message="使用人农田的当前使用者长度必须介于 0 和 64 之间")
	public String getUsedId() {
		return usedId;
	}

	public void setUsedId(String usedId) {
		this.usedId = usedId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getAssigneTime() {
		return assigneTime;
	}

	public void setAssigneTime(Date assigneTime) {
		this.assigneTime = assigneTime;
	}

	public String getUsedName() {
		return usedName;
	}

	public void setUsedName(String usedName) {
		this.usedName = usedName;
	}

	public Farmer getFarmer() {
		return farmer;
	}

	public void setFarmer(Farmer farmer) {
		this.farmer = farmer;
	}

	public Double getArea() {
		return area;
	}

	public void setArea(Double area) {
		this.area = area;
	}

	public Relay getRelay() {
		return relay;
	}

	public void setRelay(Relay relay) {
		this.relay = relay;
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