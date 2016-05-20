package com.Meetok.fragment;


import com.Meetok.Activity.AddSaleOrder;
import com.Meetok.Activity.OrderNew;
import com.Meetok.fragment.sale.Sale01Activity;
import com.Meetok.fragment.sale.Sale02Activity;
import com.Meetok.fragment.sale.Sale03Activity;
import com.Meetok.fragment.sale.Sale04Activity;
import com.Meetok.fragment.sale.Sale05Activity;
import com.imooc.tab03.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;

import android.widget.TextView;

public class SaleFragment extends Fragment implements OnClickListener
{
	private TextView bt_sale_01, bt_sale_02, bt_sale_03, bt_sale_04, bt_sale_05;
	private View show_cart_01, show_cart_02, show_cart_03, show_cart_04, show_cart_05;
	private Sale01Activity sale01Activity;
	private Sale02Activity sale02Activity;
	private Sale03Activity sale03Activity;
	private Sale04Activity sale04Activity;
	private Sale05Activity sale05Activity;
	private boolean isDel=true;
	
	private TextView addtext;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		View view = LayoutInflater.from(getActivity()).inflate(R.layout.tab04, null);
		addtext = (TextView) view.findViewById(R.id.add_text);
		addtext.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startActivity(new Intent(getActivity(),AddSaleOrder.class));
			}
		});
		initView(view);
		return view;
	}
	private void initView(View view) {
		// TODO Auto-generated method stub
		bt_sale_01 =(TextView) view.findViewById(R.id.bt_sale_01);
		bt_sale_02 =(TextView) view.findViewById(R.id.bt_sale_02);
		bt_sale_03 =(TextView) view.findViewById(R.id.bt_sale_03);
		bt_sale_04 =(TextView) view.findViewById(R.id.bt_sale_04);
		bt_sale_05 =(TextView) view.findViewById(R.id.bt_sale_05);
		
		
		show_cart_01 = view.findViewById(R.id.show_cart_01);
		show_cart_02 = view.findViewById(R.id.show_cart_02);
		show_cart_03 = view.findViewById(R.id.show_cart_03);
		show_cart_04 = view.findViewById(R.id.show_cart_04);
		show_cart_05 = view.findViewById(R.id.show_cart_05);
		
		bt_sale_01.setOnClickListener(this);
		bt_sale_02.setOnClickListener(this);
		bt_sale_03.setOnClickListener(this);
		bt_sale_04.setOnClickListener(this);
		bt_sale_05.setOnClickListener(this);
		
		sale01Activity = new Sale01Activity();
		addFragment(sale01Activity);
		showFragment(sale01Activity);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.bt_sale_01:
			if (sale01Activity == null) {
				sale01Activity = new Sale01Activity();
				addFragment(sale01Activity);
				showFragment(sale01Activity);
			} else {
				showFragment(sale01Activity);
			}
			show_cart_01.setBackgroundColor(getResources().getColor(R.color.black));
			show_cart_02.setBackgroundColor(getResources().getColor(R.color.gray));
			show_cart_03.setBackgroundColor(getResources().getColor(R.color.gray));
			show_cart_04.setBackgroundColor(getResources().getColor(R.color.gray));
			show_cart_05.setBackgroundColor(getResources().getColor(R.color.gray));
			break;
		case R.id.bt_sale_02:
			if (sale02Activity == null) {
				sale02Activity = new Sale02Activity();
				addFragment(sale02Activity);
				showFragment(sale02Activity);
			} else {
				showFragment(sale02Activity);
			}
			show_cart_01.setBackgroundColor(getResources().getColor(R.color.gray));
			show_cart_02.setBackgroundColor(getResources().getColor(R.color.black));
			show_cart_03.setBackgroundColor(getResources().getColor(R.color.gray));
			show_cart_04.setBackgroundColor(getResources().getColor(R.color.gray));
			show_cart_05.setBackgroundColor(getResources().getColor(R.color.gray));
			break;
		case R.id.bt_sale_03:
			if (sale03Activity == null) {
				sale03Activity = new Sale03Activity();
				addFragment(sale03Activity);
				showFragment(sale03Activity);
			} else {
				showFragment(sale03Activity);
			}
			show_cart_01.setBackgroundColor(getResources().getColor(R.color.gray));
			show_cart_02.setBackgroundColor(getResources().getColor(R.color.gray));
			show_cart_03.setBackgroundColor(getResources().getColor(R.color.black));
			show_cart_04.setBackgroundColor(getResources().getColor(R.color.gray));
			show_cart_05.setBackgroundColor(getResources().getColor(R.color.gray));
			break;
		case R.id.bt_sale_04:
			if (sale04Activity == null) {
				sale04Activity = new Sale04Activity();
				addFragment(sale04Activity);
				showFragment(sale04Activity);
			} else {
				showFragment(sale04Activity);
			}
			show_cart_01.setBackgroundColor(getResources().getColor(R.color.gray));
			show_cart_02.setBackgroundColor(getResources().getColor(R.color.gray));
			show_cart_03.setBackgroundColor(getResources().getColor(R.color.gray));
			show_cart_04.setBackgroundColor(getResources().getColor(R.color.black));
			show_cart_05.setBackgroundColor(getResources().getColor(R.color.gray));
			break;
		case R.id.bt_sale_05:
			if (sale05Activity == null) {
				sale05Activity = new Sale05Activity();
				addFragment(sale05Activity);
				showFragment(sale05Activity);
			} else {
				showFragment(sale05Activity);
			}
			show_cart_01.setBackgroundColor(getResources().getColor(R.color.gray));
			show_cart_02.setBackgroundColor(getResources().getColor(R.color.gray));
			show_cart_03.setBackgroundColor(getResources().getColor(R.color.gray));
			show_cart_04.setBackgroundColor(getResources().getColor(R.color.gray));
			show_cart_05.setBackgroundColor(getResources().getColor(R.color.black));
			break;
		default:
			break;
		}
	}


	/** 添加Fragment **/
	public void addFragment(Fragment fragment) {
		FragmentTransaction ft = this.getFragmentManager().beginTransaction();
		ft.add(R.id.show_Sale_view, fragment);
		ft.commitAllowingStateLoss();
	}
	/** 删除Fragment **/
	public void removeFragment(Fragment fragment) {
		FragmentTransaction ft = this.getFragmentManager().beginTransaction();
		ft.remove(fragment);
		ft.commitAllowingStateLoss();
	}

	/** 显示Fragment **/
	public void showFragment(Fragment fragment) {
		FragmentTransaction ft = this.getFragmentManager().beginTransaction();
		if (sale01Activity != null) {
			ft.hide(sale01Activity);
		}
		if (sale02Activity != null) {
			ft.hide(sale02Activity);
		}
		if (sale03Activity != null) {
			ft.hide(sale03Activity);
		}
		if (sale04Activity != null) {
			ft.hide(sale04Activity);
		}
		if (sale05Activity != null) {
			ft.hide(sale05Activity);
		}
		
		ft.show(fragment);
		ft.commitAllowingStateLoss();

	}

}
