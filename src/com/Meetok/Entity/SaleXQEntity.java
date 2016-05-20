package com.Meetok.Entity;

import java.io.Serializable;

import org.json.JSONArray;
import org.json.JSONObject;

public class SaleXQEntity implements Serializable {
	private static final long serialVersionUID = 1L;


	public String code;
	/**
	 * 线下店铺]线下店铺
	 */
	public String shopname;
	/**
	 * T是线下店铺,F不是 该字段是用来判断商品信息是否可修改在修改订单接口中需要用到
	 */
	public String isxxdp;
	/**
	 * 销售订单ID
	 */
	public String ID;
	public String Tid;
	/**
	 * 创建时间
	 */
	public String created;
	/**
	 * 
	 付款时间:
	 */
	public String paytime;
	/**
	 * 买家昵称
	 */
	public String buyernick;
	/**
	 * 物流公司
	 */
	public String logisticsname;
	/**
	 * 物流单号
	 */
	public String trackingnumber;
	/**
	 * 姓名
	 */
	public String receivername;
	/**
	 * 手机号
	 */
	public String receivermobile;
	/**
	 * 省份
	 */
	public String receiverstate;
	/**
	 * 市
	 */
	public String receivercity;
	/**
	 * 地区
	 */
	public String receiverdistrict;
	/**
	 * 详细地址
	 */
	public String receiveraddress;
	/**
	 * 身份证
	 */
	public String idnum;
	/**
	 * 姓名
	 */
	public String name;
	/**
	 * 邮费
	 */
	public String postfee;
	/**
	 * 销售订单总价（实付总额）
	 */
	public double payment;
	/**
	 * 实付税额:	
	 */
	public double rate;
	/**
	 * 商品code	
	 */
	public String Code;
	/**
	 * 商品名称	
	 */
	public String Name;
	/**
	 * 商品模型
	 */
	public String Model;
	/**
	 * 尺寸规格	
	 */
	public String Spec;
	/**
	 * 单位：件
	 */
	public String Unit;
	/**
	 * 商品单价
	 */
	public double Price;
	/**
	 * 商品数量
	 */
	public String Quantity;
	/**
	 * 商品税率
	 */
	public String Rate;
	
	public String actpage;
	public String count;
	public String Status;
	public String TypeTreeId;
	public String ProductName;
	public String Title;
	/**
	 * 分销商库存
	 */
	public int StockQuantity;
	public double DisPurchasePrice;
	public String ProductPic;


	public JSONObject jsonarray1;
}
