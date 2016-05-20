package com.Meetok.Activity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.Meetok.Entity.ParseJSONTools;
import com.Meetok.Entity.SaleEntity;
import com.Meetok.Entity.SaleXQEntity;
import com.Meetok.Tab.ImmersionBar;
import com.Meetok.adapter.Adapter_sale01;
import com.Meetok.adapter.Adapter_sale_xq;
import com.ab.http.AbHttpUtil;
import com.ab.http.AbRequestParams;
import com.ab.http.AbStringHttpResponseListener;
import com.ab.util.AbToastUtil;
import com.imooc.tab03.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class Sale_xiangqing extends Activity {
	private ImageView button_back;
	private TextView sa_code;
	private TextView sa_shopname;
	private TextView sa_created;
	private TextView sa_paytime;
	private TextView sa_buyernick;
	private TextView sa_logisticsname;
	private TextView sa_trackingnumber;
	private TextView sa_receivername;
	private TextView sa_receivermobile;
	private TextView sa_receiver;
	private TextView sa_receiveraddress;
	private TextView sa_idnum;
	private TextView sa_postfee;
	private TextView sa_payment;
	private TextView sa_rate;
	private TextView sa_name;
	private ListView mListView;
	private String sale_id;//传过来的id

	private static AbHttpUtil httpUtil = null;
	private Adapter_sale_xq adapter_sale_xq;
	List<SaleXQEntity> list_xq = new ArrayList<SaleXQEntity>();
	private static String BASE_ACCESS;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sale_xiangqing);
		ImmersionBar.setImmersionBar(this, R.color.statusbar_bg);
		View view = LayoutInflater.from(Sale_xiangqing.this).inflate(
				R.layout.sale_xiangqing, null);
		button_back = (ImageView) findViewById(R.id.button_back);
		button_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Sale_xiangqing.this.finish();
				overridePendingTransition(R.anim.slide_right_in,
						R.anim.slide_right_out);
			}
		});
		 BASE_ACCESS = LogActivity.loadDataFromLocalXML(Sale_xiangqing.this, "accesstoken");
		Intent intent = getIntent();
		sale_id = intent.getStringExtra("sale_id");
		httpUtil = AbHttpUtil.getInstance(Sale_xiangqing.this);
		httpUtil.setTimeout(10000);	
		
		initview();
		getdate(sale_id);
	}	
	private void initview() {
		// TODO Auto-generated method stub
		sa_code = (TextView) findViewById(R.id.sa_code);
		sa_shopname =(TextView) findViewById(R.id.sa_shopname);
		sa_created = (TextView) findViewById(R.id.sa_created);
		sa_paytime =(TextView) findViewById(R.id.sa_paytime);
		sa_buyernick = (TextView) findViewById(R.id.sa_buyernick);
		sa_logisticsname =(TextView) findViewById(R.id.sa_logisticsname);
		sa_trackingnumber = (TextView) findViewById(R.id.sa_trackingnumber);
		sa_receivername =(TextView) findViewById(R.id.sa_receivername);
		sa_receivermobile = (TextView) findViewById(R.id.sa_receivermobile);
		sa_name =(TextView) findViewById(R.id.sa_name);
		sa_receiver = (TextView) findViewById(R.id.sa_receiver);
		sa_receiveraddress =(TextView) findViewById(R.id.sa_receiveraddress);
		sa_idnum = (TextView) findViewById(R.id.sa_idnum);
		sa_postfee =(TextView) findViewById(R.id.sa_postfee);
		sa_rate = (TextView) findViewById(R.id.sa_rate);
		sa_payment =(TextView) findViewById(R.id.sa_payment);
		mListView = (ListView) findViewById(R.id.mylist);
	}
	/**
	 * 4、销售订单详情接口
	 * @param sale_id
	 */
	private void getdate(String sale_id) {
		// TODO Auto-generated method stub
		AbRequestParams params = new AbRequestParams();
		params.put("Method", "sendlist.getsalesorderdetail");
		params.put("Accesstoken",BASE_ACCESS);		
		String msgsearch = "{" + "\"" + "id" + "\"" + ":" + "\"" +sale_id+ "\""+"}";
		params.put("Msg", msgsearch);
		httpUtil.post(com.Meetok.config.Config.F_BASE_URL, params,
				new AbStringHttpResponseListener() {

					@Override
					public void onStart() {
						
					}

					@Override
					public void onFinish() {

					}

					@Override
					public void onFailure(int statusCode, String content,
							Throwable error) {
						AbToastUtil.showToast(Sale_xiangqing.this,
								error.getMessage());
					}

					@Override
					public void onSuccess(int statusCode, String content) {
						// AbToastUtil.showToast(MainActivity.this, content);
						try {
							JSONObject json = new JSONObject(content);
							JSONObject data = json.optJSONObject("data");					
							//JSONArray data2 = data.getJSONArray("data");
							//JSONArray data1 = data.optJSONArray("dataitem");
							SaleXQEntity s1 = (SaleXQEntity) ParseJSONTools
										.getInstance().fromJsonToJava(
												data, SaleXQEntity.class);
								s1.code = data.optString("code");
								s1.shopname = data.optString("shopname");
								s1.created = data.getString("created");
								s1.paytime = data.getString("paytime");
								s1.buyernick = data.getString("buyernick");
								s1.logisticsname = data.getString("logisticsname");
								s1.trackingnumber = data.getString("trackingnumber");
								s1.receivername = data.getString("receivername");
								s1.receivermobile = data.getString("receivermobile");
								s1.receiverstate =data.optString("receiverstate");
								s1.receivercity =data.optString("receivercity");
								s1.receiverdistrict =data.optString("receiverdistrict");
								
								String receiver = s1.receiverstate+s1.receivercity+s1.receiverdistrict;
								s1.receiveraddress = data.getString("receiveraddress");
								s1.idnum = data.getString("idnum");
								s1.name = data.optString("name");
								s1.postfee = data.getString("postfee");
								s1.payment = data.getDouble("payment");
								s1.rate = data.getDouble("rate");
								s1.isxxdp = data.optString("isxxdp");
								//T是线下店铺,F不是 该字段是用来判断商品信息是否可修改在修改订单接口中需要用到
								double p = s1.payment;
								double r = s1.rate;
								BigDecimal b= new BigDecimal(p);  
								BigDecimal ra= new BigDecimal(r);
								double payment = b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
								double rate = ra.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
								sa_code.setText(s1.code);
								sa_shopname.setText(s1.shopname);
								sa_created.setText(s1.created);
								sa_paytime.setText(s1.paytime);
								sa_buyernick.setText(s1.buyernick.split("/")[0]);
								sa_logisticsname.setText(s1.logisticsname);
								sa_trackingnumber.setText(s1.trackingnumber);
								sa_receivername.setText(s1.receivername);
								sa_receivermobile.setText(s1.receivermobile);
								sa_receiver.setText(receiver);
								sa_receiveraddress.setText(s1.receiveraddress);
								sa_idnum.setText(s1.idnum);
								sa_name.setText(s1.name);
								sa_postfee.setText(s1.postfee);
								sa_payment.setText(String.valueOf(payment));
								sa_rate.setText(String.valueOf(rate));
								JSONArray dataitem = data.optJSONArray("dataitem");
								for (int j = 0; j < dataitem.length(); j++) {
									JSONObject itObj = dataitem.optJSONObject(j);
									SaleXQEntity s2 = (SaleXQEntity) ParseJSONTools
											.getInstance().fromJsonToJava(
													itObj, SaleXQEntity.class);
									s2.Code = itObj.optString("Code");
									s2.Name = itObj.optString("Name");
									s2.Quantity = itObj.optString("Quantity");
									s2.Price = itObj.optDouble("Price");
									s2.Rate = itObj.optString("Rate");
									list_xq.add(s2);
								}							
								adapter_sale_xq = new Adapter_sale_xq(
										Sale_xiangqing.this, list_xq);
								mListView.setAdapter(adapter_sale_xq);
						
							// adapter_search.notifyDataSetChanged();
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
