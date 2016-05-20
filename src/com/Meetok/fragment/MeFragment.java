package com.Meetok.fragment;

import java.util.ArrayList;
import java.util.List;

import com.Meetok.Activity.AboutUsActivity;
import com.Meetok.Activity.CommonQuestionActivity;
import com.Meetok.Activity.CustomerService;
import com.Meetok.Activity.LogActivity;
import com.Meetok.Activity.MicroWarehouseStock;
import com.Meetok.Activity.PersonalImformation;
import com.Meetok.Activity.RegisterActivity;
import com.Meetok.Activity.ShoppingCart2;
import com.Meetok.Custom.CustomDialog;
import com.Meetok.Entity.GouWuCheEntity;
import com.Meetok.Entity.ShouyeEntity;
import com.Meetok.Tab.MainActivity;
import com.Meetok.config.Config;
import com.ab.http.AbHttpUtil;
import com.imooc.tab03.R;
import com.jingchen.pulltorefresh.pullableview.PullableScrollView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MeFragment extends Fragment implements OnClickListener{
	
	private AbHttpUtil httpUtil = null;
	private PullableScrollView mPullScrollView;
	List<ShouyeEntity> mlist1 = new ArrayList<ShouyeEntity>();
@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	
	View view=LayoutInflater.from(getActivity()).inflate(R.layout.tab05, null);
	mPullScrollView = (PullableScrollView) view
			.findViewById(R.id.content_view);
	
	httpUtil = AbHttpUtil.getInstance(getActivity());
	httpUtil.setTimeout(10000);
	initview();
	return view;
}

private void initview() {
	// TODO Auto-generated method stub
	View view = View.inflate(getActivity(), R.layout.layout_me, null);
	LinearLayout l1 = (LinearLayout) view.findViewById(R.id.logon_register);//初始化登入注册的linearlayout
	LinearLayout l2 = (LinearLayout) view.findViewById(R.id.denglu_zhuce);//初始化电话姓名的linearlayout
	LinearLayout l3 = (LinearLayout) view.findViewById(R.id.logOut);//初始化退出登录的linearlayout
	
	//购物车linearlayout的初始化，并设置监听
		LinearLayout gouwucheLinearLayout = (LinearLayout) view.findViewById(R.id.me_gouwuche);
		gouwucheLinearLayout.setOnClickListener(this);
	//个人信息linearlayout的初始化，并设置监听
	LinearLayout personalImformationLinearLayout = (LinearLayout) view.findViewById(R.id.me_personalimformation);
	personalImformationLinearLayout.setOnClickListener(this);
	//联系客服LinearLayout的初始化，并设置监听
	LinearLayout customerServiceLinearLayout = (LinearLayout) view.findViewById(R.id.me_customerservic);
	customerServiceLinearLayout.setOnClickListener(this);
	//常见问题LinearLayout的初始化，并设置监听
	LinearLayout commonqueationLinearLayout = (LinearLayout) view.findViewById(R.id.me_commonquestion);
	commonqueationLinearLayout.setOnClickListener(this);
	//关于我们LinearLayout的初始化，并设置监听
	LinearLayout aboutUSLinearLayout = (LinearLayout) view.findViewById(R.id.me_aboutus);
	aboutUSLinearLayout.setOnClickListener(this);
	//我的微仓库存LinearLayout的初始化，并设置监听
	LinearLayout microStockLinearLayout = (LinearLayout) view.findViewById(R.id.me_microwarehousestock);
	microStockLinearLayout.setOnClickListener(this);
	//判断是否登录
	//否
	if (LogActivity.loadDataFromLocalXML(getActivity(), "accesstoken")=="") {
		l1.setVisibility(View.VISIBLE);//显示登入注册的linearlayout
		l2.setVisibility(View.GONE);//隐藏姓名电话的linearlayout
		l3.setVisibility(View.GONE);//隐藏退出登录的linearlayout
		//给登录初始化并且设置监听
		TextView logonTextView = (TextView) view.findViewById(R.id.me_logon);
		logonTextView.setOnClickListener(this);
		//给注册初始化并且设置监听
		TextView registerTextView = (TextView) view.findViewById(R.id.me_register);
		registerTextView.setOnClickListener(this);
	//已登录	
	}else {
		
		
		l1.setVisibility(View.GONE);//隐藏登入注册的linearlayout
		l2.setVisibility(View.VISIBLE);//显示登入注册的linearlayout
		l3.setVisibility(View.VISIBLE);//显示退出当前账号LinarLayout，
		//退出当前账号设置点击监听
		l3.setOnClickListener(this);
		//已登录给电话和姓名的textview赋值
		TextView mobileTextView = (TextView) view.findViewById(R.id.me_mobile);
		mobileTextView.setText(LogActivity.loadDataFromLocalXML(getActivity(), "mobile"));
		TextView nameTextView = (TextView) view.findViewById(R.id.me_name);
		nameTextView.setText(LogActivity.loadDataFromLocalXML(getActivity(), "name"));
	}
	
	mPullScrollView.addView(view);
}

