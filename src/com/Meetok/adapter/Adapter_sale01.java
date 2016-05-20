package com.Meetok.adapter;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.Meetok.Activity.LogActivity;
import com.Meetok.Activity.OrderQueRen;
import com.Meetok.Activity.Sale_xiangqing2;

import com.Meetok.Activity.Sale_xiangqing;
import com.Meetok.Custom.CustomDialog;
import com.Meetok.Entity.SaleEntity;

import com.Meetok.fragment.sale.Sale01Activity;
import com.imooc.tab03.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class Adapter_sale01 extends BaseAdapter {
	private Context context;
	private List<SaleEntity> mlist;
	private LayoutInflater inflater;
	private ItemHolder itemHolder;
	View view[] = null;
	public SaleEntity sa;
	List<SaleEntity> mlist_zi = new ArrayList<SaleEntity>();

	public Adapter_sale01(Context context, List<SaleEntity> list) {

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

	@Override
	public View getView(final int position, View currentView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final HolderView holderView;
		if (currentView == null) {
			holderView = new HolderView();
			currentView = inflater.inflate(R.layout.adapter_sale_01, null);
			// holderView.iv_ProductPic=(ImageView)
			// currentView.findViewById(R.id.iv_adapter_grid_pic);
			holderView.iv_sa_Tid = (TextView) currentView
					.findViewById(R.id.sale_tid);
			holderView.iv_sa_time = (TextView) currentView
					.findViewById(R.id.sale_Created);
			holderView.iv_sa_ReceiverName = (TextView) currentView
					.findViewById(R.id.sale_ReceiverName);
			holderView.iv_sa_fasong = (TextView) currentView
					.findViewById(R.id.s_fasong);
			holderView.iv_sa_zuofei = (TextView) currentView
					.findViewById(R.id.s_zuofei);
			holderView.iv_sa_zhuangtai = (TextView) currentView
					.findViewById(R.id.s_zhuangtai);
			holderView.iv_sa_xiangqing = (TextView) currentView
					.findViewById(R.id.s_xiangqing);
			holderView.item = (LinearLayout) currentView
					.findViewById(R.id.sale_list);
			holderView.iv_sa_xiugai = (TextView) currentView
					.findViewById(R.id.s_xiugai);
			// holderView.iv_list = (MyListView)
			// currentView.findViewById(R.id.list_zi_list);
			// holderView.iv_pic.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
			currentView.setTag(holderView);
		} else {
			holderView = (HolderView) currentView.getTag();
		}
		SaleEntity sa = mlist.get(position);
		String IDID = sa.ID;
		String tid = sa.Tid;
		String time = sa.Created;
		String SalesOrderStatus = sa.SalesOrderStatus;
		String ReceiverName = sa.ReceiverName;
		JSONArray jsonarray = sa.itemarray1;

		setdata(position, holderView, sa, jsonarray);
		holderView.iv_sa_zuofei.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				final String zhuid = String.valueOf(mlist.get(position).ID);
				CustomDialog.Builder builder = new CustomDialog.Builder(context);
				builder.setMessage("是否确定删除？");
				builder.setTitle("提示");
				builder.setPositiveButton("确定",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
								Sale01Activity.zuofei(position, zhuid);
								mlist.remove(position);
								Adapter_sale01.this.notifyDataSetChanged();

							}
						});
				builder.setNegativeButton("取消",
						new android.content.DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {

								dialog.dismiss();
							}
						});
				builder.create().show();

			}
		});
		holderView.iv_sa_fasong.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				final String IDID = String.valueOf(mlist.get(position).ID);
				Sale01Activity.getfasong(IDID, context);
			}
		});
		holderView.iv_sa_xiangqing.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				final String IDID = String.valueOf(mlist.get(position).ID);
				Intent intent = new Intent(context, Sale_xiangqing.class);
				// intent.setClass(OrderQueRen.this, OrderPay.class);

				System.out.println("guids-------------" + IDID);
				intent.putExtra("sale_id", IDID);
				context.startActivity(intent);
				
			}
		});
		holderView.iv_sa_xiugai.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				final String IDID = String.valueOf(mlist.get(position).ID);
				Intent intent = new Intent(context, Sale_xiangqing2.class);
				// intent.setClass(OrderQueRen.this, OrderPay.class);

				System.out.println("guids-------------" + IDID);
				intent.putExtra("sale_id", IDID);
				context.startActivity(intent);
				//((Activity) context).overridePendingTransition(
				//		R.anim.slide_left_out, R.anim.slide_right_in);
			}
		});
		holderView.iv_sa_zhuangtai.setText(SalesOrderStatus);
		holderView.iv_sa_Tid.setText(tid);
		holderView.iv_sa_time.setText(time);
		holderView.iv_sa_ReceiverName.setText(ReceiverName);

		return currentView;
	}

	private void setdata(int position, HolderView holderView, SaleEntity sa2,
			JSONArray jsonarray) {
		// TODO Auto-generated method stub
		holderView.item.removeAllViews();
		// View itemView =
		// LayoutInflater.from(context).inflate(R.layout.adapter_child_pur,
		// null);
		if (itemHolder == null) {
			itemHolder = new ItemHolder();
		}
		for (int j = 0; j < jsonarray.length(); j++) {
			view = new View[jsonarray.length()];
			view[j] = (View) inflater
					.inflate(R.layout.adapter_child_sale, null);
			findItemView(view[j], itemHolder);
			JSONObject itemObj_s = jsonarray.optJSONObject(j);
			SaleEntity ppur = new SaleEntity();
			// ParseJSONTools.getInstance().fromJsonToJava(itemObj,PurchaseEntity.class);
			ppur.Name = itemObj_s.optString("Name");
			ppur.Quantity = itemObj_s.optString("Quantity");
			ppur.usestock = itemObj_s.optString("usestock");
			ppur.TotalFee = itemObj_s.optDouble("TotalFee");
			// ((List<PurchaseEntity>) mlist[i]).add(ppur);
			// String ssum = String.valueOf(ppur.Quantity);
			itemHolder.Item_sname.setText(ppur.Name);
			itemHolder.Item_shulaing.setText(ppur.Quantity);
			itemHolder.Item_money.setText(String.valueOf(ppur.TotalFee));

			mlist_zi.add(ppur);
			holderView.item.addView(view[j]);
		}
	}

	private void findItemView(View itemView, ItemHolder itemHolde) {
		// TODO Auto-generated method stub
		itemHolder.Item_sname = (TextView) itemView.findViewById(R.id.s_name);
		itemHolder.Item_shulaing = (TextView) itemView
				.findViewById(R.id.s_shuliang);
		itemHolder.Item_money = (TextView) itemView.findViewById(R.id.s_jine);

	}

	public class HolderView {

		private TextView iv_sa_Tid;
		private TextView iv_sa_time;
		private TextView iv_sa_ReceiverName;
		private TextView iv_sa_fasong;
		private TextView iv_sa_zuofei;
		private TextView iv_sa_zhuangtai;
		private TextView iv_sa_xiangqing;
		private TextView iv_sa_xiugai;
		public ListView iv_list;
		LinearLayout item;

	}

	private static class ItemHolder {

		private TextView Item_sname;
		private TextView Item_shulaing;
		private TextView Item_money;

	}
}
