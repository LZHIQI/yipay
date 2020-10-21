package com.mican.myapplication;

import android.content.DialogInterface;
import android.os.Bundle;

import com.mican.myapplication.util.RxManager;
import com.mican.myapplication.util.SubmitProgressDialog;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * @name lzq
 * @class name：com.mican.myapplication
 * @class describe
 * @time 2020/10/5 11:45 PM
 * @change
 * @chang
 * @class describe
 */
public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity {

    protected T mPresenter;
    private RxManager rxBus;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = setPresenter();
        if (null != mPresenter && this instanceof BaseView) {
            mPresenter.attachView(this, (BaseView) this);
        }

    }

    protected T setPresenter() {
        return null;
    }


    public RxManager getRxBus() {
        if (rxBus == null) {
            rxBus = new RxManager();
        }
        return rxBus;
    }

    public BaseActivity<T> getThis() {
        return this;
    }

    public void dismissProgressDialog() {
        try {

            if (progressDialog != null) {
                progressDialog.dismiss();
                progressDialog.cancel();
            }
        } catch (Exception e) {

            e.printStackTrace();
        } finally {

            progressDialog = null;
        }
    }
    public SubmitProgressDialog progressDialog;
    /**
     * 显示进度条
     */
    public void showProgressDialog(String message) {
        try {
            dismissProgressDialog();
            if (isFinishing()) {
                return;
            }
            progressDialog = new SubmitProgressDialog(this, message);
            progressDialog.show();
            progressDialog.setCancelable(false);
        } catch (Exception e) {

        }
    }

    /**
     * 显示进度条
     */
    public void showProgressDialog( ) {
        try {
            dismissProgressDialog();
            if (isFinishing()) {
                return;
            }
            progressDialog = new SubmitProgressDialog(this, null);
            progressDialog.show();
            progressDialog.setCancelable(false);
        } catch (Exception e) {

        }
    }
    public void showProgressDialog( boolean cancel) {
        try {
            dismissProgressDialog();
            if (isFinishing()) {
                return;
            }
            progressDialog = new SubmitProgressDialog(this, null);
            progressDialog.show();
            progressDialog.setCancelable(cancel);
        } catch (Exception e) {

        }
    }

    public void showProgressDialog(String message, boolean cancel) {
        try {
            dismissProgressDialog();
            if (isFinishing()) {
                return;
            }
            progressDialog = new SubmitProgressDialog(this, message);
            progressDialog.show();
            progressDialog.setCancelable(cancel);
        } catch (Exception e) {

        }
    }

    /**
     * 显示进度条
     */
    public void showProgressDialog(String message, DialogInterface.OnKeyListener listener) {
        try {
            dismissProgressDialog();
            if (isFinishing()) {
                return;
            }
            progressDialog = new SubmitProgressDialog(this);
            progressDialog.setOnKeyListener(listener);
            progressDialog.show();
            progressDialog.setProgressText(message);
        } catch (Exception e) {
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (rxBus != null) rxBus.clear();
        dismissProgressDialog();
    }
}
