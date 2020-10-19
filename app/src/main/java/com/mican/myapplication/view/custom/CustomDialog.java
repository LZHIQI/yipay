package com.mican.myapplication.view.custom;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.mican.myapplication.util.NumberUtils;


/**
 * Created by lzq on 2017/4/26 11:16.
 */

public class CustomDialog extends Dialog implements View.OnClickListener {
    private Context context;

    private OnCustomItemClickListener mOnCustomItemClickListener;
    /**
     * 要监听的控件id
     */
    private int[] listenedItems;
    private int et_refuse_reason;
   private View parentView;
    public CustomDialog(Context context) {
        super(context);
        this.context = context;
    }
    //R.style.mxx_tran_theme_dialog

    public CustomDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;
    }

    public CustomDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    private float wScale=0.8f; //宽度

    private float hScale=0; //高度


    public void setSize(float wScale,float hScale) {
        this.wScale= wScale;
        this.hScale=hScale;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window window = getWindow();
        window.setGravity(Gravity.CENTER); // 此处可以设置dialog显示的位置为居中
        // 宽度全屏
        WindowManager windowManager = ((Activity) context).getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        Point size = new Point();
        display.getSize(size);
        int multiply = NumberUtils.multiply(size.x, wScale);
        lp.width = NumberUtils.multiply(size.x,wScale) ; // 设置dialog宽度为屏幕的4/5
        if(hScale!=0) lp.height = NumberUtils.multiply(size.y,hScale) ; // 设置dialog宽度为屏幕的4/5
        getWindow().setAttributes(lp);
        // 点击Dialog外部消失
       // setCanceledOnTouchOutside(true);
    }
    public  void   initView(View v){
        setContentView(v);
        parentView=v;
   }


    public View getParentView() {
        return parentView;
    }

    @Override
    public void onClick(View v) {
        mOnCustomItemClickListener.onCustomMenuItemClick(this, v);
    }

    public void setOnCustomItemClickListener(OnCustomItemClickListener onCustomItemClickListener) {
        this.mOnCustomItemClickListener = onCustomItemClickListener;
    }

    public interface OnCustomItemClickListener {
        void onCustomMenuItemClick(CustomDialog dialog, View view);

    }


}
