package com.jctl.cloud.mina.entity;


import org.apache.mina.core.session.IoSession;

public class ClientSessions {
	
	private String serverMac;
	
	private String id;
	
	private IoSession session;

	public String getServerMac() {
		return serverMac;
	}

	public void setServerMac(String serverMac) {
		this.serverMac = serverMac;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public IoSession getSession() {
		return session;
	}

	public void setSession(IoSession session) {
		this.session = session;
	}
	
	

}
