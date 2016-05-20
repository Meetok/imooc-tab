package com.Meetok.Tab;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;

import com.Meetok.Tab.SystemBarTintManager;

/**
 * Created by xi on 2015/11/18.
 */
public class ImmersionBar {
    /**
     * 沉浸式状态栏
     * @param context
     */
    public static void setImmersionBar(Context context, int colorResId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(context, true);
        }

        SystemBarTintManager tintManager = new SystemBarTintManager((Activity)context);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(colorResId);
    }

    @TargetApi(19)
    private static void setTranslucentStatus(Context context, boolean on) {
        Window win = ((Activity)context).getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }
}
