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
import com.Meetok.adapter.Adapter_sale_grid;
import com.ab.http.AbHttpUtil;
import com.ab.http.AbRequestParams;
import com.ab.http.AbStringHttpResponseListener;
import com.ab.util.AbToastUtil;
import com.imooc.tab03.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;

public class Sale_WeiCang extends Activity {

	// public static final int RESULT_Sale_OK = 11;
	private ImageView button;
	private TextView queren;
	private GridView mygridView;
	private AbHttpUtil httpUtil = null;
	static List<SaleXQEntity> grid_sale = new ArrayList<SaleXQEntity>();
	private Adapter_sale_grid adapter_sale_grid;
	public String Status_sale;
	public static String s_guids = null;
	public static String[] wc_guids = new String[50];// 设最多为50个品类
	public int yema_sale;
	public int lastItem;
	private Handler handler = new Handler();
	private static String BASE_ACCESS;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sale_weicang);
		ImmersionBar.setImmersionBar(this, R.color.statusbar_bg);
		button = (ImageView) findViewById(R.id.button_back);
		button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Sale_WeiCang.this.finish();
				overridePendingTransition(R.anim.slide_right_in,
						R.anim.slide_right_out);
			}
		});
		BASE_ACCESS = LogActivity.loadDataFromLocalXML(Sale_WeiCang.this,
				"accesstoken");
		grid_sale.clear();
		httpUtil = AbHttpUtil.getInstance(this);
		httpUtil.setTimeout(10000);
		
		initview();
		getdata();
		//wc_guids =null;
	}

	private void getdata() {
		// TODO Auto-generated method stub
		AbRequestParams params = new AbRequestParams();

		params.put("Method", "stock.getbaseproductstock");
		params.put("Accesstoken", BASE_ACCESS);
		String msgs = "{" + "actpage" + ":" + "1" + ","
				+ "\"TypeTreeId\":\"\",\"Name\":\"\"}";
		params.put("Msg", msgs);
		// params.put("Msg", "code","code_order");
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
						AbToastUtil.showToast(Sale_WeiCang.this,
								error.getMessage());
					}

					@Override
					public void onSuccess(int statusCode, String content) {
						// AbToastUtil.showToast(MainActivity.this, content);
						try {
							JSONObject json = new JSONObject(content);
							JSONObject data = json.optJSONObject("data");
							String date = String.valueOf(data);
							Object actpage_a = data.opt("actpage");
							String yema = String.valueOf(actpage_a);
							yema_sale = Integer.parseInt(yema);
							Object Status = data.opt("Status");
							Status_sale = String.valueOf(Status);

							// {"money":"0.20","tid":"20160405090825934"}
							JSONArray data1 = data.optJSONArray("data");

							for (int i = 0; i < data1.length(); i++) {
								JSONObject itemObj = data1.optJSONObject(i);

								SaleXQEntity s1 = (SaleXQEntity) ParseJSONTools
										.getInstance().fromJsonToJava(itemObj,
												SaleXQEntity.class);
								s1.Code = itemObj.getString("Code");
								s1.Title = itemObj.getString("Title");
								s1.Model = itemObj.getString("Model");
								s1.Spec = itemObj.getString("Spec");
								s1.Unit = itemObj.optString("Unit");
								s1.Rate = itemObj.optString("Rate");
								s1.StockQuantity = itemObj
										.getInt("StockQuantity");
								String st = String.valueOf(s1.StockQuantity);
								// String stock=st.substring(0,st.length()-3);
								s1.DisPurchasePrice = itemObj
										.getDouble("DisPurchasePrice");
								BigDecimal b = new BigDecimal(
										s1.DisPurchasePrice);
								double purprice = b.setScale(2,
										BigDecimal.ROUND_HALF_UP).doubleValue();
								String PurchasePrice = String.valueOf(purprice);
								if(s1.StockQuantity!=0){
								JSONObject data3 = new JSONObject();
								data3.put("Code", s1.Code);
								data3.put("Title", s1.Title);
								data3.put("Model", s1.Model);
								data3.put("Spec", s1.Spec);
								data3.put("Rate", s1.Rate);
								data3.put("Unit", s1.Unit);
								data3.put("StockQuantity", st);
								data3.put("DisPurchasePrice", PurchasePrice);

								s1.jsonarray1 = data3;
								grid_sale.add(s1);
								}else{
									System.out.println("bu加入数组");
								}
							}
							adapter_sale_grid = new Adapter_sale_grid(
									Sale_WeiCang.this, grid_sale);
							mygridView.setAdapter(adapter_sale_grid);
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

	private void initview() {
		// TODO Auto-generated method stub
		queren = (TextView) findViewById(R.id.s_queren);
		mygridView = (GridView) findViewById(R.id.grid_sale);
		queren.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				StringBuffer sb = new StringBuffer();
				for (int i = 0; i < wc_guids.length; i++) {
					if (wc_guids[i] != null) {
						sb.append(wc_guids[i]);
					}
				}
				s_guids = sb.toString();
				System.out.println("weicang====s_guids=====" + s_guids);
				//wc_guids =null;
				if (s_guids.equals("") || s_guids == null) {
					Sale_WeiCang.this.finish();
				} else {
					Intent in = new Intent();
					in.putExtra("weicangguids", s_guids);
					setResult(AddSaleOrder.RESULT_Sale_OK, in);
					Sale_WeiCang.this.finish();
					for (int i = 0; i < wc_guids.length; i++) {
						wc_guids[i] = null;
						}
				}
			}
		});
		/*
		 * mygridView.setOnItemClickListener(new OnItemClickListener() {
		 * 
		 * @Override public void onItemClick(AdapterView<?> person, View view,
		 * int position, long id) { // TODO Auto-generated method stub  
		 * 
		 * 
		 * } }); 添加listview滚动监听
		 */
		mygridView.setOnScrollListener(new OnScrollListener() {
			// AbsListView view 这个view对象就是listview
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				switch (scrollState) {
				case OnScrollListener.SCROLL_STATE_IDLE:
					System.out.println("已经停止：SCROLL_STATE_IDLE");
					if (view.getLastVisiblePosition() == view.getCount() - 1) {
						System.out.println("页码" + Status_sale + "总商品数"
								+ yema_sale);
						if (Status_sale.equals("F")) {
							System.out.println("listview滑动判断当前页码" + yema_sale);
							loadData();

						}
					}
					break;
				case OnScrollListener.SCROLL_STATE_FLING:
					System.out.println("开始滚动：SCROLL_STATE_FLING");
					break;
				case OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
					System.out.println("正在滚动：SCROLL_STATE_TOUCH_SCROLL");
					break;
				}
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				lastItem = firstVisibleItem + visibleItemCount - 1;
			}
		});
	}

	protected void loadData() {
		// loading.setVisibility(View.VISIBLE);
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				loadmore();
				// loading.setVisibility(View.GONE);
				adapter_sale_grid.notifyDataSetChanged();
			}
		}, 5000);
	}

	protected void loadmore() {
		// TODO Auto-generated method stub
		AbRequestParams params = new AbRequestParams();
		params.put("Method", "sendlist.sendlist");
		params.put("Accesstoken", BASE_ACCESS);

		int ysma = yema_sale + 1;
		String msgs = "{" + "actpage" + ":" + "\"" + ysma + "\"" + ","
				+ "\"TypeTreeId\":\"\",\"Name\":\"\"}";
		// String msgsearch = "{" + "\"" + "actpage" + "\"" + ":" + "\"" + ysma
		// + "\""+ ","+editmsg+"}";
		params.put("Msg", msgs);

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
						AbToastUtil.showToast(Sale_WeiCang.this,
								error.getMessage());
					}

					@Override
					public void onSuccess(int statusCode, String content) {
						// AbToastUtil.showToast(MainActivity.this, content);
						try {
							JSONObject json = new JSONObject(content);
							JSONObject data = json.optJSONObject("data");
							String date = String.valueOf(data);
							Object actpage_a = data.opt("actpage");
							String yema = String.valueOf(actpage_a);
							yema_sale = Integer.parseInt(yema);
							Object Status = data.opt("Status");
							Status_sale = String.valueOf(Status);

							// {"money":"0.20","tid":"20160405090825934"}
							JSONArray data1 = data.optJSONArray("data");

							for (int i = 0; i < data1.length(); i++) {
								JSONObject itemObj = data1.optJSONObject(i);

								SaleXQEntity s1 = (SaleXQEntity) ParseJSONTools
										.getInstance().fromJsonToJava(itemObj,
												SaleXQEntity.class);

								grid_sale.add(s1);
							}
							adapter_sale_grid = new Adapter_sale_grid(
									Sale_WeiCang.this, grid_sale);
							mygridView.setAdapter(adapter_sale_grid);
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

	public static void getsguids(int position, boolean flag) {
		// TODO Auto-generated method stub
		JSONObject guids = grid_sale.get(position).jsonarray1;
		if (flag) {
			s_guids = String.valueOf(guids);
			wc_guids[position] = s_guids + ",";
			System.out.println("weicang=========" + wc_guids);
		} else {
			wc_guids[position] = null;
		}
	}
}
