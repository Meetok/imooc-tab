package com.Meetok.adapter;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;

import com.Meetok.Activity.OrderNew;
import com.Meetok.Custom.CustomDialog;
import com.Meetok.Entity.GouWuCheEntity;
import com.Meetok.Util.StringUtils;
import com.Meetok.fragment.ShoppingCartFragment;
import com.imooc.tab03.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import android.R.integer;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Adapter_gouwuche extends BaseAdapter implements OnClickListener {

	private Context context;
	private int qipinum;
	private List<GouWuCheEntity> mlist;
	private int gwcNum;
	private String guid;
	// 用来导入布局
	private LayoutInflater inflater = null;
	private ListView listView;
	protected boolean flag=false;
	// 用来控制CheckBox的选中状况
	private static HashMap<Integer, Boolean> isSelected;

	public Adapter_gouwuche(Context context, List<GouWuCheEntity> list) {

		this.context = context;
		this.mlist = list;
		inflater = LayoutInflater.from(context);
		isSelected = new HashMap<Integer, Boolean>();
		// 初始化数据
		initDate();
	}

	// 初始化isSelected的数据
	private void initDate() {
		for (int i = 0; i < mlist.size(); i++) {
			getIsSelected().put(i, false);
		}
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mlist.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mlist.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View currentView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		final HolderView holderView;
		if (currentView == null) {
			holderView = new HolderView();
			currentView = LayoutInflater.from(context).inflate(
					R.layout.adapter_shoppingcart_item, null);

			holderView.iv_pic = (ImageView) currentView
					.findViewById(R.id.iv_adapter_list_pic);
			holderView.iv_title = (TextView) currentView
					.findViewById(R.id.iv_adapter_title);
			holderView.iv_DisPurchasePrice = (TextView) currentView
					.findViewById(R.id.iv_adapter_DisPurchasePrice);
			holderView.iv_xioaji = (TextView) currentView
					.findViewById(R.id.xioaji);
			holderView.iv_num = (TextView) currentView
					.findViewById(R.id.gwc_input);
			holderView.iv_cb = (CheckBox) currentView
					.findViewById(R.id.pro_checkbox);
			holderView.iv_left = (ImageView) currentView
					.findViewById(R.id.gwc_left);
			holderView.iv_right = (ImageView) currentView
					.findViewById(R.id.gwc_right);
			holderView.iv_gwc_shanchu = (TextView) currentView
					.findViewById(R.id.gwc_shanchu);

			holderView.iv_pic.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
			currentView.setTag(holderView);
		} else {
			holderView = (HolderView) currentView.getTag();
		}
		final GouWuCheEntity gou = mlist.get(position);

		String title = gou.Title;
		 double d = gou.DisPurchasePrice;
		BigDecimal b = new BigDecimal(d);  
		final double danjia = b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue(); 
		
		String dj = String.valueOf(danjia);
		gwcNum = gou.Num;
		qipinum = gou.WholeSaleNum;// qi起批量
		guid = gou.GUID;
		String edit_num = String.valueOf(gou.Num);
		double xiaojie = danjia * gou.Num;
		BigDecimal b1 = new BigDecimal(xiaojie);  
		double idj2 = b1.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue(); 
		String xj = String.valueOf(idj2);

		// final TextView xioajie_text = holderView.iv_xioaji;
		holderView.iv_left.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub	 
				System.out.println("hhhhhhhhhhh");
				flag = false;
				String guids = mlist.get(position).GUID;
				int i = Integer.valueOf(holderView.iv_num.getText().toString());
				if (i > 2) {
					i = i - 1;

					String si = String.valueOf(i);
					double idj =i * danjia;
					BigDecimal b = new BigDecimal(idj);  
					BigDecimal c = new BigDecimal(danjia); 
					double idj2 = b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue(); 
					double danjia = c.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue(); 
					String new_xj = String.valueOf(idj2);
					holderView.iv_num.setText(si);
					holderView.iv_xioaji.setText(new_xj);
					
					ShoppingCartFragment.getnum(i, guids, position,context,flag,danjia,holderView.iv_cb);
				} else{
					i = 2;
					String si = String.valueOf(i);
					double idj =i * danjia;
					BigDecimal b = new BigDecimal(idj);  
					BigDecimal c = new BigDecimal(danjia); 
					double idj2 = b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue(); 
					double danjia = c.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue(); 
					String new_xj = String.valueOf(idj2);
					holderView.iv_num.setText(si);
					holderView.iv_xioaji.setText(new_xj);
				}
				
				//Adapter_gouwuche.this.notifyDataSetChanged();
			}
		});
		holderView.iv_right.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				flag = true;
				String guids = mlist.get(position).GUID;
				// int j = mlist.get(position).Num;
				int j = Integer.valueOf(holderView.iv_num.getText().toString());
				if (j <= mlist.get(position).Stock) {
					j = j + 1;
					
				} else{
					j = 2;
				}
				// mlist.
				String sj = String.valueOf(j);
				double idj =j * danjia;
				BigDecimal b = new BigDecimal(idj);
				BigDecimal c = new BigDecimal(danjia); 
				double idj2 = b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue(); 
				double danjia = c.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue(); 
				String new_xj = String.valueOf(idj2);
				holderView.iv_num.setText(sj);
				holderView.iv_xioaji.setText(new_xj);
				ShoppingCartFragment.getnum(j, guids, position,context,flag,danjia,holderView.iv_cb);
				//Adapter_gouwuche.this.notifyDataSetChanged();
			}
		});
		holderView.iv_gwc_shanchu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				System.out.println("ppp==="+position);
				String guidd = mlist.get(position).GUID;
				double d = mlist.get(position).DisPurchasePrice;
				int num = mlist.get(position).Num;
				double xiaojie = d * num;
				BigDecimal b1 = new BigDecimal(xiaojie);  
				double idxj = b1.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue(); 
				
				ShoppingCartFragment.showInfo(position, guidd,idxj);
				
				mlist.remove(position);
				 Adapter_gouwuche.this.notifyDataSetChanged();

				// 此方法单独刷新某一行item

			}
		});
		holderView.iv_cb.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//HolderView holder = (HolderView) position.getTag();
				// 改变CheckBox的状态
				holderView.iv_cb.toggle();
				if(holderView.iv_cb.isChecked() == true){
					holderView.iv_cb.setChecked(false);
				}else{
					holderView.iv_cb.setChecked(true);
				}
				String xiaojie = holderView.iv_xioaji.getText().toString();
				String guid_i = mlist.get(position).GUID;
				double xj = Double.parseDouble(xiaojie);
				ShoppingCartFragment.change_cb(xj,guid_i,position,holderView.iv_cb);
			}
		});
		holderView.iv_pic.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent(context, OrderNew.class);
				int int_code = mlist.get(position).Code;
				String xq_code = String.valueOf(int_code);
				System.out.println("code=========" + xq_code);
				i.putExtra("shangpingcode", xq_code);
				context.startActivity(i);
			}
		});
		// 根据isSelected来设置checkbox的选中状况
		holderView.iv_cb.setChecked(getIsSelected().get(position));

		//
		holderView.iv_DisPurchasePrice.setText(dj);
		holderView.iv_xioaji.setText(xj);
		holderView.iv_title.setText(title);
		holderView.iv_num.setText(edit_num);
		// displayImage(gou.ProductPic, holderView.iv_pic);
		DisplayImageOptions options = new DisplayImageOptions.Builder()
				// .showImageOnLoading(R.drawable.ic_launcher)
				.showImageOnFail(R.drawable.icon_error)
				.cacheOnDisk(true)
				.cacheInMemory(false)
				// 设置下载的图片是否缓存在内存中
				.cacheOnDisc(true)
				// 设置下载的图片是否缓存在SD卡中
				.bitmapConfig(Bitmap.Config.RGB_565)
				.imageScaleType(ImageScaleType.EXACTLY)
				.delayBeforeLoading(200).build();
		  
		ImageLoader.getInstance().displayImage(gou.ProductPic,
				holderView.iv_pic, options);

		return currentView;
	}

	public void setListView(ListView listView) {
		this.listView = listView;
	}

	public static class HolderView {

		private ImageView iv_pic;
		private TextView iv_title;
		private TextView iv_DisPurchasePrice;
		public TextView iv_xioaji;
		public TextView iv_num;
		public CheckBox iv_cb;
		public ImageView iv_left;
		public ImageView iv_right;
		private TextView iv_gwc_shanchu;
	}

	public static HashMap<Integer, Boolean> getIsSelected() {
		return isSelected;
	}

	public static void setIsSelected(HashMap<Integer, Boolean> isSelected) {
		Adapter_gouwuche.isSelected = isSelected;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

}
