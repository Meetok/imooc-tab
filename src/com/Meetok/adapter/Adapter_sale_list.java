package com.Meetok.adapter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.Meetok.Activity.AddSaleOrder;
import com.Meetok.Entity.SaleXQEntity;
import com.Meetok.adapter.Adapter_sale_grid.HolderView;

import com.imooc.tab03.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.R.integer;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Adapter_sale_list extends BaseAdapter {
	private Context context;
	private List<SaleXQEntity> mlist;

	// 定义一个HashMap，用来存放EditText的值，Key是position
	private List<Map<String, String>> mData = new ArrayList<Map<String, String>>();
	private Integer index = -1;
	private boolean cishu = true; // 记录选中的edtitext
	private boolean flag = true;
	private double allprice=0;

	public Adapter_sale_list(Context context, List<SaleXQEntity> list) {
		cishu = true;
		this.context = context;
		this.mlist = list;
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

	@SuppressWarnings("unchecked")
	@Override
	public View getView(final int position, View currentView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		final HolderView holderView;
		if (currentView == null) {
			holderView = new HolderView();
			currentView = LayoutInflater.from(context).inflate(
					R.layout.adapter_sale_list, null);
			// holderView.iv_ProductPic=(ImageView)
			// currentView.findViewById(R.id.iv_adapter_grid_ProductPic);
			holderView.iv_title = (TextView) currentView
					.findViewById(R.id.sale_title);
			holderView.iv_price = (TextView) currentView
					.findViewById(R.id.iv_sale_jiage);
			holderView.iv_kucun = (TextView) currentView
					.findViewById(R.id.iv_sale_kucuen);
			holderView.iv_left = (ImageView) currentView
					.findViewById(R.id.left);
			holderView.iv_right = (ImageView) currentView
					.findViewById(R.id.right);
			holderView.iv_input = (TextView) currentView
					.findViewById(R.id.input);
			holderView.iv_sale_jiage = (TextView) currentView
					.findViewById(R.id.iv_adapter_jiage);
			holderView.iv_sanchu = (TextView) currentView
					.findViewById(R.id.sale_sanchu);

			/*
			 * holderView.iv_sale_jiage.addTextChangedListener(new TextWatcher()
			 * {
			 * 
			 * private Integer textnum2;
			 * 
			 * @Override public void onTextChanged(CharSequence s, int start,
			 * int before, int count) { // TODO Auto-generated method stub
			 * 
			 * }
			 * 
			 * @Override public void beforeTextChanged(CharSequence arg0, int
			 * arg1, int arg2, int arg3) { // TODO Auto-generated method stub //
			 * textnum2 = //
			 * Integer.valueOf(holderView.iv_input.getText().toString()); }
			 * 
			 * @Override public void afterTextChanged(Editable s) { // TODO
			 * Auto-generated method stub 前 // 在这里做事
			 * 
			 * /* String jg=holderView.iv_sale_jiage.getText().toString();
			 * 
			 * int textnum = Integer.valueOf(holderView.iv_input.getText(
			 * ).toString()); //String edt=s.toString();
			 * if(holderView.iv_sale_jiage.equals("")){
			 * System.out.println("edt3==kong="+jg); }else{ double p =
			 * Double.valueOf(jg); System.out.println("edt3==="+jg); BigDecimal
			 * b= new BigDecimal(p); double prive =
			 * b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
			 * 
			 * AddSaleOrder.ChangeEdit(textnum, prive); }
			 * 
			 * hashMap.put(position, s.toString());
			 * System.out.println("hashMap.position)===" +
			 * hashMap.get(position)); } });
			 */
			currentView.setTag(holderView);
		} else {
			holderView = (HolderView) currentView.getTag();
		}

		SaleXQEntity da = mlist.get(position);
		int num = da.StockQuantity;
		// int num = (int) num1;
		double p = da.DisPurchasePrice;
		BigDecimal b = new BigDecimal(p);
		double prive = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		String numm = String.valueOf(num);
		if(num >= 2){
		holderView.iv_input.setText("2");
	
		}else{
			holderView.iv_input.setText("0");
		}
		
		holderView.iv_title.setText(da.Title);
		holderView.iv_price.setText(String.valueOf(da.DisPurchasePrice));
		holderView.iv_kucun.setText(numm);
		holderView.iv_sale_jiage.setText(String.valueOf(da.DisPurchasePrice));
		// displayImage(data1.ProductPic,holderView.iv_ProductPic);
		holderView.iv_left.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// 起始的订购数量，----------有问题
				System.out.println("---------");
				flag = false;
				int num = Integer.valueOf(holderView.iv_input.getText()
						.toString());
				String s_jg = holderView.iv_sale_jiage.getText().toString();
				// double p = mlist.get(position).DisPurchasePrice;
				double p = Double.valueOf(s_jg);
				BigDecimal b = new BigDecimal(p);
				double prive = b.setScale(2, BigDecimal.ROUND_HALF_UP)
						.doubleValue();
				if (num != 0 && num >= 1) {
					num = num - 1;
					String numm = String.valueOf(num);
					holderView.iv_input.setText(numm);
					AddSaleOrder.Changejiagejian(1, prive, flag, numm);
				} else {
					// holderView.iv_input.setText("0");
					// AddSaleOrder.Changejiagejian(0, prive,flag);
				}
			}
		});
		holderView.iv_right.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				// 起始的订购数量，----------有问题,
				System.out.println("+++++++++");
				flag = true;
				int textnum = Integer.valueOf(holderView.iv_input.getText()
						.toString());
				int num = mlist.get(position).StockQuantity;
				String s_jg = holderView.iv_sale_jiage.getText().toString()
						.trim();
				// double p = mlist.get(position).DisPurchasePrice;
				double p = Double.valueOf(s_jg);
				BigDecimal b = new BigDecimal(p);
				double prive = b.setScale(2, BigDecimal.ROUND_HALF_UP)
						.doubleValue();
				if (textnum < num) {
					textnum = textnum + 1;
					String numm = String.valueOf(textnum);
					holderView.iv_input.setText(numm);
					AddSaleOrder.Changejiagejian(1, prive, flag, numm);
				} else {
					System.out.println("库存不足");
				}
			}
		});
		holderView.iv_sanchu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				mlist.remove(position);
				int num = Integer.valueOf(holderView.iv_input.getText()
						.toString());
				String s_jg = holderView.iv_sale_jiage.getText().toString();
						System.out.println("san==="+s_jg);
				// double p = mlist.get(position).DisPurchasePrice;
				double p = Double.valueOf(s_jg);
				BigDecimal b = new BigDecimal(p);
				double prive = b.setScale(2, BigDecimal.ROUND_HALF_UP)
						.doubleValue();
				AddSaleOrder.getsanchu(num, prive);

				Adapter_sale_list.this.notifyDataSetChanged();
			}
		});
		holderView.iv_sale_jiage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// LayoutInflater factory = LayoutInflater.from(context);//提示框
				AlertDialog.Builder builder = new AlertDialog.Builder(context);
				builder.setTitle("输入销售价");
				View view = LayoutInflater.from(context).inflate(
						R.layout.dialog_okcancel_edit, null);
				final EditText inputServer = (EditText) view
						.findViewById(R.id.edit_message);
				builder.setView(view);
				builder.setPositiveButton("确定",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface arg0, int arg1) {
								// TODO Auto-generated method stub
								hintKbOne();
								double beforejage =Double.valueOf(holderView.iv_sale_jiage.getText().toString());
								BigDecimal c = new BigDecimal(beforejage);
								beforejage = c.setScale(2,
										BigDecimal.ROUND_HALF_UP).doubleValue();
								
								String salejiage = inputServer.getText().toString();
								double salejiage1 = Double.valueOf(salejiage);
								BigDecimal b = new BigDecimal(salejiage1);
								salejiage1 = b.setScale(2,
										BigDecimal.ROUND_HALF_UP).doubleValue();
								int num = Integer.valueOf(holderView.iv_input
										.getText().toString());
								AddSaleOrder.ChangeEdit(num,beforejage,salejiage1);
								holderView.iv_sale_jiage.setText(salejiage);
							}


						});
				builder.setNegativeButton("取消", null);
				builder.show();
			}
		});

		return currentView;
	}
	//此方法，如果显示则隐藏，如果隐藏则显示
	private void hintKbOne() {
	InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);    
	// 得到InputMethodManager的实例
	if (imm.isActive()) {
	 // 如果开启
	imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT,
	InputMethodManager.HIDE_NOT_ALWAYS);
	         
	    }
	}
	class HolderView {

		private TextView iv_title;
		private TextView iv_price;
		private TextView iv_kucun;
		private TextView iv_sale_jiage;
		private ImageView iv_left;
		private ImageView iv_right;
		private TextView iv_input;
		private TextView iv_sanchu;
		int ref;
	}
}
