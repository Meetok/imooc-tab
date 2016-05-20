package com.Meetok.Activity;

import org.json.JSONException;
import org.json.JSONObject;

import com.Meetok.Custom.CustomCheckPassword;
import com.Meetok.Custom.CustomDialog;
import com.Meetok.Tab.ImmersionBar;
import com.Meetok.Tab.MainActivity;
import com.ab.http.AbHttpUtil;
import com.ab.http.AbRequestParams;
import com.ab.http.AbStringHttpResponseListener;
import com.ab.util.AbToastUtil;
import com.imooc.tab03.R;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class RetrievePasswordCompeteActivity extends Activity implements OnClickListener{
	private TextView mobileTextView;
	private EditText passwordEditText1;
	private EditText passwordEditText2;
	private static AbHttpUtil httpUtil = null;
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.retrievepasswordcompete);
		ImmersionBar.setImmersionBar(this, R.color.statusbar_bg);
		httpUtil = AbHttpUtil.getInstance(RetrievePasswordCompeteActivity.this);
		httpUtil.setTimeout(10000);
		mobileTextView = (TextView) findViewById(R.id.retrievepasswordcompete_mobile_tv);
		String mobileString = LogActivity.loadDataFromLocalXML(this, "mobile");
		mobileTextView.setText(mobileString);
		initView();
	}
	
	public void initView(){
		//保存找回密码按钮初始化，并且保存按钮设置监听
		Button rcButton = (Button) findViewById(R.id.retrievepasswordcompete_bt);
		rcButton.setOnClickListener(this);
		//初始化关于我们的返回按钮，并且设置监听
		LinearLayout returnLinearLayout = (LinearLayout) findViewById(R.id.retrievepasswordcompete_returnback);
		returnLinearLayout.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		// 保存点击事件
		case R.id.retrievepasswordcompete_bt:
			nologsetpwd();
			break;
			
		case R.id.retrievepasswordcompete_returnback:
			RetrievePasswordCompeteActivity.this.finish();
			overridePendingTransition(R.anim.slide_right_in,
					R.anim.slide_right_out);
			break;
		default:
			break;
		}
	}
	/**
	 * 保存的动作
	 */
	public void nologsetpwd(){
		passwordEditText1=(EditText) findViewById(R.id.retrievepasswordcompete_password_et);
		passwordEditText2=(EditText) findViewById(R.id.retrievepasswordcompete_passwordagain_et);
		String mobileString = (String) mobileTextView.getText();
		String passwordString1 = passwordEditText1.getText().toString().trim();
		String passwordString2 = passwordEditText2.getText().toString().trim();
		
			if (!passwordString1.equals(passwordString2)) {
				Toast toast = Toast.makeText(getBaseContext(), "密码不一致，修改密码失败",
						3000);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
				passwordEditText2.setText("");
				passwordEditText1.setText("");

			} else {
				if (passwordString1.length()>7&&CustomCheckPassword.checkPassword(passwordString1)==true) {
					nologsetpwdInterface(LogActivity.loadDataFromLocalXML(this, "token"),passwordString1);
				}else {
					CustomDialog.Builder builder = new CustomDialog.Builder(this);  
				    builder.setMessage("密码至少8位，数字+字母格式");  
				    builder.setTitle("http://m.meetok.com");  
				    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {  
				        public void onClick(DialogInterface dialog, int which) {  
				            dialog.dismiss(); 
				        }  
				    });  
				    builder.create().show();
					passwordEditText2.setText("");
					passwordEditText1.setText("");
				}
			}
		
	}
	/**
	 * 找回密码接口
	 */
	public void nologsetpwdInterface(String token,String pwd){
		// TODO Auto-generated method stub
				String Msg = "{\"token\":\"" + token + "\",\"pwd\":\""+pwd+"\"}";
				AbRequestParams params = new AbRequestParams();
				params.put("Method", "nologsetpwd");
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
								AbToastUtil.showToast(RetrievePasswordCompeteActivity.this,
										error.getMessage());
							}

							@Override
							public void onSuccess(int statusCode, String content) {
								// AbToastUtil.showToast(MainActivity.this, content);
								try {
									JSONObject json = new JSONObject(content);
									
									Object codeObject = json.opt("code");
									String codeString = String.valueOf(codeObject);
									Object dataObject = json.opt("accesstoken");
									String accesstokenString = String.valueOf(dataObject);
									if (codeString.equalsIgnoreCase("error")) {
										Toast.makeText(getBaseContext(), "服务器错误", 20000).show();
									}else if(accesstokenString.length()>0) {
										Intent intent = new Intent(RetrievePasswordCompeteActivity.this,
												LogActivity.class);
										LogActivity.saveDataToLocalXML(RetrievePasswordCompeteActivity.this,"accesstoken", "");
										LogActivity.saveDataToLocalXML(RetrievePasswordCompeteActivity.this, "token","");
										startActivity(intent);
//										finish();
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
