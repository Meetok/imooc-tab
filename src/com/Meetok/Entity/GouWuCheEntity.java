package com.Meetok.Entity;

import java.io.Serializable;

import android.R.string;

public class GouWuCheEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	public int Num;
	public int num;
	public String GUID;

	public String guid;
	public String ProductPic;
	public String Title;
	/**
	 * /起批量
	 */
	public int WholeSaleNum;
	public int Code;
	public double DisPurchasePrice;
	public int Stock;
	
	public double AllPrice;
	public String TitGroupBeginTimele;
	public String GroupEndTime;
	public String GroupPrice;
	/*
	public int getNum() {
		return Num;
	}
	public void setNum(int Num) {
		this.Num = Num;
	}
	public int getnum() {
		return Num;
	}
	public void setnum(int num) {
		this.num = num;
	}
	
	public String getProductPic() {
		return ProductPic;
	}
	public void setProductPic(String ProductPic) {
		this.ProductPic = ProductPic;
	}
	public String gettitle() {
		return Title;
	}
	public void settitle(String Title) {
		this.Title = Title;
	}
	public String getguid() {
		return guid;
	}
	public void setguid(String guid) {
		this.guid = guid;
	}
	public String getGUID() {
		return GUID;
	}
	public void setGUID(String GUID) {
		this.GUID = GUID;
	}
	
	public int getWholeSaleNum() {
		return WholeSaleNum;
	}
	public void setWholeSaleNum(int WholeSaleNum) {
		this.WholeSaleNum = WholeSaleNum;
	}
	public float getAllPrice() {
		return AllPrice;
	}
	public void setAllPrice(int AllPrice) {
		this.AllPrice = AllPrice;
	}
	public int getStock() {
		return Stock;
	}
	public void setStock(int Stock) {
		this.Stock = Stock;
	}
	public int getCode() {
		return Code;
	}
	public void setCode(int Code) {
		this.Code = Code;
	}
	public float getDisPurchasePrice() {
		return DisPurchasePrice;
	}
	public void setDisPurchasePrice(int DisPurchasePrice) {
		this.DisPurchasePrice = DisPurchasePrice;
	}
	public String getTitGroupBeginTimele() {
		return TitGroupBeginTimele;
	}
	public void setTitGroupBeginTimele(String TitGroupBeginTimele) {
		this.TitGroupBeginTimele = TitGroupBeginTimele;
	}
	public String getGroupEndTime() {
		return GroupEndTime;
	}
	public void setGroupEndTime(String GroupEndTime) {
		this.GroupEndTime = GroupEndTime;
	}
	public String getGroupPrice() {
		return GroupPrice;
	}
	public void setGroupPrice(String GroupPrice) {
		this.GroupPrice = GroupPrice;
	}
	//...................................................
	private int id = 0;
	private String title = "";
	private String delete = "";//0是可以删 1是不可以删除
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDelete() {
		return delete;
	}
	public void setDelete(String delete) {
		this.delete = delete;
	}
	
	public GouWuCheEntity(int a,String b,String c){
		this.id = a;
		this.delete = b;
		this.title = c;
	}
*/
}
