package com.example.testmodule;

import android.app.Application;

import com.tencent.mmkv.MMKV;


/**
 * @name lzq
 * @class name：com.mican.myapplication
 * @class describe
 * @time 2020/9/25 1:53 PM
 * @change
 * @chang
 * @class describe
 */
public class YPApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        String rootDir = MMKV.initialize(this);
        System.out.println("mmkv root: " + rootDir);
    }
}
