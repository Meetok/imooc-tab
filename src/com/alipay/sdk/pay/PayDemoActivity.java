package com.alipay.sdk.pay;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.Meetok.Activity.AddSaleOrder;
import com.Meetok.Activity.LogActivity;
import com.Meetok.Activity.OrderNew;
import com.Meetok.Activity.OrderQueRen;
import com.Meetok.Custom.CustomDialog;
import com.Meetok.Entity.FuKuangEntity;
import com.Meetok.Entity.ParseJSONTools;
import com.Meetok.Tab.ImmersionBar;
import com.Meetok.Tab.MainActivity;
import com.Meetok.adapter.Adapter_already_zhu;
import com.Meetok.fragment.HomeFragment;
import com.Meetok.fragment.Pur.AlreadyPaid_P;
import com.ab.http.AbHttpUtil;
import com.ab.http.AbRequestParams;
import com.ab.http.AbStringHttpResponseListener;
import com.ab.util.AbToastUtil;
import com.alipay.sdk.pay.demo.ExternalPartner;
import com.alipay.sdk.pay.demo.Keys;
import com.alipay.sdk.pay.demo.PayResult;
import com.imooc.tab03.R;

public class PayDemoActivity extends FragmentActivity {

	// 商户PID
	public static final String PARTNER = Keys.DEFAULT_PARTNER;
	// 商户收款账号
	public static final String SELLER = Keys.DEFAULT_SELLER;
	// 商户私钥，pkcs8格式
	public static final String RSA_PRIVATE = Keys.PRIVATE;
	// 支付宝公钥
	public static final String RSA_PUBLIC = Keys.PUBLIC;

	private static final int SDK_PAY_FLAG = 1;

