package com.Meetok.Entity;

import java.io.Serializable;

public class OrderEntity implements Serializable {


	private static final long serialVersionUID = 1L;
	public String typepic;
	public int TypeId;
	/**
	 *商品种类名字
	 */
	public String name;	
	/**
	 * 库存
	 */
	public int Stock;
	/**
	 * 商品名字
	 */
	public String Title;
	/**
	 *商品图片
	 */
	public String ProductPic;
	/**
	 *详情多图
	 */
	
	public String Description;
	/**
	 * 采购价
	 */
	public float DisPurchasePrice;
	/**
	 *市场价
	 */
	public float RetailPrice;

	public String GUID;
	public int Code;
	public String SellNum;
	public String Weight;
	public float Rate;
	/**
	 *搜索总数
	 */
	public int count;
	public String data;
}
