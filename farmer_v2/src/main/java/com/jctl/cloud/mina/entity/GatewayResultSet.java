package com.jctl.cloud.mina.entity;

import java.util.List;

public class GatewayResultSet {
	
	private String serverMac;
	private String serverIpAddress;
	private String powerSupply;
	private List<String> clientMacList;
	
	public String getServerMac() {
		return serverMac;
	}
	public void setServerMac(String serverMac) {
		this.serverMac = serverMac;
	}
	
	public String getPowerSupply() {
		return powerSupply;
	}
	public void setPowerSupply(String powerSupply) {
		this.powerSupply = powerSupply;
	}
	public List<String> getClientMacList() {
		return clientMacList;
	}
	public void setClientMacList(List<String> clientMacList) {
		this.clientMacList = clientMacList;
	}
	
	public String getServerIpAddress() {
		return serverIpAddress;
	}
	public void setServerIpAddress(String serverIpAddress) {
		this.serverIpAddress = serverIpAddress;
	}
	@Override
	public String toString() {
		return "中继信息 [\n中继Mac=" + serverMac + ",\n 中继电量="
				+ powerSupply + ",\n 中继下子节点Mac=" + clientMacList + "]";
	}
	
	
	
	

}
