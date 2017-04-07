/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.jctl.cloud.manager.relay.entity;

import com.jctl.cloud.manager.farmer.entity.Farmer;
import com.jctl.cloud.manager.farmerland.entity.Farmland;
import org.hibernate.validator.constraints.Length;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jctl.cloud.modules.sys.entity.User;

import com.jctl.cloud.common.persistence.DataEntity;

/**
 * 中继管理Entity
 * @author ll
 * @version 2017-02-25
 */
public class Relay extends DataEntity<Relay> {
	
	private static final long serialVersionUID = 1L;
	private String relayNum;		// 中继编号
	private Date bindingTime;		// 中继绑定时间
	private Long farmerId;		// 所属农场
	private String log;		// 经度
	private String lat;		// 纬度
	private String area;		// 地理位置
	private User user;		// 所有人
	private User used;//管理者
	private String ip;		// 中继ip
	private String powerSupply;		// 电量
	private String ports;		// 端口
	private String orderNum;		// 排序
	private String useFalg;		// 是否使用
	private Date addTime;		// 创建时间
	private String addUserId;		// 创建人
	private String updateUserId;		// 修改人
	private Date updateTime;		// 修改时间
	private Farmer farmer;

	private String farmerName;
	private Integer nodeNum;

	private List<Farmland> farmlands;

	public List<Farmland> getFarmlands() {
		return farmlands;
	}

	public void setFarmlands(List<Farmland> farmlands) {
		this.farmlands = farmlands;
	}

	public String getFarmerName() {
		return farmerName;
	}

	public void setFarmerName(String farmerName) {
		this.farmerName = farmerName;
	}

	public User getUsed() {
		return used;
	}

	public void setUsed(User used) {
		this.used = used;
	}

	public Long getFarmerId() {
		return farmerId;
	}

	public void setFarmerId(Long farmerId) {
		this.farmerId = farmerId;
	}

	public Integer getNodeNum() {
		return nodeNum;
	}

	public void setNodeNum(Integer nodeNum) {
		this.nodeNum = nodeNum;
	}

	public Relay() {
		super();
	}

	public Relay(String id){
		super(id);
	}

	@Length(min=0, max=64, message="中继编号长度必须介于 0 和 64 之间")
	public String getRelayNum() {
		return relayNum;
	}

	public void setRelayNum(String relayNum) {
		this.relayNum = relayNum;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getBindingTime() {
		return bindingTime;
	}

	public void setBindingTime(Date bindingTime) {
		this.bindingTime = bindingTime;
	}

	public Farmer getFarmer() {
		return farmer;
	}

	public void setFarmer(Farmer farmer) {
		this.farmer = farmer;
	}

	public String getLog() {
		return log;
	}

	public void setLog(String log) {
		this.log = log;
	}
	
	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}
	
	@Length(min=0, max=255, message="地理位置长度必须介于 0 和 255 之间")
	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	@Length(min=0, max=32, message="中继ip长度必须介于 0 和 32 之间")
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	
	@Length(min=0, max=12, message="电量长度必须介于 0 和 12 之间")
	public String getPowerSupply() {
		return powerSupply;
	}

	public void setPowerSupply(String powerSupply) {
		this.powerSupply = powerSupply;
	}
	
	@Length(min=0, max=32, message="端口长度必须介于 0 和 32 之间")
	public String getPorts() {
		return ports;
	}

	public void setPorts(String ports) {
		this.ports = ports;
	}
	
	@Length(min=0, max=11, message="排序长度必须介于 0 和 11 之间")
	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	
	@Length(min=0, max=1, message="是否使用长度必须介于 0 和 1 之间")
	public String getUseFalg() {
		return useFalg;
	}

	public void setUseFalg(String useFalg) {
		this.useFalg = useFalg;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	
	@Length(min=0, max=64, message="创建人长度必须介于 0 和 64 之间")
	public String getAddUserId() {
		return addUserId;
	}

	public void setAddUserId(String addUserId) {
		this.addUserId = addUserId;
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