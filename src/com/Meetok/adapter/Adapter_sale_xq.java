package com.Meetok.adapter;

import java.util.List;

import com.Meetok.Entity.SaleEntity;
import com.Meetok.Entity.SaleXQEntity;
import com.Meetok.Entity.ShouyeEntity;
import com.Meetok.adapter.Adapter_home1.HolderView;
import com.imooc.tab03.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class Adapter_sale_xq extends BaseAdapter {
	
	private Context context;
	private List<SaleXQEntity> mlist;
	private LayoutInflater inflater;
	
	public Adapter_sale_xq(Context context, List<SaleXQEntity> list) {

		this.context = context;
		this.mlist = list;
		inflater = LayoutInflater.from(context);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mlist.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(final int position, View currentView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		 final HolderView holderView;
			if (currentView==null) {
				holderView=new HolderView();
				currentView=LayoutInflater.from(context).inflate(R.layout.adapter_sale_xiangqing, null);
				holderView.iv_Code=(TextView) currentView.findViewById(R.id.Code);
				holderView.iv_name = (TextView) currentView.findViewById(R.id.Name);
				holderView.iv_Quantity=(TextView) currentView.findViewById(R.id.Quantity);
				holderView.iv_Price = (TextView) currentView.findViewById(R.id.Price);
				holderView.iv_Rate = (TextView) currentView.findViewById(R.id.Rate);
				//holderView.iv_Quantity.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
				currentView.setTag(holderView);
			}else {
				holderView=(HolderView) currentView.getTag();
			}
			SaleXQEntity se=mlist.get(position);
			String str = se.name;
		//	String name = str.subSequence(1, str.length()).toString();
			//
			holderView.iv_Code.setText(se.Code);
			holderView.iv_name.setText(se.Name);
			holderView.iv_Quantity.setText(se.Quantity);
			holderView.iv_Price.setText(String.valueOf(se.Price));
			holderView.iv_Rate.setText(se.Rate+"%");
		return currentView;
	}
public class HolderView {
		
		private TextView iv_Code;
		private TextView  iv_name;
		private TextView iv_Quantity;
		private TextView  iv_Price;
		private TextView iv_Rate;
		
	}
}
