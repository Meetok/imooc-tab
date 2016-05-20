package com.Meetok.Entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;


import android.content.Context;

public class PurchaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
/**
 * 页码
 */
	public int actpage;
	/**
	 * 	订单总数
	 */
	public int count;
	/**
	 * 查询的订单状态 0:未付款，1：已付款，2：已退款，99：已取消
	 */
	public int Status;
	/**
	 * 订单号
	 */
	public String Tid;
	
	public int ID;
	/**
	 * 下单时间
	 */
	public String Created;
	
	public double Payment;
	
	public int RedpaperID;
	public float Discount;
	/**
	 * 子商品图片
	 */
	public String ProductPic;
	/**
	 * 子商品名称
	 */
	public String Title;
	/**
	 * 子商品单价
	 */
	public double Price;
	/**
	 * z子仓储费
	 */
	public double StorageCost;
	/**
	 * 子商品数量
	 */
	public int Quantity;



	



	public List<PurchaseEntity> childlist;
	   private PurchaseEntity lists;



	public JSONArray jsonarray1;
/*
	 public List<PurchaseEntity> getLists() {
			return lists;
		}

		public void setLists(List<PurchaseEntity> lists) {
			this.lists = lists;
		}
*/
		public PurchaseEntity getLists() {
			return lists;
		}

		public void setLists(PurchaseEntity str_zi) {
			this.lists = str_zi;
		}
}
