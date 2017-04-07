package com.jctl.cloud.mina.entity;

/**
 * 节点返回结果集
 */
public class DataResultSet {
	
	private String serverMac;//服务端地址
	private String clientMac;//客户端地址
	private String deviceType;		//外设类型 （字段）
	
	private Double airTemperature;//大气温度（摄氏度）
	private Double airHumidity;//大气湿度（%RH）
	private Double soilTemperature1;//1号菌棒温度（摄氏度）
	private Double soilHumidity1;//1号菌棒湿度（%RH）
	private Double soilTemperature2;//2号菌棒温度（摄氏度）
	private Double soilHumidity2;//2号菌棒湿度（%RH）
	private Double soilTemperature3;//3号菌棒温度（摄氏度）
	private Double soilHumidity3;//3号菌棒湿度（%RH）
	private Double co2;//二氧化碳浓度（ppm）
	
	private Integer isOpen;		    //开关控制
	private Integer powerSupply;	//电池电量（字段）
	private Integer frequencyPoint; //切换频点
	private Integer power;			//切换功率
	public String getServerMac() {
		return serverMac;
	}
	public void setServerMac(String serverMac) {
		this.serverMac = serverMac;
	}
	public String getClientMac() {
		return clientMac;
	}
	public void setClientMac(String clientMac) {
		this.clientMac = clientMac;
	}
	public String getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	public Double getAirTemperature() {
		return airTemperature;
	}
	public void setAirTemperature(Double airTemperature) {
		this.airTemperature = airTemperature;
	}
	public Double getAirHumidity() {
		return airHumidity;
	}
	public void setAirHumidity(Double airHumidity) {
		this.airHumidity = airHumidity;
	}
	public Double getSoilTemperature1() {
		return soilTemperature1;
	}
	public void setSoilTemperature1(Double soilTemperature1) {
		this.soilTemperature1 = soilTemperature1;
	}
	public Double getSoilHumidity1() {
		return soilHumidity1;
	}
	public void setSoilHumidity1(Double soilHumidity1) {
		this.soilHumidity1 = soilHumidity1;
	}
	public Double getSoilTemperature2() {
		return soilTemperature2;
	}
	public void setSoilTemperature2(Double soilTemperature2) {
		this.soilTemperature2 = soilTemperature2;
	}
	public Double getSoilHumidity2() {
		return soilHumidity2;
	}
	public void setSoilHumidity2(Double soilHumidity2) {
		this.soilHumidity2 = soilHumidity2;
	}
	public Double getSoilTemperature3() {
		return soilTemperature3;
	}
	public void setSoilTemperature3(Double soilTemperature3) {
		this.soilTemperature3 = soilTemperature3;
	}
	public Double getSoilHumidity3() {
		return soilHumidity3;
	}
	public void setSoilHumidity3(Double soilHumidity3) {
		this.soilHumidity3 = soilHumidity3;
	}
	public Double getCo2() {
		return co2;
	}
	public void setCo2(Double co2) {
		this.co2 = co2;
	}
	public Integer getIsOpen() {
		return isOpen;
	}
	public void setIsOpen(Integer isOpen) {
		this.isOpen = isOpen;
	}
	public Integer getPowerSupply() {
		return powerSupply;
	}
	public void setPowerSupply(Integer powerSupply) {
		this.powerSupply = powerSupply;
	}
	public Integer getFrequencyPoint() {
		return frequencyPoint;
	}
	public void setFrequencyPoint(Integer frequencyPoint) {
		this.frequencyPoint = frequencyPoint;
	}
	public Integer getPower() {
		return power;
	}
	public void setPower(Integer power) {
		this.power = power;
	}
	@Override
	public String toString() {
		return "节点监测数据 [\n中继Mac=" + serverMac + ",\n 节点Mac="
				+ clientMac + ",\n 设备类型=" + deviceType
				+ ",\n 大气温度=" + airTemperature + ",\n 大气湿度="
				+ airHumidity + ",\n 土壤温度1=" + soilTemperature1
				+ ",\n 土壤湿度1=" + soilHumidity1 + ",\n 土壤温度2="
				+ soilTemperature2 + ",\n 土壤湿度2=" + soilHumidity2
				+ ",\n 土壤温度3=" + soilTemperature3 + ",\n 土壤湿度3="
				+ soilHumidity3 + ",\n co2浓度=" + co2 + ",\n 开关状态=" + isOpen
				+ ",\n 电源电量=" + powerSupply + ",\n 频点="
				+ frequencyPoint + ",\n 功率=" + power + "]";
	}
	
	
}
