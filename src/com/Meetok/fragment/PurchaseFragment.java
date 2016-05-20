package com.Meetok.fragment;

import com.Meetok.fragment.Pur.AlreadyPaid_P;
import com.Meetok.fragment.Pur.Canceled_P;
import com.Meetok.fragment.Pur.YiPayment_P;
import com.imooc.tab03.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
/**
 * 采购进货主界面
 * @author ayumi
 *
 */
public class PurchaseFragment extends Fragment implements OnClickListener
{
	private TextView bt_alreadyPaid, bt_noPayment, bt_canceled;
	private View show_cart_all, show_cart_low, show_cart_stock;
	private AlreadyPaid_P alreadyPaid_P;
	private YiPayment_P noPayment_P;
	private Canceled_P canceled_P;
	private boolean isDel=true;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		View view = LayoutInflater.from(getActivity()).inflate(R.layout.purchase_f, null);
		initView(view);
		return view;
	}

	private void initView(View view) {
		// TODO Auto-generated method stub
		bt_alreadyPaid= (TextView) view.findViewById(R.id.bt_cart_all);
		bt_noPayment = (TextView) view.findViewById(R.id.bt_cart_low);
		bt_canceled = (TextView) view.findViewById(R.id.bt_cart_stock);
		
		show_cart_all = view.findViewById(R.id.show_cart_all);
		show_cart_low = view.findViewById(R.id.show_cart_low);
		show_cart_stock = view.findViewById(R.id.show_cart_stock);
		
		bt_alreadyPaid.setOnClickListener(this);
		bt_noPayment.setOnClickListener(this);
		bt_canceled.setOnClickListener(this);
		alreadyPaid_P = new AlreadyPaid_P();
		addFragment(alreadyPaid_P);
		showFragment(alreadyPaid_P);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.bt_cart_all:
			if (alreadyPaid_P == null) {
				alreadyPaid_P = new AlreadyPaid_P();
				addFragment(alreadyPaid_P);
				showFragment(alreadyPaid_P);
			} else {
				showFragment(alreadyPaid_P);
			}
			show_cart_all.setBackgroundColor(getResources().getColor(R.color.black));
			show_cart_low.setBackgroundColor(getResources().getColor(R.color.gray));
			show_cart_stock.setBackgroundColor(getResources().getColor(R.color.gray));
			break;
		case R.id.bt_cart_low:
			if (noPayment_P == null) {
				noPayment_P = new YiPayment_P();
				addFragment(noPayment_P);
				showFragment(noPayment_P);
			} else {
				showFragment(noPayment_P);
			}
			show_cart_low.setBackgroundColor(getResources().getColor(R.color.black));
			show_cart_all.setBackgroundColor(getResources().getColor(R.color.gray));
			show_cart_stock.setBackgroundColor(getResources().getColor(R.color.gray));

			break;
		case R.id.bt_cart_stock:
			if (canceled_P == null) {
				canceled_P = new Canceled_P();
				addFragment(canceled_P);
				showFragment(canceled_P);
			} else {
				showFragment(canceled_P);
			}
			show_cart_stock.setBackgroundColor(getResources().getColor(R.color.black));
			show_cart_all.setBackgroundColor(getResources().getColor(R.color.gray));
			show_cart_low.setBackgroundColor(getResources().getColor(R.color.gray));

			break;
		default:
			break;
		}
	}
	

	/** 添加Fragment **/
	public void addFragment(Fragment fragment) {
		FragmentTransaction ft = this.getFragmentManager().beginTransaction();
		ft.add(R.id.show_cart_view, fragment);
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
		if (alreadyPaid_P != null) {
			ft.hide(alreadyPaid_P);
		}
		if (noPayment_P != null) {
			ft.hide(noPayment_P);
		}
		if (canceled_P != null) {
			ft.hide(canceled_P);
		}

		ft.show(fragment);
		ft.commitAllowingStateLoss();

	}

}
