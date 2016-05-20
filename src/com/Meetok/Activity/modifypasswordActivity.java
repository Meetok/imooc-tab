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
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class modifypasswordActivity extends Activity implements OnClickListener{
	private EditText originalEditText;
	private EditText newEditText;
	private EditText newagainEditText;
	private AbHttpUtil httpUtil=null;
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.modifypassword);
		ImmersionBar.setImmersionBar(this, R.color.statusbar_bg);
		httpUtil = AbHttpUtil.getInstance(modifypasswordActivity.this);
		httpUtil.setTimeout(10000);
		initView();
	}
	public void initView(){
		//初始化提交修改按钮，并设置监听
		Button button = (Button) findViewById(R.id.modifypassword_commit);
		button.setOnClickListener(this);
		//初始化返回按钮，并且设置监听
		LinearLayout returnLinearLayout = (LinearLayout) findViewById(R.id.modifypassword_returnback);
		returnLinearLayout.setOnClickListener(this);
		//初始化三个editText
		originalEditText = (EditText) findViewById(R.id.modifypassword_originalpassword_et);
		newEditText = (EditText) findViewById(R.id.modifypassword_newpassword_et);
		newagainEditText = (EditText) findViewById(R.id.modifypassword_newpasswordagain_et);
	}
	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.modifypassword_returnback:
			modifypasswordActivity.this.finish();
			overridePendingTransition(R.anim.slide_right_in,
					R.anim.slide_right_out);
			break;
			
		case R.id.modifypassword_commit:
			String oldPasswordString =originalEditText.getText().toString().trim();
			String newPasswordString = newEditText.getText().toString().trim();
			String newPasswordagainString = newagainEditText.getText().toString().trim();
			if (oldPasswordString.length() > 0 && newPasswordString.length() > 0
					&& newPasswordagainString.length() > 0) {
				if (checkPasswordSame()) {
					if (newPasswordagainString.length()>7&&CustomCheckPassword.checkPassword(newPasswordagainString)==true) {
						setpwdInterface(originalEditText.getText().toString().trim(),
							newEditText.getText().toString().trim());
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
					    newagainEditText.setText("");
					    newEditText.setText("");
					}
					
				} else {
					Toast.makeText(getBaseContext(), "新密码与确认密码不一致", 3000).show();
					newagainEditText.setText("");
					newEditText.setText("");

					newEditText.setFocusableInTouchMode(true);
				}
			} else {
				
			}
			break;
			
		default:
			break;
		}
		
		
	}
	/**
	 * 判断新密码是否一致
	 * @return true为一致，false为不一致
	 */
	public Boolean checkPasswordSame(){
		
		return newEditText.getText().toString().trim().equalsIgnoreCase(newagainEditText.getText().toString().trim());
		
	}
	/**
	 * 重置密码接口
	 */
	public void setpwdInterface(String oldPwd,String newPwd){
		
		String Msg = "{\"oldpwd\":\"" + oldPwd + "\",\"newpwd\":\"" + newPwd + "\"}";
		AbRequestParams params = new AbRequestParams();
		params.put("Method", "setpwd");
		params.put("Accesstoken", LogActivity.loadDataFromLocalXML(this, "accesstoken"));
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
						AbToastUtil.showToast(modifypasswordActivity.this,
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
								Toast.makeText(getBaseContext(), String.valueOf(json.opt("errmsg")), 2000).show();
							}else if(codeString.equals("session lose")){
								Object er = json.opt("errmsg");
								String errmsg = String.valueOf(er);
								CustomDialog.Builder builder = new CustomDialog.Builder(modifypasswordActivity.this);  
							    builder.setMessage(errmsg);  
							    builder.setTitle("提示");  
							    builder.setPositiveButton("重新登录", new DialogInterface.OnClickListener() {  
							        public void onClick(DialogInterface dialog, int which) { 
							        	Intent intent = new Intent(modifypasswordActivity.this,LogActivity.class);
							        	//startActivity(intent);
							        	//getActivity().finish();
							        	startActivityForResult(intent, 0);							        	
							        	finish();
							        	overridePendingTransition(R.anim.slide_left_in,
							    				R.anim.slide_left_out);
							        }  
							    });

							    builder.create().show(); 
							}else if(accesstokenString.length()>0) {
								Intent intent = new Intent(modifypasswordActivity.this,
										MainActivity.class);
								Bundle bundle = new Bundle();
								bundle.putInt("fragment", 5);
								LogActivity.saveDataToLocalXML(modifypasswordActivity.this,"accesstoken", accesstokenString);
								intent.putExtras(bundle);
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
}
