package com.mican.myapplication;

import android.os.Bundle;

import com.mican.myapplication.util.RxManager;

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
}
