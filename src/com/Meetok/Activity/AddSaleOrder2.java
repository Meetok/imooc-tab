package com.Meetok.Activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.Meetok.Entity.ParseJSONTools;
import com.Meetok.Entity.SaleXQEntity;
import com.Meetok.adapter.Adapter_sale_list;
import com.ab.http.AbHttpUtil;
import com.ab.http.AbRequestParams;
import com.ab.http.AbStringHttpResponseListener;
import com.ab.util.AbToastUtil;
import com.imooc.tab03.R;
import com.jingchen.pulltorefresh.pullableview.MyListener;
import com.jingchen.pulltorefresh.pullableview.PullToRefreshLayout;
import com.jingchen.pulltorefresh.pullableview.PullableScrollView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
/**
 * 已有销售订单修改页面
 * @author ayumi
 *
 */
public class AddSaleOrder2  extends Activity implements OnClickListener{
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
	private static TextView add_zonge;
	private Button add_order;
	private ListView mylistsale;
	private String sale_id;//传过来的id
	private String list_josn;
	public static String wx;
	private AbHttpUtil httpUtil = null;
	List<SaleXQEntity> list_sale = new ArrayList<SaleXQEntity>();
	private Adapter_sale_list adapter_sale_list;
	protected String id;
	private static String BASE_ACCESS;
@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.addorder);
	View view = LayoutInflater.from(AddSaleOrder2.this).inflate(
			R.layout.addorder, null);
	mPullScrollView = (PullableScrollView) findViewById(R.id.content_view);
	((PullToRefreshLayout) view.findViewById(R.id.refresh_view))
			.setOnRefreshListener(new MyListener() {
				@Override
				public void onRefresh(
						final PullToRefreshLayout pullToRefreshLayout) {

					// infos.clear();

				}
			});

	button = (ImageView)findViewById(R.id.button_back);
	button.setOnClickListener(new OnClickListener(){
		public void onClick(View v)
		{
			AddSaleOrder2.this.finish();
			  overridePendingTransition(R.anim.slide_right_in,  
	                    R.anim.slide_right_out);  
		}	
		});
	 BASE_ACCESS = LogActivity.loadDataFromLocalXML(AddSaleOrder2.this, "accesstoken");
	Intent intent = getIntent();
	sale_id = intent.getStringExtra("sale_id");
	list_josn =intent.getStringExtra("list_josn");
	
	getlistshangping(list_josn);
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
	add_dizhi =  (TextView) findViewById(R.id.add_dizhi);//地址选择器
	add_order = (Button) findViewById(R.id.add_order);//新增商品
	queding = (TextView) findViewById(R.id.queding);
	s_save = (TextView) findViewById(R.id.s_save);
	add_zonge = (TextView) findViewById(R.id.add_zonge);
	
	mylistsale= (ListView) findViewById(R.id.listsale);
	
	queding.setOnClickListener(this);
	s_save.setOnClickListener(this);
	add_dizhi.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent i = new Intent(AddSaleOrder2.this, SaleDizhi.class);
			
			startActivityForResult(i, 0);
		}
	});
	add_order.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent i = new Intent(AddSaleOrder2.this, Sale_WeiCang.class);
			
			startActivityForResult(i, 0);
		}
	});
}

