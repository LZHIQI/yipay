package com.mican.myapplication.util;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.TextView;

import com.mican.myapplication.R;


public class SubmitProgressDialog extends Dialog {
    private TextView textView;
    private String message;
    public SubmitProgressDialog(Context context) {
        super(context, R.style.CustomProgressDialog);
    }

    public SubmitProgressDialog(Context context, String message) {
        this(context, R.style.CustomProgressDialog,message);
    }
    public SubmitProgressDialog(Context context, int theme, String message) {
        super(context, theme);
        this.message=message;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(StringUtils.isEmpty(message)?R.layout.layout_submit_process_dialog:R.layout.layout_submit_process_message_dialog);
        if(!StringUtils.isEmpty(message))  {
            textView = (TextView) findViewById(R.id.progress_text);
            setProgressText(message);
        };
        getWindow().getAttributes().gravity = Gravity.CENTER;
    }

    public void setProgressText(String content) {
        if (textView != null && !TextUtils.isEmpty(content)) {
            textView.setText(content);
        }
    }
}
