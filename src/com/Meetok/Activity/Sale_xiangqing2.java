package com.Meetok.Activity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.Meetok.Custom.CustomDialog;
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
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class Sale_xiangqing2 extends Activity implements OnClickListener {
	private ImageView button_back;
	private TextView sa_code;
	private TextView sa_shopname;
	private TextView sa_created;
	private TextView sa_paytime;
	private TextView sa_buyernick;
	private TextView sa_logisticsname;
	private TextView sa_trackingnumber;
	private EditText sa_receivername;
	private EditText sa_receivermobile;
	private TextView sa_receiver;
	private EditText sa_receiveraddress;
	private EditText sa_idnum;//身份证号
	private EditText sa_name;//身份证姓名
	private TextView sa_postfee;
	private TextView sa_payment;
	private TextView sa_rate;
	private TextView s_save;//保存
	private ListView mListView;
	private String sale_id;//传过来的id

	private static AbHttpUtil httpUtil = null;
	private Adapter_sale_xq adapter_sale_xq;
	List<SaleXQEntity> list_xq = new ArrayList<SaleXQEntity>();
	protected JSONObject changedata;
	protected String str_data;
	private static String BASE_ACCESS;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sale_xiangqing2);
		ImmersionBar.setImmersionBar(this, R.color.statusbar_bg);
		View view = LayoutInflater.from(Sale_xiangqing2.this).inflate(
				R.layout.sale_xiangqing2, null);
		button_back = (ImageView) findViewById(R.id.button_back);
		button_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Sale_xiangqing2.this.finish();
				overridePendingTransition(R.anim.slide_right_in,
						R.anim.slide_right_out);
			}
		});
		 BASE_ACCESS = LogActivity.loadDataFromLocalXML(Sale_xiangqing2.this, "accesstoken");
		Intent intent = getIntent();
		sale_id = intent.getStringExtra("sale_id");
		System.out.println(sale_id);
		httpUtil = AbHttpUtil.getInstance(Sale_xiangqing2.this);
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
		sa_receivername =(EditText) findViewById(R.id.sa_receivername);
		sa_receivermobile = (EditText) findViewById(R.id.sa_receivermobile);
		sa_name =(EditText) findViewById(R.id.sa_name);
		sa_receiver = (TextView) findViewById(R.id.sa_receiver);
		sa_receiveraddress =(EditText) findViewById(R.id.sa_receiveraddress);
		sa_idnum = (EditText) findViewById(R.id.sa_idnum);
		sa_postfee =(TextView) findViewById(R.id.sa_postfee);
		sa_rate = (TextView) findViewById(R.id.sa_rate);
		sa_payment =(TextView) findViewById(R.id.sa_payment);
		s_save = (TextView) findViewById(R.id.s_save);
		mListView = (ListView) findViewById(R.id.mylist);
		
		s_save.setOnClickListener(this);
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
						AbToastUtil.showToast(Sale_xiangqing2.this,
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
							changedata = data;
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
										Sale_xiangqing2.this, list_xq);
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
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.s_save:
			getmsg();//要配置的字符串
			
			getneworder();
			break;

		default:
			break;
		}
	}
	/**
	 * 保存修改订单
	 */
	private void getneworder() {
		// TODO Auto-generated method stub

		AbRequestParams params = new AbRequestParams();

		params.put("Method", "sendlist.saveorder");
		params.put("Accesstoken", BASE_ACCESS);

		params.put("Msg", str_data);
		// params.put("Msg", "code","code_order");
		httpUtil.post(com.Meetok.config.Config.F_BASE_URL, params,
				new AbStringHttpResponseListener() {

					private String Code_n;
					private String id;

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
						AbToastUtil.showToast(Sale_xiangqing2.this,
								error.getMessage());
					}

					@Override
					public void onSuccess(int statusCode, String content) {
						// AbToastUtil.showToast(MainActivity.this, content);
						try {
							JSONObject json = new JSONObject(content);
							Object co = json.opt("code");
							String mcode = String.valueOf(co);// session lose
							if (mcode.equals("error")) {
								Object emsg = json.opt("errmsg");
								String errmsg = String.valueOf(emsg);
								System.out.println("订单信息填充不全"+errmsg);
								//showtishi(errmsg);
							} else {

								JSONObject data = json.optJSONObject("data");
								Object sa_id = data.opt("id");
								Object sa_msg = data.opt("msg");
								id = (String) sa_id;
								String msg = (String) sa_msg;
								System.out.println("新增订单/修改结果===" + msg + id);
								CustomDialog.Builder builder = new CustomDialog.Builder(Sale_xiangqing2.this);
								builder.setMessage(msg);
								builder.setTitle("提示");
								builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog, int which) {
										 Sale_xiangqing2.this.finish();
										dialog.dismiss();
									}
								});
								builder.create().show();
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
	 * 配置发送的字符串
	 */
	private void getmsg() {
		// TODO Auto-generated method stub
		try {
			
			SaleXQEntity s1 = (SaleXQEntity) ParseJSONTools
					.getInstance().fromJsonToJava(
							changedata, SaleXQEntity.class);
			s1.receivername = sa_receivername.getText().toString().trim();
			System.out.println(s1.receivername);
			s1.receivermobile = sa_receivermobile.getText().toString().trim();
			s1.receiveraddress = sa_receiveraddress.getText().toString().trim();
			s1.idnum = sa_idnum.getText().toString().trim();
			s1.name = sa_name.getText().toString().trim();
			
			changedata.put("receivername", s1.receivername);
			changedata.put("receivermobile", s1.receivermobile);
			changedata.put("receiveraddress", s1.receiveraddress);
			changedata.put("idnum", s1.idnum);
			changedata.put("name", s1.name);
			String m ="\"id\":"+"\""+sale_id+"\",";
			str_data =changedata.toString();
			str_data = str_data.substring(0, 1) + m + str_data.substring(1, str_data.length());
			str_data = str_data.replaceFirst("dataitem", "products");
			System.out.println(str_data);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
