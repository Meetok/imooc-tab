package com.Meetok.Activity;

import com.Meetok.Custom.CustomDialog;
import com.Meetok.Tab.ImmersionBar;
import com.Meetok.Tab.MainActivity;
import com.imooc.tab03.R;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.Toast;

public class CustomerService extends Activity implements OnClickListener{
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.customerservic);
		ImmersionBar.setImmersionBar(this, R.color.statusbar_bg);
		initview();
	}
	public void initview(){
		//初始化客服qq，并设置监听
		LinearLayout qqLinearLayout = (LinearLayout) findViewById(R.id.customerservice_qq);
		qqLinearLayout.setOnClickListener(this);
		//初始化客服热线，并设置监听
		LinearLayout mobileLinearLayout = (LinearLayout) findViewById(R.id.customerservice_mobile);
		mobileLinearLayout.setOnClickListener(this);
		//初始化关于我们的返回按钮，并且设置监听
		LinearLayout returnLinearLayout = (LinearLayout) findViewById(R.id.customerservic_returnback);
		returnLinearLayout.setOnClickListener(this);
	}
	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.customerservice_qq:
			try {
				String qqString= getResources().getString(R.string.customerqq);
				String url = "mqqwpa://im/chat?chat_type=wpa&uin="+qqString;
				startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
			} catch (Exception e) {
				noQQTip();
			}
			break;
		
		case R.id.customerservice_mobile:
			whetherCall();
			break;
		case R.id.customerservic_returnback:
			CustomerService.this.finish();
			overridePendingTransition(R.anim.slide_right_in,
					R.anim.slide_right_out);
			break;	
			
		default:
			break;
		}
	}
	/**
	 * 无QQApp提示
	 */
	public  void noQQTip(){
		CustomDialog.Builder builder = new CustomDialog.Builder(this);  
	    builder.setMessage("手机没有相关APP,确认前往拨打客服热线？");  
	    builder.setTitle("http://m.meetok.com");  
	    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {  
	        public void onClick(DialogInterface dialog, int which) {
	        	dialog.dismiss();
	        	//确定跳转到拨打热线电话
	        	try {
					whetherCall();
				} catch (Exception e) {
					// TODO: handle exception
					noMobileApp();
				}
	        	
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
	/**
	 * 无打电话插件提示
	 */
	public void noMobileApp(){
		CustomDialog.Builder builder = new CustomDialog.Builder(this);  
	    builder.setMessage("此设备不支持拨打电话功能");  
	    builder.setTitle("http://m.meetok.com"); 
	    builder.setNegativeButton("确定",  
	            new android.content.DialogInterface.OnClickListener() {  
	                public void onClick(DialogInterface dialog, int which) {  
	                    dialog.dismiss();  
	                }  
	            });  

	    builder.create().show();
	}
	/**
	 * 提示是否前往拨打电话
	 */
	public void whetherCall(){
		CustomDialog.Builder builder = new CustomDialog.Builder(this);  
	    builder.setMessage("是否前往拨打客服热线？");  
	    builder.setTitle("http://m.meetok.com");  
	    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {  
	        public void onClick(DialogInterface dialog, int which) {
	        	dialog.dismiss();
	        	//确定跳转到拨打热线电话
	        	try {
					String mobile = getResources().getString(R.string.customermobilenum);
					Intent intent=new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+mobile));
					startActivity(intent);
				} catch (Exception e) {
					// TODO: handle exception
					noMobileApp();
				}
	        	
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
