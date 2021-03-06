package com.example.testmodule.net.configure;

import android.app.Activity;
import android.text.TextUtils;


import com.example.testmodule.net.core.CoreApiException;

import java.lang.ref.SoftReference;

import io.reactivex.observers.DisposableObserver;

/**
 */

public abstract class ResponseDisposable<T> extends DisposableObserver<T> {

    private SoftReference<Activity> mContext;
    //调用此无参构造函数无网络加载框
    public ResponseDisposable(Activity context) {
        this(context, false);
    }

    private boolean  isToast=true;
    public ResponseDisposable(Activity context, boolean isShowLoading) {
        this(context, isShowLoading, null);
    }

    public ResponseDisposable(Activity context, boolean isShowLoading, String loadingContent) {
        if (context != null) {
            mContext = new SoftReference(context);
            if (isShowLoading) {
                loadingContent = TextUtils.isEmpty(loadingContent) ? "" : loadingContent;
                showDialog(loadingContent);
            }
        }
    }
    public ResponseDisposable(Activity context, boolean isShowLoading,boolean isToast) {
        this(context, isShowLoading, null);
        this.isToast=isToast;
    }


    public ResponseDisposable(Activity context, boolean isShowLoading, String loadingContent, boolean isToast) {
        this(context, isShowLoading, loadingContent);
        this.isToast=isToast;
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onNext(T t) {
        dismissDialog();
        onSuccess(t);
    }

    @Override
    public void onError(Throwable e) {
        unsubscribe();

        //统一处理错误异常
        CoreApiException coreApiException = FactoryException.analysisExcetpion(e);
        //用户登录失效，清除本地用户信息
        int code = coreApiException.getCode();
        if(code==1){
            onSuccess(null);
        }else {
            onFailure(code,coreApiException.getDisplayMessage());
        }
        dismissDialog();
    }

    @Override
    public void onComplete() {
        unsubscribe();
        dismissDialog();
    }

    protected abstract void onSuccess(T t);

    protected abstract void onFailure(int errorCode, String errorMsg);

    protected void showDialog(String loadingContent) {
        if (mContext == null) return;


    }

    protected void dismissDialog() {
        if (mContext == null) return;
    }

    public void unsubscribe() {
       if (isDisposed()) {
            dispose();
        }
    }
}
