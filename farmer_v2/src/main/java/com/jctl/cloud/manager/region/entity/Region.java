/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.jctl.cloud.manager.region.entity;

import org.hibernate.validator.constraints.Length;

import com.jctl.cloud.common.persistence.DataEntity;

/**
 * 地区Entity
 * @author Lewkay
 * @version 2017-03-17
 */
public class Region extends DataEntity<Region> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 省市区名称
	private String parentid;		// 上级ID
	private String shortname;		// 简称
	private String leveltype;		// 级别:0,中国；1，省分；2，市；3，区、县
	private String citycode;		// 城市代码
	private String zipcode;		// 邮编
	private String lng;		// 经度
	private String lat;		// 纬度
	private String pinyin;		// 拼音
	private String status;		// status
	
	public Region() {
		super();
	}

	public Region(String id){
		super(id);
	}

	@Length(min=0, max=40, message="省市区名称长度必须介于 0 和 40 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=7, message="上级ID长度必须介于 0 和 7 之间")
	public String getParentid() {
		return parentid;
	}

	public void setParentid(String parentid) {
		this.parentid = parentid;
	}
	
	@Length(min=0, max=40, message="简称长度必须介于 0 和 40 之间")
	public String getShortname() {
		return shortname;
	}

	public void setShortname(String shortname) {
		this.shortname = shortname;
	}
	
	@Length(min=0, max=2, message="级别:0,中国；1，省分；2，市；3，区、县长度必须介于 0 和 2 之间")
	public String getLeveltype() {
		return leveltype;
	}

	public void setLeveltype(String leveltype) {
		this.leveltype = leveltype;
	}
	
	@Length(min=0, max=7, message="城市代码长度必须介于 0 和 7 之间")
	public String getCitycode() {
		return citycode;
	}

	public void setCitycode(String citycode) {
		this.citycode = citycode;
	}
	
	@Length(min=0, max=7, message="邮编长度必须介于 0 和 7 之间")
	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	
	@Length(min=0, max=20, message="经度长度必须介于 0 和 20 之间")
	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}
	
	@Length(min=0, max=20, message="纬度长度必须介于 0 和 20 之间")
	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}
	
	@Length(min=0, max=40, message="拼音长度必须介于 0 和 40 之间")
	public String getPinyin() {
		return pinyin;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}