package com.mican.myapplication.api.imp;

import com.mican.myapplication.api.UserApi;
import com.mican.myapplication.api.UserContract;
import com.mican.myapplication.api.req.PayCallReq;
import com.mican.myapplication.api.req.QueryReq;
import com.mican.myapplication.api.req.Register;
import com.mican.myapplication.api.result.Login;
import com.mican.myapplication.api.result.MerchantStatus;
import com.mican.myapplication.api.result.PayCallResult;
import com.mican.myapplication.api.result.QueryOrder;
import com.mican.myapplication.api.result.QueryStatus;
import com.mican.myapplication.api.result.TcResult;
import com.mican.myapplication.api.result.UserDetail;
import com.mican.myapplication.net.ApiService;
import com.mican.myapplication.net.configure.ResponseDisposable;
import com.mican.myapplication.net.configure.RxHelper;
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

    @Override
    public void login(Register register, UserContract.View view) {
        mRxManager.add(ApiService.createApi(UserApi.class).login(register)
                .compose(RxHelper.rxSchedulerObservable()).subscribeWith(new ResponseDisposable<Login>(mContext,true) {
                    @Override
                    protected void onSuccess(Login response) {
                        view.getSuccess(response);
                    }
                    @Override
                    protected void onFailure(int errorCode, String errorMsg) {
                        view.getError(errorMsg);
                    }
                })
        );
    }

    @Override
    public void detail(QueryReq queryReq, UserContract.View view) {
        mRxManager.add(ApiService.createApi(UserApi.class).detail(queryReq)
                .compose(RxHelper.rxSchedulerObservable()).subscribeWith(new ResponseDisposable<UserDetail>(mContext,true) {
                    @Override
                    protected void onSuccess(UserDetail response) {
                        view.getSuccess(response);
                    }
                    @Override
                    protected void onFailure(int errorCode, String errorMsg) {
                        view.getError(errorMsg);
                    }
                })
        );
    }

    @Override
    public void tcList( UserContract.View view) {
        mRxManager.add(ApiService.createApi(UserApi.class).tcList()
                .compose(RxHelper.rxSchedulerObservable()).subscribeWith(new ResponseDisposable<ArrayList<TcResult>>(mContext,true) {
                    @Override
                    protected void onSuccess(ArrayList<TcResult> response) {
                        view.getSuccess(response);
                    }
                    @Override
                    protected void onFailure(int errorCode, String errorMsg) {
                        view.getError(errorMsg);
                    }
                })
        );
    }

    @Override
    public void queryOrder(QueryReq queryReq,UserContract.View view) {
        mRxManager.add(ApiService.createApi(UserApi.class).queryOrder(queryReq)
                .compose(RxHelper.rxSchedulerObservable()).subscribeWith(new ResponseDisposable<QueryOrder>(mContext,true) {
                    @Override
                    protected void onSuccess(QueryOrder  response) {
                        view.getSuccess(response);
                    }
                    @Override
                    protected void onFailure(int errorCode, String errorMsg) {
                        view.getError(errorMsg);
                    }
                })
        );
    }

    @Override
    public void queryStatus(QueryReq queryReq,UserContract.View view) {
        mRxManager.add(ApiService.createApi(UserApi.class).queryStatus(queryReq)
                .compose(RxHelper.rxSchedulerObservable()).subscribeWith(new ResponseDisposable<QueryStatus>(mContext,true) {
                    @Override
                    protected void onSuccess(QueryStatus  response) {
                        view.getSuccess(response);
                    }
                    @Override
                    protected void onFailure(int errorCode, String errorMsg) {
                        view.getError(errorMsg);
                    }
                })
        );
    }

    @Override
    public void merchantStatus(QueryReq queryReq,UserContract.View view) {
        mRxManager.add(ApiService.createApi(UserApi.class).merchantStatus(queryReq)
                .compose(RxHelper.rxSchedulerObservable()).subscribeWith(new ResponseDisposable<MerchantStatus>(mContext,true) {
                    @Override
                    protected void onSuccess(MerchantStatus  response) {
                        view.getSuccess(response);
                    }
                    @Override
                    protected void onFailure(int errorCode, String errorMsg) {
                        view.getError(errorMsg);
                    }
                })
        );
    }

    @Override
    public void payCall(PayCallReq queryReq,UserContract.View view) {
        mRxManager.add(ApiService.createApi(UserApi.class).payCall(queryReq)
                .compose(RxHelper.rxSchedulerObservable()).subscribeWith(new ResponseDisposable<PayCallResult>(mContext,true) {
                    @Override
                    protected void onSuccess(PayCallResult  response) {
                        view.getSuccess(response);
                    }
                    @Override
                    protected void onFailure(int errorCode, String errorMsg) {
                        view.getError(errorMsg);
                    }
                })
        );
    }
}
