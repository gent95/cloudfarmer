package com.jctl.cloud.mina.main;

import com.jctl.cloud.mina.core.ConsoleUtil;
import com.jctl.cloud.mina.core.SerialPort;
import com.jctl.cloud.mina.entity.ClientSessions;
import com.jctl.cloud.mina.server.MinaLongConnServer;


public class Main {
	
	
	public static void main(String[] args) {
		System.out.println(new String(sendCilentOrders("0C05942B", "363830303035")));
		System.out.println(new String(sendGatewayOrders()));
		System.out.println(new String(sendCilentOrdersOpen("0C05942B", "363830303035")));
		System.out.println(new String(sendCilentOrdersClose("0C05942B", "363830303035")));
	}


	//发送节点的指令
	public static char[] sendCilentOrders(String nodeMac,String relayMac){
		SerialPort serialPort = new SerialPort();
		try {

            ConsoleUtil.CtrlCmdName curCmd = ConsoleUtil.CtrlCmdName.CMD_REQUEST_SENSOR_INFO;
            String strGet = ConsoleUtil.getStringForComTx(curCmd,
            		nodeMac,
            		relayMac,
                    0x1FFF,
                    0x0000);
            return serialPort.comPortTxActionTest(strGet);
        } catch (Exception e) {
            e.printStackTrace();
        }
		return null;
	}

	public static char[] sendCilentOrdersBase(ConsoleUtil.CtrlCmdName cmd, String clientMac, String serverMac, int deviceType, int ctrlState){
		SerialPort serialPort = new SerialPort();
		try {
            String strGet = ConsoleUtil.getStringForComTx(cmd,clientMac,serverMac,deviceType,ctrlState);
            return serialPort.comPortTxActionTest(strGet);
        } catch (Exception e) {
            e.printStackTrace();
        }
		return null;
	}

	//发送获取获取节点数据的指令
	public static char[] sendCilentOrdersGetData(String nodeMac,String relayMac){
		ConsoleUtil.CtrlCmdName cmd = ConsoleUtil.CtrlCmdName.CMD_REQUEST_SENSOR_INFO;
		return sendCilentOrdersBase(cmd, nodeMac, relayMac, 0x1FFF, 0x0000);
	}

	//发送开启开关的指令
	public static char[] sendCilentOrdersOpen(String nodeMac,String relayMac){
		ConsoleUtil.CtrlCmdName cmd = ConsoleUtil.CtrlCmdName.CMD_CONTROL_SWITCH_ON_OFF;
		return sendCilentOrdersBase(cmd, nodeMac, relayMac, 0x0000, 0x0001);
	}

	//发送关闭开关的指令
	public static char[] sendCilentOrdersClose(String nodeMac,String relayMac){
		ConsoleUtil.CtrlCmdName cmd = ConsoleUtil.CtrlCmdName.CMD_CONTROL_SWITCH_ON_OFF;
		return sendCilentOrdersBase(cmd, nodeMac, relayMac, 0x0000, 0x0000);
	}
	
	//发送获取中继信息的命令
	public static char[] sendGatewayOrders(){
		SerialPort serialPort = new SerialPort();
		String strGet = ConsoleUtil.getStringForComTr();
		return serialPort.comPortTxActionTest(strGet);
	}
	
	//断开中继链接
	public static void disconnect(String relayMac){
		for(ClientSessions cs :  MinaLongConnServer.sessionList){
			if(relayMac.equals(cs.getServerMac())){
				cs.getSession().close(true);
				MinaLongConnServer.sessionList.remove(cs);
			}
		}
		
	}
}
