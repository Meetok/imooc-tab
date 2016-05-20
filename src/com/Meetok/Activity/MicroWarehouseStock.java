package com.Meetok.Activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.Meetok.Custom.CustomDialog;
import com.Meetok.Entity.OrderEntity;
import com.Meetok.Entity.ParseJSONTools;
import com.Meetok.Entity.SaleEntity;
import com.Meetok.Tab.ImmersionBar;
import com.Meetok.Tab.MainActivity;
import com.Meetok.View.MyGridView;
import com.Meetok.adapter.Adapter_microwarehousestock;
import com.Meetok.adapter.Adapter_order;
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
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MicroWarehouseStock extends Activity implements OnClickListener{
	private AbHttpUtil httpUtil = null;
	private TextView ptCode;
	private TextView ptTitle;
	private TextView ptStockQuantity;
	private TextView ptUseQuantity;
	private EditText searchEditText;
	List<SaleEntity> mlistxq = new ArrayList<SaleEntity>();
	private GridView micro_stockGridView;
	private Adapter_microwarehousestock microwarehousestock_adapter;
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.micro_warehouse_stock);
		ImmersionBar.setImmersionBar(this, R.color.statusbar_bg);
		httpUtil = AbHttpUtil.getInstance(this);
		httpUtil.setTimeout(10000);
		getdata("");
		initview();
		
	}
	public void initview(){
		// 初始化关于我们的返回按钮，并且设置监听
		LinearLayout returnLinearLayout = (LinearLayout) findViewById(R.id.micro_warehouse_stock_returnback);
		returnLinearLayout.setOnClickListener(this);
		//初始化搜索按钮，并且设置监听
		Button searchButton = (Button) findViewById(R.id.micro_stock_search_bt);
		searchButton.setOnClickListener(this);
		//初始化搜索编辑框
		searchEditText = (EditText) findViewById(R.id.micro_stock_product_et);
		//给要显示的信息控件初始化
		micro_stockGridView = (GridView) findViewById(R.id.micro_stock_gridview);
		
	}
	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.micro_warehouse_stock_returnback:
			MicroWarehouseStock.this.finish();
			overridePendingTransition(R.anim.slide_right_in,
					R.anim.slide_right_out);
			break;
		
		case R.id.micro_stock_search_bt:
			searchProduct();
			break;
			
		default:
			break;
		}
	}
	/**
	 * 搜索我的微仓库存的方法
	 */
	public void searchProduct(){
		mlistxq.clear();
		String searchString = searchEditText.getText().toString();
		getdata(searchString);
	}
	/**
	 * 我的微仓库存接口
	 */
	public void getdata(String search){
		String Msg = "{\"search\":\"" + search + "\"}";
		String accesstoken = LogActivity.loadDataFromLocalXML(this, "accesstoken");
		AbRequestParams params = new AbRequestParams();

		params.put("Method", "report.getproductstock");
		params.put("Accesstoken", accesstoken);
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
						AbToastUtil.showToast(MicroWarehouseStock.this, error.getMessage());
					}

					@Override
					public void onSuccess(int statusCode, String content) {
						// AbToastUtil.showToast(MainActivity.this, content);
						try {
							JSONObject json = new JSONObject(content);
							Object co = json.opt("code");
							String mcode = String.valueOf(co);//session lose errmsg
							if(mcode.equals("session lose")){
								Object er = json.opt("errmsg");
								String errmsg = String.valueOf(er);
								CustomDialog.Builder builder = new CustomDialog.Builder(MicroWarehouseStock.this);  
							    builder.setMessage(errmsg);  
							    builder.setTitle("提示");  
							    builder.setPositiveButton("重新登录", new DialogInterface.OnClickListener() {  
							        public void onClick(DialogInterface dialog, int which) { 
							        	Intent intent = new Intent(MicroWarehouseStock.this,LogActivity.class);
							        	startActivityForResult(intent, 0);							        	
							        	finish();
							        	overridePendingTransition(R.anim.slide_left_in,
							    				R.anim.slide_left_out);
							        }  
							    });  
							    
							    builder.create().show(); 
 
							}else{
							JSONArray productstockJsonArray = json.optJSONArray("data");

							for (int i = 0; i < productstockJsonArray.length(); i++) {
								JSONObject itemObj = productstockJsonArray.optJSONObject(i);

								SaleEntity se = (SaleEntity) ParseJSONTools
										.getInstance().fromJsonToJava(itemObj,
												SaleEntity.class);
								se.Title = itemObj.optString("Title");
								se.Code = itemObj.optString("Code");
								se.Stock = itemObj.optString("Stock");
								se.StockQuantity = itemObj.optInt("StockQuantity");
								se.SendQuantity = itemObj.optString("SendQuantity");
								se.NoSendQuantity = itemObj.optString("NoSendQuantity");
								se.UseQuantity = itemObj.optString("UseQuantity");
								se.Sort = itemObj.optString("Sort");
								mlistxq.add(se);
							}
							}
							microwarehousestock_adapter = new Adapter_microwarehousestock(MicroWarehouseStock.this,
									mlistxq);
							micro_stockGridView.setAdapter(microwarehousestock_adapter);
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
