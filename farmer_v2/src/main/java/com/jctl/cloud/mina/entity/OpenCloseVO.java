package com.jctl.cloud.mina.entity;

import java.util.Date;

public class OpenCloseVO {
	
	private String  relayMac;
	
	private String nodeMac;
	
	private String openOrClose;
	
	private Date time;;
	
	

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getRelayMac() {
		return relayMac;
	}

	public void setRelayMac(String relayMac) {
		this.relayMac = relayMac;
	}

	public String getNodeMac() {
		return nodeMac;
	}

	public void setNodeMac(String nodeMac) {
		this.nodeMac = nodeMac;
	}

	public String getOpenOrClose() {
		return openOrClose;
	}

	public void setOpenOrClose(String openOrClose) {
		this.openOrClose = openOrClose;
	}

	@Override
	public String toString() {
		return "OpenCloseVO [relayMac=" + relayMac + ", nodeMac=" + nodeMac
				+ ", openOrClose=" + openOrClose + "]";
	}
	
	
	
	

}
