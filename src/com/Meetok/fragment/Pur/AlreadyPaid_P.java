package com.Meetok.fragment.Pur;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.Meetok.Activity.LogActivity;
import com.Meetok.Custom.CustomDialog;
import com.Meetok.Entity.FuKuangEntity;
import com.Meetok.Entity.GouWuCheEntity;
import com.Meetok.Entity.ParseJSONTools;
import com.Meetok.Entity.PurchaseEntity;
import com.Meetok.Entity.ShouyeEntity;
import com.Meetok.Tab.MainActivity;
import com.Meetok.View.MyListView;
import com.Meetok.adapter.Adapter_Child_already;
import com.Meetok.adapter.Adapter_gouwuche;
import com.Meetok.adapter.Adapter_already_zhu;
import com.ab.http.AbHttpUtil;
import com.ab.http.AbRequestParams;
import com.ab.http.AbStringHttpResponseListener;
import com.ab.util.AbToastUtil;
import com.google.gson.JsonObject;
import com.imooc.tab03.R;

import android.R.integer;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;
import android.widget.RelativeLayout;

/**
 * 未付款
 * 
 * @author ayumi
 * 
 */

public class AlreadyPaid_P extends Fragment {

	private static AbHttpUtil httpUtil = null;
	private ListView myListView;
	private Adapter_already_zhu adapter_already_zhu;
	List<PurchaseEntity> mlist_zhu = new ArrayList<PurchaseEntity>();
	//List<PurchaseEntity> mlist_zi = new ArrayList<PurchaseEntity>();
	PurchaseEntity[] str_zi;
	private View mFooterView;
	public int yema_actpage;
	public int c_count;
	private RelativeLayout loading;
	private Handler handler = new Handler();
	public int lastItem;
	protected static String zfb_partner;
	protected static int zfb_key;
	protected static String zfb_seller;
	private static float zfb_payment;
	protected static String zfb_tid;
	private static String BASE_ACCESS;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		View view = LayoutInflater.from(getActivity()).inflate(
				R.layout.p_alreadypaid, null);
		httpUtil = AbHttpUtil.getInstance(getActivity());
		httpUtil.setTimeout(10000);
		
