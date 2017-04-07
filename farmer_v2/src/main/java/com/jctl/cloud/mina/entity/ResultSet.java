package com.jctl.cloud.mina.entity;

/**
 * 返回结果类
 *
 */
public class ResultSet {
	
	private DataResultSet dataResultSet;
	
	private GatewayResultSet gatewayResultSet;
	
	private OpenCloseVO openCloseVo;
	
	private String serverMac;
	
	private String orders;
	
	
	
	public OpenCloseVO getOpenCloseVo() {
		return openCloseVo;
	}

	public void setOpenCloseVo(OpenCloseVO openCloseVo) {
		this.openCloseVo = openCloseVo;
	}

	public DataResultSet getDataResultSet() {
		return dataResultSet;
	}

	public void setDataResultSet(DataResultSet dataResultSet) {
		this.dataResultSet = dataResultSet;
	}

	public GatewayResultSet getGatewayResultSet() {
		return gatewayResultSet;
	}

	public void setGatewayResultSet(GatewayResultSet gatewayResultSet) {
		this.gatewayResultSet = gatewayResultSet;
	}

	public String getServerMac() {
		return serverMac;
	}

	public void setServerMac(String serverMac) {
		this.serverMac = serverMac;
	}

	public String getOrders() {
		return orders;
	}

	public void setOrders(String orders) {
		this.orders = orders;
	}

	@Override
	public String toString() {
		return "处理结果--> [\n节点数据=" + dataResultSet
				+ ",\n 中继数据=" + gatewayResultSet+",\n 开关控制反馈=" + openCloseVo + ",\n 中继Mac地址="
				+ serverMac + ",\n 命令=" + orders + "\n]";
	}
	
	

}
