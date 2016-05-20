package com.Meetok.adapter;

import java.util.List;

import com.Meetok.Activity.Sale_WeiCang;
import com.Meetok.Entity.SaleXQEntity;
import com.Meetok.Entity.ShouyeEntity;
import com.Meetok.adapter.Adapter_home_all.HolderView;
import com.imooc.tab03.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Adapter_sale_grid extends BaseAdapter {

	private Context context;
	private List<SaleXQEntity> mlist;
	private boolean flag = true;
	
	public Adapter_sale_grid(Context context,List<SaleXQEntity> list){
		
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
				currentView=LayoutInflater.from(context).inflate(R.layout.adapter_sale_weicang, null);
				holderView.iv_ProductPic=(ImageView) currentView.findViewById(R.id.iv_adapter_grid_ProductPic);
				holderView.iv_title = (TextView) currentView.findViewById(R.id.iv_adapter_grid_title);
				holderView.iv_price = (TextView) currentView.findViewById(R.id.iv_adapter_jiage);
				holderView.iv_kucun = (TextView) currentView.findViewById(R.id.iv_adapter_kucuen);
				holderView.iv_liang = (LinearLayout) currentView.findViewById(R.id.liang);
				holderView.iv_ProductPic.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
				currentView.setTag(holderView);
			}else {
				holderView=(HolderView) currentView.getTag();
			}
			SaleXQEntity da=mlist.get(position);
			//String title = data1.Title;
			//String price = se.DisPurchasePrice;
			holderView.iv_liang.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(flag){
						holderView.iv_liang.setBackgroundColor(Color.parseColor("#ffff00"));
						Sale_WeiCang.getsguids(position,flag);
						flag = false;
					}else{
						holderView.iv_liang.setBackgroundColor(Color.parseColor("#ffffff"));
						Sale_WeiCang.getsguids(position,flag);
						flag = true;
					}
				}
			});
			holderView.iv_title.setText(da.Title);
			holderView.iv_price.setText(String.valueOf(da.DisPurchasePrice));
			holderView.iv_kucun.setText(String.valueOf(da.StockQuantity));
			//displayImage(data1.ProductPic,holderView.iv_ProductPic);
			 //显示图片的配置  
	        DisplayImageOptions options = new DisplayImageOptions.Builder()  
	               // .showImageOnLoading(R.drawable.app_logo)  
	               // .showImageOnFail(R.drawable.icon_error)  
	                .cacheInMemory(true)  
	                .cacheOnDisk(true)  
	                .bitmapConfig(Bitmap.Config.RGB_565)  
	                .build();  
	          
	        ImageLoader.getInstance().displayImage(da.ProductPic, holderView.iv_ProductPic, options); 
		return currentView;
	}
	
	public class HolderView {
		
		private ImageView iv_ProductPic;
		private TextView  iv_title;
		private TextView iv_price;
		private TextView iv_kucun;
		private LinearLayout iv_liang;
		
	}
}