		BASE_ACCESS = LogActivity.loadDataFromLocalXML(getActivity(), "accesstoken");
		mlist_zhu.clear();
		initView(view);
		getdata();
		return view;
	}

	private void initView(View view) {
		// TODO Auto-generated method stub
		mFooterView = LayoutInflater.from(getActivity()).inflate(
				R.layout.listview_footer, null);
		loading = (RelativeLayout) mFooterView.findViewById(R.id.loading);
		// View view = View.inflate(getActivity(), R.layout.gouwuche, null);
		myListView = (ListView) view.findViewById(R.id.p_already_list);
		// 添加listview滚动监听
		myListView.setOnScrollListener(new OnScrollListener() {
			// AbsListView view 这个view对象就是listview
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				switch (scrollState) {
				case OnScrollListener.SCROLL_STATE_IDLE:
					System.out.println("已经停止：SCROLL_STATE_IDLE");
					if (view.getLastVisiblePosition() == view.getCount() - 1) {
						System.out.println("页码" + yema_actpage + "总商品数"
								+ c_count);
						if ((c_count / 10) >= yema_actpage && c_count > 10) {
							System.out.println("listview滑动判断当前页码"
									+ yema_actpage);
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
		loading.setVisibility(View.VISIBLE);
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				loadmore();
				loading.setVisibility(View.GONE);
				adapter_already_zhu.notifyDataSetChanged();
			}
		}, 5000);
	}

	protected void loadmore() {
		// TODO Auto-generated method stub

		//int mcount = adapter_already_zhu.getCount() + 1;
		AbRequestParams params = new AbRequestParams();
		params.put("Method", "order.dispurchaseorderlist");
		params.put("Accesstoken",BASE_ACCESS);
		// 订单状态可空:空即未付款,0:未付款，1：已付款，2：已退款，99：已取消
		yema_actpage = yema_actpage + 1;
		String msgs = "{" + "\"" + "actpage" + "\"" + ":" + "\"" + yema_actpage
				+ "\"" + "," + "\"" + "status" + "\"" + ":" + "\"" + "0" + "\""
				+ "," + "\"" + "tid" + "\"" + ":" + "\"" + "\"" + "}";
		params.put("Msg", msgs);
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
						AbToastUtil.showToast(getActivity(), error.getMessage());
					}

					@Override
					public void onSuccess(int statusCode, String content) {
						// AbToastUtil.showToast(MainActivity.this, content);
						try {
							JSONObject json = new JSONObject(content);
							JSONObject data = json.optJSONObject("data");

							Object actpage_a = data.opt("actpage");
							String yema = String.valueOf(actpage_a);
							yema_actpage = Integer.parseInt(yema);
							Object count_d = data.opt("count");
							String cout = String.valueOf(count_d);
							c_count = Integer.parseInt(cout);
							System.out.println("页码" + yema + "总商品数" + cout);
							JSONArray data1 = data.optJSONArray("data");
							for (int i = 0; i < data1.length(); i++) {
								JSONObject itemObj = data1.optJSONObject(i);
								PurchaseEntity pur = (PurchaseEntity) ParseJSONTools
										.getInstance().fromJsonToJava(itemObj,
												PurchaseEntity.class);
								pur.Tid = itemObj.optString("Tid");
								pur.Created = itemObj.optString("Created");
								pur.Payment = itemObj.optDouble("Payment");

								JSONArray jsonarray1 = itemObj
										.optJSONArray("dataitem");
								pur.jsonarray1 = jsonarray1;
								mlist_zhu.add(pur);
							}

							adapter_already_zhu = new Adapter_already_zhu(
									getActivity(), mlist_zhu);
							myListView.addFooterView(mFooterView);
							myListView.setAdapter(adapter_already_zhu);
							myListView.setSelection(lastItem);
							myListView.removeFooterView(mFooterView);

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

	private void getdata() {
		AbRequestParams params = new AbRequestParams();
		params.put("Method", "order.dispurchaseorderlist");
		params.put("Accesstoken", BASE_ACCESS);
		// 订单状态可空:空即未付款,0:未付款，1：已付款，2：已退款，99：已取消
		String msgs = "{" + "\"" + "actpage" + "\"" + ":" + "\"" + "1" + "\""
				+ "," + "\"" + "status" + "\"" + ":" + "\"" + "0" + "\"" + ","
				+ "\"" + "tid" + "\"" + ":" + "\"" + "\"" + "}";
		params.put("Msg", msgs);
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
						AbToastUtil.showToast(getActivity(), error.getMessage());
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
								CustomDialog.Builder builder = new CustomDialog.Builder(getActivity());  
							    builder.setMessage(errmsg);  
							    builder.setTitle("提示");  
							    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {  
							        public void onClick(DialogInterface dialog, int which) { 
							        	Intent intent = new Intent(getActivity(),LogActivity.class);
							        	//startActivity(intent);
							        	//getActivity().finish();
							        	startActivityForResult(intent, 0);							        	
							        	
							        }  
							    });  
							    builder.setNegativeButton("取消",  
							            new android.content.DialogInterface.OnClickListener() {  
							                public void onClick(DialogInterface dialog, int which) {  
							                	Intent intent = new Intent(getActivity(),MainActivity.class);
									        	startActivityForResult(intent, 0);	
							                    dialog.dismiss();  
							                    getActivity().finish();
							                }  
							            });  

							    builder.create().show(); 
 
							}else{
							JSONObject data = json.optJSONObject("data");

							Object actpage_a = data.opt("actpage");
							String yema = String.valueOf(actpage_a);
							yema_actpage = Integer.parseInt(yema);
							Object count_d = data.opt("count");
							String cout = String.valueOf(count_d);
							c_count = Integer.parseInt(cout);

							System.out.println("页码" + yema_actpage + "总商品数"
									+ c_count);
							JSONArray data1 = data.optJSONArray("data");
							System.out.println("2222222data1" + data1.length());
							for (int i = 0; i < data1.length(); i++) {
								JSONObject itemObj = data1.optJSONObject(i);
								PurchaseEntity pur = (PurchaseEntity) ParseJSONTools
										.getInstance().fromJsonToJava(itemObj,
												PurchaseEntity.class);
								pur.Tid = itemObj.optString("Tid");
								pur.Created = itemObj.optString("Created");
								pur.Payment = itemObj.optDouble("Payment");

								JSONArray jsonarray1 = itemObj
										.optJSONArray("dataitem");
								pur.jsonarray1 = jsonarray1;
								mlist_zhu.add(pur);
							}
							}
							adapter_already_zhu = new Adapter_already_zhu(
									getActivity(), mlist_zhu);
							myListView.addFooterView(mFooterView);
							myListView.setAdapter(adapter_already_zhu);
							myListView.removeFooterView(mFooterView);

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

	public static void showInfo(int position, String zhuid) {
		// TODO Auto-generated method stub
		AbRequestParams params = new AbRequestParams();
		params.put("Method", "order.cancelorder");
		params.put("Accesstoken",BASE_ACCESS);
		String msgs = "{" + "\"" + "id" + "\"" + ":" + "\"" + zhuid + "\""
				+ "}";
		params.put("Msg", msgs);
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
						// AbToastUtil.showToast(getActivity(),error.getMessage());
					}

					@Override
					public void onSuccess(int statusCode, String content) {
						// AbToastUtil.showToast(MainActivity.this, content);
						try {

							JSONObject json = new JSONObject(content);
							Object del = json.opt("data");
							String delete = (String) del;
							System.out.println("delete--------" + delete);

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
	public void onSaveInstanceState(Bundle outState) {//防止重叠
	// TODO Auto-generated method stub
	//super.onSaveInstanceState(outState);
	}
	/**
	 * 付款2
	 * @param tid
	 * @param title 
	 * @param money 
	 * @param context 
	 */
		public static void getdingdan2(String tid, String title, String money, Context context) {
			// TODO Auto-generated method stub
			getdingdan(tid);
			Intent intent = new Intent();
			intent.setClass(context, com.alipay.sdk.pay.PayDemoActivity.class);
			System.out.println("tid1-----money1--------" + tid + "ooooo"
					+ money);
			String zfbpayment = String.valueOf(zfb_payment);			
			intent.putExtra("zfb_partner", zfb_partner);
			intent.putExtra("zfb_seller", zfb_seller);
			intent.putExtra("zongjiage", money);
			intent.putExtra("zfb_tid", tid);
			intent.putExtra("zfb_title", title);
			context.startActivity(intent);
			//((Activity) context).overridePendingTransition(R.anim.slide_right_out,
			//		R.anim.slide_right_in);
		}

	private static void getdingdan(String tid) {
		// TODO Auto-generated method stub
		AbRequestParams params = new AbRequestParams();

		params.put("Method", "order.pay");
		params.put("Accesstoken",BASE_ACCESS);
		String msg ="{"+ "\""+"tid"+"\""+":"+"\""+tid+"\""+"}";
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
						//AbToastUtil.showToast(OrderPay.this, error.getMessage());
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
								zfb_seller =zfb.seller;
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
		
	}
	@Override
	public void onResume() {
		super.onResume();
 
	}

	@Override
	public void onPause() {
		super.onPause();
		System.gc();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		System.gc();
	}
		
}
