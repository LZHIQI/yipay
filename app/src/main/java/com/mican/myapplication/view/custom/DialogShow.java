package com.mican.myapplication.view.custom;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.TextView;


import com.mican.myapplication.R;
import com.mican.myapplication.api.result.VersionResult;
import com.mican.myapplication.util.StringUtils;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

/**
 * @name lzq
 * @class name：com.mican.view_module.widget.dialog.custom
 * @class describe
 * @time 2020/7/14 3:28 PM
 * @change
 * @chang
 * @class describe
 */
public class DialogShow {

    /**
     *  * * * * * * *
     *  * 标题/内容   *
     *  * * * * * * *
     *  * 取消 * 确认 *
     *  * * * * * * *
     */
    public static CustomDialog showDefDialog(Activity activity, CustomCallBack customCallBack, String content, String title, String left, String right,boolean isCancel,boolean isDismiss) {
        CustomDialogBuilder customDialogBuilder = new CustomDialogBuilder(activity);
        CustomDialog customDialog = customDialogBuilder.setDialog_bg_mode(CustomDialogBuilder.DialogType.SHADOW_MODE)
                .setLayoutId(R.layout.dialog_def_layout)
                .setEnableCancel(isCancel)
                .create();
        customDialogBuilder.show();
        if (!StringUtils.isEmpty(title)) {
            TextView def_title = customDialog.findViewById(R.id.def_title);
            def_title.setText(title);

        }
        if (!StringUtils.isEmpty(content)) {
            TextView def_content = customDialog.findViewById(R.id.def_content);
            def_content.setText(content);

        }
        TextView defCancel = customDialog.findViewById(R.id.def_cancel);
        TextView def_submit = customDialog.findViewById(R.id.def_submit);
        if (!StringUtils.isEmpty(left)) {
            defCancel.setText(left);
        }
        if (!StringUtils.isEmpty(right)) {
            def_submit.setText(right);
        }
        defCancel.setOnClickListener(v -> {
            if(isDismiss){
                customDialog.dismiss();
            }
            if (customCallBack != null) customCallBack.left();
        });
        def_submit.setOnClickListener(v -> {
            if(isDismiss){
                customDialog.dismiss();
            }
            if (customCallBack != null) customCallBack.right();
        });

        return customDialog;

    }


    /**
     *  * * * * * * *
     *  *    提示    *
     *  * * * * * * *
     *  * 取消 * 确认 *
     *  * * * * * * *
     */

    public static CustomDialog showNoTitleDialog(Activity activity, CustomCallBack customCallBack, String content, String left, String right) {
        CustomDialogBuilder customDialogBuilder = new CustomDialogBuilder(activity);
        CustomDialog customDialog = customDialogBuilder.setDialog_bg_mode(CustomDialogBuilder.DialogType.SHADOW_MODE)
                .setLayoutId(R.layout.dialog_def2_layout)
                .create();
        customDialogBuilder.show();
        if (!StringUtils.isEmpty(content)) {
            TextView def_content = customDialog.findViewById(R.id.def_content);
            def_content.setText(content);
        }
        TextView defCancel = customDialog.findViewById(R.id.def_cancel);
        TextView def_submit = customDialog.findViewById(R.id.def_submit);
        if (!StringUtils.isEmpty(left)) {
            defCancel.setText(left);
        }
        if (!StringUtils.isEmpty(right)) {
            def_submit.setText(right);
        }
        defCancel.setOnClickListener(v -> {
            customDialog.dismiss();
            if (customCallBack != null) customCallBack.left();
        });
        def_submit.setOnClickListener(v -> {
            customDialog.dismiss();
            if (customCallBack != null) customCallBack.right();
        });

        return customDialog;

    }
  /*  public static CustomDialog showUpdateApp(Activity activity, VersionResult versionResult){
        if(StringUtils.isEmpty(versionResult.appUrl)||StringUtils.isEmpty(versionResult.versionNumber))return null;
        CustomDialogBuilder customDialogBuilder = new CustomDialogBuilder(activity);
        CustomDialog customDialog = customDialogBuilder.setDialog_bg_mode(CustomDialogBuilder.DialogType.SHADOW_MODE)
                .setLayoutId(R.layout.dialog_def_layout)
                .setCancelable(false)
                .create();
        if(versionResult.versionNumber.equals(SettingUtil.getSpUtils().getString(Constants.UP_DATE_VERSION))&&versionResult.forceUpdate!=1){
            if(TimeUtils.getNowMills()>SettingUtil.getSpUtils().getLong(Constants.UP_DATE_TIME)+24*60*60*1000){
                SettingUtil.getSpUtils().put(Constants.UP_DATE_TIME, TimeUtils.getNowMills());
                showUp(customDialogBuilder,versionResult,customDialog,activity);
            }
        }else {
            SettingUtil.getSpUtils().put(Constants.UP_DATE_TIME, TimeUtils.getNowMills());
            SettingUtil.getSpUtils().put(Constants.UP_DATE_VERSION,versionResult.versionNumber);
            showUp(customDialogBuilder,versionResult,customDialog,activity);
        }
        return customDialog;
    }
*/

   /* private static void showUp(CustomDialogBuilder customDialogBuilder,VersionResult versionResult, CustomDialog customDialog,Activity activity) {
        customDialogBuilder.show();
        if (!StringUtils.isEmpty(versionResult.versionNumber)) {
            TextView version_num = customDialog.findViewById(R.id.up_title);
            version_num.setText(String.format("v%s",versionResult.versionNumber));
        }
        if (!StringUtils.isEmpty(versionResult.versionDescription)) {
            TextView up_content = customDialog.findViewById(R.id.up_content);
            up_content.setText(versionResult.versionDescription);
        }
        TextView defCancel = customDialog.findViewById(R.id.up_callback);
        defCancel.setVisibility(versionResult.forceUpdate==1?View.GONE:View.VISIBLE);
        TextView def_submit = customDialog.findViewById(R.id.up_submit);
        defCancel.setOnClickListener(v -> {
            customDialog.dismiss();
        });
        def_submit.setOnClickListener(v -> {
            UpdateUtils.getInstance(activity).startLoading(versionResult.forceUpdate,versionResult.versionNumber,versionResult.appUrl);
            customDialog.dismiss();
        });
    }*/
}
