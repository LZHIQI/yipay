/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mican.myapplication;



import android.app.Activity;

import com.mican.myapplication.util.RxManager;



import io.reactivex.disposables.Disposable;


public abstract class BasePresenter<T extends BaseView> {

    protected BaseActivity mContext;
    protected RxManager mRxManager;
    protected T mView;

    public void attachView(BaseActivity context, T view) {
        mContext = context;
        mRxManager = new RxManager();
        this.mView = view;
    }


    public void detachView() {
    if(mRxManager!=null) mRxManager.clear();
    }


     public  void  add(Disposable disposable){
         if(mRxManager!=null) mRxManager.add(disposable);
     }


}
