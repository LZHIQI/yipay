package com.mican.myapplication.view.custom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.mican.myapplication.R;


public class CustomDialogBuilder {

    public class   DialogType{
        public static final int   SHADOW_MODE= 1;
        public static  final int  SHADOW_NO_MODE= 2;
    }
     CustomDialog customDialog;

    private int  dialog_bg_mode;
    private Context context;
    private int layoutId;
    private boolean enableCancel=true;
    public CustomDialogBuilder setLayoutId(int layoutId) {
        this.layoutId = layoutId;
        return this;
    }

    public boolean isEnableCancel() {
        return enableCancel;
    }

    public CustomDialogBuilder setEnableCancel(boolean enableCancel) {
        this.enableCancel = enableCancel;
        return this;
    }

    public CustomDialogBuilder(Context context) {
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

    public CustomDialogBuilder setContext(Context context) {
        this.context = context;
       return this;
    }


    public   CustomDialog create(){
        customDialog=new CustomDialog(getContext(),getDialog_bg_mode());
        customDialog.setCancelable(enableCancel);
        View inflate = LayoutInflater.from(context).inflate(layoutId, null);
        customDialog.initView(inflate);
      return   customDialog;
    }



    public   CustomDialog create(float wScale,float hScale){
        customDialog=new CustomDialog(getContext(),getDialog_bg_mode());
        customDialog.setSize(wScale,hScale);
        View inflate = LayoutInflater.from(context).inflate(layoutId, null);
        customDialog.initView(inflate);
        return   customDialog;
    }

    public void show(){
        if(customDialog!=null){
            dismiss();
            customDialog.show();
        }
    }

    public void  dismiss(){
        customDialog.dismiss();
    }
}
