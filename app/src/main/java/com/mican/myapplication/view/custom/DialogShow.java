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


import com.mican.myapplication.BaseActivity;
import com.mican.myapplication.R;
import com.mican.myapplication.api.result.VersionResult;
import com.mican.myapplication.util.StringUtils;
import com.mican.myapplication.util.UpdateUtils;

import org.jetbrains.annotations.NotNull;

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
                .setCancelable(isCancel)
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


    public static CustomDialog showMkDialog(Activity activity, CustomCallBack customCallBack, String content, String title,  String right,boolean isCancel,boolean isDismiss) {
        CustomDialogBuilder customDialogBuilder = new CustomDialogBuilder(activity);
        CustomDialog customDialog = customDialogBuilder.setDialog_bg_mode(CustomDialogBuilder.DialogType.SHADOW_MODE)
                .setLayoutId(R.layout.dialog_mk_layout)
                .setCancelable(isCancel)
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
        TextView def_submit = customDialog.findViewById(R.id.def_submit);
        if (!StringUtils.isEmpty(right)) {
            def_submit.setText(right);
        }
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

    public static void showUpdateApp0(@NotNull BaseActivity<?> activity, @NotNull VersionResult o) {
         if(o.isForce==1){
             showDefDialog(activity, new CustomCallBack() {
                 @Override
                 public void right() {
                     UpdateUtils.getInstance(activity).startLoading(o.isForce,o.versionCode,o.url);
                 }

                 @Override
                 public void left() {
                 }
             },o.introduce,"更新提醒","取消","更新",false,true);
         }else {
             showMkDialog(activity, new CustomCallBack() {
                 @Override
                 public void right() {
                     UpdateUtils.getInstance(activity).startLoading(o.isForce,o.versionCode,o.url);
                 }

                 @Override
                 public void left() {

                 }
             },o.introduce,"更新提醒","更新",false,true);
         }
    }




}
