package com.Meetok.fragment;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.Meetok.Activity.LogActivity;
import com.Meetok.Activity.OrderQueRen;
import com.Meetok.Custom.CustomDialog;
import com.Meetok.Entity.GouWuCheEntity;
import com.Meetok.Entity.ParseJSONTools;
import com.Meetok.Tab.MainActivity;
import com.Meetok.View.MyListView;
import com.Meetok.adapter.Adapter_gouwuche;
import com.Meetok.adapter.Adapter_gouwuche.HolderView;
import com.ab.http.AbHttpUtil;
import com.ab.http.AbRequestParams;
import com.ab.http.AbStringHttpResponseListener;
import com.ab.util.AbToastUtil;
import com.imooc.tab03.R;
import com.jingchen.pulltorefresh.pullableview.MyListener;
import com.jingchen.pulltorefresh.pullableview.PullToRefreshLayout;
import com.jingchen.pulltorefresh.pullableview.PullableScrollView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ShoppingCartFragment extends Fragment implements OnClickListener {
	private PullableScrollView mPullScrollView;
	private static TextView heji;
	private static CheckBox check_quanxuan;
	private CheckBox check_xuan;
	private TextView editText;
	private TextView jiesuan;
	private TextView shanchu;
	private ImageView left;
	private ImageView right;
	private static String BASE_ACCESS;
	private double zsum = 0;
	private static MyListView myListView;
	private static AbHttpUtil httpUtil = null;
	private static Adapter_gouwuche adapter_gouwuche;
	static List<GouWuCheEntity> mlist_gwc = new ArrayList<GouWuCheEntity>();

	private static int checkNum = 0; // 记录选中的条目数量
	public static double AllPrice = 0;
	private static String[] guid_str = new String[100];
	private String[] guid_all = new String[100];
	// ArrayList<String> guid_str = new ArrayList<String>();
	private List<Map<String, Object>> mData;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = LayoutInflater.from(getActivity()).inflate(R.layout.tab02,
				null);
		mPullScrollView = (PullableScrollView) view
				.findViewById(R.id.content_view);

		httpUtil = AbHttpUtil.getInstance(getActivity());
		httpUtil.setTimeout(10000);
		BASE_ACCESS = LogActivity.loadDataFromLocalXML(getActivity(),
				"accesstoken");

		heji = (TextView) view.findViewById(R.id.heji);
		jiesuan = (TextView) view.findViewById(R.id.jiesuan_button);
		check_quanxuan = (CheckBox) view.findViewById(R.id.quanxuan);
		mlist_gwc.clear();
		AllPrice = 0;
		initview();
		getdata();
		return view;
	}

	private void initview() {
		// TODO Auto-generated method stub
		View view = View.inflate(getActivity(), R.layout.gouwuche, null);
		check_xuan = (CheckBox) view.findViewById(R.id.pro_checkbox);
		myListView = (MyListView) view
				.findViewById(R.id.cart_shopping_listview);
		shanchu = (TextView) view.findViewById(R.id.gwc_shanchu);
		editText = (TextView) view.findViewById(R.id.input);
		left = (ImageView) view.findViewById(R.id.gwc_left);
		right = (ImageView) view.findViewById(R.id.gwc_right);

		jiesuan.setOnClickListener(this);
		check_quanxuan.setOnClickListener(this);

		/*myListView.setChoiceMode(myListView.CHOICE_MODE_SINGLE);
		myListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				// 取得ViewHolder对象，这样就省去了通过层层的findViewById去实例化我们需要的cb实例的步骤
				HolderView holder = (HolderView) arg1.getTag();
				// 改变CheckBox的状态
				holder.iv_cb.toggle();
				String xiaojie = holder.iv_xioaji.getText().toString();
				String zongjia = heji.getText().toString();
				//String d_jia = xiaojie.subSequence(1, xiaojie.length()).toString();

				String guid_i = mlist_gwc.get(arg2).GUID;
				double xj = Double.parseDouble(xiaojie);
				double zongj;
				if(zongjia.equals("")){
					zongj = 0;
				}else{
				zongj = Double.parseDouble(zongjia);
				}
				
				// 将CheckBox的选中状况记录下来
				Adapter_gouwuche.getIsSelected().put(arg2,holder.iv_cb.isChecked());
				// 调整选定条目
				if (holder.iv_cb.isChecked() == true) {
					checkNum++;
					guid_str[arg2] = guid_i + ",";
					zongj = zongj + xj;
				} else {
					checkNum--;
					guid_str[arg2] = null;
					zongj = zongj - xj;
				}
				// 用TextView显示
				if (checkNum == mlist_gwc.size()) {
					check_quanxuan.setChecked(true);
					System.out.println("全选了");
				} else {
					check_quanxuan.setChecked(false);
					System.out.println("不全选了"+checkNum);
					System.out.println("guid_str数组" + guid_str[arg2]+guid_str[0]);
				}
				System.out.println("guid_str数组" + guid_str[arg2]+guid_str[0]);
				heji.setText(String.valueOf( zongj));
			}
		});
		/*
		check_quanxuan.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				// TODO Auto-generated method stub

			}
		});*/
		mPullScrollView.addView(view);
	}

	private void getdata() {
		// TODO Auto-generated method stub
		mlist_gwc.clear();
		AbRequestParams params = new AbRequestParams();
		params.put("Method", "product.getcartlist");
		params.put("Accesstoken", BASE_ACCESS);
		params.put("Msg", "");
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
 
							}else
							{JSONArray data = json.optJSONArray("data");
							// System.out.println("222222222222"+data);
							for (int i = 0; i < data.length(); i++) {
								JSONObject itemObj = data.optJSONObject(i);

								GouWuCheEntity gwc = (GouWuCheEntity) ParseJSONTools
										.getInstance().fromJsonToJava(itemObj,
												GouWuCheEntity.class);
								gwc.Title = itemObj.optString("Title");
								gwc.ProductPic = itemObj
										.optString("ProductPic");
								gwc.DisPurchasePrice = itemObj
										.optDouble("DisPurchasePrice");
								gwc.Num = itemObj.optInt("Num");
								gwc.AllPrice = itemObj.optDouble("AllPrice");
								gwc.GUID = itemObj.optString("GUID");
								guid_all[i] = gwc.GUID + ",";
								AllPrice = AllPrice + gwc.AllPrice;
								System.out.println(AllPrice + "...." + guid_all);
								mlist_gwc.add(gwc);

							}
							}
							adapter_gouwuche = new Adapter_gouwuche(
									getActivity(), mlist_gwc);
							myListView.setAdapter(adapter_gouwuche);
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

		case R.id.jiesuan_button:
			System.out.println("结算");
			String all_guid = "";

			for(int i = 0; i < myListView.getChildCount(); i++){  
		         View view = myListView.getChildAt(i);  
		        if(((CheckBox) (myListView.getChildAt(i)).findViewById(R.id.pro_checkbox)).isChecked()){
		        	guid_str[i] = mlist_gwc.get(i).GUID+",";
		        	all_guid = all_guid+guid_str[i];
		        } 
		     }  

			//Adapter_gouwuche.getguids(mlist_gwc);			
			System.out.println("全部guid-----" + all_guid);
			if (all_guid != "") {
				String aguid = all_guid.substring(0, all_guid.length() - 1);
				Intent intent = new Intent();
				intent.setClass(getActivity(), OrderQueRen.class);
				System.out.println("guids-------------" + aguid);

				intent.putExtra("ordernew_guid", aguid);
				startActivityForResult(intent, 0);
			} else {
				System.out.println("没有选商品guid-----" + all_guid);
				errorchange();
			}

			break;
		case R.id.quanxuan:
			System.out.println("全选");
			if (check_quanxuan.isChecked()) {
				// 遍历list的长度，将MyAdapter中的map值全部设为true
				// getdata();
				for (int i = 0; i < mlist_gwc.size(); i++) {
					Adapter_gouwuche.getIsSelected().put(i, true);
					((CheckBox) (myListView.getChildAt(i)).findViewById(R.id.pro_checkbox)).setChecked(true);
						System.out.println("sss");
				}
				// 数量设为list的长度
				checkNum = mlist_gwc.size();
				// 刷新listview和TextView的显示
				zsum = AllPrice;
				dataChanged(zsum);
			} else {
				// 遍历list的长度，将已选的按钮设为未选
				System.out.println("checkNum"+checkNum);
				if(checkNum ==0||checkNum ==mlist_gwc.size()){
				for (int i = 0; i < mlist_gwc.size(); i++) {
					if (Adapter_gouwuche.getIsSelected().get(i)) {
						//Adapter_gouwuche.getIsSelected().put(i, false);
						((CheckBox) (myListView.getChildAt(i)).findViewById(R.id.pro_checkbox)).setChecked(false);
						checkNum--;// 数量减1
					}
				}				
				// 刷新listview和TextView的显示
				dataChanged(0.0);
			}
			zsum = 0.0;
		}
			break;
		default:
			break;
		}
	}

	/**
	 * 未选商品提示
	 */
	private void errorchange() {
		// TODO Auto-generated method stub
		CustomDialog.Builder builder = new CustomDialog.Builder(getActivity());
		builder.setMessage("您还没有选择商品");
		builder.setTitle("温馨提示");
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.create().show();
	}

	private void dataChanged(Double zsum) {
		// TODO Auto-generated method stub
		// 通知listView刷新

		//adapter_gouwuche.notifyDataSetChanged();
		// TextView显示价格
		heji.setText(String.valueOf(zsum));
	}

	/**
	 * 删除购物车
	 * 
	 * @param position
	 * @param guid
	 * @param idxj
	 */
	public static void showInfo(final int position, final String guid,
			final double idxj) {
		AbRequestParams params = new AbRequestParams();
		params.put("Method", "product.delcartgood");
		params.put("Accesstoken", BASE_ACCESS);
		String msgs = "{" + "\"" + "guid" + "\"" + ":" + "\"" + guid + "\""
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
							if (check_quanxuan.isChecked()) {
								AllPrice = AllPrice - idxj;
								heji.setText(String.valueOf(AllPrice));
							} else {
								AllPrice = AllPrice - idxj;
							}
							// View view = myListView.getChildAt(position-
							// myListView.getFirstVisiblePosition());
							// myListView.getAdapter().getView(position, view,
							// myListView );
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
	 * 6、修改购物车数量
	 * 
	 * @param i
	 * @param guids
	 * @param position
	 * @param context
	 * @param flag
	 *            //判断加减
	 * @param danjia
	 *            //单价
	 * @param iv_cb 
	 */
	public static void getnum(final int i, String guids, final int position,
			final Context context, final boolean flag, final double danjia, final CheckBox iv_cb) {
		// TODO Auto-generated method stub
		AbRequestParams params = new AbRequestParams();
		params.put("Method", "product.modcartgood");
		params.put("Accesstoken", BASE_ACCESS);
		String msgs = "{" + "\"" + "guid" + "\"" + ":" + "\"" + guids + "\""
				+ "," + "\"num\":" + "\"" + i + "\"" + "}";
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
							String data = (String) del;
							System.out.println("数量data--------" + data + i);
							
							if (check_quanxuan.isChecked()) {// 有没有全选
								if (flag) {
									AllPrice = AllPrice + danjia;
									BigDecimal b = new BigDecimal(AllPrice); 
									 AllPrice = b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue(); 
									heji.setText(String.valueOf(AllPrice));
								} else {
									AllPrice = AllPrice - danjia;
									BigDecimal b = new BigDecimal(AllPrice); 
									 AllPrice = b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue(); 
									heji.setText(String.valueOf(AllPrice));
								}
							} else {
								if (iv_cb.isChecked() == true) {
									if (flag) {// 加

										AllPrice = AllPrice + danjia;
										double show_p =Double.valueOf(heji.getText().toString()) + danjia;
										BigDecimal c = new BigDecimal(show_p); 
										show_p = c.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue(); 
										heji.setText(String.valueOf(show_p));
									} else {// 减
										AllPrice = AllPrice - danjia;
										double show_p =Double.valueOf(heji.getText().toString()) - danjia;
										BigDecimal d = new BigDecimal(show_p); 
										show_p = d.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue(); 
										heji.setText(String.valueOf(show_p));
										//heji.setText(String.valueOf(Double.valueOf(heji.getText().toString()) - danjia));
									}
								} else {
									if (flag) {
										AllPrice = AllPrice + danjia;
									} else {
										AllPrice = AllPrice - danjia;
									}
								}
							}
							// adapter.notifyDataSetChanged();notifyDataSetChanged();
							// mlist_gwc.clear();
							// adapter_gouwuche.notifyDataSetChanged();
							// View view = myListView.getChildAt(position-
							// myListView.getFirstVisiblePosition());
							// myListView.getAdapter().getView(position, view,
							// myListView );
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
	 * item内的选择框点击事件处理	
	 * @param xj 小节价
	 * @param guid_i
	 * @param position 
	 * @param iv_cb
	 */
	public static void change_cb(double xj, String guid_i, int position, CheckBox iv_cb) {
		// TODO Auto-generated method stub
		//iv_cb.setChecked(true);
		String zongjia = heji.getText().toString();
		double zongj;
		if(zongjia.equals("")){
			zongj = 0;
		}else{
		zongj = Double.parseDouble(zongjia);
		}
		// 将CheckBox的选中状况记录下来
		//Adapter_gouwuche.getIsSelected().put(position,iv_cb.isChecked());
		// 调整选定条目
		if (iv_cb.isChecked() == true) {
			checkNum++;			
			zongj = zongj + xj;
		} else {
			checkNum--;
			zongj = zongj - xj;
		}
		// 用TextView显示
		if (checkNum == mlist_gwc.size()) {
			check_quanxuan.setChecked(true);
			System.out.println("全选了");
		} else {
			check_quanxuan.setChecked(false);
		}

		heji.setText(String.valueOf(zongj));
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
