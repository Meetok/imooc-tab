package com.Meetok.Activity;

import com.Meetok.Tab.ImmersionBar;
import com.imooc.tab03.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PersonalImformation extends Activity implements OnClickListener{
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.personalimformation);
		ImmersionBar.setImmersionBar(this, R.color.statusbar_bg);
		initView();
	}
	public void initView(){
		TextView mobileTextView = (TextView) findViewById(R.id.personalimformation_mobile_tv);
		mobileTextView.setText(LogActivity.loadDataFromLocalXML(this, "mobile"));
		TextView nameTextView = (TextView) findViewById(R.id.personalimformation_name_tv);
		nameTextView.setText(LogActivity.loadDataFromLocalXML(this, "name"));
		//初始化修改密码，并设置监听
		LinearLayout modifyLinearLayout = (LinearLayout) findViewById(R.id.personalimformation_modifypassword);
		modifyLinearLayout.setOnClickListener(this);
		//初始化关于我们的返回按钮，并且设置监听
		LinearLayout returnLinearLayout = (LinearLayout) findViewById(R.id.personalimformation_returnback);
		returnLinearLayout.setOnClickListener(this);
	}
	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.personalimformation_returnback:
			PersonalImformation.this.finish();
			break;
		
		case R.id.personalimformation_modifypassword:
			Intent intent = new Intent(PersonalImformation.this,modifypasswordActivity.class);
			startActivity(intent);
			overridePendingTransition(R.anim.slide_left_in,
					R.anim.slide_left_out);
			break;
		
		default:
			break;
		}
		
	}
	
}
