package com.Meetok.Activity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.Meetok.Custom.CustomDialog;
import com.Meetok.Entity.ParseJSONTools;
import com.Meetok.Entity.SaleXQEntity;
import com.Meetok.Tab.ImmersionBar;
import com.Meetok.Tab.MainActivity;
import com.Meetok.View.MyListView;
import com.Meetok.adapter.Adapter_sale_list;
import com.ab.http.AbHttpUtil;
import com.ab.http.AbRequestParams;
import com.ab.http.AbStringHttpResponseListener;
import com.ab.util.AbToastUtil;

import com.imooc.tab03.R;
import com.jingchen.pulltorefresh.pullableview.PullableScrollView;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class AddSaleOrder extends Activity implements OnClickListener {
	static final int RESULT_Sale_OK = 11;
	private PullableScrollView mPullScrollView;
	private ImageView button;
	private EditText add_name;
	private EditText add_number;
	private EditText add_xxdizhi;
	private EditText add_idnum;
	private EditText add_idname;
	private TextView add_dizhi;
	private TextView queding;
	private TextView s_save;
	public static TextView add_zonge;
	private Button add_order;
	private MyListView mylistsale;
	private String weicang1;
	public static String wx;
	public String msg;
	public static String num_input;//数量
	public static Double zongjia;//最下方的总价
	private AbHttpUtil httpUtil = null;
	List<SaleXQEntity> list_sale = new ArrayList<SaleXQEntity>();
	private Adapter_sale_list adapter_sale_list;
	protected String id;
	private static String BASE_ACCESS;

	private int next = -1;
	private boolean choosesave;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.addorder);
		ImmersionBar.setImmersionBar(this, R.color.statusbar_bg);

		mPullScrollView = (PullableScrollView) findViewById(R.id.content_view);

		button = (ImageView) findViewById(R.id.button_back);
		button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				AddSaleOrder.this.finish();
				overridePendingTransition(R.anim.slide_right_in,
						R.anim.slide_right_out);
			}
		});
		BASE_ACCESS = LogActivity.loadDataFromLocalXML(AddSaleOrder.this,
				"accesstoken");

		httpUtil = AbHttpUtil.getInstance(this);
		httpUtil.setTimeout(10000);
		initview();
	}

	private void initview() {
		// TODO Auto-generated method stub
		add_name = (EditText) findViewById(R.id.add_name);
		add_number = (EditText) findViewById(R.id.add_number);
		add_xxdizhi = (EditText) findViewById(R.id.add_xxdizhi);
		add_idnum = (EditText) findViewById(R.id.add_idnum);
		add_idname = (EditText) findViewById(R.id.add_idname);
		add_dizhi = (TextView) findViewById(R.id.add_dizhi);// 地址选择器
		add_order = (Button) findViewById(R.id.add_order);// 新增商品
		queding = (TextView) findViewById(R.id.queding);
		s_save = (TextView) findViewById(R.id.s_save);
		add_zonge = (TextView) findViewById(R.id.add_zonge);
		mylistsale = (MyListView) findViewById(R.id.listsale);

		queding.setOnClickListener(this);
		s_save.setOnClickListener(this);
		add_dizhi.setOnClickListener(this);	
		
		add_order.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent(AddSaleOrder.this, Sale_WeiCang.class);
				startActivityForResult(i, 0);
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (resultCode) {
		case RESULT_OK:
			System.out.println(data.getExtras().getString("result"));
			add_dizhi.setText(data.getExtras().getString("result"));
			//
			break;
		case RESULT_Sale_OK:
			list_sale.clear();//清空原有的商品
			//adapter_sale_list.notifyDataSetChanged();
			zongjia =0.0;//初始化总价格
			add_zonge.setText(String.valueOf(zongjia));
			
			wx = data.getExtras().getString("weicangguids");
			wx = wx.substring(0,wx.length() - 1);
			wx = "[" + wx + "]";

			try {
				// JSONObject myJsonObject = new JSONObject(wx);
				JSONArray myJsonArray = new JSONArray(wx);

				for (int i = 0; i < myJsonArray.length(); i++) {
					JSONObject itemObj = myJsonArray.optJSONObject(i);

					SaleXQEntity s1 = (SaleXQEntity) ParseJSONTools
							.getInstance().fromJsonToJava(itemObj,
									SaleXQEntity.class);
					s1.Title = itemObj.optString("Title");
					s1.Price = itemObj.optDouble("Price");
					// s1.DisPurchasePrice =
					// itemObj.optString("DisPurchasePrice");
					// s1.jsonarray1 = myJsonArray;
					s1.DisPurchasePrice = itemObj.optDouble("DisPurchasePrice");
					s1.StockQuantity = itemObj.optInt("StockQuantity");
					s1.Rate = itemObj.optString("Rate");
					double disp =s1.DisPurchasePrice;
					BigDecimal b = new BigDecimal(disp);
					double disprice = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
					if(s1.StockQuantity>=2){
						zongjia = zongjia+disprice*2;
					}
					list_sale.add(s1);
				}
				add_zonge.setText(String.valueOf(zongjia));
				adapter_sale_list = new Adapter_sale_list(AddSaleOrder.this,
						list_sale);
				mylistsale.setAdapter(adapter_sale_list);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			System.out.println(data.getExtras().getString("weicangguids"));

			break;
		default:
			break;
		}

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.add_dizhi://地址选择
			Intent i = new Intent(AddSaleOrder.this, SaleDizhi.class);
			startActivityForResult(i, 0);
			break;
		case R.id.queding:	
			choosesave=false;
			getfasongid();
			
			break;

		case R.id.s_save:
			choosesave=true;
			getmsg();
			String msg3 = "{" + msg + "}";
			getsave(msg3,choosesave);
			//
			//AddSaleOrder.this.finish();

			break;
		default:
			break;
		}
	}

	private void getfasongid() {
		// TODO Auto-generated method stub
		String msg2 = "{" + getmsg() + "}";
			getsave(msg2,choosesave);
			System.out.println("id" + id);
	}

	private String getmsg() {
		// TODO Auto-generated method stub
		 msg = null;
		 weicang1 ="";
		String m1 = add_name.getText().toString().trim();
		String mtxt1 = "\"receivername\":" + "\"" + m1 + "\",";
		String m2 = add_number.getText().toString().trim();
		String mtxt2 = "\"receivermobile\":" + "\"" + m2 + "\",";
		String mraddress = add_xxdizhi.getText().toString().trim();
		String mtxtraddress = "\"receiveraddress\":" + "\"" + mraddress + "\""
				+ ",";
		String m4 = add_idnum.getText().toString().trim();
		String mtxt4 = "\"idnum\":" + "\"" + m4 + "\"" + ",";
		String m5 = add_idname.getText().toString().trim();
		String mtxt5 = "\"name\":" + "\"" + m5 + "\"" + ",";
		String mother = "\"postfee\":" + "\"" + 5 + "\"" + "," + "\"isxxdp\":"
				+ "\"" + "T" + "\"" + "," + "\"id\":" + "\"" + "" + "\"" + ",";
		String mdizhi = add_dizhi.getText().toString().trim();

		String[] tempArr = mdizhi.split("\\.");
		// tempArr[1].trim();
		String mstate = tempArr[0].trim();
		String mcity = tempArr[1].trim();
		String mdistrict = tempArr[2].trim();
		mdizhi = "\"receiverstate\":" + "\"" + mstate + "\"" + ","
				+ "\"receivercity\":" + "\"" + mcity + "\"" + ","
				+ "\"receiverdistrict\":" + "\"" + mdistrict + "\"" + ",";
		
		String p = "\"Quantity\":" + "\"" + num_input + "\"" + ","+ "\"Price\":" + "\"" + zongjia + "\"" + ",";//获取数量
		if(wx!=null)
		{
			 weicang1 = "\"products\":" + wx.substring(0, 2) + p + wx.substring(2, wx.length());
		}
		//String weicang2 = weicang1.replaceFirst("DisPurchasePrice", "Price");
		//String weicang = weicang2.replaceFirst("StockQuantity", "Quantity");//选择的数量，新增商品没有这个属性，从text取
		if (mstate.equals("省")) {
			String error = "订单信息填充不全";
			System.out.println("订单信息填充不全");
			showtishi(error);
		} else if (m1 == "" || m2 == "" || mraddress == "" || m4 == ""
				|| m5 == "" || weicang1 == "") {
			System.out.println("订单信息填充不全");
			String error = "订单信息填充不全";
			showtishi(error);
			
		} else {
			msg = mtxt1 + mtxt2 + mdizhi + mtxtraddress + mtxt4 + mtxt5
					+ mother + weicang1;
		}

		return msg;
	}

	/**
	 * 保存新增订单
	 * @param msg 
	 * @param choosesave 
	 * 
	 * @return
	 */
	private String getsave(String msg, final boolean choosesave) {
		// TODO Auto-generated method stub

		AbRequestParams params = new AbRequestParams();

		params.put("Method", "sendlist.saveorder");
		params.put("Accesstoken", BASE_ACCESS);

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
						AbToastUtil.showToast(AddSaleOrder.this,
								error.getMessage());
					}

					@Override
					public void onSuccess(int statusCode, String content) {
						// AbToastUtil.showToast(MainActivity.this, content);
						try {
							JSONObject json = new JSONObject(content);
							Object co = json.opt("code");
							String mcode = String.valueOf(co);// session lose
																// errmsg
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
								CustomDialog.Builder builder = new CustomDialog.Builder(AddSaleOrder.this);
								builder.setMessage(msg);
								builder.setTitle("提示");
								builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog, int which) {
										if(choosesave){
										Intent i=new Intent( AddSaleOrder.this, MainActivity.class);  
										Bundle bundle = new Bundle();
										bundle.putInt("fragment", 4);
										i.putExtras(bundle);
							        	i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
							        	startActivity(i);
							        	dialog.dismiss();
							        	}else{
							        		
							        		if(id != null){
							        			getqueding(id);
							        			}
							        		dialog.dismiss();
							        	}
										
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
		return id;
	}

	private void showtishi(String errmsg) {
		// TODO Auto-generated method stub
		CustomDialog.Builder builder = new CustomDialog.Builder(this);
		builder.setMessage(errmsg);
		builder.setTitle("错误："+"请重新填写");
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				 Intent intent = new Intent(AddSaleOrder.this,
				 AddSaleOrder.class);
				 //startActivity(intent);
				// AddSaleOrder.this.finish();
				dialog.dismiss();
			}
		});
		builder.create().show();
	}

	/**
	 * 发送新增订单
	 * 
	 * @param id2
	 */
	private void getqueding(String id2) {
		// TODO Auto-generated method stub
		AbRequestParams params = new AbRequestParams();
		params.put("Method", "sendlist.sendtohg");
		params.put("Accesstoken", BASE_ACCESS);

		String msg = "{" + "\"" + "id" + "\"" + ":" + "\"" + id2 + "\"" + "}";
		params.put("Msg", msg);

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
						// AbToastUtil.showToast(Sale01Activity.this,error.getMessage());
					}

					@Override
					public void onSuccess(int statusCode, String content) {
						// AbToastUtil.showToast(MainActivity.this, content);
						try {
							JSONObject json = new JSONObject(content);
							Object co = json.opt("code");
							String mcode = String.valueOf(co);// session lose
																// errmsg
							if (mcode.equals("error")) {
								Object emsg = json.opt("errmsg");
								String errmsg = String.valueOf(emsg);
								showtishi(errmsg);
							} else {
								JSONObject data = json.optJSONObject("data");
								Object statuscode = data.opt("statuscode");
								String scode = String.valueOf(statuscode);
								Object money = data.opt("money");
								String my = String.valueOf(money);
								Object msg = data.opt("msg");
								String allmsg = String.valueOf(msg);
								if (scode.equals("2")) {
									System.out.println("提示缺少金额:" + my + "元");
								
									CustomDialog.Builder builder = new CustomDialog.Builder(AddSaleOrder.this);  
								    builder.setMessage("提示缺少金额:"+my+"元");  
								    builder.setTitle("提示");  
								    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {  
								        public void onClick(DialogInterface dialog, int which) { 
								        	// Intent intent = new Intent(AddSaleOrder.this,
								    				// AddSaleOrder.class);
								    				// startActivity(intent);
								    				// AddSaleOrder.this.finish();
								            dialog.dismiss(); 
								        }  
								    });  
								    builder.create().show();
								} else {								
									System.out.println("提示:"+allmsg);
									CustomDialog.Builder builder = new CustomDialog.Builder(AddSaleOrder.this);  
								    builder.setMessage("提示:"+allmsg);  
								    builder.setTitle("http://m.meetok.com");  
								    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {  
								        public void onClick(DialogInterface dialog, int which) { 
								        	
								            dialog.dismiss(); 
								        }  
								    });  
								    builder.create().show();
								}
							}
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

	/**
	 * 按增减按钮
	 * 
	 * @param prive
	 * @param num
	 * @param flag 
	 * @param numm 
	 * @param iv_input 
	 */
	public static void Changejiagejian(int num, Double prive, boolean flag, String numm) {
		// TODO Auto-generated method stub
		num_input = numm;
		double yuan_zj = Double.valueOf(add_zonge.getText().toString());
		double zj = num * prive;
		BigDecimal b = new BigDecimal(zj);
		double zongjia1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		if(flag){
			double zongjia2=zongjia1+yuan_zj;
			BigDecimal c = new BigDecimal(zongjia2);
			double zongjia_s = c.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				zongjia = zongjia_s;	
		}else{
			double zongjia2=yuan_zj-zongjia1;
			BigDecimal c = new BigDecimal(zongjia2);
			double zongjia_s = c.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			zongjia = zongjia_s;	
		}
		add_zonge.setText(String.valueOf(zongjia));
	}

	/**
	 * 删除
	 */
	public static void getsanchu(int num, Double prive) {
		// TODO Auto-generated method stub
	//	wx = null;
		
		double yuan_zj = Double.valueOf(add_zonge.getText().toString());
		System.out.println("san=yuan_zj=="+yuan_zj);
		double zj = num * prive;
		BigDecimal b = new BigDecimal(zj);
		double zongjia1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		zongjia = yuan_zj-zongjia1;	
		add_zonge.setText(String.valueOf(zongjia));
	}

	
	/**
	 * edittext改变时
	 * @param textnum
	 * @param beforejage 改变后的销售价
	 * @param salejiage1 改变前的价格
	 */
	public static void ChangeEdit(int textnum, double beforejage, double salejiage1) {
		// TODO Auto-generated method stub
		zongjia = Double.valueOf(add_zonge.getText().toString());
		BigDecimal cc = new BigDecimal(zongjia);
		zongjia = cc.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		
		double zj = textnum * (salejiage1-beforejage);
		BigDecimal b = new BigDecimal(zj);
		double zongjia1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		zongjia = zongjia + zongjia1;
			
		add_zonge.setText(String.valueOf(zongjia));
	}

}
