package com.jctl.cloud.mina.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class OrdersQueueVO {
	
  	private long time;         //收到需要执行的任务的时间戳
	
	private String order;      //命令的指令
	
	private long index;         //命令的游标

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public long getIndex() {
		return index;
	}

	public void setIndex(long index) {
		this.index = index;
	}
	

	@Override
	public String toString() {
		return "OrdersQueueVO [time=" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS").format(new Date(time)) + ", order=" + order + ", index="
				+ index + "]";
	}

	

}
