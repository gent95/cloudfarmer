package com.jctl.cloud.manager.marketnews.entity;

import java.io.Serializable;

/**
 * 市场动态实体
 *create by gent 2017年3月23日
 */
public class MktDyn implements Serializable{
	private int id;
	private String productName;
	private String price;
	private String marketName;
	private String releaseDate;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getMarketName() {
		return marketName;
	}
	public void setMarketName(String marketName) {
		this.marketName = marketName;
	}
	public String getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}

	@Override
	public String toString() {
		return "MktDyn{" +
				"id=" + id +
				", productName='" + productName + '\'' +
				", price='" + price + '\'' +
				", marketName='" + marketName + '\'' +
				", releaseDate='" + releaseDate + '\'' +
				'}';
	}
}
