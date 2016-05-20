package com.Meetok.adapter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

import com.Meetok.Entity.ShouyeEntity;
import com.Meetok.adapter.Adapter_home1.HolderView;
import com.imooc.tab03.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Adapter_home_all extends BaseAdapter {

	private Context context;
	private List<ShouyeEntity> mlist;
	
	
	public Adapter_home_all(Context context,List<ShouyeEntity> list){
		
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
				//LinearLayout iv_Lin = (LinearLayout) currentView.findViewById(R.id.ll);
				holderView.iv_ProductPic=(ImageView) currentView.findViewById(R.id.iv_adapter_grid_ProductPic);
				holderView.iv_title = (TextView) currentView.findViewById(R.id.iv_adapter_grid_title);
				holderView.iv_price = (TextView) currentView.findViewById(R.id.iv_adapter_jiage);
				holderView.iv_sell = (TextView) currentView.findViewById(R.id.iv_adapter_kucuen);
				holderView.iv_ProductPic.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
			
				currentView.setTag(holderView);
			}else {
				holderView=(HolderView) currentView.getTag();
			}
			//currentView.setLayoutParams(new GridView.LayoutParams(wh[0] / 2, wh[0] / 2+50));
			ShouyeEntity data1=mlist.get(position);
			String title = data1.Title;
			//String price = se.DisPurchasePrice;
			float price = data1.DisPurchasePrice;
			int sell = data1.SellNum;
			String p = String.valueOf(price);
			String s = String.valueOf(sell);
			//
			holderView.iv_title.setText(title);
			holderView.iv_price.setText(p);
			holderView.iv_sell.setText(s);
			//displayImage(data1.ProductPic,holderView.iv_ProductPic);
			//显示图片的配置  
	        DisplayImageOptions options = new DisplayImageOptions.Builder()  
	                //.showImageOnLoading(R.drawable.app_logo)  
	                //.showImageOnFail(R.drawable.icon_error)  
	                .cacheInMemory(true)  
	                .cacheOnDisk(true)  
	                .bitmapConfig(Bitmap.Config.RGB_565)  
	                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)//
	                .displayer(new FadeInBitmapDisplayer(100))
	                .build();  
	          
	        ImageLoader.getInstance().displayImage(data1.ProductPic, holderView.iv_ProductPic, options); 
			/*
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
					      
				       //holderView.iv_ProductPic.setMaxWidth(screenWidth);
				       holderView.iv_ProductPic.setMaxHeight(screenWidth/2);
				       holderView.iv_ProductPic.setImageBitmap(loadedImage);
				
					       
			        	//comp(bmp);
					      // holderView.iv_ProductPic.setImageBitmap(comp(bmp));
				       
				       
			        }  
			    }); */
		return currentView;
	}
	
	
	public void displayImage(String imageURL, ImageView imageView) {
		ImageLoader.getInstance().displayImage(imageURL, imageView);
	}
	public class HolderView {
		
		private LinearLayout iv_Lin;
		private ImageView iv_ProductPic;
		private TextView  iv_title;
		private TextView iv_price;
		private TextView iv_sell;
		
		
	}
}
