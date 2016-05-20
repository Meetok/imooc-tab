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
import com.Meetok.View.MyListView;
import com.Meetok.View.MyLoadListView;
import com.Meetok.View.MyLoadListView.ILoadListener;
import com.Meetok.adapter.Adapter_order;
import com.Meetok.adapter.Adapter_search;
import com.ab.http.AbHttpUtil;
import com.ab.http.AbRequestParams;
import com.ab.http.AbStringHttpResponseListener;
import com.ab.util.AbToastUtil;
import com.imooc.tab03.R;
import com.jingchen.pulltorefresh.pullableview.MyListener;
import com.jingchen.pulltorefresh.pullableview.PullToRefreshLayout;
import com.jingchen.pulltorefresh.pullableview.PullableScrollView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

public class Serach_shouye extends Activity implements OnItemClickListener,
		OnClickListener {
	private boolean isLoading=false;
	private EditText editText;
	private ImageView button;
	private Button btnSearch;
	private ImageView back;
	private PullableScrollView mPullScrollView;
	// private MyGridView gridView;
	private ListView mlistView;
	private View mFooterView;
	private RelativeLayout loading;
	public int yema_serach;
	public int count_serach;
	public int lastItem;
	private Handler handler = new Handler();
	private AbHttpUtil httpUtil = null;
	public String serachid;
	private Adapter_search adapter_search;
	List<OrderEntity> mlist_1 = new ArrayList<OrderEntity>();
	private String text;

	// List<OrderEntity> mlist_2 = new ArrayList<OrderEntity>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shoushuo_shouye);
		ImmersionBar.setImmersionBar(this, R.color.statusbar_bg);
		button = (ImageView) findViewById(R.id.button_back);
		button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Serach_shouye.this.finish();
				overridePendingTransition(R.anim.slide_right_in,
						R.anim.slide_right_out);
			}
		});

		httpUtil = AbHttpUtil.getInstance(this);
		httpUtil.setTimeout(10000);
		Intent intent = getIntent();
		serachid = intent.getStringExtra("serachid");
		if(serachid == null){
			serachid = "";
		}
		initview();
		getdata();

	}

	private void initview() {
		// TODO Auto-generated method stub
		
		//获取底部对象
		mFooterView = LayoutInflater.from(Serach_shouye.this).inflate(R.layout.listview_footer, null);
		loading = (RelativeLayout) mFooterView.findViewById(R.id.loading);

		editText = (EditText) findViewById(R.id.edittext);
		editText.clearFocus();
		btnSearch = (Button) findViewById(R.id.btnSearch);
		mlistView = (ListView) findViewById(R.id.mygrid_s);

		 text = editText.getText().toString();
		
		// mlistView.setInterface(this);
		btnSearch.setOnClickListener(this);
		
		adapter_search = new Adapter_search(
				Serach_shouye.this, mlist_1);
		mlistView.setAdapter(adapter_search);
		
		mlistView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> person, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent i = new Intent(Serach_shouye.this, OrderNew.class);
				int int_code = mlist_1.get(position).Code;
				String xq_code = String.valueOf(int_code);
				System.out.println("code=========" + xq_code);
				i.putExtra("shangpingcode", xq_code);
				startActivityForResult(i, 0);
			}
		});
		//获取底部footer的对象
		mFooterView=View.inflate(this.getBaseContext(), R.layout.listview_footer, null);
		//加入底部footer
		mlistView.addFooterView(mFooterView);
		mFooterView.findViewById(R.id.loading).setVisibility(View.GONE);
		// 添加listview滚动监听
		
		mlistView.setOnScrollListener(new OnScrollListener() {
			//总数量
			private int totalItemCount;
			//最后一个可见的数量
			private int lastVisiableCount;
			// AbsListView view 这个view对象就是listview
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				//第一  当前的lastVisiableCount等于totalItemCount 
				//第二  当前listview属于滑动停止状态
				if(lastVisiableCount==totalItemCount&&scrollState==SCROLL_STATE_IDLE){
					if(!isLoading){
						isLoading=true;
						//显示底部footer
						mFooterView.findViewById(R.id.loading).setVisibility(View.VISIBLE);
//						loadmore();
						loadData();
					}
				}
//				switch (scrollState) {
//				case OnScrollListener.SCROLL_STATE_IDLE:
//					System.out.println("已经停止：SCROLL_STATE_IDLE");
//					if (view.getLastVisiblePosition() == view.getCount() - 1) {
//						System.out.println("页码" + yema_serach + "总商品数"
//								+ count_serach);
//						if ((count_serach / 20) >= yema_serach&& count_serach > 10) {
//							System.out.println("listview滑动判断当前页码" + yema_serach);
//							loadData();
//
//						}
//					}
//					break;
//				case OnScrollListener.SCROLL_STATE_FLING:
//					System.out.println("开始滚动：SCROLL_STATE_FLING");
//					break;
//				case OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
//					System.out.println("正在滚动：SCROLL_STATE_TOUCH_SCROLL");
//					break;
//				}
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				lastItem = firstVisibleItem + visibleItemCount - 1;
				this.lastVisiableCount=firstVisibleItem+visibleItemCount;
				this.totalItemCount=totalItemCount;
			}
		});
	}

	protected void loadData() {
		// TODO Auto-generated method stub
		
		System.out.println("进入了加载数据");
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				System.out.println("进入了run");
				loadmore();
//				loading.setVisibility(View.GONE);
				mFooterView.findViewById(R.id.loading).setVisibility(View.GONE);
//				adapter_search.notifyDataSetChanged();
				isLoading=false;
			}
		}, 2000);
	}

	protected void loadmore() {
		// TODO Auto-generated method stub
		AbRequestParams params = new AbRequestParams();
		params.put("Method", "product.getsearchproductlist");
		params.put("Accesstoken", "");
		yema_serach = yema_serach + 1;
		String text = editText.getText().toString();
		String getsearch = "{" + "\"actpage\"" + ":" + "\"" + yema_serach+ "\"" +","+ "\"search\":"+"\""+text+"\""+","+ "\"typeid\":"+"\""+serachid+"\""+"}";
		params.put("Msg", getsearch);

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
						AbToastUtil.showToast(Serach_shouye.this,
								error.getMessage());
					}

					@Override
					public void onSuccess(int statusCode, String content) {
						// AbToastUtil.showToast(MainActivity.this, content);
						try {
							JSONObject json = new JSONObject(content);
							JSONObject data = json.optJSONObject("data");
							JSONArray data1 = data.optJSONArray("data");
							Object actpage_a = data.opt("actpage");
							String yema = String.valueOf(actpage_a);
							yema_serach = Integer.parseInt(yema);
							Object count_d = data.opt("count");
							String cout = String.valueOf(count_d);
							count_serach = Integer.parseInt(cout);

							for (int i = 0; i < data1.length(); i++) {
								JSONObject itemObj = data1.optJSONObject(i);

								OrderEntity search = (OrderEntity) ParseJSONTools
										.getInstance().fromJsonToJava(itemObj,
												OrderEntity.class);
								search.ProductPic = itemObj
										.optString("ProductPic");
								search.DisPurchasePrice = itemObj
										.optInt("DisPurchasePrice");
								search.RetailPrice = itemObj
										.optInt("RetailPrice");
								search.Stock = itemObj.optInt("Stock");

								//mlist_1.add(search);
								search.count = itemObj.optInt("count");
								mlist_1.add(search);
							}
//							adapter_search = new Adapter_search(
//									Serach_shouye.this, mlist_1);
//							mlistView.addFooterView(mFooterView);
//							mlistView.setAdapter(adapter_search);
//							mlistView.setSelection(lastItem);
//							mlistView.removeFooterView(mFooterView);
							adapter_search.notifyDataSetChanged();
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
		// TODO Auto-generated method stub
		AbRequestParams params = new AbRequestParams();
		params.put("Method", "product.getsearchproductlist");
		params.put("Accesstoken", "");
		String text = editText.getText().toString();
		String getsearch = "{" + "\"actpage\"" + ":" + "\"" + 1 + "\"" +","+ "\"search\":"+"\""+text+"\""+","+ "\"typeid\":"+"\""+serachid+"\""+ "}";
		params.put("Msg", getsearch);

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
						AbToastUtil.showToast(Serach_shouye.this,
								error.getMessage());
					}

					@Override
					public void onSuccess(int statusCode, String content) {
						// AbToastUtil.showToast(MainActivity.this, content);
						try {
							JSONObject json = new JSONObject(content);
							JSONObject data = json.optJSONObject("data");
							JSONArray data1 = data.optJSONArray("data");
							Object actpage_a = data.opt("actpage");
							String yema = String.valueOf(actpage_a);
							yema_serach = Integer.parseInt(yema);
							Object count_d = data.opt("count");
							String cout = String.valueOf(count_d);
							count_serach = Integer.parseInt(cout);

							for (int i = 0; i < data1.length(); i++) {
								JSONObject itemObj = data1.optJSONObject(i);

								OrderEntity search = (OrderEntity) ParseJSONTools
										.getInstance().fromJsonToJava(itemObj,
												OrderEntity.class);
								search.ProductPic = itemObj
										.optString("ProductPic");
								search.DisPurchasePrice = itemObj
										.optInt("DisPurchasePrice");
								search.RetailPrice = itemObj
										.optInt("RetailPrice");
								search.Stock = itemObj.optInt("Stock");

								//mlist_1.add(search);
								search.count = itemObj.optInt("count");
								mlist_1.add(search);
							}
//							adapter_search = new Adapter_search(
//									Serach_shouye.this, mlist_1);

//							mlistView.addFooterView(mFooterView);
//							mlistView.setAdapter(adapter_search);
//							mlistView.removeFooterView(mFooterView);
						    adapter_search.notifyDataSetChanged();
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
		case R.id.btnSearch:
			// {"actpage":1, "search":"��", "countryid":"", "typeid":"",
			// "brandid":""}
			mlist_1.clear();
			hintKbOne();
			String text = editText.getText().toString();
			String text0 = "\"" + "actpage" + "\"" + ":" + "\"" + 1 + "\"";
			String text1 = "\"" + "search" + "\"" + ":" + "\"" + text + "\"";
			String text2 = "\"" + "countryid" + "\"" + ":" + "\"" + "\"";
			String text3 = "\"" + "typeid" + "\"" + ":" + "\"" + "\"";
			String text4 = "\"" + "brandid" + "\"" + ":" + "\"" + "\"";
			String stext = "{" + text0 + "," + text1 + "," + text2 + ","
					+ text3 + "," + text4 + "}";
			getsearch(stext);
			break;

		default:
			break;
		}
	}

	private void getsearch(String stext) {
		// TODO Auto-generated method stub
		AbRequestParams params = new AbRequestParams();
		params.put("Method", "product.getsearchproductlist");
		params.put("Accesstoken", "");
		// String mtext = stext;
		params.put("Msg", stext);
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
						AbToastUtil.showToast(Serach_shouye.this,
								error.getMessage());
					}

					@Override
					public void onSuccess(int statusCode, String content) {
						// AbToastUtil.showToast(MainActivity.this, content);
						try {
							JSONObject json = new JSONObject(content);
							JSONObject data = json.optJSONObject("data");
							// JSONArray data = json_main.optJSONArray("main");
							JSONArray data1 = data.optJSONArray("data");
							for (int i = 0; i < data1.length(); i++) {
								JSONObject itemObj = data1.optJSONObject(i);

								OrderEntity search = (OrderEntity) ParseJSONTools
										.getInstance().fromJsonToJava(itemObj,
												OrderEntity.class);
								search.ProductPic = itemObj
										.optString("ProductPic");
								search.DisPurchasePrice = itemObj
										.optInt("DisPurchasePrice");
								search.RetailPrice = itemObj
										.optInt("RetailPrice");
								search.Stock = itemObj.optInt("Stock");
								search.count = itemObj.optInt("count");
								mlist_1.add(search);
							}
							adapter_search = new Adapter_search(
									Serach_shouye.this, mlist_1);
							// adapter_search.onDateChange(mlist_1);
							mlistView.setAdapter(adapter_search);
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

	// 此方法，如果显示则隐藏，如果隐藏则显示
	private void hintKbOne() {
		InputMethodManager imm = (InputMethodManager) getBaseContext()
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		// 得到InputMethodManager的实例
		if (imm.isActive()) {
			// 如果开启
			imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT,
					InputMethodManager.HIDE_NOT_ALWAYS);

		}
	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub

	}

}
