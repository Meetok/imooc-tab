package com.Meetok.adapter;

import java.util.List;

import com.Meetok.Entity.PurchaseEntity;
import com.imooc.tab03.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class Adapter_Child_already extends BaseAdapter {

	//private List<PurchaseEntity> mlist;
	private Context context;
	private LayoutInflater inflater;
	private List<PurchaseEntity> mlist;

	public Adapter_Child_already(Context context, List<PurchaseEntity> list) {
		
		this.mlist = list;
		this.context = context;
		
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
	public View getView(final int position, View currentView, ViewGroup parent) {
		// TODO Auto-generated method stub
		 final HolderView holderView;
			if (currentView==null) {
				holderView=new HolderView();
				currentView=LayoutInflater.from(context).inflate(R.layout.adapter_child_pur, null);
				holderView.iv_ProductPic=(ImageView) currentView.findViewById(R.id.img_pic);
				holderView.iv_list_title = (TextView) currentView.findViewById(R.id.iv_list_title);
				holderView.iv_danjia = (TextView) currentView.findViewById(R.id.iv_danjia);
				holderView.iv_StorageCost = (TextView) currentView.findViewById(R.id.iv_StorageCost);
				
				holderView.iv_sum = (TextView) currentView.findViewById(R.id.iv_sum);
				holderView.iv_xiaoji = (TextView) currentView.findViewById(R.id.iv_xiaoji);
				
				holderView.iv_ProductPic.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
				currentView.setTag(holderView);
			}else {
				holderView=(HolderView) currentView.getTag();
			}
			PurchaseEntity pu=mlist.get(position);
			String ProductPic = pu.ProductPic;
			String title = pu.Title;
			double danjia = pu.Price;
			double sg = pu.StorageCost;
			int sum = pu.Quantity;
			String ssum = String.valueOf(sum);
			double xioaji = pu.Payment;
			 //显示图片的配置  
	        DisplayImageOptions options = new DisplayImageOptions.Builder()  
	                .showImageOnLoading(R.drawable.app_logo)  
	                .showImageOnFail(R.drawable.icon_error)  
	                .cacheInMemory(false)  
	                .cacheOnDisk(false)  
	                .bitmapConfig(Bitmap.Config.RGB_565)  
	                .build();  
	          
	        ImageLoader.getInstance().displayImage(pu.ProductPic, holderView.iv_ProductPic, options); 			
			holderView.iv_list_title.setText(title);
			holderView.iv_sum.setText(ssum);
			holderView.iv_danjia.setText(String.valueOf(danjia));
			holderView.iv_StorageCost.setText(String.valueOf(sg));
			holderView.iv_xiaoji.setText(String.valueOf(xioaji));
			
		return currentView;
	}
public class HolderView {
		
		private ImageView iv_ProductPic;
		private TextView  iv_list_title;
		private TextView iv_danjia;
		private TextView iv_StorageCost;
		private TextView iv_sum;
		private TextView iv_xiaoji;
	
		
		
	}
}
