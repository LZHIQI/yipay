package com.mican.myapplication.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import java.lang.reflect.Method;

import androidx.annotation.NonNull;

import static android.view.View.NO_ID;

/**
 * @name lzq
 * @class name：com.mican.baselibrary.util
 * @class describe
 * @time 2020/9/14 11:19 AM
 * @change
 * @chang
 * @class describe
 */
public class AndroidBottomSoftBar {

        private static final String NAVIGATION= "navigationBarBackground";
    private View mViewObserved;//被监听的视图
    private int usableHeightPrevious;//视图变化前的可用高度
    private ViewGroup.LayoutParams frameLayoutParams;


    private AndroidBottomSoftBar(View viewObserving, final Activity activity) {
        mViewObserved = viewObserving;
        //给View添加全局的布局监听器
        mViewObserved.getViewTreeObserver().addOnGlobalLayoutListener(() -> resetLayoutByUsableHeight(activity));
        frameLayoutParams = mViewObserved.getLayoutParams();
    }

    /**
     * 关联要监听的视图
     */
    public static void assistActivity(View viewObserving, Activity activity) {
        new AndroidBottomSoftBar(viewObserving, activity);
    }

    /**
     * dpi 通过反射，获取包含虚拟键的整体屏幕高度
     * height 获取屏幕尺寸，但是不包括虚拟功能高度
     *
     * @return
     */
    public static int getHasVirtualKey(Activity activity) {
        int dpi = 0;
        Display display = activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        @SuppressWarnings("rawtypes")
        Class c;
        try {
            c = Class.forName("android.view.Display");
            @SuppressWarnings("unchecked")
            Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
            method.invoke(display, dm);
            dpi = dm.heightPixels;
        } catch (Exception e) {
            e.printStackTrace();
        }
        int height = activity.getWindowManager().getDefaultDisplay().getHeight();
        return dpi - height;
    }

        // 该方法需要在View完全被绘制出来之后调用，否则判断不了
        //在比如 onWindowFocusChanged（）方法中可以得到正确的结果
        public static  boolean isNavigationBarExist(@NonNull Activity activity){
        ViewGroup vp = (ViewGroup) activity.getWindow().getDecorView();
        if (vp != null) {
            for (int i = 0; i < vp.getChildCount(); i++) {
                vp.getChildAt(i).getContext().getPackageName();
                if (vp.getChildAt(i).getId()!= NO_ID && NAVIGATION.equals(activity.getResources().getResourceEntryName(vp.getChildAt(i).getId()))) {
                    return true;
                }
            }
        }
        return false;
    }

    private void resetLayoutByUsableHeight(Activity activity) {

        WindowManager wm = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        Point point = new Point();
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        Point realSize = new Point();
        display.getSize(size);
        display.getRealSize(realSize);
        int usableHeightNow = point.y;
        //比较布局变化前后的View的可用高度
        if (usableHeightNow != usableHeightPrevious) {
            mViewObserved.setPadding(0, 0, 0, getHasVirtualKey(activity));
            mViewObserved.requestLayout();//请求重新布局
            usableHeightPrevious = usableHeightNow;
        }
    }






}
