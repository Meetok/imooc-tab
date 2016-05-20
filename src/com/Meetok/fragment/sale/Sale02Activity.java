package com.Meetok.fragment.sale;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.Meetok.Activity.LogActivity;
import com.Meetok.Entity.ParseJSONTools;
import com.Meetok.Entity.SaleEntity;
import com.Meetok.adapter.Adapter_sale01;
import com.Meetok.adapter.Adapter_sale_all;
import com.ab.http.AbHttpUtil;
import com.ab.http.AbRequestParams;
import com.ab.http.AbStringHttpResponseListener;
import com.ab.util.AbToastUtil;
import com.imooc.tab03.R;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AbsListView.OnScrollListener;

public class Sale02Activity  extends Fragment{
	private static AbHttpUtil httpUtil = null;
	private Button sousuo_01;
	private ListView shou_listView;
	private EditText EditText_1;
	private EditText EditText_2;
	private String msg_s;
	public String Status_sale ;
	public int yema_sale;
	private Adapter_sale_all adapter_sale_all2;
	List<SaleEntity> list_sale2 = new ArrayList<SaleEntity>();
	
	private View mFooterView;
	private Handler handler = new Handler();
	public int lastItem;
	private RelativeLayout loading;
	private static String BASE_ACCESS;
@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
	// TODO Auto-generated method stub

	View view = LayoutInflater.from(getActivity()).inflate(
			R.layout.layout_2, null);
	httpUtil = AbHttpUtil.getInstance(getActivity());
	httpUtil.setTimeout(10000);
	 BASE_ACCESS = LogActivity.loadDataFromLocalXML(getActivity(), "accesstoken");
	initview(view);
	getdata(msg_s);
	return view;
}
private String getmsg() {
	//订单状态   空：所有   dfshg：待发送海关、dhwfx：待货物放行、thdcl: 退回待处理、fhljsb：发货拦截失败
	String edit_A = EditText_1.getText().toString();
	String edit_B = EditText_2.getText().toString();
	 msg_s = "\""+"HGStatus"+"\""+":"+"\""+"dhwfx"+"\""+","+"\""+"searchparaA"+"\""+":"+"\""+edit_A+"\""+","+"\""+"searchProduct"+"\""+":"+"\""+edit_B+"\"";
return msg_s;
}
private void initview(View view) {
	// TODO Auto-generated method stub
	
	mFooterView = LayoutInflater.from(getActivity()).inflate(
			R.layout.listview_footer, null);
	loading = (RelativeLayout) mFooterView.findViewById(R.id.loading);
	
	shou_listView = (ListView) view.findViewById(R.id.layout1_list);
	EditText_1= (EditText) view.findViewById(R.id.sou_1);
	EditText_2 = (EditText) view.findViewById(R.id.sou_2);
	sousuo_01 = (Button) view.findViewById(R.id.sousuo_01);
	sousuo_01.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			list_sale2.clear();
			hintKbOne();
			getdata(msg_s);
		}
	});
	shou_listView.setOnScrollListener(new OnScrollListener() {
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
						System.out.println("listview滑动判断当前页码"
								+ yema_sale);
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
//此方法，如果显示则隐藏，如果隐藏则显示
	private void hintKbOne() {
		InputMethodManager imm = (InputMethodManager) getActivity()
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		// 得到InputMethodManager的实例
		if (imm.isActive()) {
			// 如果开启
			imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT,
					InputMethodManager.HIDE_NOT_ALWAYS);

		}
	}
protected void loadData() {
	loading.setVisibility(View.VISIBLE);
	handler.postDelayed(new Runnable() {
		@Override
		public void run() {
			loadmore();
			loading.setVisibility(View.GONE);
			adapter_sale_all2.notifyDataSetChanged();
		}
	}, 5000);
}
protected void loadmore() {
	// TODO Auto-generated method stub
	AbRequestParams params = new AbRequestParams();
	params.put("Method", "sendlist.sendlist");
	params.put("Accesstoken",BASE_ACCESS);
	String editmsg = getmsg();
	int ysma = yema_sale+1;
	String msgsearch = "{" + "\"" + "actpage" + "\"" + ":" + "\"" + ysma + "\""+ ","+editmsg+"}";
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
					AbToastUtil.showToast(getActivity(),
							error.getMessage());
				}

				@Override
				public void onSuccess(int statusCode, String content) {
					// AbToastUtil.showToast(MainActivity.this, content);
					try {
						JSONObject json = new JSONObject(content);
						JSONObject data = json.optJSONObject("data");
						
						Object actpage_a = data.opt("actpage");
						String yema = String.valueOf(actpage_a);
						yema_sale = Integer.parseInt(yema);
						Object Status = data.opt("Status");
						Status_sale = String.valueOf(Status);
						
						
						JSONArray data1 = data.optJSONArray("data");
						
							for (int i = 0; i < data1.length(); i++) {
								JSONObject itemObj = data1.optJSONObject(i);

								SaleEntity s1 = (SaleEntity) ParseJSONTools
										.getInstance().fromJsonToJava(
												itemObj, SaleEntity.class);
								s1.Tid = itemObj.optString("Tid");
								s1.Created = itemObj.optString("Created");
								s1.ReceiverName = itemObj.optString("ReceiverName");
                                s1.SalesOrderStatus = itemObj.optString("SalesOrderStatus");
								JSONArray jsonarray1 = itemObj.optJSONArray("dataitem");
								s1.itemarray1 = jsonarray1;
								
								list_sale2.add(s1);
							}
							
							adapter_sale_all2 = new Adapter_sale_all(
									getActivity(), list_sale2);
							shou_listView.setAdapter(adapter_sale_all2);
					
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

private void getdata(String msg_s) {
	// TODO Auto-generated method stub
	AbRequestParams params = new AbRequestParams();
	params.put("Method", "sendlist.sendlist");
	params.put("Accesstoken",BASE_ACCESS);
	String editmsg = getmsg();
	String msgsearch = "{" + "\"" + "actpage" + "\"" + ":" + "\"" + "1" + "\""+ ","+editmsg+"}";
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
					AbToastUtil.showToast(getActivity(),
							error.getMessage());
				}

				@Override
				public void onSuccess(int statusCode, String content) {
					// AbToastUtil.showToast(MainActivity.this, content);
					try {
						JSONObject json = new JSONObject(content);
						JSONObject data = json.optJSONObject("data");
						
						Object actpage_a = data.opt("actpage");
						String yema = String.valueOf(actpage_a);
						yema_sale = Integer.parseInt(yema);
						Object Status = data.opt("Status");
						Status_sale = String.valueOf(Status);
						JSONArray data1 = data.optJSONArray("data");
						
							for (int i = 0; i < data1.length(); i++) {
								JSONObject itemObj = data1.optJSONObject(i);

								SaleEntity s1 = (SaleEntity) ParseJSONTools
										.getInstance().fromJsonToJava(
												itemObj, SaleEntity.class);
								s1.Tid = itemObj.optString("Tid");
								s1.Created = itemObj.optString("Created");
								s1.ReceiverName = itemObj.optString("ReceiverName");
                                s1.SalesOrderStatus = itemObj.optString("SalesOrderStatus");
								JSONArray jsonarray1 = itemObj.optJSONArray("dataitem");
								s1.itemarray1 = jsonarray1;
								
								list_sale2.add(s1);
							}
							
							adapter_sale_all2 = new Adapter_sale_all(
									getActivity(), list_sale2);
							shou_listView.setAdapter(adapter_sale_all2);
					
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
