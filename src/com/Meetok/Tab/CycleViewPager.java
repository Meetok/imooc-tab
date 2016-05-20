package com.Meetok.Tab;

import android.content.Context;
import android.database.DataSetObserver;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

public class CycleViewPager extends ViewPager {

    private InnerPagerAdapter mAdapter;

    public CycleViewPager(Context context) {
        super( context);
        setOnPageChangeListener( null);
    }

    public CycleViewPager(Context context, AttributeSet attrs) {
        super( context, attrs);
        setOnPageChangeListener( null);
    }

    @Override
    public void setAdapter(PagerAdapter arg0) {
        mAdapter = new InnerPagerAdapter( arg0);
        super.setAdapter( mAdapter);
        setCurrentItem(1);
    }

    @Override
    public void setOnPageChangeListener(OnPageChangeListener listener) {
        super.setOnPageChangeListener( new InnerOnPageChangeListener( listener));
    }

    private class InnerOnPageChangeListener implements OnPageChangeListener {

        private OnPageChangeListener listener;
        private int position;

        public InnerOnPageChangeListener(OnPageChangeListener listener) {
            this.listener = listener;
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
            if(null != listener) {
                listener.onPageScrollStateChanged( arg0);
            }
            if(arg0 == ViewPager.SCROLL_STATE_IDLE) {
                if(position == mAdapter.getCount() - 1) {
                    setCurrentItem( 1, false);
                }
                else if(position == 0) {
                    setCurrentItem(mAdapter.getCount() - 2, false);
                }
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            if(null != listener) {
                listener.onPageScrolled( arg0, arg1, arg2);
            }
        }

        @Override
        public void onPageSelected(int arg0) {
            position = arg0;
            if(null != listener) {
                listener.onPageSelected( arg0);
            }
        }
    }

    private class InnerPagerAdapter extends PagerAdapter {

        private PagerAdapter adapter;

        public InnerPagerAdapter(PagerAdapter adapter) {
            this.adapter = adapter;
            adapter.registerDataSetObserver( new DataSetObserver() {

                @Override
                public void onChanged() {
                    notifyDataSetChanged();
                }

                @Override
                public void onInvalidated() {
                    notifyDataSetChanged();
                }

            });
        }

        @Override
        public int getCount() {
            return adapter.getCount() + 2;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return adapter.isViewFromObject( arg0, arg1);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            if(position == 0) {
                position = adapter.getCount() - 1;
            }
            else if(position == adapter.getCount() + 1) {
                position = 0;
            }
            else {
                position -= 1;
            }
            return adapter.instantiateItem( container, position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            adapter.destroyItem( container, position, object);
        }

    }
private float xDistance, yDistance, xLast, yLast,xDown, mLeft;  
@Override  
public boolean dispatchTouchEvent(MotionEvent ev) {  
    getParent().requestDisallowInterceptTouchEvent(true);  
    switch (ev.getAction()) {  
    case MotionEvent.ACTION_DOWN:  
        Log.d("touch", "ACTION_DOWN");  
        xDistance = yDistance = 0f;  
        xLast = ev.getX();  
        yLast = ev.getY();  
        xDown = ev.getX();  
        mLeft = ev.getX();// 解决与侧边栏滑动冲突  
        break;  
    case MotionEvent.ACTION_MOVE:  
        final float curX = ev.getX();  
        final float curY = ev.getY();  
        xDistance += Math.abs(curX - xLast);  
        yDistance += Math.abs(curY - yLast);  
        xLast = curX;  
        yLast = curY;  
        if (mLeft < 100 || xDistance < yDistance) {  
            getParent().requestDisallowInterceptTouchEvent(false);  
        } else {  
            if (getCurrentItem() == 0) {  
                if (curX < xDown) {  
                    getParent().requestDisallowInterceptTouchEvent(true);  
                } else {  
                    getParent().requestDisallowInterceptTouchEvent(false);  
                }  
            } else if (getCurrentItem() == (getAdapter().getCount()-1)) {  
                if (curX > xDown) {  
                    getParent().requestDisallowInterceptTouchEvent(true);  
                } else {  
                    getParent().requestDisallowInterceptTouchEvent(false);  
                }  
            } else {  
                getParent().requestDisallowInterceptTouchEvent(true);  
            }  
        }  
            break;  
        case MotionEvent.ACTION_UP:  
        case MotionEvent.ACTION_CANCEL:  
            break;  
    }  
    return super.dispatchTouchEvent(ev);  
}  

    
}
