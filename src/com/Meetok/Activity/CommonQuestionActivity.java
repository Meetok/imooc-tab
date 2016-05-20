package com.Meetok.Activity;

import com.Meetok.Tab.ImmersionBar;
import com.imooc.tab03.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CommonQuestionActivity extends Activity implements OnClickListener{
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.commonquestion);
		ImmersionBar.setImmersionBar(this, R.color.statusbar_bg);
		initview();
	}
	public void initview(){
		//初始化问题，并设置监听
		TextView question_1 = (TextView) findViewById(R.id.question_1);
		question_1.setOnClickListener(this);
		
		TextView question_2 = (TextView) findViewById(R.id.question_2);
		question_2.setOnClickListener(this);

		TextView question_3 = (TextView) findViewById(R.id.question_3);
		question_3.setOnClickListener(this);
		
		TextView question_4 = (TextView) findViewById(R.id.question_4);
		question_4.setOnClickListener(this);
		
		TextView question_5 = (TextView) findViewById(R.id.question_5);
		question_5.setOnClickListener(this);
		
		TextView question_6 = (TextView) findViewById(R.id.question_6);
		question_6.setOnClickListener(this);
		
		TextView question_7 = (TextView) findViewById(R.id.question_7);
		question_7.setOnClickListener(this);
		//初始化返回并设置监听
		LinearLayout reLinearLayout = (LinearLayout) findViewById(R.id.commonquestion_returnback);
		reLinearLayout.setOnClickListener(this);
	}
	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.question_1:
			showAndhiddenAnswer(R.id.answer_1);
			break;
		
		case R.id.question_2:
			showAndhiddenAnswer(R.id.answer_2);
			break;
			
		case R.id.question_3:
			showAndhiddenAnswer(R.id.answer_3);
			break;
				
		case R.id.question_4:
			showAndhiddenAnswer(R.id.answer_4);
			break;
				
		case R.id.question_5:
			showAndhiddenAnswer(R.id.answer_5);
			break;
				
		case R.id.question_6:
			showAndhiddenAnswer(R.id.answer_6);
			break;
				
		case R.id.question_7:
			showAndhiddenAnswer(R.id.answer_7);
			break;
		
		case R.id.commonquestion_returnback:
			CommonQuestionActivity.this.finish();
			overridePendingTransition(R.anim.slide_right_in,
					R.anim.slide_right_out);
			break;
			
		default:
			break;
		}
	}
	/**
	 * 展开关闭答案
	 */
	public void showAndhiddenAnswer(int id){
		TextView ansTextView = (TextView) findViewById(id);
		if (ansTextView.getVisibility()==View.VISIBLE) {
			ansTextView.setVisibility(View.GONE);
		}else {
			ansTextView.setVisibility(View.VISIBLE);
		}
		
	}
}
