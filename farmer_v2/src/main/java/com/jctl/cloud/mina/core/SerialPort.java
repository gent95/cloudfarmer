package com.jctl.cloud.mina.core;

import com.jctl.cloud.mina.entity.DataResultSet;
import com.jctl.cloud.mina.entity.GatewayResultSet;
import com.jctl.cloud.mina.entity.OpenCloseVO;
import com.jctl.cloud.mina.entity.ResultSet;
import com.jctl.cloud.mina.server.MinaLongConnServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;


public class SerialPort {
	
	private String bufferLocal = "";
	protected OutputStream mOutputStream = new OutputStream() {
		
		@Override
		public void write(int b) throws IOException {
			// TODO Auto-generated method stub
			
		}
	};
	private InputStream mInputStream ;
	
	
	
    public ResultSet comReadData(final byte[] buffer, final int size)
    {
    	String fullStr = new String(buffer);
    	ResultSet resultSet = new ResultSet();
    	if(fullStr != null)
    		//命令
    		resultSet.setOrders(fullStr);
    	//解析Gateway命令所返回来的信息
    	if(fullStr.substring(0, 8).equals("Gateway:") && !fullStr.equals("Gateway:")){

    		System.out.println("解析Gateway的结果信息！");
    		String bufStr = fullStr.substring(8);
    		String[] threePartResult = bufStr.split(";");
    		
    		GatewayResultSet gatewayResultSet = new GatewayResultSet();
    		if(!(threePartResult.length <= 1)){
    			//中继Mac地址
    			gatewayResultSet.setServerMac(threePartResult[0].substring(2));
    			//中继电量
        		gatewayResultSet.setPowerSupply(threePartResult[1].substring(2));
        		List<String> clientMacList = new ArrayList<String>();

        		String clientMacStr = threePartResult[2].substring(2);
        		if(clientMacStr != null && !"".equals(clientMacStr)){
        			String[] clientMacArray = clientMacStr.split(",");
            		Collections.addAll(clientMacList,clientMacArray);
            		//中继下子节点Mac
            		gatewayResultSet.setClientMacList(clientMacList);
        		}else{
        			gatewayResultSet.setClientMacList(null);
        		}
        		//添加中继数据
        		resultSet.setGatewayResultSet(gatewayResultSet);
    		}
    	}
    	if(fullStr.length() < "Client:AT+TM=".length()){
    		return resultSet;
    	}
    	if(fullStr.substring(0, 13).equals("Client:AT+TM=")){
        	String bufStr2 = fullStr.substring(7);
            System.out.println("rcv:" + bufStr2);
            //传感器回应
            System.out.println("解析Client:AT+TM=发送的指令信息！");
            int serverMacLen = Integer.parseInt(bufStr2.substring(16,16+2),16)*2;
            String serverMac = bufStr2.substring(18,18+serverMacLen);
            resultSet.setServerMac(serverMac);
		}
    	if(fullStr.substring(0, 13).equals("Client:AT+RM=")){
    		//解析Client發送到Server的數據
    		System.out.println("解析Client:AT+RM=的结果信息！");
    		String bufStr = fullStr.substring(7);
            System.out.println("rcv:" + bufStr);
            if (bufStr.substring(0,6).equals("AT+RM=") && bufStr.substring(8,8+2).equals("AA")) {
                if (bufStr.substring(12,12+4).equals("808F")) {
                    //查看版本号


                } else if (bufStr.substring(12,12+4).equals("8081")) {
                	int responseOrderLength = fullStr.length();
                	System.out.println("=========================="+fullStr.length());
                	//解析开关命令的执行情况
                	if(responseOrderLength == 57){
                		 OpenCloseVO ocVO = new OpenCloseVO();
                		 ocVO.setTime(new Date());
                		 int clientMacLen = Integer.parseInt(bufStr.substring(16,16+2),16)*2;
                         String clientMac = bufStr.substring(18,18+clientMacLen);
                         ocVO.setNodeMac(clientMac);
                         System.out.println("nodeMac-->"+clientMac);

                         int serverMacLen = Integer.parseInt(bufStr.substring(18+clientMacLen,18+clientMacLen+2),16)*2;
                         String serverMac = bufStr.substring(18+clientMacLen+2,18+clientMacLen+2+serverMacLen);
                         ocVO.setRelayMac(serverMac);
                         System.out.println("relayMac-->"+serverMac);
                         
                         int dTypeStart = 18+clientMacLen+2+serverMacLen+2;//30
                         String openOrClose = bufStr.substring(dTypeStart,dTypeStart+4);
                         ocVO.setOpenOrClose(openOrClose);
                         System.out.println(ocVO.toString());
                         MinaLongConnServer.openCloseNodeResponseList.add(ocVO);
                         resultSet.setOpenCloseVo(ocVO);
                		
                	}else if(responseOrderLength > 57){
                		//解析传感器数据
                    	DataResultSet dataResultSet = new DataResultSet(); 
                        System.out.println("sensor resp 8081 entry");
                        int clientMacLen = Integer.parseInt(bufStr.substring(16,16+2),16)*2;
                        String clientMac = bufStr.substring(18,18+clientMacLen);
                        dataResultSet.setClientMac(clientMac);
                        System.out.println("clientMac-->"+clientMac);

                        int serverMacLen = Integer.parseInt(bufStr.substring(18+clientMacLen,18+clientMacLen+2),16)*2;
                        String serverMac = bufStr.substring(18+clientMacLen+2,18+clientMacLen+2+serverMacLen);
                        dataResultSet.setServerMac(serverMac);
                        System.out.println("serverMac-->"+serverMac);

                        int dTypeStart = 18+clientMacLen+2+serverMacLen;//30
                        int deviceType = Integer.parseInt(bufStr.substring(dTypeStart,dTypeStart+4),16);
                        dataResultSet.setDeviceType(bufStr.substring(dTypeStart,dTypeStart+4));
                        System.out.println("deviceType:"+deviceType);

                        int stateStart = dTypeStart + 4;
                        int clientState;
                       
                        //大气温度传感器（字段） 2 bytes
                        dataResultSet.setAirTemperature(ConsoleUtil.getFloatFrom2byte(bufStr.substring(stateStart,stateStart+4)));
                        stateStart = stateStart+4;
                        //大气湿度传感器（字段） 2 Bytes
                        dataResultSet.setAirHumidity(ConsoleUtil.getFloatFrom2byte(bufStr.substring(stateStart,stateStart+4)));
                        stateStart = stateStart+4;
                        //土壤温度传感器1（字段）	2 Bytes
                        dataResultSet.setSoilTemperature1(ConsoleUtil.getFloatFrom2byte(bufStr.substring(stateStart,stateStart+4)));
                        stateStart = stateStart+4;
                        //土壤水分传感器1（字段）	2 Bytes
                        dataResultSet.setSoilHumidity1(ConsoleUtil.getFloatFrom2byte(bufStr.substring(stateStart,stateStart+4)));
                        stateStart = stateStart+4;
                        //土壤温度传感器2（字段）	2 Bytes
                        dataResultSet.setSoilTemperature2(ConsoleUtil.getFloatFrom2byte(bufStr.substring(stateStart,stateStart+4)));
                        stateStart = stateStart+4;
                        //土壤水分传感器2（字段）	2 Bytes
                        dataResultSet.setSoilHumidity2(ConsoleUtil.getFloatFrom2byte(bufStr.substring(stateStart,stateStart+4)));
                        stateStart = stateStart+4;
                        //土壤温度传感器3（字段）	2 Bytes
                        dataResultSet.setSoilTemperature3(ConsoleUtil.getFloatFrom2byte(bufStr.substring(stateStart,stateStart+4)));
                        stateStart = stateStart+4;
                        //土壤水分传感器3（字段）	2 Bytes
                        dataResultSet.setSoilHumidity3(ConsoleUtil.getFloatFrom2byte(bufStr.substring(stateStart,stateStart+4)));
                        stateStart = stateStart+4;
                        //CO2浓度传统器（字段）	2 Bytes
                        dataResultSet.setCo2(ConsoleUtil.getCo2From2byte(bufStr.substring(stateStart,stateStart+4)));
                        stateStart = stateStart+4;
                        //开关控制 1 Bytes
                        clientState = Integer.parseInt(bufStr.substring(stateStart,stateStart+2),16);
                        dataResultSet.setIsOpen(clientState);
                        stateStart = stateStart+2;
                        //电池电量（字段）1 Bytes
                        clientState = Integer.parseInt(bufStr.substring(stateStart,stateStart+2),16);
                        dataResultSet.setPowerSupply(clientState);
                        stateStart = stateStart+2;
                        //切换频点 1 Bytes
                        clientState = Integer.parseInt(bufStr.substring(stateStart,stateStart+2),16);
                        dataResultSet.setFrequencyPoint(clientState);
                        stateStart = stateStart+2;
                        //切换功率	1 Bytes
                        clientState = Integer.parseInt(bufStr.substring(stateStart,stateStart+2),16);
                        dataResultSet.setPower(clientState);
                        stateStart = stateStart+2;
//                        System.out.println(dataResultSet.toString());
                        resultSet.setDataResultSet(dataResultSet);
					 }
                   }
                }
    		}
			return resultSet;
//			28AA14000106FFFEDD667788040EDCBA061FFFDCBB
        }
    
    
    public double parsing16ToDouble(int value){
    	return 0.0;
    }

    
    //transmit string using com port, string should be as format "AT+TM..."
    public void comPortTxAction(String txStr)
    {
        int i;
        CharSequence t = txStr;
        char[] text = new char[t.length()];
        for (i=0; i<t.length(); i++) {
            text[i] = t.charAt(i);
        }
        System.out.println("SerialPort---"+txStr);
        try {
            mOutputStream.write(new String(text).getBytes());
            //mOutputStream.write('\n');
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public char[] comPortTxActionTest(String txStr)
    {
    	 int i;
         CharSequence t = txStr;
         char[] text = new char[t.length()];
         for (i=0; i<t.length(); i++) {
             text[i] = t.charAt(i);
         }
         System.out.println("SerialPort---"+txStr);
         System.out.println("....................Text:"+text.toString());
         return text;
    }


   

    /**
     * 当前的命令是否符合正确的格式
     * @param bufStr
     * @return
     */
    private boolean isCorret(String bufStr){
        try {
            if (isNumber(bufStr.substring(6,8))){
                int a = Integer.valueOf(bufStr.substring(6,8));
                if (isNumber(bufStr.substring(10,12))){
                    int b = Integer.valueOf(bufStr.substring(10,12));
                    if (a/b==2){
                        return true;
                    }
                }else {
                    return false;
                }
            }else {
                return false;
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 判断当前字符是否为字符串
     * @param str
     * @return
     */
    private boolean isNumber(String str){
        Pattern numberPattern = Pattern
                .compile("^[0-9]$");
        return numberPattern.matcher(str).matches();
    }
}