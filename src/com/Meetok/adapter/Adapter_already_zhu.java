package com.Meetok.adapter;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.Meetok.Activity.LogActivity;
import com.Meetok.Entity.PurchaseEntity;
import com.Meetok.Tab.MainActivity;
import com.Meetok.View.MyListView;
import com.Meetok.adapter.Adapter_home1.HolderView;
import com.Meetok.fragment.ShoppingCartFragment;
import com.Meetok.fragment.Pur.AlreadyPaid_P;
import com.imooc.tab03.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Adapter_already_zhu extends BaseAdapter {

	private Context context;
	private List<PurchaseEntity> mlist;

	//private Adapter_Child_already adapter_Child_already;
	public PurchaseEntity se;
	private ItemHolder itemHolder;
	View view[] = null;
	private int index = 0;
	private LayoutInflater inflater;
	List<PurchaseEntity> mlist_zi = new ArrayList<PurchaseEntity>();

	public Adapter_already_zhu(Context context, List<PurchaseEntity> list) {

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
		return mlist == null ? null : mlist.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return mlist == null ? null : arg0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public View getView(final int position, View currentView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final HolderView holderView;
		if (currentView == null) {
			holderView = new HolderView();
			currentView = inflater.inflate(R.layout.adapter_pur, null);
			// holderView.iv_ProductPic=(ImageView)
			// currentView.findViewById(R.id.iv_adapter_grid_pic);
			holderView.iv_cg_tid = (TextView) currentView
					.findViewById(R.id.cg_tid);
			holderView.iv_cg_time = (TextView) currentView
					.findViewById(R.id.cg_time);
			holderView.iv_cg_zongji = (TextView) currentView
					.findViewById(R.id.cg_zongji);

			holderView.iv_cg_fukuan = (TextView) currentView
					.findViewById(R.id.cg_fukuan);
			holderView.iv_cg_quxiao = (TextView) currentView
					.findViewById(R.id.cg_quxiao);
			holderView.item = (LinearLayout) currentView
					.findViewById(R.id.lin_list);
			//holderView.iv_list = (MyListView) currentView.findViewById(R.id.list_zi_list);
			// holderView.iv_pic.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
			currentView.setTag(holderView);
		} else {
			holderView = (HolderView) currentView.getTag();
		}
		PurchaseEntity se = mlist.get(position);

		String tid = se.Tid;
		double iv_pay = se.Payment;
		String time = se.Created;
		JSONArray jsonarray = se.jsonarray1;
		
		setdata(position, holderView, se, jsonarray);
		
		holderView.iv_cg_quxiao.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				final String zhuid =String.valueOf(mlist.get(position).ID);
			new AlertDialog.Builder(context).setTitle("温馨提示").setMessage("确认取消该订单？")
			.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
					AlreadyPaid_P.showInfo(position, zhuid);
				
				mlist.remove(position);
				Adapter_already_zhu.this.notifyDataSetChanged();
			}})
			.setNegativeButton("取消",null)
			.show();
			}
		});
		holderView.iv_cg_fukuan.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String tid = mlist.get(position).Tid;
				//String title = mlist.get(position).Title;
				String title = "mlist.get(position).Title";
				String money = String.valueOf(mlist.get(position).Payment);
				AlreadyPaid_P.getdingdan2(tid,title,money,context);

			}
		});
		holderView.iv_cg_tid.setText(tid);
		holderView.iv_cg_time.setText(time);
		holderView.iv_cg_zongji.setText(String.valueOf(iv_pay));

		return currentView;
	}

	private void setdata(int position, HolderView holderView,
			PurchaseEntity se2, JSONArray jsonarray) {
		// TODO Auto-generated method stub
		holderView.item.removeAllViews();
		//View itemView = LayoutInflater.from(context).inflate(R.layout.adapter_child_pur, null);
		if (itemHolder == null) {
			itemHolder = new ItemHolder();
		}
		//显示图片的配置  
        DisplayImageOptions options = new DisplayImageOptions.Builder()  
                //.showImageOnLoading(R.drawable.app_logo)  
                //.showImageOnFail(R.drawable.icon_error)  
                .cacheInMemory(true)  
                .cacheOnDisk(true)  
                .bitmapConfig(Bitmap.Config.RGB_565)  
                .imageScaleType(ImageScaleType.EXACTLY)//
                .build();
		for (int j = 0; j < jsonarray.length(); j++) {
			 view = new View[jsonarray.length()];
			 view[j] = (View)inflater.inflate(R.layout.adapter_child_pur, null);
			 findItemView(view[j], itemHolder);			 
			JSONObject itemObj_s = jsonarray.optJSONObject(j);
			PurchaseEntity ppur = new PurchaseEntity();
			// ParseJSONTools.getInstance().fromJsonToJava(itemObj,PurchaseEntity.class);
			ppur.ProductPic = itemObj_s.optString("ProductPic");
			ppur.Title = itemObj_s.optString("Title");
			ppur.Price = itemObj_s.optDouble("Price");
			ppur.StorageCost = itemObj_s.optDouble("StorageCost");
			ppur.Payment = itemObj_s.optDouble("Payment");
			ppur.Quantity = itemObj_s.optInt("Quantity");
			// ((List<PurchaseEntity>) mlist[i]).add(ppur);
			String payment = String.valueOf(ppur.Payment);
			String ssum = String.valueOf(ppur.Quantity);
			itemHolder.Item_list_title.setText(ppur.Title);
			itemHolder.Item_danjia.setText(String.valueOf(ppur.Price));
			itemHolder.Item_sum.setText(ssum);
			itemHolder.Item_StorageCost.setText(String.valueOf(ppur.StorageCost));
			itemHolder.Item_xiaoji.setText(payment);
			   ImageLoader.getInstance().displayImage(ppur.ProductPic, itemHolder.Item_ProductPic, options); 
			//displayImage(ppur.ProductPic, itemHolder.Item_ProductPic);
			mlist_zi.add(ppur);
			holderView.item.addView(view[j]);	
		}
		//holderView.item.addView(itemView);

	}

	private void displayImage(String productPic, ImageView item_ProductPic) {
		// TODO Auto-generated method stub
		ImageLoader.getInstance().displayImage(productPic, item_ProductPic);
	}

	private void findItemView(View itemView, ItemHolder itemHolder) {
		// TODO Auto-generated method stub
		itemHolder.Item_ProductPic = (ImageView) itemView
				.findViewById(R.id.img_pic);
		itemHolder.Item_list_title = (TextView) itemView
				.findViewById(R.id.iv_list_title);
		itemHolder.Item_danjia = (TextView) itemView
				.findViewById(R.id.iv_danjia);
		itemHolder.Item_StorageCost = (TextView) itemView
				.findViewById(R.id.iv_StorageCost);

		itemHolder.Item_sum = (TextView) itemView.findViewById(R.id.iv_sum);
		itemHolder.Item_xiaoji = (TextView) itemView
				.findViewById(R.id.iv_xiaoji);
	}

	public class HolderView {

		private ImageView iv_ProductPic;
		private TextView iv_cg_tid;
		private TextView iv_cg_time;
		private TextView iv_cg_zongji;
		private TextView iv_cg_fukuan;
		private TextView iv_cg_quxiao;
		public MyListView iv_list;
		LinearLayout item;

	}

	private static class ItemHolder {
		private ImageView Item_ProductPic;
		private TextView Item_list_title;
		private TextView Item_danjia;
		private TextView Item_StorageCost;
		private TextView Item_sum;
		private TextView Item_xiaoji;

	}
}
