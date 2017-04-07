/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.jctl.cloud.manager.nodedatadetails.entity;

import com.jctl.cloud.common.supcan.annotation.treelist.cols.SupCol;
import com.jctl.cloud.common.utils.excel.annotation.ExcelField;
import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jctl.cloud.common.persistence.DataEntity;

/**
 * 节点数据详情管理Entity
 * @author ll
 * @version 2017-02-27
 */
public class NodeDataDetails extends DataEntity<NodeDataDetails> {
	
	private static final long serialVersionUID = 1L;
	private String nodeMac;		// 节点id
	private Long nodeTypeId;		// 数据类型id
	private Double airTemperature;		// 大气温度
	private Double airHumidity;		// 大气湿度
	private Double soilTemperature1;		// 1号菌棒温度
	private Double soilHumidity1;		// 1号菌棒湿度
	private Double soilTemperature2;		// 2号菌棒温度
	private Double soilHumidity2;		// 2号菌棒湿度
	private Double soilTemperature3;		// 3号菌棒温度
	private Double soilHumidity3;		// 3号菌棒湿度
	private Double co2;		// 二氧化碳浓度
	private String openFlag;		// 开关状态
	private Integer power;		// 电量
	private Integer frequencyPoint;		// 切换频点
	private Integer powerSupply;		// 切换功率
	private Date addTime;		// 添加时间
	private String addUserId;		// 添加人
	private Date updateTime;		// 修改时间
	private String updateUserId;		// 修改人
	private Date startTime;//开始时间
	private Date endTime;//结束时间

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public NodeDataDetails() {
		super();
	}
	public NodeDataDetails(Date date,String nodeMac) {
		this.addTime=date;
		this.nodeMac =nodeMac;
	}

	public NodeDataDetails(String id){
		super(id);
	}
	@SupCol(isUnique="true", isHide="true")
	@ExcelField(title="编号", type=1, align=2, sort=1)
	public String getId(){return  id;}
	@Length(min=0, max=64, message="节点id长度必须介于 0 和 64 之间")
	public String getNodeMac() {
		return nodeMac;
	}

	public void setNodeMac(String nodeMac) {
		this.nodeMac = nodeMac;
	}
	
	public Long getNodeTypeId() {
		return nodeTypeId;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title = "采集时间",align=2, sort=70)
	public Date getAddTime() {
		return addTime;
	}

	public void setNodeTypeId(Long nodeTypeId) {
		this.nodeTypeId = nodeTypeId;
	}
	@ExcelField(title = "大气温度(℃)",align=2, sort=20)
	public Double getAirTemperature() {
		return airTemperature;
	}

	public void setAirTemperature(Double airTemperature) {
		this.airTemperature = airTemperature;
	}
	@ExcelField(title = "大气湿度(%RH)",align=2, sort=20)
	public Double getAirHumidity() {
		return airHumidity;
	}

	public void setAirHumidity(Double airHumidity) {
		this.airHumidity = airHumidity;
	}
	@ExcelField(title = "1号菌棒温度(℃)",align=2, sort=20)
	public Double getSoilTemperature1() {
		return soilTemperature1;
	}

	public void setSoilTemperature1(Double soilTemperature1) {
		this.soilTemperature1 = soilTemperature1;
	}
	@ExcelField(title = "1号菌棒湿度(%RH)",align=2, sort=20)
	public Double getSoilHumidity1() {
		return soilHumidity1;
	}

	public void setSoilHumidity1(Double soilHumidity1) {
		this.soilHumidity1 = soilHumidity1;
	}
	@ExcelField(title = "2号菌棒温度(℃)",align=2, sort=20)
	public Double getSoilTemperature2() {
		return soilTemperature2;
	}

	public void setSoilTemperature2(Double soilTemperature2) {
		this.soilTemperature2 = soilTemperature2;
	}
	@ExcelField(title = "2号菌棒湿度(%RH)",align=2, sort=20)
	public Double getSoilHumidity2() {
		return soilHumidity2;
	}

	public void setSoilHumidity2(Double soilHumidity2) {
		this.soilHumidity2 = soilHumidity2;
	}
	@ExcelField(title = "3号菌棒温度(℃)",align=2, sort=20)
	public Double getSoilTemperature3() {
		return soilTemperature3;
	}

	public void setSoilTemperature3(Double soilTemperature3) {
		this.soilTemperature3 = soilTemperature3;
	}
	@ExcelField(title = "3号菌棒湿度(%RH)",align=2, sort=20)
	public Double getSoilHumidity3() {
		return soilHumidity3;
	}

	public void setSoilHumidity3(Double soilHumidity3) {
		this.soilHumidity3 = soilHumidity3;
	}
	@ExcelField(title = "二氧化碳(ppm)",align=2, sort=20)
	public Double getCo2() {
		return co2;
	}

	public void setCo2(Double co2) {
		this.co2 = co2;
	}
	
	@Length(min=0, max=1, message="开关状态长度必须介于 0 和 1 之间")
	@ExcelField(title = "开关(1.开，0.关)",align=2, sort=20)
	public String getOpenFlag() {
		return openFlag;
	}

	public void setOpenFlag(String openFlag) {
		this.openFlag = openFlag;
	}
	@ExcelField(title = "电量(%)",align=2, sort=20)
	public Integer getPower() {
		return power;
	}

	public void setPower(Integer power) {
		this.power = power;
	}
	
	public Integer getFrequencyPoint() {
		return frequencyPoint;
	}

	public void setFrequencyPoint(Integer frequencyPoint) {
		this.frequencyPoint = frequencyPoint;
	}
	
	public Integer getPowerSupply() {
		return powerSupply;
	}

	public void setPowerSupply(Integer powerSupply) {
		this.powerSupply = powerSupply;
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