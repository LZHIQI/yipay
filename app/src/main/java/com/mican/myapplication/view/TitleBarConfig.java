package com.mican.myapplication.view;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;


import com.mican.myapplication.R;
import com.mican.myapplication.util.BarUtils;
import com.mican.myapplication.util.StringUtils;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

public  class TitleBarConfig {

    /**
     * parent 必须为ConstraintLayout
     * @param activity
     * @param rootView
     * @param title
     * @param rightText
     */
   public  static  void   addX_W_Bar(Activity activity, ViewGroup rootView, String title, String rightText, TitleBarView.onViewClick click){
       TitleBarView titleBarView=new TitleBarView(activity);
       rootView.addView(titleBarView);
       titleBarView.setLeftDrawable(R.drawable.bar_back);
       titleBarView.setBackgroundColor(ContextCompat.getColor(activity,R.color.transparent));
       ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) titleBarView.getLayoutParams();
       layoutParams.topToTop= ConstraintLayout.LayoutParams.PARENT_ID;
       titleBarView.setLayoutParams(layoutParams);
       titleBarView.setTitleHeight(48);
       titleBarView.defSet_1();
       if(!StringUtils.isEmpty(rightText)){
           titleBarView.setRightText(rightText);
           titleBarView.setRightTextColor(R.color.white);
       }
       if(!StringUtils.isEmpty(title)){
           titleBarView.setTitle(title);
           titleBarView.setTitleColor(R.color.white);
       }
       titleBarView.setBar();
       titleBarView.setOnViewClick(click);
    }

    public  static  void   addX_W_Bar(Activity activity, ViewGroup rootView, String title, String rightText){
        TitleBarView titleBarView=new TitleBarView(activity);
        rootView.addView(titleBarView);
        titleBarView.setLeftDrawable(R.drawable.bar_close);
        titleBarView.setBackgroundColor(ContextCompat.getColor(activity,R.color.transparent));
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) titleBarView.getLayoutParams();
        layoutParams.topToTop= ConstraintLayout.LayoutParams.PARENT_ID;
        titleBarView.setLayoutParams(layoutParams);
        titleBarView.setTitleHeight(48);
        titleBarView.defSet_1();
        if(!StringUtils.isEmpty(rightText)){
            titleBarView.setRightText(rightText);
            titleBarView.setRightTextColor(R.color.white);
        }
        if(!StringUtils.isEmpty(title)){
            titleBarView.setTitle(title);
            titleBarView.setTitleColor(R.color.white);
        }
        titleBarView.setBar();
        titleBarView.setOnViewClick(new TitleBarView.onViewClick() {
            @Override
            public void leftClick() {
                activity.finish();
            }

            @Override
            public void rightClick() {

            }
        });
    }

    public  static  void   addBack_W_Bar(Activity activity, ViewGroup rootView, String title, String rightText, TitleBarView.onViewClick click){
        TitleBarView titleBarView=new TitleBarView(activity);
        rootView.addView(titleBarView);
        titleBarView.setLeftDrawable(R.drawable.bar_back);
        titleBarView.setBackgroundColor(ContextCompat.getColor(activity,R.color.transparent));
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) titleBarView.getLayoutParams();
        layoutParams.topToTop= ConstraintLayout.LayoutParams.PARENT_ID;
        titleBarView.setLayoutParams(layoutParams);
        titleBarView.setTitleHeight(48);
        titleBarView.defSet_1();
        if(!StringUtils.isEmpty(rightText)){
            titleBarView.setRightText(rightText);
            titleBarView.setRightTextColor(R.color.white);
        }
        if(!StringUtils.isEmpty(title)){
            titleBarView.setTitle(title);
            titleBarView.setTitleColor(R.color.white);
        }
        titleBarView.setBar();
        titleBarView.setOnViewClick(click);
    }

    public  static  void   addBack_W_Bar(Activity activity, ViewGroup rootView, String title, String rightText){
        TitleBarView titleBarView=new TitleBarView(activity);
        rootView.addView(titleBarView);
        setBack_W_Bar(titleBarView,activity,title,rightText);
    }



    public static  void setBack_W_Bar(TitleBarView titleBarView, Activity activity, String title, String rightText){
        titleBarView.setLeftDrawable(R.drawable.bar_back);
        titleBarView.setBackgroundColor(ContextCompat.getColor(activity,R.color.transparent));
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) titleBarView.getLayoutParams();
        layoutParams.topToTop= ConstraintLayout.LayoutParams.PARENT_ID;
        titleBarView.setLayoutParams(layoutParams);
        titleBarView.setTitleHeight(48);
        titleBarView.defSet_1();
        if(!StringUtils.isEmpty(rightText)){
            titleBarView.setRightText(rightText);
            titleBarView.setRightTextColor(R.color.white);
        }
        if(!StringUtils.isEmpty(title)){
            titleBarView.setTitle(title);
            titleBarView.setTitleColor(R.color.white);
        }
        titleBarView.setBar();
        titleBarView.setOnViewClick(new TitleBarView.onViewClick() {
            @Override
            public void leftClick() {
                activity.finish();
            }

            @Override
            public void rightClick() {

            }
        });
    }

    public static  void setBack_B_Bar(TitleBarView titleBarView, Activity activity, String title, String rightText, TitleBarView.onViewClick click){
        BarUtils.setStatusBarLightMode(activity,true);
        titleBarView.setLeftDrawable(R.drawable.bar_back,R.color.tx666);
        titleBarView.setBar();
        titleBarView.setTitleHeight(48);
        titleBarView.defSet_1();
        if(!StringUtils.isEmpty(rightText)){
            titleBarView.setRightText(rightText);
            titleBarView.setRightTextColor(R.color.tx333);
        }
        if(!StringUtils.isEmpty(title)){
            titleBarView.setTitle(title);
            titleBarView.setTitleColor(R.color.tx333);
        }
        if(click!=null){
            titleBarView.setOnViewClick(click);
        }
    }

    public static  void setBack_B_Bar(TitleBarView titleBarView, Activity activity, String title, String rightText){
        BarUtils.setStatusBarLightMode(activity,true);
        titleBarView.setLeftDrawable(R.drawable.bar_back,R.color.tx666);
        titleBarView.setBar();
        titleBarView.setTitleHeight(48);
        titleBarView.defSet_1();
        if(!StringUtils.isEmpty(rightText)){
            titleBarView.setRightText(rightText);
            titleBarView.setRightTextColor(R.color.tx333);
        }
        if(!StringUtils.isEmpty(title)){
            titleBarView.setTitle(title);
            titleBarView.setTitleColor(R.color.tx333);
        }
        titleBarView.setOnViewClick(new TitleBarView.onViewClick() {
            @Override
            public void leftClick() {
                activity.finish();
            }

            @Override
            public void rightClick() {

            }
        });
    }


    public static  void setBack_B_Bar2(TitleBarView titleBarView, Activity activity, String title, String rightText, TitleBarView.onViewClick click){
        BarUtils.setStatusBarLightMode(activity,true);
        titleBarView.setLeftDrawable(R.drawable.bar_back,R.color.tx666);
        titleBarView.setBar();
        titleBarView.setTitleHeight(48);
        titleBarView.defSet_1();
        if(!StringUtils.isEmpty(rightText)){
            titleBarView.setRightText(rightText);
            titleBarView.setRightTextColorList(R.color.present_def_tx_color);
        }
        if(!StringUtils.isEmpty(title)){
            titleBarView.setTitle(title);
            titleBarView.setTitleColor(R.color.tx333);
        }
        titleBarView.setOnViewClick(click);
    }
   public static void setStatusBar(View view, Activity activity){
       view.setVisibility( View.VISIBLE);
       ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
       layoutParams.height=BarUtils.getStatusBarHeight();
       BarUtils.setStatusBarLightMode(activity,true);
    }




    public static void setStatusBar(View view, Activity activity, boolean islightmode){
        view.setVisibility( View.VISIBLE);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height=BarUtils.getStatusBarHeight();
        BarUtils.setStatusBarLightMode(activity,islightmode);
    }



    public static  void setBackABar(TitleBarView titleBarView,
                                    Activity activity,
                                    String title,
                                    String rightText,
                                     int txColor  ,
                                     int backColor){
        BarUtils.setStatusBarLightMode(activity,true);
        titleBarView.setLeftDrawable(R.drawable.bar_back,txColor);
        titleBarView.setBackgroundColor(ContextCompat.getColor(activity,backColor));
        titleBarView.setBar();
        titleBarView.setTitleHeight(48);
        titleBarView.defSet_1();
        if(!StringUtils.isEmpty(rightText)){
            titleBarView.setRightText(rightText);
            titleBarView.setRightTextColor(txColor);
        }
        if(!StringUtils.isEmpty(title)){
            titleBarView.setTitle(title);
            titleBarView.setTitleColor(txColor);
        }
        titleBarView.setOnViewClick(new TitleBarView.onViewClick() {
            @Override
            public void leftClick() {
                activity.finish();
            }

            @Override
            public void rightClick() {

            }
        });
    }

    public static  void setBackABar(TitleBarView titleBarView,
                                    Activity activity,
                                    String title,
                                    String rightText,
                                    int txColor  ,
                                    int backColor,boolean isLightMode){
        BarUtils.setStatusBarLightMode(activity,isLightMode);
        titleBarView.setLeftDrawable(R.drawable.bar_back,txColor);
        titleBarView.setBackgroundColor(ContextCompat.getColor(activity,backColor));
        titleBarView.setBar();
        titleBarView.setTitleHeight(48);
        titleBarView.defSet_1();
        if(!StringUtils.isEmpty(rightText)){
            titleBarView.setRightText(rightText);
            titleBarView.setRightTextColor(txColor);
        }
        if(!StringUtils.isEmpty(title)){
            titleBarView.setTitle(title);
            titleBarView.setTitleColor(txColor);
        }
        titleBarView.setOnViewClick(new TitleBarView.onViewClick() {
            @Override
            public void leftClick() {
                activity.finish();
            }

            @Override
            public void rightClick() {

            }
        });
    }

    public static  void barConfig(TitleBarView titleBarView, Activity activity,
                                  String title, String rightText,
                                  @DrawableRes int resLeftId, @DrawableRes int resRightId, @ColorRes int resColor){
        BarUtils.setStatusBarLightMode(activity,true);
        titleBarView.setLeftDrawable(resLeftId,resColor);
        titleBarView.setRightDrawable(resRightId,resColor);
        titleBarView.setBar();
        titleBarView.setTitleHeight(48);
        titleBarView.defSet_1();
        if(!StringUtils.isEmpty(rightText)){
            titleBarView.setRightText(rightText);
            titleBarView.setRightTextColor(R.color.tx333);
        }
        if(!StringUtils.isEmpty(title)){
            titleBarView.setTitle(title);
            titleBarView.setTitleColor(R.color.tx333);
        }
        titleBarView.setOnViewClick(new TitleBarView.onViewClick() {
            @Override
            public void leftClick() {
                activity.finish();
            }

            @Override
            public void rightClick() {

            }
        });
    }
    public static  void barConfig(TitleBarView titleBarView, Activity activity,
                                  String title, String rightText,
                                  @DrawableRes int resLeftId, @DrawableRes int resRightId, @ColorRes int resColor, TitleBarView.onViewClick onViewClick){
        BarUtils.setStatusBarLightMode(activity,true);
        titleBarView.setLeftDrawable(resLeftId,resColor);
        titleBarView.setRightDrawable(resRightId,resColor);
        titleBarView.setBar();
        titleBarView.setTitleHeight(48);
        titleBarView.defSet_1();
        if(!StringUtils.isEmpty(rightText)){
            titleBarView.setRightText(rightText);
            titleBarView.setRightTextColor(R.color.tx333);
        }
        if(!StringUtils.isEmpty(title)){
            titleBarView.setTitle(title);
            titleBarView.setTitleColor(R.color.tx333);
        }
        titleBarView.setOnViewClick(onViewClick);
    }

    public static  void barConfig(TitleBarView titleBarView, Activity activity,
                                  String title, String rightText,
                                  @DrawableRes int resLeftId, @DrawableRes int resRightId){
        BarUtils.setStatusBarLightMode(activity,true);
        if(resLeftId!=0){
            titleBarView.setLeftDrawable(resLeftId);
        }
        if(resRightId!=0){
            titleBarView.setRightDrawable(resRightId);
        }
        titleBarView.setBar();
        titleBarView.setTitleHeight(48);
        titleBarView.defSet_1();
        if(!StringUtils.isEmpty(rightText)){
            titleBarView.setRightText(rightText);
            titleBarView.setRightTextColor(R.color.tx333);
        }
        if(!StringUtils.isEmpty(title)){
            titleBarView.setTitle(title);
            titleBarView.setTitleColor(R.color.tx333);
        }
        titleBarView.setOnViewClick(new TitleBarView.onViewClick() {
            @Override
            public void leftClick() {
                activity.finish();
            }

            @Override
            public void rightClick() {

            }
        });
    }

    public static  void barConfig(TitleBarView titleBarView, Activity activity,
                                  String title, String rightText,
                                  @DrawableRes int resLeftId, @DrawableRes int resRightId , TitleBarView.onViewClick onViewClick){
        BarUtils.setStatusBarLightMode(activity,true);
        if(resLeftId!=0){
            titleBarView.setLeftDrawable(resLeftId);
        }
        if(resRightId!=0){
            titleBarView.setRightDrawable(resRightId);
        }
        titleBarView.setBar();
        titleBarView.setTitleHeight(48);
        titleBarView.defSet_1();
        if(!StringUtils.isEmpty(rightText)){
            titleBarView.setRightText(rightText);
            titleBarView.setRightTextColor(R.color.tx333);
        }
        if(!StringUtils.isEmpty(title)){
            titleBarView.setTitle(title);
            titleBarView.setTitleColor(R.color.tx333);
        }
        titleBarView.setOnViewClick(onViewClick);
    }
}
