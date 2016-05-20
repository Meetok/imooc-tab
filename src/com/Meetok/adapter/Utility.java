package com.Meetok.adapter;

import com.Meetok.View.MyGridView;

import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;
/**
 * guidview
 * @author Administrator
 *
 */
public class Utility {

	public static void setListViewHeightBasedOnChildren(MyGridView mygridView) {
		ListAdapter guidAdapter = mygridView.getAdapter();
		if (guidAdapter == null) {
			// pre-condition
			return;
		}

		int totalHeight = 0;
		for (int i = 0; i < guidAdapter.getCount(); i++) {
			View listItem = guidAdapter.getView(i, null, mygridView);
			listItem.measure(0, 0); //计算子项View 的宽高  
			totalHeight =totalHeight+ listItem.getMeasuredHeight(); //统计所有子项的总高度  
		}

		ViewGroup.LayoutParams params = mygridView.getLayoutParams();
		params.height = (totalHeight)/3;
		mygridView.setLayoutParams(params);
	}
	public static void setGridViewHeightBasedOnChildren(MyGridView mygridView) {
		ListAdapter guidAdapter = mygridView.getAdapter();
		if (guidAdapter == null) {
			// pre-condition
			return;
		}

		int totalHeight = 0;
		for (int i = 0; i < guidAdapter.getCount(); i++) {
			View listItem = guidAdapter.getView(i, null, mygridView);
			listItem.measure(0, 0); //计算子项View 的宽高  
			totalHeight =totalHeight+ listItem.getMeasuredHeight(); //统计所有子项的总高度  
		}

		ViewGroup.LayoutParams params = mygridView.getLayoutParams();
		params.height = (totalHeight)/2;
		mygridView.setLayoutParams(params);
	}
}
