package com.mican.myapplication.api;

import com.mican.myapplication.api.req.QueryReq;
import com.mican.myapplication.api.result.Login;
import com.mican.myapplication.api.req.Register;
import com.mican.myapplication.api.result.MerchantStatus;
import com.mican.myapplication.api.result.PayCallResult;
import com.mican.myapplication.api.result.QueryOrder;
import com.mican.myapplication.api.result.QueryStatus;
import com.mican.myapplication.api.result.TcResult;
import com.mican.myapplication.api.result.UserDetail;
import com.mican.myapplication.net.core.Response;

import java.util.ArrayList;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * @name lzq
 * @class name：com.mican.myapplication.api
 * @class describe
 * @time 2020/10/5 1:51 PM
 * @change
 * @chang
 * @class describe
 */
public  interface UserApi {

    /**
     * 注册
     */
    @POST("/api/user/register")
    Observable<Response<Void>> register(@Body Register register);


    /**
     * 登录
     */
    @POST("/api/user/login")
    Observable<Response<Login>> login(@Body Register register);


    /**
     * 详细
     */
    @POST("/api/user/details")
    Observable<Response<UserDetail>> detail(@Body QueryReq queryReq);


    /**
     * 详细
     */
    @POST("/api/meal/list")
    Observable<Response<ArrayList<TcResult>>> tcList( );


    /**
     * 查询订单
     */
    @POST("/api/order/create")
    Observable<Response<ArrayList<QueryOrder>>> queryOrder(@Body QueryReq queryReq);



    /**
     * 查询订单状态
     */
    @POST("/api/order/checkOrder")
    Observable<Response<QueryStatus>> queryStatus(@Body QueryReq queryReq);


    /**
     * 查询商家状态
     */
    @POST("/api/order/getState")
    Observable<Response<MerchantStatus>> merchantStatus(@Body QueryReq queryReq);


    /**
     * 查询支付以后的回调接口
     */
    @POST("/api/order/callBack")
    Observable<Response<PayCallResult>> payCall(@Body QueryReq queryReq);



}
