package com.Meetok.Activity;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.Meetok.Entity.OrderEntity;
import com.Meetok.Entity.ParseJSONTools;
import com.Meetok.Tab.ImmersionBar;
import com.Meetok.View.MyGridView;
import com.Meetok.adapter.Adapter_order;

import com.ab.http.AbHttpUtil;
import com.ab.http.AbRequestParams;
import com.ab.http.AbStringHttpResponseListener;
import com.ab.util.AbToastUtil;
import com.imooc.tab03.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class TuWen_xiangqing extends Activity {

	
	private TextView text1;
	private WebView webView;
	private ImageView button;
	List<OrderEntity> mlisttu = new ArrayList<OrderEntity>();

	private AbHttpUtil httpUtil = null;
	protected String imgurl;

	@SuppressLint("NewApi") @Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tuwen_image_grid);
		ImmersionBar.setImmersionBar(this, R.color.statusbar_bg);
		
		webView = (WebView) findViewById(R.id.web);		
		WebSettings webSettings= webView.getSettings(); //     
		webSettings.setJavaScriptCanOpenWindowsAutomatically(true);  
		webSettings.setUseWideViewPort(true);//自适应
		webSettings.setLoadWithOverviewMode(true); //自适应
		webSettings.setLayoutAlgorithm(LayoutAlgorithm.NARROW_COLUMNS);  		      
		webSettings.setDisplayZoomControls(false);  
		webSettings.setJavaScriptEnabled(true); // 
		webSettings.setAllowFileAccess(true); // 
		webSettings.setBuiltInZoomControls(true); //
		webSettings.setSupportZoom(true); //
		
		button = (ImageView)findViewById(R.id.button_back);
		button.setOnClickListener(new OnClickListener(){
			public void onClick(View v)
			{
				TuWen_xiangqing.this.finish();
				  overridePendingTransition(R.anim.slide_right_in,  
		                    R.anim.slide_right_out);  
			}	
			});
		Intent intent = getIntent();
		String code_order = intent.getStringExtra("tuwenxq_code");

		httpUtil = AbHttpUtil.getInstance(this);
		httpUtil.setTimeout(10000);
		getdata(code_order);
		
	}

	private void getdata(String code_order) {
		// TODO Auto-generated method stub
		AbRequestParams params = new AbRequestParams();

		params.put("Method", "product.getproductinfo");
		params.put("Accesstoken", "");

		params.put("Msg", code_order);
		// params.put("Msg", "code","code_order");
		httpUtil.post(com.Meetok.config.Config.F_BASE_URL, params,
				new AbStringHttpResponseListener() {

					@Override
					public void onStart() {
						// loading.showLoading();

					}

					@Override
					public void onFinish() {
						System.out.println(".1111111");
					}

					@Override
					public void onFailure(int statusCode, String content,
							Throwable error) {
						AbToastUtil.showToast(TuWen_xiangqing.this,
								error.getMessage());
					}

					@Override
					public void onSuccess(int statusCode, String content) {
						// AbToastUtil.showToast(MainActivity.this, content);
						try {
							JSONObject json = new JSONObject(content);

							JSONObject json_main = json.optJSONObject("data");
							// JSONArray data = json_main.optJSONArray("main");

							JSONArray main = json_main.optJSONArray("main");
							for (int i = 0; i < main.length(); i++) {
								JSONObject itemObj = main.optJSONObject(i);

								//JSONArray Description = itemObj.optJSONArray("Description");
								//for (int j = 0; j < Description.length(); j++) {
									OrderEntity or = (OrderEntity) ParseJSONTools
											.getInstance().fromJsonToJava(
													itemObj, OrderEntity.class);
									or.Description = itemObj.optString("Description");
									 imgurl = or.Description;
									System.out.println(".1111111"+imgurl);
																	
							}
						
							webView.loadData(imgurl, "text/html", "UTF-8"); 
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
