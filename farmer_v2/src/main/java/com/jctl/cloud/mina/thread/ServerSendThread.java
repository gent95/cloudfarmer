package com.jctl.cloud.mina.thread;

import com.jctl.cloud.mina.core.SerialPort;
import com.jctl.cloud.mina.entity.ClientSessions;
import com.jctl.cloud.mina.entity.OrdersQueueVO;
import com.jctl.cloud.mina.entity.ResultSet;
import com.jctl.cloud.mina.server.MinaLongConnServer;
import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ServerSendThread extends Thread{
	
	@Override
	public void run() {
		//先去除掉两次命令时间差小于1秒的数据
		for(OrdersQueueVO ordersQueueVO : MinaLongConnServer.ordersQueueVOList) {
			OrdersQueueVO oq = MinaLongConnServer.ordersLogMap.get(ordersQueueVO.getOrder().trim());
			
			if( oq != null ){	//如果原来发送过这个命令 执行以下判断
				long oqTime = oq.getTime();
				long nowTime = ordersQueueVO.getTime();
				if(oq.getIndex() != ordersQueueVO.getIndex() && Math.abs( oqTime - nowTime ) < 1000){ //如果两次命令的时间差小于1秒 直接舍弃掉
					 MinaLongConnServer.ordersQueueVOList.remove(ordersQueueVO);
				}
			}
			
		}
		//去除List之中重复的数据
		Map<Long,OrdersQueueVO> tempMap = new HashMap<Long, OrdersQueueVO>();
		for(OrdersQueueVO ordersQueueVO : MinaLongConnServer.ordersQueueVOList){
			OrdersQueueVO vo = tempMap.get(ordersQueueVO.getIndex());
			if(vo == null ){
				tempMap.put(ordersQueueVO.getIndex(), ordersQueueVO);
			}else {
				MinaLongConnServer.ordersQueueVOList.remove(ordersQueueVO);
			}
			
		}
		
		
		//如果List之中所有的数据都被去除了，直接返回
		if(MinaLongConnServer.ordersQueueVOList.size() <= 0){
			return;
		}
		
		for(OrdersQueueVO ordersQueueVO : MinaLongConnServer.ordersQueueVOList){
			//循环执行列表
			if(ordersQueueVO.getOrder().trim().equals("Gateway:")){
				for(ClientSessions cs :  MinaLongConnServer.sessionList){
					//只针对于目标的节点发送命令
					cs.getSession().write(ordersQueueVO.getOrder().trim());
					System.out.println("*****************************获取中继信息的命令已经发送"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS").format(new Date()) +"-->"+ordersQueueVO.getOrder());
				}
			}else {
				for(ClientSessions cs :  MinaLongConnServer.sessionList){
					if(!ordersQueueVO.getOrder().trim().equals("Gateway:")){
						//重新解析
						String str = ordersQueueVO.getOrder().trim();
						SerialPort sp = new SerialPort();
						byte[] strByte = str.getBytes();
						ResultSet rs = sp.comReadData(strByte, strByte.length);
						//只针对于目标的节点发送命令
						if(StringUtils.isNotBlank(cs.getServerMac())){
							if(cs.getServerMac().equals(rs.getServerMac())){
								System.out.println("ssrss"+rs.getOrders().trim());
								cs.getSession().write(rs.getOrders().trim());
								System.out.println("*****************************已经发送命令至中继"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS").format(new Date())+":"+rs.getServerMac()+"-->"+rs.getOrders());
							}
						}
					}
					System.out.println("################################MinaLongConnServer.sessionList.size()-->"+MinaLongConnServer.sessionList.size());
					
				}
		   }
		   //执行完成之后，更新日志Map
			String key = ordersQueueVO.getOrder().trim();
			MinaLongConnServer.ordersLogMap.remove(key);
			MinaLongConnServer.ordersLogMap.put(key, ordersQueueVO);
		   //移除掉当前时间前的指令
		   if(ordersQueueVO.getTime()<= System.currentTimeMillis()){
			   MinaLongConnServer.ordersQueueVOList.remove(ordersQueueVO);
		   }
		   
		   if(MinaLongConnServer.ordersQueueVOList.size() > 0){
			   try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
		   }
		}
		
		System.out.println("$$$$$$$$$$$$$$$$$$$$$$-After-$$$$$$$$$$$$$$$$$$$$$$$$");
		for(OrdersQueueVO ordersQueueVO : MinaLongConnServer.ordersQueueVOList) {
			System.out.println(ordersQueueVO.toString());
		}

	}

}
