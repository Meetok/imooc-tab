package com.Meetok.Entity;

import java.io.Serializable;

public class ShouyeEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 *种类图片
	 */
	public String typepic;
	public int id;
	/**
	 * 商品种类名字
	 */
	public String name;	
	/**
	 * 商品名字
	 */
	public String Title;
	/**
	 * 商品图片
	 */
	public String ProductPic;
	/**
	 *采购价
	 */
	public float DisPurchasePrice;
	/**
	 * 市场价
	 */
	public float RetailPrice;

	public String GUID;
	public int Code;
	/**
	 * 库存
	 */
	public int SellNum;

}
