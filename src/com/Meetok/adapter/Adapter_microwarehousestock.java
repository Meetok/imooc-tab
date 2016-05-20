package com.Meetok.adapter;

import java.math.BigDecimal;
import java.util.List;

import com.Meetok.Entity.SaleEntity;
import com.imooc.tab03.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class Adapter_microwarehousestock extends BaseAdapter{
	private Context context;
	private List<SaleEntity> mlist;
	
	public Adapter_microwarehousestock(Context context,List<SaleEntity> list){
		this.context=context;
		this.mlist=list;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mlist.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return mlist == null ? null : mlist.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View currentView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		final HolderView holderView;
		if (currentView == null) {
			holderView = new HolderView();
			currentView = LayoutInflater.from(context).inflate(
					R.layout.adapter_child_microstock, null);
			holderView.productCode = (TextView) currentView
					.findViewById(R.id.micro_stock_productcode_tv);
			holderView.productTitle = (TextView) currentView
					.findViewById(R.id.micro_stock_producttitle_tv);
			holderView.productStockQuantity = (TextView) currentView
					.findViewById(R.id.micro_stock_stockquantity_tv);
			holderView.productUseQuantity = (TextView) currentView
					.findViewById(R.id.micro_stock_usequantity_tv);
			currentView.setTag(holderView);
		} else {
			holderView = (HolderView) currentView.getTag();
		}
		SaleEntity data1 = mlist.get(position);

		String title = data1.Title;
		String code = data1.Code;
		int stockQuantity = data1.StockQuantity;
		String useQuantity = data1.UseQuantity;
		
		holderView.productCode.setText(code);
		holderView.productTitle.setText(title);
		holderView.productStockQuantity.setText(String.valueOf(stockQuantity));
		holderView.productUseQuantity.setText(useQuantity);

		return currentView;
	}

	public class HolderView {

		private TextView productCode;
		private TextView productTitle;
		private TextView productUseQuantity;
		private TextView productStockQuantity;

	}
}