	private static final int SDK_CHECK_FLAG = 2;

	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SDK_PAY_FLAG: {
				PayResult payResult = new PayResult((String) msg.obj);

				// 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
				String resultInfo = payResult.getResult();

				String resultStatus = payResult.getResultStatus();

				// 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
				if (TextUtils.equals(resultStatus, "9000")) {
					//Toast.makeText(PayDemoActivity.this, "支付成功",Toast.LENGTH_SHORT).show();
					CustomDialog.Builder builder = new CustomDialog.Builder(PayDemoActivity.this);  
				    builder.setMessage("商品搬入您的微仓库存");  
				    builder.setTitle("采购已完成");  
				    builder.setPositiveButton("立即新增订单", new DialogInterface.OnClickListener() {  
				        public void onClick(DialogInterface dialog, int which) {  

				            dialog.dismiss();  
				            //设置你的操作事项  
				            Intent i=new Intent( PayDemoActivity.this, AddSaleOrder.class);  
				            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				        	startActivity(i);
		    				overridePendingTransition(R.anim.slide_right_in,
		    						R.anim.slide_right_out);
				        	finish();
				        }  
				    });  

				    builder.setNegativeButton("返回继续采购",  
				            new android.content.DialogInterface.OnClickListener() {  
				                public void onClick(DialogInterface dialog, int which) {  
				                	Intent i = new Intent(PayDemoActivity.this, MainActivity.class);
				    				i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				    				startActivityForResult(i, RESULT_OK);
				    				overridePendingTransition(R.anim.slide_right_in,
				    						R.anim.slide_right_out);
				                	dialog.dismiss();  
				                }  
				            });  

				    builder.create().show();  
				} else {
					// 判断resultStatus 为非“9000”则代表可能支付失败
					// “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
					if (TextUtils.equals(resultStatus, "8000")) {
						Toast.makeText(PayDemoActivity.this, "支付结果确认中",
								Toast.LENGTH_SHORT).show();

					} else {
						// 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
						//Toast.makeText(PayDemoActivity.this, "支付失败",Toast.LENGTH_SHORT).show();
						CustomDialog.Builder builder = new CustomDialog.Builder(PayDemoActivity.this);  
					    builder.setMessage("支付失败");  
					    builder.setTitle("http://m.meetok.com");  
					    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {  
					        public void onClick(DialogInterface dialog, int which) { 
					        	Intent i = new Intent(PayDemoActivity.this, MainActivity.class);
			    				i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			    				startActivityForResult(i, RESULT_OK);
			    				overridePendingTransition(R.anim.slide_right_in,
			    						R.anim.slide_right_out);
					            dialog.dismiss(); 
					        }  
					    });  
					    builder.create().show();
					}
				}
				break;
			}
			case SDK_CHECK_FLAG: {
				Toast.makeText(PayDemoActivity.this, "检查结果为：" + msg.obj,
						Toast.LENGTH_SHORT).show();
				break;
			}
			default:
				break;
			}
		};
	};
	private String payment;
	private String partner;//
	private String tid;// 订单号
	private String title = "采购进货";

	private ImageView button_back;
	private ImageView fanhui;
	private static String BASE_ACCESS;
	private String order_guid;
	private String sum;
	private String zfb_title;
	private String tid1;
	private TextView jin_pay;
	protected static String zfb_partner;
	protected static int zfb_key;
	protected static String zfb_seller;
	private static float zfb_payment;
	protected static String zfb_tid;
	private static AbHttpUtil httpUtil = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pay_main);
		ImmersionBar.setImmersionBar(this, R.color.statusbar_bg);
		/*
		 * Intent intent = getIntent(); payment =
		 * intent.getStringExtra("zfb_payment"); tid =
		 * intent.getStringExtra("zfb_tid"); title =
		 * intent.getStringExtra("zfb_title");
		 * System.out.println("sddddsssddssd" + tid+"..."+payment);
		 */
		button_back = (ImageView) findViewById(R.id.button_back);
		fanhui = (ImageView) findViewById(R.id.fanhui);

		/*
		 * findViewById(R.id.check).setOnClickListener(new OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { // TODO Auto-generated method
		 * stub
		 * 
		 * ExternalPartner.getInstance(PayDemoActivity.this,title, tid,
		 * mHandler, payment).check(); } });
		 */

		button_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				PayDemoActivity.this.finish();
				overridePendingTransition(R.anim.slide_right_in,
						R.anim.slide_right_out);
			}
		});

		fanhui.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//startActivity(new Intent(PayDemoActivity.this,HomeFragment.class));
				Intent i = new Intent(PayDemoActivity.this, MainActivity.class);
				i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivityForResult(i, RESULT_OK);
			}
		});
		BASE_ACCESS = LogActivity.loadDataFromLocalXML(PayDemoActivity.this,
				"accesstoken");
		Intent intent = getIntent();
		order_guid = intent.getStringExtra("order_guid");
		sum = intent.getStringExtra("zongjiage");
		zfb_title = intent.getStringExtra("zfb_title");
		zfb_tid =intent.getStringExtra("zfb_tid");
		System.out.println("sddddsssddssd" + order_guid);
		String reds = "\"" + "redpaperid" + "\"" + ":" + "\"" + 0 + "\"";
		String msgs = "{" + "\"" + "guids" + "\"" + ":" + "\"" + order_guid
				+ "\"" + "," + reds + "}";
		System.out.println("支付：----"+msgs);
		httpUtil = AbHttpUtil.getInstance(this);
		httpUtil.setTimeout(10000);

		if(zfb_tid!=""&&zfb_tid !=null){
			tid1 = zfb_tid;
		}else {
			getdata(msgs);
		}
			initview();
	}

	private void initview() {
		// TODO Auto-generated method stub
		jin_pay = (TextView) findViewById(R.id.jin_pay);
		jin_pay.setText(sum);
		findViewById(R.id.pay).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getdingdan(tid1);
				payment = sum;
				ExternalPartner.getInstance(PayDemoActivity.this, title, tid1,
						mHandler, payment).doOrder();
			}
		});
	}

	/**
	 * 订单保存
	 * 
	 * @param msgs
	 * @return
	 */
	private String getdata(String msgs) {
		// TODO Auto-generated method stub
		AbRequestParams params = new AbRequestParams();

		params.put("Method", "order.saveorder");
		params.put("Accesstoken", BASE_ACCESS);

		params.put("Msg", msgs);
		// params.put("Msg", "code","code_order");
		System.out.println("支付：----"+msgs);
		httpUtil.post(com.Meetok.config.Config.F_BASE_URL, params,
				new AbStringHttpResponseListener() {

					private String Code_n;

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
						AbToastUtil.showToast(PayDemoActivity.this,
								error.getMessage());
					}

					@Override
					public void onSuccess(int statusCode, String content) {
						// AbToastUtil.showToast(MainActivity.this, content);
						try {
							JSONObject json = new JSONObject(content);
							JSONObject data = json.optJSONObject("data");
							String date = String.valueOf(data);
							System.out.println("33333333" + date);
							// {"money":"0.20","tid":"20160405090825934"}
							String[] tempArr = date.split("\"");

							String mon = tempArr[1].trim();
							String m3 = tempArr[3].trim();
							String m7 = tempArr[7].trim();
							System.out.println("m3===" + m3 + "mon===" + mon
									+ "m7=====" + m7);
							tid1 = m7;

						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
		return tid1;
	}

	/**
	 * 订单支付，返回支付宝所需数据
	 * 
	 * @param tid1
	 * @return
	 */
	public static float getdingdan(String tid1) {
		// TODO Auto-generated method stub
		AbRequestParams params = new AbRequestParams();

		params.put("Method", "order.pay");
		params.put("Accesstoken", BASE_ACCESS);
		String msg = "{" + "\"" + "tid" + "\"" + ":" + "\"" + tid1 + "\"" + "}";
		params.put("Msg", msg);
		// params.put("Msg", "code","code_order");
		httpUtil.post(com.Meetok.config.Config.F_BASE_URL, params,
				new AbStringHttpResponseListener() {

					private String Code_n;

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
						// AbToastUtil.showToast(OrderPay.this,
						// error.getMessage());
					}

					@Override
					public void onSuccess(int statusCode, String content) {
						// AbToastUtil.showToast(MainActivity.this, content);
						try {
							JSONObject json = new JSONObject(content);
							JSONArray data = json.optJSONArray("data");
							for (int i = 0; i < data.length(); i++) {
								JSONObject ffObj = data.optJSONObject(i);
								FuKuangEntity zfb = (FuKuangEntity) ParseJSONTools
										.getInstance().fromJsonToJava(ffObj,
												FuKuangEntity.class);
								zfb.tid = ffObj.getString("tid");
								zfb.key = ffObj.optInt("key");
								zfb.seller = ffObj.optString("seller");
								zfb.partner = ffObj.optString("partner");
								zfb.payment = ffObj.optInt("payment");
								zfb.notifyUrl = ffObj.optString("notifyUrl");

								zfb_partner = zfb.partner;
								zfb_key = zfb.key;
								zfb_seller = zfb.seller;
								zfb_payment = zfb.payment;
								zfb_tid = zfb.tid;
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
		return zfb_payment;
	}
}