@Override  
protected void onActivityResult( int requestCode, int resultCode, Intent data )  
{  
    switch ( resultCode ) {  
        case RESULT_OK :  
            System.out.println(data.getExtras().getString( "result" ));  
            add_dizhi.setText( data.getExtras().getString( "result" )); 
          //    
            break;
        case RESULT_Sale_OK:
        	 wx = "["+data.getExtras().getString("weicangguids")+"]";
        	 getlistshangping(wx);
		
        	System.out.println(data.getExtras().getString("weicangguids"));
        	
        	break;
        default :  
            break;  
    }  
      
}
private void getlistshangping(String wx) {
	// TODO Auto-generated method stub
	try {
			//JSONObject myJsonObject = new JSONObject(wx);
			JSONArray  myJsonArray = new JSONArray(wx);

			for (int i = 0; i < myJsonArray.length(); i++) {
				JSONObject itemObj = myJsonArray.optJSONObject(i);

				SaleXQEntity s1 = (SaleXQEntity) ParseJSONTools
						.getInstance().fromJsonToJava(itemObj,
								SaleXQEntity.class);
				s1.Title = itemObj.optString("Title");
				s1.Price = itemObj.optDouble("Price");
				//s1.DisPurchasePrice = itemObj.optString("DisPurchasePrice");
				//s1.jsonarray1 = myJsonArray;
				s1.DisPurchasePrice = itemObj.optDouble("DisPurchasePrice");
				s1.StockQuantity = itemObj.optInt("StockQuantity");
				s1.Rate = itemObj.optString("Rate");
				list_sale.add(s1);
			}
			adapter_sale_list = new Adapter_sale_list(
					AddSaleOrder2.this, list_sale);
			mylistsale.setAdapter(adapter_sale_list);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
}
@Override
public void onClick(View v) {
	// TODO Auto-generated method stub
	switch (v.getId()) {
	case R.id.queding:
		
		int idd = Integer.valueOf(getsave());
		if(idd == 1){
		getqueding(idd);
		//
		AddSaleOrder2.this.finish();
		}else{
			System.out.println("msg”:“该订单不存在,”id”:”0”");
		}
		break;
		
	case R.id.s_save:
		getsave();
		//
		AddSaleOrder2.this.finish();
		break;
	default:
		break;
	}
}
private String getmsg() {
	// TODO Auto-generated method stub
	String msg = null;
	String m1= add_name.getText().toString().trim();
	m1 = "\"receivername\":"+"\""+m1+"\",";
	String m2= add_number.getText().toString().trim();
	m2 = "\"receivermobile\":"+"\""+m2+"\",";
	String mraddress= add_xxdizhi.getText().toString().trim();
	mraddress = "\"receiveraddress\":"+"\""+mraddress+"\""+",";
	String m4= add_idnum.getText().toString().trim();
	m4 = "\"idnum\":"+"\""+m4+"\""+",";
	String m5= add_idname.getText().toString().trim();
	m5 = "\"name\":"+"\""+m5+"\""+",";
	String mother ="\"postfee\":"+"\""+5+"\""+","+"\"isxxdp\":"+"\""+"T"+"\""+","+"\"id\":"+"\""+sale_id+"\""+",";
	String mdizhi=add_dizhi.getText().toString().trim();
	
	String[] tempArr = mdizhi.split("\\.");
	// tempArr[1].trim();
	String mstate= tempArr[0].trim();
	String mcity= tempArr[1].trim();
	String mdistrict= tempArr[2].trim();
	mdizhi = "\"receiverstate\":"+"\""+mstate+"\""+","+"\"receivercity\":"+"\""+mcity+"\""+","+"\"receiverdistrict\":"+"\""+mdistrict+"\""+",";
	//String mproducts= add_name.getText().toString().trim();//shangping[]
	
	String weicang1 = "\"products\":"+wx;
	 String weicang2 = weicang1.replaceFirst("DisPurchasePrice", "Price");
	 String weicang = weicang2.replaceFirst("StockQuantity", "Quantity");
	if(m1 != null &&m2 != null&&mraddress != null&&m4 != null&&m5 != null&&weicang!=null){
	msg = m1+m2+mdizhi+mraddress+m4+m5+mother+weicang;
	}else{
		System.out.println("订单信息填充不全");
	}
	
	return msg;
}
/**
 * 保存新增订单
 * @return 
 */
private String getsave() {
	// TODO Auto-generated method stub
	
	String msg ="{"+getmsg()+"}";
	AbRequestParams params = new AbRequestParams();

	params.put("Method", "sendlist.saveorder");
	params.put("Accesstoken",BASE_ACCESS);

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
					AbToastUtil.showToast(AddSaleOrder2.this, error.getMessage());
				}

				@Override
				public void onSuccess(int statusCode, String content) {
					// AbToastUtil.showToast(MainActivity.this, content);
					try {
						JSONObject json = new JSONObject(content);
						JSONObject data = json.optJSONObject("data");
						Object sa_id = data.opt("id");
						Object sa_msg = data.opt("msg");
						 id = (String)sa_id;
						String msg = (String)sa_msg;
						System.out.println("新增订单/修改结果===" + msg+id);

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

/**
 * 发送新增订单
 * @param idd 
 */
private void getqueding(int idd) {
	// TODO Auto-generated method stub
	AbRequestParams params = new AbRequestParams();
	params.put("Method", "sendlist.sendtohg");
	params.put("Accesstoken", BASE_ACCESS);
	
	String msg = "{" + "\"" + "id" + "\"" + ":" + "\"" + idd + "\""+"}";
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
					//AbToastUtil.showToast(Sale01Activity.this,error.getMessage());
				}

				@Override
				public void onSuccess(int statusCode, String content) {
					// AbToastUtil.showToast(MainActivity.this, content);
					try {
						JSONObject json = new JSONObject(content);
						JSONObject data = json.optJSONObject("data");
						Object statuscode = data.opt("statuscode");
						String scode = String.valueOf(statuscode);
						Object money = data.opt("money");
						String my = String.valueOf(money);
						Object msg = data.opt("msg");
						String allmsg = String.valueOf(msg);
						if(scode.equals("2")){
							System.out.println("提示缺少金额:"+my+"元");
						}else{
						System.out.println("提示:"+allmsg);
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
 * 按减少按钮
 * @param prive 
 * @param num
 */
public static void Changejiagejian(int num, Double prive) {
	// TODO Auto-generated method stub
	String zongjia = String.valueOf(num*prive);
	add_zonge.setText(zongjia);
}
/**
 * 删除
 */
public static void getsanchu() {
	// TODO Auto-generated method stub
	wx =null;
}
 
}

