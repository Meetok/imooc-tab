package com.Meetok.Activity;

import java.security.PublicKey;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.Meetok.Custom.CustomDialog;
import com.Meetok.Entity.ParseJSONTools;
import com.Meetok.Entity.ShouyeEntity;
import com.Meetok.Tab.IBtnCallListener;
import com.Meetok.Tab.ImmersionBar;
import com.Meetok.Tab.MainActivity;
import com.Meetok.adapter.Adapter_home1;
import com.Meetok.adapter.Adapter_home_all;
import com.Meetok.config.Config;
import com.Meetok.fragment.MeFragment;
import com.ab.http.AbHttpUtil;
import com.ab.http.AbRequestParams;
import com.ab.http.AbStringHttpResponseListener;
import com.ab.util.AbAppUtil;
import com.ab.util.AbToastUtil;
import com.imooc.tab03.R;
import com.imooc.tab03.R.string;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LogActivity extends Activity implements OnClickListener,
		IBtnCallListener {
	public static final int RESULT_OK_Log = 1110;
	private TextView retrievePasswordTV;// 定义找回密码的TextView
	private TextView registerTV;// 定义注册的TextView
	private Button loginBT;// 定义立即登录的button
	private EditText mobileET;// 定义登入电话（账号）的EditText
	private EditText passwordET;// 定义登录密码的EditText
	private AbHttpUtil httpUtil = null;
//	public static String staticname = "";
//	public static String staticmobile = "";

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		ImmersionBar.setImmersionBar(this, R.color.statusbar_bg);
		httpUtil = AbHttpUtil.getInstance(LogActivity.this);
		httpUtil.setTimeout(10000);
		initView();
	}

	// 初始化组件
	private void initView() {
		retrievePasswordTV = (TextView) findViewById(R.id.retrievePassword);// 初始化找回密码按钮
		registerTV = (TextView) findViewById(R.id.register);// 初始化注册按钮
		loginBT = (Button) findViewById(R.id.loginBT);// 初始化立即登入按钮
		// 给找回密码和注册设置点击监听
		retrievePasswordTV.setOnClickListener(this);
		registerTV.setOnClickListener(this);
		// 给立即登录按钮设置点击事件监听
		loginBT.setOnClickListener(this);
		//初始化关于我们的返回按钮，并且设置监听
		LinearLayout returnLinearLayout = (LinearLayout) findViewById(R.id.login_returnback);
		returnLinearLayout.setOnClickListener(this);

	}

	@Override
	public void transferMsg() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.retrievePassword:
			Intent retrievePasswordIntent = new Intent(LogActivity.this,
					RetrievePasswordActivity.class);// 初始化找回密码的Intent
			startActivity(retrievePasswordIntent);
			break;

		case R.id.register:
			Intent registerIntent = new Intent(LogActivity.this,
					RegisterActivity.class);// 初始化注册的Intent
			startActivity(registerIntent);
			break;

		case R.id.loginBT:
			mobileET = (EditText) findViewById(R.id.mobilelogin);
			passwordET = (EditText) findViewById(R.id.passwordlogin);
			System.out.println(mobileET.getText());
			System.out.println(passwordET.getText());
			getdata(mobileET.getText().toString(), passwordET.getText()
					.toString().trim());
			break;
			
		case R.id.login_returnback:
			Intent i=new Intent( LogActivity.this, MainActivity.class);  
			Bundle bundle = new Bundle();
			bundle.putInt("fragment", 5);
			i.putExtras(bundle);
        	i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        	startActivity(i);
			overridePendingTransition(R.anim.slide_right_in,
					R.anim.slide_right_out);
			break;
			
		}

	}

	/*
	 * 此方法将密钥、用户电话、用户名等存入本地，以保持登入状态
	 */
	public static void saveDataToLocalXML(Activity activity,String key, String value) {
		SharedPreferences sp = activity.getSharedPreferences("localconfig",
				MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putString(key, value);
		editor.commit();
	}

	/*
	 * 此方法从本地取出 密钥、用户电话、用户名称等
	 */
	public static String loadDataFromLocalXML(Activity activity, String key) {
		SharedPreferences sp = activity.getSharedPreferences("localconfig",
				MODE_PRIVATE);
		String value = sp.getString(key, "").toString();
		return value;
	}

	/*
	 * 登录失败提示
	 */
	private void logonFailTip(){
		CustomDialog.Builder builder = new CustomDialog.Builder(this);  
	    builder.setMessage("用户名或密码错误");  
	    builder.setTitle("http://m.meetok.com");  
	    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {  
	        public void onClick(DialogInterface dialog, int which) {  
	            dialog.dismiss(); 
	        }  
	    });  
	    builder.create().show();
	}
	/**
	 * 登入接口
	 * @param mobile
	 * @param password
	 */
	private void getdata(final String mobile, String password) {
		// TODO Auto-generated method stub
		String Msg = "{\"mobile\":\"" + mobile + "\",\"password\":\""
				+ password + "\"}";
		AbRequestParams params = new AbRequestParams();
		params.put("Method", "logon");
		params.put("Accesstoken", "");
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
						AbToastUtil.showToast(LogActivity.this,
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
								logonFailTip();
							}else if(codeString.equalsIgnoreCase("success")) {
								JSONObject data = json.optJSONObject("data");
								Object nameObject= data.opt("Name");
								Object accesstokenObject = data.opt("accesstoken");
								String Username = String.valueOf(nameObject) ;
								String accesstoken = String.valueOf(accesstokenObject) ;
								saveDataToLocalXML(LogActivity.this,"name", Username);
								saveDataToLocalXML(LogActivity.this,"accesstoken", accesstoken);
								saveDataToLocalXML(LogActivity.this,"mobile", mobile);
								
								
								Intent i=new Intent( LogActivity.this, MainActivity.class);  
								Bundle bundle = new Bundle();
								bundle.putInt("fragment", 5);
								i.putExtras(bundle);
					        	i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					        	startActivity(i);
//					        	startActivityForResult(i,1);
					        	
								finish();
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

}
