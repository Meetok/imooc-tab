package com.Meetok.Entity;

import java.io.Serializable;

public class DingDanEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String typepic;

	public int Num;

	public String GUID;
	/**
	 * 商品图片
	 */
	public String ProductPic;
	/**
	 * 商品名字
	 */
	public String Title;
	public int Code;
	/**
	 *  采购价
	 */
	public float DisPurchasePrice;
	/**
	 * 仓租
	 */
	public float StorageCost;
	/** 
	 * 总价
	 */
	public String AllPrice;
/**
 *  活动开始，结束时间
 */
	public String GroupBeginTime;
	public String GroupEndTime;
	/**
	 * 活动价格
	 */
	public String GroupPrice;

	/**
	 * 红包名称
	 */
	public String Name;
	/**
	 红包编号
	 */
	public int ID;
	/**
	 * 供应商id
	 */
	public int DISID;
	public int RedPaperID;
	/**
	 *最小金额
	 */
	public String MinMoney;
	/**
	 * 优惠金额
	 */
	public String Discount;
	public int Status;
	public String BeginTime;
	public String EndTime;
}
