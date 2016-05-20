package com.Meetok.fragment.Pur;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.Meetok.Activity.LogActivity;
import com.Meetok.Entity.ParseJSONTools;
import com.Meetok.Entity.PurchaseEntity;
import com.Meetok.adapter.Adapter_already_C;
import com.ab.http.AbHttpUtil;
import com.ab.http.AbRequestParams;
import com.ab.http.AbStringHttpResponseListener;
import com.ab.util.AbToastUtil;
import com.imooc.tab03.R;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.AbsListView.OnScrollListener;
/**
 * 已取消
 * @author ayumi
 *
 */
public class Canceled_P extends Fragment {
	private static AbHttpUtil httpUtil = null;
	private ListView myListView;
	private View mFooterView;
	private RelativeLayout loading;
	public int yema_actpage;
	public int c_count;
	private Handler handler = new Handler();
	public int lastItem;
	List<PurchaseEntity> mlist_c = new ArrayList<PurchaseEntity>();
	private Adapter_already_C adapter_already_C;
	private static String BASE_ACCESS;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = LayoutInflater.from(getActivity()).inflate(R.layout.p_canceled, null);
		httpUtil = AbHttpUtil.getInstance(getActivity());
		httpUtil.setTimeout(10000);
		 BASE_ACCESS = LogActivity.loadDataFromLocalXML(getActivity(), "accesstoken");
		initView(view);
		getdata();
		return view;
	}

	private void initView(View view) {
		// TODO Auto-generated method stub
		mFooterView = LayoutInflater.from(getActivity()).inflate(
				R.layout.listview_footer, null);
		loading = (RelativeLayout) mFooterView.findViewById(R.id.loading);
		myListView = (ListView) view.findViewById(R.id.p_canceled_list);
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
				adapter_already_C.notifyDataSetChanged();
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
				+ "\"" + "," + "\"" + "status" + "\"" + ":" + "\"" + "99" + "\""
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
								mlist_c.add(pur);
							}

							adapter_already_C = new Adapter_already_C(
									getActivity(), mlist_c);
							myListView.addFooterView(mFooterView);
							myListView.setAdapter(adapter_already_C);
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
		params.put("Accesstoken",BASE_ACCESS);
		// 订单状态可空:空即未付款,0:未付款，1：已付款，2：已退款，99：已取消
		String msgs = "{" + "\"" + "actpage" + "\"" + ":" + "\"" + "1" + "\""
				+ "," + "\"" + "status" + "\"" + ":" + "\"" + "99" + "\"" + ","
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
								mlist_c.add(pur);
							}

							adapter_already_C = new Adapter_already_C(
									getActivity(), mlist_c);
							myListView.addFooterView(mFooterView);
							myListView.setAdapter(adapter_already_C);
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
	@Override
	public void onSaveInstanceState(Bundle outState) {//防止重叠
	// TODO Auto-generated method stub
	//super.onSaveInstanceState(outState);
	}
}
