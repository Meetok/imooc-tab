package com.Meetok.Activity;

import org.json.JSONException;
import org.json.JSONObject;

import com.Meetok.Custom.CustomCheckMobile;
import com.Meetok.Custom.CustomCountTimer;
import com.Meetok.Custom.CustomDialog;
import com.Meetok.Tab.ImmersionBar;
import com.Meetok.Tab.MainActivity;
import com.Meetok.config.Config;
import com.ab.http.AbHttpUtil;
import com.ab.http.AbRequestParams;
import com.ab.http.AbStringHttpResponseListener;
import com.ab.util.AbToastUtil;
import com.imooc.tab03.R;
import com.imooc.tab03.R.string;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView.FindListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends Activity implements OnClickListener{
	private Button regNextButton;//注册下一步按钮
	private Button getverificationcodeButton;//获取验证码按钮
	private static AbHttpUtil httpUtil = null;
	private MyCountDownTimer timer;
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		ImmersionBar.setImmersionBar(this, R.color.statusbar_bg);
		httpUtil = AbHttpUtil.getInstance(RegisterActivity.this);
		httpUtil.setTimeout(10000);
		initView();
	}
	// 初始化组件
	private void initView() {
		regNextButton = (Button) findViewById(R.id.regNextBT);// 初始化注册下一步按钮
		getverificationcodeButton = (Button) findViewById(R.id.register_getverificationcode_bt);//初始化获取验证码按钮
		
		// 给注册下一步按钮设置点击监听
		regNextButton.setOnClickListener(this);
		//给获取验证码按钮设置点击监听
		getverificationcodeButton.setOnClickListener(this);
		//初始化关于我们的返回按钮，并且设置监听
		LinearLayout returnLinearLayout = (LinearLayout) findViewById(R.id.register_returnback);
		returnLinearLayout.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		//注册下一步按钮点击事件
		case R.id.regNextBT:
			nextStep();
			overridePendingTransition(R.anim.slide_left_in,
					R.anim.slide_left_out);
			break;
		//获取验证码点击事件
		case R.id.register_getverificationcode_bt:
			checkMobile(v);
			break;
		case R.id.register_returnback:
			RegisterActivity.this.finish();
			overridePendingTransition(R.anim.slide_right_in,
					R.anim.slide_right_out);
			break;
		default:
			break;
		}
		
	}
	/**
	 * 根据手机号码，和获取到的验证码，下一步进行密码设置
	 */
	public void nextStep(){
		EditText mobileEditText = (EditText) findViewById(R.id.register_mobile);
		EditText codeEditText = (EditText) findViewById(R.id.register_code);
		String mobileString = mobileEditText.getText().toString();
		String codeString = codeEditText.getText().toString();
		if (mobileString.length()!=11) {
			customCheckTip("电话号码位数不正确");
		}else if (CustomCheckMobile.checkMobile(mobileString)==false) {
			customCheckTip("手机号码不正确");
		}else if (codeString.length()!=6){
			customCheckTip("验证码位数不正确");
		}else if (mobileString.length()==11&&codeString.length()==6&&CustomCheckMobile.checkMobile(mobileString)==true) {
			//String string=returnString;
			checkCode(this,mobileString,"y",codeString);
		}
	}
	/**
	 * 验证验证码
	 * @return string
	 */
	public void checkCode(final Activity activity,String mobile, String checksame,String code){
		// TODO Auto-generated method stub
		String Msg = "{\"type\":\"dissignup\",\"mobile\":\"" + mobile
				+ "\",\"code\":\"" + code + "\",\"checksame\":\"" + checksame
				+ "\"}";
		AbRequestParams params = new AbRequestParams();
		params.put("Method", "checkcode");
		params.put("Accesstoken", "");
		params.put("Msg", Msg);
		httpUtil.post(com.Meetok.config.Config.F_BASE_URL, params,new AbStringHttpResponseListener() {

					@Override
					public void onStart() {
						// loading.showLoading();
					}

					@Override
					public void onFinish() {

					}

					@Override
					public void onFailure(int statusCode, String content,
							Throwable error) {
						AbToastUtil.showToast(activity, error.getMessage());
					}

					@Override
					public void onSuccess(int statusCode, String content) {
						// AbToastUtil.showToast(MainActivity.this, content);
						try {
							JSONObject json = new JSONObject(content);

							Object codeObject = json.opt("code");
							String codeString = String.valueOf(codeObject);
							if (codeString.equalsIgnoreCase("error")) {
								customCheckTip(String.valueOf(json.opt("errmsg")));
							} else if (codeString.equalsIgnoreCase("success")) {
								JSONObject data = json.optJSONObject("data");
//								String dataString = String.valueOf(data);
//								String[] dataStrings = dataString.split("\"");
//								String mobile = dataStrings[7];
//								String token = dataStrings[3];
								Object mobileObject= data.opt("mobile");
								Object tokenObject = data.opt("token");
								String mobile = String.valueOf(mobileObject) ;
								String token = String.valueOf(tokenObject) ;
								LogActivity.saveDataToLocalXML(RegisterActivity.this, "mobile",mobile);
								LogActivity.saveDataToLocalXML(RegisterActivity.this, "token",token);
								Intent intent = new Intent(RegisterActivity.this,RegisterCompeteActivity.class);//初始化注册下一步的Intent
								startActivity(intent);
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
	}
	/**
	 * 获取验证码前，做电话号码长度的判断，不对给出提示，电话号码长度正确获取验证码
	 */
	public void checkMobile(View view){
		EditText mobileEditText = (EditText) findViewById(R.id.register_mobile);
		String mobileString = mobileEditText.getText().toString();
		mobileString=mobileString.trim();
		if (mobileString.length()!=11) {
			customCheckTip("电话号码位数不正确");
		}else if (CustomCheckMobile.checkMobile(mobileString)==false) {
			customCheckTip("手机号码不正确");
		}
		else if (mobileString.length()==11&&CustomCheckMobile.checkMobile(mobileString)==true) {
			getMobileCode(this, mobileString, "y", view);
		}
	}
	/**
	 * 电话号码位数(验证码位数)不正确给出的提示框
	 */
	private void customCheckTip(String message){
		CustomDialog.Builder builder = new CustomDialog.Builder(this);  
	    builder.setMessage(message);  
	    builder.setTitle("http://m.meetok.com");  
	    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {  
	        public void onClick(DialogInterface dialog, int which) {  
	            dialog.dismiss();  
	        }  
	    });  

	    builder.create().show();  
	}
	/**
	 * 获取验证码方法
	 * @return 
	 */
	private void getMobileCode(final Activity activity,String mobile, String checksame,final View view) {
		// TODO Auto-generated method stub
		String Msg = "{\"type\":\"dissignup\",\"checksame\":\"" + checksame
				+ "\",\"mobile\":\"" + mobile + "\"}";
		AbRequestParams params = new AbRequestParams();
		params.put("Method", "sendcode");
		params.put("Accesstoken", "ae116efkk5aszff444ferfshy6oxi6");
		params.put("Msg", Msg);
		httpUtil.post(com.Meetok.config.Config.F_BASE_URL, params,
				new AbStringHttpResponseListener() {

					@Override
					public void onStart() {
						// loading.showLoading();
					}

					@Override
					public void onFinish() {

					}

					@Override
					public void onFailure(int statusCode, String content,
							Throwable error) {
						AbToastUtil.showToast(activity,
								error.getMessage());
					}

					@Override
					public void onSuccess(int statusCode, String content) {
						// AbToastUtil.showToast(MainActivity.this, content);
						try {
							JSONObject json = new JSONObject(content);

							Object codeObject = json.opt("code");
							String codeString = String.valueOf(codeObject);
							if (codeString.equalsIgnoreCase("error")) {
								customCheckTip("此电话号码已注册");
							}else if (codeString.equalsIgnoreCase("success")) {
								//改变按钮样式
								changeVerificationCodeBT(view, R.id.register_getverificationcode_bt);
								//提示发送成功
								Toast.makeText(getBaseContext(), R.string.codeSendSuccessfully, Toast.LENGTH_SHORT).show();
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
	}
	/**
	 * 获取验证码后的验证码按钮样式改变的方法
	 */
	public void changeVerificationCodeBT(View view,int btid){
		Button button = (Button) view.findViewById(btid);
//		button.setBackgroundColor(Color.parseColor("#C5C3C8"));
		button.setBackgroundResource(R.drawable.shape_30_gray);
		button.setEnabled(false);
		timer = new MyCountDownTimer(60000, 1000);
		timer.start();
	}
	/** 
	   * 继承 CountDownTimer 防范 
	   * 
	   * 重写 父类的方法 onTick() 、 onFinish() 
	   */
	  
	  class MyCountDownTimer extends CustomCountTimer { 
	    /** 
	     * 
	     * @param millisInFuture 
	     *      表示以毫秒为单位 倒计时的总数 
	     * 
	     *      例如 millisInFuture=1000 表示1秒 
	     * 
	     * @param countDownInterval 
	     *      表示 间隔 多少微秒 调用一次 onTick 方法 
	     * 
	     *      例如: countDownInterval =1000 ; 表示每1000毫秒调用一次onTick() 
	     * 
	     */
	    public MyCountDownTimer(long millisInFuture, long countDownInterval) { 
	      super(millisInFuture, countDownInterval); 
	    } 
	  
	    @Override
	    public void onFinish() { 
//	      tv.setText("done"); 
	    	Button button = (Button) findViewById(R.id.register_getverificationcode_bt);
	    	button.setBackgroundResource(R.drawable.shape_30_green);
			button.setText("再次获取验证码");
			button.setEnabled(true);
	    } 
	  
	    @Override
	    public void onTick(long millisUntilFinished) { 
//	      Log.i("MainActivity", millisUntilFinished + ""); 
			Button button = (Button) findViewById(R.id.register_getverificationcode_bt);
			button.setText("再次获取(" + millisUntilFinished / 1000 + ")秒");
	    } 
	  }
}
