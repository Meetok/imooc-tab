package com.Meetok.adapter;

import java.util.List;

import com.Meetok.Entity.OrderEntity;
import com.Meetok.Entity.ShouyeEntity;
import com.Meetok.adapter.Adapter_home_all.HolderView;
import com.imooc.tab03.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
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

public class Adapter_order extends BaseAdapter {

	private Context context;
	private List<OrderEntity> mlist;
	
	
	public Adapter_order(Context context,List<OrderEntity> list){
		
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
				currentView=LayoutInflater.from(context).inflate(R.layout.adapter_home_all, null);
				holderView.iv_ProductPic=(ImageView) currentView.findViewById(R.id.iv_adapter_grid_ProductPic);
				holderView.iv_title = (TextView) currentView.findViewById(R.id.iv_adapter_grid_title);
				holderView.iv_price = (TextView) currentView.findViewById(R.id.iv_adapter_jiage);
				holderView.iv_sell = (TextView) currentView.findViewById(R.id.iv_adapter_kucuen);
			//	holderView.iv_pic.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
				currentView.setTag(holderView);
			}else {
				holderView=(HolderView) currentView.getTag();
			}
			OrderEntity data1=mlist.get(position);
			String title = data1.Title;
			//String price = se.DisPurchasePrice;
			float price = data1.DisPurchasePrice;
			String sell = data1.SellNum;
			String p = String.valueOf(price);
			//String s = String.valueOf(sell);
			//
			holderView.iv_title.setText(title);
			holderView.iv_price.setText(p);
			holderView.iv_sell.setText(sell);
			//displayImage(data1.ProductPic,holderView.iv_ProductPic);
			
			ImageLoader.getInstance().loadImage(data1.ProductPic,  new SimpleImageLoadingListener() {  
			        @Override  
			        public void onLoadingStarted(String imageUri, View view) {  
			        	
			        }  

			        @Override  
			        public void onLoadingFailed(String imageUri, View view, FailReason failReason) {  

			        }  

			        @Override  
			        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) { 
			        	Bitmap bmp = loadedImage;
			        	WindowManager wm = (WindowManager) context
					               .getSystemService(Context.WINDOW_SERVICE);
					       @SuppressWarnings("deprecation")
						   int screenWidth = wm.getDefaultDisplay().getWidth();
					       ViewGroup.LayoutParams lp = holderView.iv_ProductPic.getLayoutParams();
					       lp.width=screenWidth/2;				      
					       lp.height=(int)(screenWidth * bmp.getHeight() / bmp.getWidth())/2;  				     				    	  		       
					       holderView.iv_ProductPic.setLayoutParams(lp);
					       holderView.iv_ProductPic.setImageBitmap(loadedImage);
				       
				       
			        }  
			    }); 
			
		return currentView;
	}
	public void displayImage(String imageURL, ImageView imageView) {
		ImageLoader.getInstance().displayImage(imageURL, imageView);
	}
public class HolderView {
		
		private ImageView iv_ProductPic;
		private TextView  iv_title;
		private TextView iv_price;
		private TextView iv_sell;
		
		
	}

}
