package com.example.testmodule.view.custom;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.example.testmodule.R;


public class CustomDialogBuilder {

    public class   DialogType{
        public static final int   SHADOW_MODE= 1;
        public static  final int  SHADOW_NO_MODE= 2;
    }
     CustomDialog customDialog;
    private boolean isCancelable=true;
    private int  dialog_bg_mode;
    private Activity context;
    private int layoutId;

    public CustomDialogBuilder setLayoutId(int layoutId) {
        this.layoutId = layoutId;
        return this;
    }

    public boolean isEnableCancel() {
        return isCancelable;
    }


    public CustomDialogBuilder(Activity context) {
        this.context = context;
    }
    public int getDialog_bg_mode() {
        if(dialog_bg_mode== DialogType.SHADOW_MODE){
            return R.style.mxx_tran_theme_dialog;
        }else {
            return R.style.mxx_no_shadow_theme_dialog;
        }
    }

    public CustomDialogBuilder setDialog_bg_mode(int dialog_bg_mode) {
        this.dialog_bg_mode = dialog_bg_mode;
        return this;
    }


    public Context getContext() {
        return context;
    }

    public CustomDialogBuilder setContext(Activity context) {
        this.context = context;
       return this;
    }


    public   CustomDialog create(){
        customDialog=new CustomDialog(getContext(),getDialog_bg_mode());
        customDialog.setCancelable(isCancelable);
        View inflate = LayoutInflater.from(context).inflate(layoutId, null);
        customDialog.initView(inflate);
      return   customDialog;
    }



    public   CustomDialog create(float wScale,float hScale){
        customDialog=new CustomDialog(getContext(),getDialog_bg_mode());
        customDialog.setSize(wScale,hScale);
        View inflate = LayoutInflater.from(context).inflate(layoutId, null);
        customDialog.initView(inflate);
        customDialog.setCancelable(isCancelable);
        return   customDialog;
    }

    public void show(){
        if(customDialog!=null){
            dismiss();
            if(context!=null){
                customDialog.show();
            };
        }
    }


    public CustomDialogBuilder setCancelable(boolean isCancelable) {
        this.isCancelable = isCancelable;
        return this;
    }

    public void  dismiss(){
        customDialog.dismiss();
    }
}