@Override
public void onClick(View v) {
	// TODO Auto-generated method stub
	switch (v.getId()) {
	case R.id.logOut:
		customlogOutOrNotTip();
		break;
		
	case R.id.me_logon:
		Intent intent = new Intent(getActivity(),LogActivity.class);
		startActivity(intent);
		getActivity().overridePendingTransition(R.anim.slide_left_in,
				R.anim.slide_left_out);
		break;
		
	case R.id.me_register:
		Intent intent2 = new Intent(getActivity(),RegisterActivity.class);
		startActivity(intent2);
		getActivity().overridePendingTransition(R.anim.slide_left_in,
				R.anim.slide_left_out);
		break;
	
	case R.id.me_personalimformation:
		getPersonalImformation();
		getActivity().overridePendingTransition(R.anim.slide_left_in,
				R.anim.slide_left_out);
		break;
		
	case R.id.me_customerservic:
		Intent intent3 = new Intent(getActivity(),CustomerService.class);
		startActivity(intent3);
		getActivity().overridePendingTransition(R.anim.slide_left_in,
				R.anim.slide_left_out);
		break;
	
	case R.id.me_commonquestion:
		Intent intent4 = new Intent(getActivity(),CommonQuestionActivity.class);
		startActivity(intent4);
		getActivity().overridePendingTransition(R.anim.slide_left_in,
				R.anim.slide_left_out);
		break;
	
	case R.id.me_aboutus:
		Intent intent5 = new Intent(getActivity(),AboutUsActivity.class);
		startActivity(intent5);
		getActivity().overridePendingTransition(R.anim.slide_left_in,
				R.anim.slide_left_out);
		break;
		
	case R.id.me_microwarehousestock:
		getMicroWarehouseStock();
		getActivity().overridePendingTransition(R.anim.slide_left_in,
				R.anim.slide_left_out);
		break;
	case R.id.me_gouwuche:
		Intent intent7 = new Intent(getActivity(),ShoppingCart2.class);
		startActivity(intent7);
		getActivity().overridePendingTransition(R.anim.slide_left_in,
				R.anim.slide_left_out);
		break;
	default:
		break;
	}
	
}
/**
 * 获取个人信息
 */
public void getPersonalImformation(){
	if (WhetherLogIn()==false) {
		JumpToLogOn();
	}else {
		Intent intent = new Intent(getActivity(),PersonalImformation.class);
		startActivity(intent);
		
	}
}
/**
 * 打开微仓库存
 */
public void getMicroWarehouseStock(){
	if (WhetherLogIn()==false) {
		JumpToLogOn();
	}else {
		Intent intent6 = new Intent(getActivity(),MicroWarehouseStock.class);
		startActivity(intent6);
		
	}
}
/**
 * 判断是否登录,true为已登录，false为未登录
 */
public Boolean WhetherLogIn(){
	return LogActivity.loadDataFromLocalXML(getActivity(), "accesstoken")!="";
}
/**
 * 跳转到登录页面
 */
public void JumpToLogOn(){
	Intent intent = new Intent(getActivity(),LogActivity.class);
	startActivity(intent);
}
/*
 * 判断是否退出当前账号的系统自带提示框
 */
public void logOutOrNotTip(){
	new AlertDialog.Builder(getActivity()).setTitle("退出当前账号提示框").setMessage("确认退出当前账户？")
	.setPositiveButton("确定", new DialogInterface.OnClickListener() {
	public void onClick(DialogInterface dialog, int which) {
		dialog.dismiss();
		//确认退出登入，清除本地的密钥，姓名，电话的记录
		LogActivity.saveDataToLocalXML(getActivity(), "name", "");
		LogActivity.saveDataToLocalXML(getActivity(), "mobile", "");
		LogActivity.saveDataToLocalXML(getActivity(), "accesstoken", "");
		//清理完成后跳转回个人中心
		Intent logOutIntent = new Intent(getActivity(),
				MainActivity.class);//实例化一个Intent
		Bundle bundle = new Bundle();
		bundle.putInt("fragment", 5);
		logOutIntent.putExtras(bundle);
		startActivity(logOutIntent);
		
	}})
	.setNegativeButton("取消",null)
	.show();
	
}
/*
 * 自定义提示框
 */
public void customlogOutOrNotTip(){
	CustomDialog.Builder builder = new CustomDialog.Builder(getActivity());  
    builder.setMessage("确认退出当前账号？");  
    builder.setTitle("提示");  
    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {  
        public void onClick(DialogInterface dialog, int which) {  
        	//确认退出登入，清除本地的密钥，姓名，电话的记录
    		LogActivity.saveDataToLocalXML(getActivity(), "name", "");
    		LogActivity.saveDataToLocalXML(getActivity(), "mobile", "");
    		LogActivity.saveDataToLocalXML(getActivity(), "accesstoken", "");
    		//清理完成后跳转回个人中心
            dialog.dismiss();  
            //设置你的操作事项  
            Intent i=new Intent( getActivity(), MainActivity.class);  
			Bundle bundle = new Bundle();
			bundle.putInt("fragment", 5);
			i.putExtras(bundle);
        	i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        	startActivity(i);
        }  
    });  

    builder.setNegativeButton("取消",  
            new android.content.DialogInterface.OnClickListener() {  
                public void onClick(DialogInterface dialog, int which) {  
                    dialog.dismiss();  
                }  
            });  

    builder.create().show();  
}
}
