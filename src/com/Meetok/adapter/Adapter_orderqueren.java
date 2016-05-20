package com.Meetok.adapter;

import java.util.List;

import com.Meetok.Entity.DingDanEntity;
import com.Meetok.Entity.OrderEntity;
import com.Meetok.adapter.Adapter_order.HolderView;
import com.imooc.tab03.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.R.integer;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class Adapter_orderqueren extends BaseAdapter {


	private Context context;
	private List<DingDanEntity> mlist;

	
	
	public Adapter_orderqueren(Context context,List<DingDanEntity> list){
		
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
				currentView=LayoutInflater.from(context).inflate(R.layout.adapter_orderqueren, null);
				holderView.iv_ProductPic=(ImageView) currentView.findViewById(R.id.iv_adapter_grid_ProductPic);
				holderView.iv_title = (TextView) currentView.findViewById(R.id.iv_adapter_list_title);
				holderView.iv_price = (TextView) currentView.findViewById(R.id.iv_adapter_jiage);
				holderView.iv_num = (TextView) currentView.findViewById(R.id.iv_adapter_num);
				holderView.iv_allprice = (TextView) currentView.findViewById(R.id.iv_adapter_zhongjia);
				holderView.iv_ProductPic.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
				currentView.setTag(holderView);
			}else {
				holderView=(HolderView) currentView.getTag();
			}
			DingDanEntity data1=mlist.get(position);
			String title = data1.Title;
			int num =data1.Num;
			//String price = se.DisPurchasePrice;
			float price = data1.DisPurchasePrice;
			String allprice = data1.AllPrice;
			
			String p = String.valueOf(price);
			String snum = String.valueOf(num);

			holderView.iv_title.setText(title);
			holderView.iv_price.setText(p);
			holderView.iv_num.setText(snum);
			holderView.iv_allprice.setText(allprice);
			displayImage(data1.ProductPic,holderView.iv_ProductPic);
		return currentView;
	}
	public void displayImage(String imageURL, ImageView imageView) {
		ImageLoader.getInstance().displayImage(imageURL, imageView);
	}
public class HolderView {
		
		private ImageView iv_ProductPic;
		private TextView  iv_title;
		private TextView iv_price;
		private TextView iv_num;
		private TextView iv_allprice;
		
		
	}
}
