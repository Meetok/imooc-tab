package com.Meetok.adapter;

import java.util.ArrayList;
import java.util.List;

import com.Meetok.Entity.OrderEntity;
import com.Meetok.Entity.ShouyeEntity;
import com.Meetok.adapter.Adapter_home_all.HolderView;
import com.imooc.tab03.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class Adapter_search extends BaseAdapter {

	private Context context;
	private List<OrderEntity> mlist;
	
	
	public Adapter_search(Context context,List<OrderEntity> list){
		
		this.context=context;
		this.mlist=list;
	}
	public void onDateChange(List<OrderEntity>  list) {
		this.mlist = list;
		this.notifyDataSetChanged();
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
				currentView=LayoutInflater.from(context).inflate(R.layout.adapter_search, null);
				holderView.iv_ProductPic=(ImageView) currentView.findViewById(R.id.img_pic);
				holderView.iv_title = (TextView) currentView.findViewById(R.id.iv_grid_title);
				holderView.iv_DisPurchasePrice = (TextView) currentView.findViewById(R.id.iv_DisPurchasePrice);
				holderView.iv_RetailPrice = (TextView) currentView.findViewById(R.id.iv_RetailPrice);
				holderView.iv_stock =(TextView) currentView.findViewById(R.id.iv_stock);
				holderView.iv_ProductPic.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
				currentView.setTag(holderView);
			}else {
				holderView=(HolderView) currentView.getTag();
			}
			OrderEntity search = mlist.get(position);
			String title = search.Title;
			float DisPurchasePrice = search.DisPurchasePrice;
			float RetailPrice = search.RetailPrice;
			int stock = search.Stock;
			String p1 = String.valueOf(DisPurchasePrice);
			String p2 = String.valueOf(RetailPrice);
			String st = String.valueOf(stock);
			holderView.iv_title.setText(title);
			holderView.iv_DisPurchasePrice.setText(p1);
			holderView.iv_RetailPrice.setText(p2);
			holderView.iv_stock.setText(st);
			
			//displayImage(search.typepic,holderView.iv_ProductPic);
			//显示图片的配置  
	        DisplayImageOptions options = new DisplayImageOptions.Builder()  
	                //.showImageOnLoading(R.drawable.app_logo)  
	                //.showImageOnFail(R.drawable.icon_error)  
	                .cacheInMemory(false)  
	                .cacheOnDisk(false)  
	                .bitmapConfig(Bitmap.Config.RGB_565)  
	                .imageScaleType(ImageScaleType.EXACTLY)//
	                .build();  
	          
	        ImageLoader.getInstance().displayImage(search.ProductPic, holderView.iv_ProductPic, options); 
			
		return currentView;
	}
	



public class HolderView {
		
		private ImageView iv_ProductPic;
		private TextView  iv_title;
		private TextView iv_DisPurchasePrice;
		private TextView iv_RetailPrice;
		private TextView iv_stock;
		
		
	}
}
