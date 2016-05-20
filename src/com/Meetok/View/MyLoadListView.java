package com.Meetok.View;


import com.imooc.tab03.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;

public class MyLoadListView extends ListView implements OnScrollListener {


	View footer;// 底部布局；
	int totalItemCount;// 总数量；
	int lastVisibleItem;// 最后一个可见的item；
	boolean isLoading;// 正在加载；
	ILoadListener iLoadListener;
	public MyLoadListView(Context context) {
		super(context);
	}

	public MyLoadListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MyLoadListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,MeasureSpec.AT_MOST);

		super.onMeasure(widthMeasureSpec, expandSpec);
	}


	/**
	 * 添加底部加载提示布局到listview
	 * 
	 * @param context
	 */
	private void initView(Context context) {
		LayoutInflater inflater = LayoutInflater.from(context);
		footer = inflater.inflate(R.layout.load_more, null);
		footer.findViewById(R.id.loadmore_view).setVisibility(View.GONE);
		this.addFooterView(footer);
		this.setOnScrollListener(this);
	}

	@Override
	public void onScroll(AbsListView arg0, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub
		this.lastVisibleItem = firstVisibleItem + visibleItemCount;
		this.totalItemCount = totalItemCount;
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
		if (totalItemCount == lastVisibleItem
				&& scrollState == SCROLL_STATE_IDLE) {
			if (!isLoading) {
				isLoading = true;
				footer.findViewById(R.id.loadmore_view).setVisibility(
						View.VISIBLE);
				// 加载更多
				iLoadListener.onLoad();
			}
		}
	}

	/**
	 * 加载完毕
	 */
	public void loadComplete(){
		isLoading = false;
		footer.findViewById(R.id.loadmore_view).setVisibility(
				View.GONE);
	}
	

	public void setInterface(ILoadListener iLoadListener){
		this.iLoadListener = iLoadListener;
	}
	//加载更多数据的回调接口
	public interface ILoadListener{
		public void onLoad();
	}
}
