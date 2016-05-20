package com.Meetok.Activity;

import com.Meetok.Tab.ImmersionBar;
import com.imooc.tab03.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

public class AboutUsActivity extends Activity implements OnClickListener{
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about_us);
		ImmersionBar.setImmersionBar(this, R.color.statusbar_bg);
		initview();
	}
	
	public void initview(){
		//初始化关于我们的返回按钮，并且设置监听
		LinearLayout returnLinearLayout = (LinearLayout) findViewById(R.id.about_us_returnback);
		returnLinearLayout.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.about_us_returnback:
			AboutUsActivity.this.finish();
			overridePendingTransition(R.anim.slide_right_in,
					R.anim.slide_right_out);
			break;

		default:
			break;
		}
		
	}
}
