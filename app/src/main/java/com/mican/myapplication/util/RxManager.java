package com.mican.myapplication.util;


import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 用于管理RxBus的事件和Rxjava相关代码的生命周期处理
 */

public class RxManager {

    private CompositeDisposable mCompositeDisposable;

    public RxManager() {
        this.mCompositeDisposable = new CompositeDisposable();
    }

    public void add(Disposable disposable) {
        if (mCompositeDisposable != null && disposable != null) {
            mCompositeDisposable.add(disposable);
        }
    }

    public void clear() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
            mCompositeDisposable = null;
        }
    }


    public void unbind(Disposable disposable) {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.remove(disposable);

        }
    }

    public void add(Completable disposable, final Action onComplete, final Consumer<? super Throwable> onError) {
        if (mCompositeDisposable != null && disposable != null) {
            mCompositeDisposable.add(disposable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribe(onComplete,onError));
        }
    }

}