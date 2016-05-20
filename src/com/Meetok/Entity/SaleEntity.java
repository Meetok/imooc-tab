package com.Meetok.Entity;

import java.io.Serializable;

import org.json.JSONArray;

public class SaleEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	
	/**
	 *搜索条件a
	 */
	public String searchparaA;
	/**
	 *搜索条件b
	 */
	public String searchProduct;
	/**
	 * 页码
	 */
	public int actpage;
	public int count;
	/**
	 *订单状态
	 */
	public String HGStatus;
	/**
	 *是否还有加载更多F：无、T：有
	 */
	public String Status;
	/**
	 * 销售订单ID
	 */
	public String ID;
	public String Tid;
	/**
	 * 时间
	 */
	public String Created;
	/**
	 * 收货人姓名
	 */
	public String ReceiverName;
	/**
	 * normal 正常，cancel作废
	 */
	public String HangupType;
	/**
	 * 待发送海关状态
	 */
	public String SalesOrderStatus;
	/**
	 * 子商品code
	 */
	public String Code;
	/**
	 * 子商品code
	 */
	public String Name;
	/**
	 * 子商品code
	 */
	public String Quantity;
	/**
	 * 子商品code
	 */
	public String usestock;
	/**
	 * 销售订单发送接口返回状态
	 */
	public String statuscode;
	public String msg;
/**
 * 销售订单item集合
 */
	public JSONArray itemarray1;
	/**
	 * 库存
	 */
	public String Stock;
	/**
	 * 商品名称
	 */
	public String Title;
	/**
	 * 实际库存
	 */
	public int StockQuantity;
	/**
	 * 已发库存
	 */
	public String SendQuantity;
	/**
	 * 未发库存
	 */
	public String NoSendQuantity;
	/**
	 * 可用库存
	 */
	public String UseQuantity;
	/**
	 * 
	 */
	public String Sort;
/**
 * 销售发货页显示的金额
 */
	public double TotalFee;
}
