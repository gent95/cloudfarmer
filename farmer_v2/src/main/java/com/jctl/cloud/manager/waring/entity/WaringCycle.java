/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.jctl.cloud.manager.waring.entity;

import org.hibernate.validator.constraints.Length;

import com.jctl.cloud.common.persistence.DataEntity;

/**
 * 异常采集Entity
 * @author lewkay
 * @version 2017-03-07
 */
public class WaringCycle extends DataEntity<WaringCycle> {
	
	private static final long serialVersionUID = 1L;
	private String nodeNum;		// 节点编号
	private String property;		// 属性
	private Double max;		// 最大值
	private Double min;		// 最小值
	private String cycle;		// 异常周期
	
	public WaringCycle() {
		super();
	}

	public WaringCycle(String id){
		super(id);
	}

	@Length(min=0, max=64, message="节点编号长度必须介于 0 和 64 之间")
	public String getNodeNum() {
		return nodeNum;
	}

	public void setNodeNum(String nodeNum) {
		this.nodeNum = nodeNum;
	}
	
	@Length(min=0, max=126, message="属性长度必须介于 0 和 126 之间")
	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}
	
	public Double getMax() {
		return max;
	}

	public void setMax(Double max) {
		this.max = max;
	}
	
	public Double getMin() {
		return min;
	}

	public void setMin(Double min) {
		this.min = min;
	}
	
	@Length(min=0, max=255, message="异常周期长度必须介于 0 和 255 之间")
	public String getCycle() {
		return cycle;
	}

	public void setCycle(String cycle) {
		this.cycle = cycle;
	}
	
}