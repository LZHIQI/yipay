package com.example.testmodule.api.imp;


import com.example.testmodule.api.UserApi;
import com.example.testmodule.api.UserContract;
import com.example.testmodule.api.req.Register;
import com.example.testmodule.net.ApiService;
import com.example.testmodule.net.configure.ResponseDisposable;
import com.example.testmodule.net.configure.RxHelper;

import java.util.ArrayList;

/**
 * @name lzq
 * @class name：com.mican.myapplication.api.imp
 * @class describe
 * @time 2020/10/5 3:08 PM
 * @change
 * @chang
 * @class describe
 */
public class UserContractImp extends UserContract.Presenter {


    @Override
    public void register(Register req, UserContract.View view) {
        mRxManager.add(ApiService.createApi(UserApi.class).register(req)
                .compose(RxHelper.rxSchedulerObservable()).subscribeWith(new ResponseDisposable<Void>(mContext,true) {
                    @Override
                    protected void onSuccess(Void response) {
                        view.getSuccess(null);
                    }
                    @Override
                    protected void onFailure(int errorCode, String errorMsg) {
                        view.getError(errorMsg);
                    }
                })
        );


    }







}
