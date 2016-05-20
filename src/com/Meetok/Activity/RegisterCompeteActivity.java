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

public class RegisterCompeteActivity extends Activity implements OnClickListener{
	private TextView mobileTextView;
	private EditText nameEditText;
	private EditText passwordEditText1;
	private EditText passwordEditText2;
	private static AbHttpUtil httpUtil = null;
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registercompete);
		ImmersionBar.setImmersionBar(this, R.color.statusbar_bg);
		mobileTextView = (TextView) findViewById(R.id.registercompete_mobile_tv);
		String mobileString = LogActivity.loadDataFromLocalXML(this, "mobile");
		mobileTextView.setText(mobileString);
		httpUtil = AbHttpUtil.getInstance(RegisterCompeteActivity.this);
		httpUtil.setTimeout(10000);
		initView();
	}
	
	public void initView(){
		//完成注册按钮初始化，并且完成注册按钮设置监听
		Button rcButton = (Button) findViewById(R.id.registercompete_bt);
		rcButton.setOnClickListener(this);
		//初始化关于我们的返回按钮，并且设置监听
		LinearLayout returnLinearLayout = (LinearLayout) findViewById(R.id.registercompete_returnback);
		returnLinearLayout.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		// 完成注册点击事件
		case R.id.registercompete_bt:
			singup();
			break;
			
		case R.id.registercompete_returnback:
			RegisterCompeteActivity.this.finish();
			overridePendingTransition(R.anim.slide_right_in,
					R.anim.slide_right_out);
			break;
			
		default:
			break;
		}
	}
	
	/**
	 * 完成注册的动作
	 */
	public void singup(){
		
		nameEditText = (EditText) findViewById(R.id.registercompete_name_et);
		passwordEditText1=(EditText) findViewById(R.id.registercompete_password_et);
		passwordEditText2=(EditText) findViewById(R.id.registercompete_passwordagain_et);
		String mobileString = (String) mobileTextView.getText();
		String nameString = nameEditText.getText().toString().trim();
		String passwordString1 = passwordEditText1.getText().toString().trim();
		String passwordString2 = passwordEditText2.getText().toString().trim();
		if (nameString.length()<=0) {
			Toast toast=Toast.makeText(getBaseContext(), "姓名必填*，注册失败", 3000);
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.show();
		}else {
			if (!passwordString1.equals(passwordString2)) {
				Toast toast = Toast.makeText(getBaseContext(), "密码不一致，注册失败",
						3000);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
				passwordEditText2.setText("");
				passwordEditText1.setText("");

			} else {
				if (passwordString1.length()>7&&CustomCheckPassword.checkPassword(passwordString1)==true) {
					singupInterface(LogActivity.loadDataFromLocalXML(this, "token"),nameString,passwordString1);
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
		
	}
	/**
	 * 注册接口
	 */
	public void singupInterface(String token,String name,String pwd){
		// TODO Auto-generated method stub
				String Msg = "{\"token\":\"" + token + "\",\"name\":\""
						+ name + "\",\"pwd\":\""+pwd+"\"}";
				AbRequestParams params = new AbRequestParams();
				params.put("Method", "singup");
				params.put("Accesstoken", "");
				params.put("Msg", Msg);
				LogActivity.saveDataToLocalXML(RegisterCompeteActivity.this,"name", name);
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
								AbToastUtil.showToast(RegisterCompeteActivity.this,
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
										Toast.makeText(getBaseContext(), "该手机号已注册", 20000).show();
									}else if(accesstokenString.length()>0) {
										Intent intent = new Intent(RegisterCompeteActivity.this,
												MainActivity.class);
										Bundle bundle = new Bundle();
										bundle.putInt("fragment", 5);
										LogActivity.saveDataToLocalXML(RegisterCompeteActivity.this,"accesstoken", accesstokenString);
										LogActivity.saveDataToLocalXML(RegisterCompeteActivity.this, "token","");
										intent.putExtras(bundle);
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
