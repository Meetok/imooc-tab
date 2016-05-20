package com.Meetok.Tab;

import java.util.Timer;
import java.util.TimerTask;

import com.Meetok.Activity.LogActivity;
import com.Meetok.Application.SysApplication;
import com.Meetok.Custom.CustomDialog;
import com.Meetok.Util.NetWorkUtil;
import com.Meetok.fragment.HomeFragment;
import com.Meetok.fragment.MeFragment;
import com.Meetok.fragment.PurchaseFragment;
import com.Meetok.fragment.SaleFragment;
import com.Meetok.fragment.ShoppingCartFragment;
import com.imooc.tab03.R;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Main
 * 
 * @author
 * 
 */
public class MainActivity extends FragmentActivity implements OnClickListener,
		IBtnCallListener {

	// 界面底部的菜单按钮
	private ImageView[] bt_menu = new ImageView[5];
	private TextView[] tv = new TextView[5];
	// 界面底部的菜单按钮id
	private int[] bt_menu_id = { R.id.iv_menu_0, R.id.iv_menu_1,
			R.id.iv_menu_2, R.id.iv_menu_3, R.id.iv_menu_4 };
	private int[] tv_id = { R.id.t_0, R.id.t_1, R.id.t_2, R.id.t_3, R.id.t_4 };
	// 界面底部的选中菜单按钮资源
	private int[] select_on = { R.drawable.shouye1, R.drawable.gouwuche1,
			R.drawable.jinhuo1, R.drawable.fahuo1, R.drawable.mine1 };
	// 界面底部的未选中菜单按钮资源
	private int[] select_off = { R.drawable.shouye, R.drawable.gouwuche,
			R.drawable.jinhuo, R.drawable.fahuo, R.drawable.mine };
	private HomeFragment home_F;
	private ShoppingCartFragment tao_F;
	private PurchaseFragment discover_F;
	private SaleFragment cart_F;
	private MeFragment user_F;
	private static Boolean isQuit = false;
	Timer timer = new Timer();
	// private static TipsToast tipsToast;
	public static Activity home;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SysApplication.getInstance().addActivity(this);

		setContentView(R.layout.activity_main);

		home = this;

		ImmersionBar.setImmersionBar(this, R.color.statusbar_bg);
		initView();
		/*
		 * if (!NetWorkUtil.isNetworkConnected(MainActivity.this)) {
		 * showTips(R.drawable.tips_error, "网络未连接，请先连接网络..."); Intent intent =
		 * new Intent().setClass(MainActivity.this, NetWorkActivity.class);
		 * startActivityForResult(intent, 1); }
		 */

	}

	// 初始化组件
	private void initView() {
		// 找到底部菜单的按钮并设置监听
		for (int i = 0; i < bt_menu.length; i++) {
			bt_menu[i] = (ImageView) findViewById(bt_menu_id[i]);
			bt_menu[i].setOnClickListener(this);
		}
		for (int i = 0; i < bt_menu.length; i++) {
			tv[i] = (TextView) findViewById(tv_id[i]);
			tv[i].setOnClickListener(this);
		}
		//showDefaultFragment();
		// 获取从登入的activity传递过来的数据，并跳转到个人中心页面
		
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		if (bundle != null) {
			if (bundle.getInt("fragment") == 5) {
				// 初始化显示的界面个人中心
				System.out.println("333333333333");
				if (user_F == null) {
					user_F = new MeFragment();
					addFragment(user_F);
					showFragment(user_F);
					
				} else {
					showFragment(user_F);
					
					
				}
				// 设置默认首页为点击时的图片
				bt_menu[4].setImageResource(select_on[4]);
				tv[4].setTextColor(android.graphics.Color.parseColor("#24A93A"));
			}else if(bundle.getInt("fragment") == 2){
				// 初始化显示的界面购物车
				
				if (tao_F == null) {
					tao_F = new ShoppingCartFragment();
					addFragment(tao_F);
					showFragment(tao_F);
					
				} else {
					showFragment(tao_F);
					
					
				}
				// 设置默认首页为点击时的图片
				bt_menu[1].setImageResource(select_on[1]);
				tv[1].setTextColor(android.graphics.Color.parseColor("#24A93A"));
			}else if(bundle.getInt("fragment") == 3){
				// 初始化显示的界面购物车
				
				if (discover_F == null) {
					discover_F = new PurchaseFragment();
					addFragment(discover_F);
					showFragment(discover_F);
					
				} else {
					showFragment(discover_F);
					
					
				}
				// 设置默认首页为点击时的图片
				bt_menu[2].setImageResource(select_on[2]);
				tv[2].setTextColor(android.graphics.Color.parseColor("#24A93A"));
			}else if(bundle.getInt("fragment") == 4){
				// 初始化显示的界面购物车
				
				if (cart_F == null) {
					cart_F = new SaleFragment();
					addFragment(cart_F);
					showFragment(cart_F);
					
				} else {
					showFragment(cart_F);
					
					
				}
				// 设置默认首页为点击时的图片
				bt_menu[3].setImageResource(select_on[3]);
				tv[3].setTextColor(android.graphics.Color.parseColor("#24A93A"));
			}else {
				showDefaultFragment();
			}
		}else {
			showDefaultFragment();
		}
		 
	}

	/*
	 * 初始化默认显示的界面方法
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	public void showDefaultFragment() {
		// 初始化默认显示的界面
		if (home_F == null) {
			home_F = new HomeFragment();
			addFragment(home_F);
			showFragment(home_F);

		} else {
			showFragment(home_F);

		}
		// 设置默认首页为点击时的图片
		bt_menu[0].setImageResource(select_on[0]);
		tv[0].setTextColor(android.graphics.Color.parseColor("#24A93A"));
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_menu_0:
			// 主界面
			tv[0].setTextColor(android.graphics.Color.parseColor("#24A93A"));
			tv[1].setTextColor(android.graphics.Color.parseColor("#000000"));
			tv[2].setTextColor(android.graphics.Color.parseColor("#000000"));
			tv[3].setTextColor(android.graphics.Color.parseColor("#000000"));
			tv[4].setTextColor(android.graphics.Color.parseColor("#000000"));
			if (home_F == null) {
				home_F = new HomeFragment();
				// 判断当前界面是否隐藏，如果隐藏就进行添加显示，false表示显示，true表示当前界面隐藏
				addFragment(home_F);
				showFragment(home_F);

			} else {
				if (home_F.isHidden()) {
					showFragment(home_F);

				}
			}

			break;
		case R.id.iv_menu_1:
			// 购物车界面
			
			tv[0].setTextColor(android.graphics.Color.parseColor("#000000"));
			tv[1].setTextColor(android.graphics.Color.parseColor("#24A93A"));
			tv[2].setTextColor(android.graphics.Color.parseColor("#000000"));
			tv[3].setTextColor(android.graphics.Color.parseColor("#000000"));
			tv[4].setTextColor(android.graphics.Color.parseColor("#000000"));
			if (LogActivity.loadDataFromLocalXML(MainActivity.this,
					"accesstoken") == "") {	
								Intent intent = new Intent(MainActivity.this,
										LogActivity.class);
								//MainActivity.this.finish();
								startActivityForResult(intent, 0);
								overridePendingTransition(R.anim.slide_right_in,
										R.anim.slide_right_out);
			} else {
				
				shuaxin(2);
				System.out.println("11111111111111");

			}
			break;
		case R.id.iv_menu_2:
			// 采购进货界面
			
			tv[0].setTextColor(android.graphics.Color.parseColor("#000000"));
			tv[1].setTextColor(android.graphics.Color.parseColor("#000000"));
			tv[2].setTextColor(android.graphics.Color.parseColor("#24A93A"));
			tv[3].setTextColor(android.graphics.Color.parseColor("#000000"));
			tv[4].setTextColor(android.graphics.Color.parseColor("#000000"));
			if (LogActivity.loadDataFromLocalXML(MainActivity.this,
					"accesstoken") == "") {

				Intent intent = new Intent(MainActivity.this,
						LogActivity.class);
				//MainActivity.this.finish();
				startActivityForResult(intent, 0);
				overridePendingTransition(R.anim.slide_right_in,
						R.anim.slide_right_out);
			} else {
				shuaxin(3);
			}
			break;
		case R.id.iv_menu_3:
			// 销售发货界面
		
			tv[0].setTextColor(android.graphics.Color.parseColor("#000000"));
			tv[1].setTextColor(android.graphics.Color.parseColor("#000000"));
			tv[2].setTextColor(android.graphics.Color.parseColor("#000000"));
			tv[3].setTextColor(android.graphics.Color.parseColor("#24A93A"));
			tv[4].setTextColor(android.graphics.Color.parseColor("#000000"));
			if (LogActivity.loadDataFromLocalXML(MainActivity.this,
					"accesstoken") == "") {

				Intent intent = new Intent(MainActivity.this,
						LogActivity.class);
				//MainActivity.this.finish();
				startActivityForResult(intent, 0);
				overridePendingTransition(R.anim.slide_right_in,
						R.anim.slide_right_out);
			} else {
				shuaxin(4);
			}
			break;
		case R.id.iv_menu_4:
			// 会员中心界面
			tv[0].setTextColor(android.graphics.Color.parseColor("#000000"));
			tv[1].setTextColor(android.graphics.Color.parseColor("#000000"));
			tv[2].setTextColor(android.graphics.Color.parseColor("#000000"));
			tv[3].setTextColor(android.graphics.Color.parseColor("#000000"));
			tv[4].setTextColor(android.graphics.Color.parseColor("#24A93A"));
			if (user_F == null) {
				user_F = new MeFragment();
				// 判断当前界面是否隐藏，如果隐藏就进行添加显示，false表示显示，true表示当前界面隐藏
				if (!user_F.isHidden()) {
					addFragment(user_F);
					showFragment(user_F);
				}
			} else {
				if (user_F.isHidden()) {
					showFragment(user_F);

				}
			}

			break;
		case R.id.t_0:
			// 主界面
			tv[0].setTextColor(android.graphics.Color.parseColor("#24A93A"));
			tv[1].setTextColor(android.graphics.Color.parseColor("#000000"));
			tv[2].setTextColor(android.graphics.Color.parseColor("#000000"));
			tv[3].setTextColor(android.graphics.Color.parseColor("#000000"));
			tv[4].setTextColor(android.graphics.Color.parseColor("#000000"));
			if (home_F == null) {
				home_F = new HomeFragment();
				// 判断当前界面是否隐藏，如果隐藏就进行添加显示，false表示显示，true表示当前界面隐藏
				addFragment(home_F);
				showFragment(home_F);

			} else {
				if (home_F.isHidden()) {
					showFragment(home_F);

				}
			}

			break;
		case R.id.t_1:
			// 购物车界面
			
			tv[0].setTextColor(android.graphics.Color.parseColor("#000000"));
			tv[1].setTextColor(android.graphics.Color.parseColor("#24A93A"));
			tv[2].setTextColor(android.graphics.Color.parseColor("#000000"));
			tv[3].setTextColor(android.graphics.Color.parseColor("#000000"));
			tv[4].setTextColor(android.graphics.Color.parseColor("#000000"));
			if (LogActivity.loadDataFromLocalXML(MainActivity.this,
					"accesstoken") == "") {	
								Intent intent = new Intent(MainActivity.this,
										LogActivity.class);
								//MainActivity.this.finish();
								startActivityForResult(intent, 0);
								overridePendingTransition(R.anim.slide_right_in,
										R.anim.slide_right_out);
			} else {
			if (tao_F == null) {
				shuaxin(2);
				/*tao_F = new ShoppingCartFragment();
				// 判断当前界面是否隐藏，如果隐藏就进行添加显示，false表示显示，true表示当前界面隐藏
				if (!tao_F.isHidden()) {
					addFragment(tao_F);
					showFragment(tao_F);

				}
			} else {
				if (tao_F.isHidden()) {
					
					showFragment(tao_F);

				}*/
			}
			}
			break;
		case R.id.t_2:
			// 采购进货界面
		
			tv[0].setTextColor(android.graphics.Color.parseColor("#000000"));
			tv[1].setTextColor(android.graphics.Color.parseColor("#000000"));
			tv[2].setTextColor(android.graphics.Color.parseColor("#24A93A"));
			tv[3].setTextColor(android.graphics.Color.parseColor("#000000"));
			tv[4].setTextColor(android.graphics.Color.parseColor("#000000"));
			if (LogActivity.loadDataFromLocalXML(MainActivity.this,
					"accesstoken") == "") {	
								Intent intent = new Intent(MainActivity.this,
										LogActivity.class);
								//MainActivity.this.finish();
								startActivityForResult(intent, 0);
								overridePendingTransition(R.anim.slide_right_in,
										R.anim.slide_right_out);
			} else {
				shuaxin(3);
			}
			break;
		case R.id.t_3:
			// 销售发货界面
		
			tv[0].setTextColor(android.graphics.Color.parseColor("#000000"));
			tv[1].setTextColor(android.graphics.Color.parseColor("#000000"));
			tv[2].setTextColor(android.graphics.Color.parseColor("#000000"));
			tv[3].setTextColor(android.graphics.Color.parseColor("#24A93A"));
			tv[4].setTextColor(android.graphics.Color.parseColor("#000000"));
			if (LogActivity.loadDataFromLocalXML(MainActivity.this,
					"accesstoken") == "") {	
								Intent intent = new Intent(MainActivity.this,
										LogActivity.class);
								//MainActivity.this.finish();
								startActivityForResult(intent, 0);
								overridePendingTransition(R.anim.slide_right_in,
										R.anim.slide_right_out);
			} else {
				shuaxin(4);
			}
			break;
		case R.id.t_4:
			// 会员中心界面
			tv[0].setTextColor(android.graphics.Color.parseColor("#000000"));
			tv[1].setTextColor(android.graphics.Color.parseColor("#000000"));
			tv[2].setTextColor(android.graphics.Color.parseColor("#000000"));
			tv[3].setTextColor(android.graphics.Color.parseColor("#000000"));
			tv[4].setTextColor(android.graphics.Color.parseColor("#24A93A"));
			if (user_F == null) {
				user_F = new MeFragment();
				// 判断当前界面是否隐藏，如果隐藏就进行添加显示，false表示显示，true表示当前界面隐藏
				if (!user_F.isHidden()) {
					addFragment(user_F);
					showFragment(user_F);

				}
			} else {
				if (user_F.isHidden()) {
					showFragment(user_F);

				}
			}

			break;
		}

		// 设置按钮的选中和未选中资源
		for (int i = 0; i < bt_menu.length; i++) {
			bt_menu[i].setImageResource(select_off[i]);
			if ((v.getId() == bt_menu_id[i]) || (v.getId() == tv_id[i])) {
				bt_menu[i].setImageResource(select_on[i]);
			}
		}
	}

	private void shuaxin(int tab) {
		// TODO Auto-generated method stub
		Intent i=new Intent( MainActivity.this, MainActivity.class);  
		Bundle bundle = new Bundle();
		bundle.putInt("fragment", tab);
		i.putExtras(bundle);
    	i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    	startActivity(i);
    	//overridePendingTransition(anim_enter, anim_exit);
	}

	/** 添加Fragment **/
	public void addFragment(Fragment fragment) {
		FragmentTransaction ft = this.getSupportFragmentManager()
				.beginTransaction();
		ft.add(R.id.show_layout, fragment);
		ft.commit();
	}

	/** 删除Fragment **/
	public void removeFragment(Fragment fragment) {
		FragmentTransaction ft = this.getSupportFragmentManager()
				.beginTransaction();
		ft.remove(fragment);
		ft.commit();
	}

	/** 显示Fragment **/
	public void showFragment(Fragment fragment) {
		FragmentTransaction ft = this.getSupportFragmentManager()
				.beginTransaction();
		// 设置Fragment的切换动画
		// ft.setCustomAnimations(R.anim.cu_push_right_in,
		// R.anim.cu_push_left_out);

		// 判断页面是否已经创建，如果已经创建，那么就隐藏掉
		if (home_F != null) {
			ft.hide(home_F);
		}
		if (tao_F != null) {
			ft.hide(tao_F);
		}
		if (discover_F != null) {
			ft.hide(discover_F);
		}
		if (cart_F != null) {
			ft.hide(cart_F);
		}
		if (user_F != null) {
			ft.hide(user_F);
		}

		ft.show(fragment);
		ft.commitAllowingStateLoss();

	}

	private long mPressedTime = 0;

	@Override
	public void onBackPressed() {
		long mNowTime = System.currentTimeMillis();//获取第一次按键时间
		if((mNowTime - mPressedTime) > 2000){//比较两次按键时间差
				Toast.makeText(this, "再按一次退出米到仓", Toast.LENGTH_SHORT).show();
				mPressedTime = mNowTime;
		}
		else{//退出程序
			this.finish();
			System.exit(0);
		}
	}

	/** Fragment的回调函数 */
	@SuppressWarnings("unused")
	private IBtnCallListener btnCallListener;

	@Override
	public void onAttachFragment(Fragment fragment) {
		try {
			btnCallListener = (IBtnCallListener) fragment;
		} catch (Exception e) {
		}

		super.onAttachFragment(fragment);
	}

	/**
	 * 响应从Fragment中传过来的消息
	 */
	@Override
	public void transferMsg() {
		if (user_F == null) {
			user_F = new MeFragment();
			addFragment(user_F);
			showFragment(user_F);
		} else {
			showFragment(user_F);
		}
		bt_menu[3].setImageResource(select_off[3]);
		tv[3].setTextColor(android.graphics.Color.parseColor("#000000"));
		bt_menu[4].setImageResource(select_on[4]);
		tv[4].setTextColor(android.graphics.Color.parseColor("#24A93A"));

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		System.out.println("onActivityResult==="+requestCode+resultCode+ data);
		if (requestCode == 1) {
			if (user_F == null) {
				user_F = new MeFragment();
				addFragment(user_F);
				showFragment(user_F);
			} else {
				showFragment(user_F);
			}
			bt_menu[4].setImageResource(select_on[4]);
			tv[4].setTextColor(android.graphics.Color.parseColor("#24A93A"));
		}
			switch (requestCode) {
			case 1:
				String keyStr = data.getStringExtra("key");
				if ("-1".equals(keyStr)) {
					// showTips(R.drawable.tips_error, "网络不可用...");

				} else {
					// showTips(R.drawable.tips_smile, "网络已恢复正常...");
				}
				break;
			case 2:
				if (home_F == null) {
					home_F = new HomeFragment();
					addFragment(home_F);
					showFragment(home_F);
				} else {
					showFragment(home_F);
				}
				bt_menu[0].setImageResource(select_on[0]);
				tv[0].setTextColor(android.graphics.Color.parseColor("#24A93A"));
				break;

			}
		}


	/*
	 * private void showTips(int iconResId, String tips) { if (tipsToast !=
	 * null) { if (Build.VERSION.SDK_INT <
	 * Build.VERSION_CODES.ICE_CREAM_SANDWICH) { tipsToast.cancel(); } } else {
	 * tipsToast = TipsToast.makeText(getApplication().getBaseContext(), tips,
	 * TipsToast.LENGTH_SHORT); } tipsToast.show();
	 * tipsToast.setIcon(iconResId); tipsToast.setText(tips); }
	 */

}
