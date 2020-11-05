package com.mican.myapplication.api;

import com.mican.myapplication.User;
import com.mican.myapplication.api.req.PayCallReq;
import com.mican.myapplication.api.req.QueryReq;
import com.mican.myapplication.api.req.VersionReq;
import com.mican.myapplication.api.result.LoginResult;
import com.mican.myapplication.api.req.Register;
import com.mican.myapplication.api.result.MerchantStatus;
import com.mican.myapplication.api.result.PayCallResult;
import com.mican.myapplication.api.result.QueryOrder;
import com.mican.myapplication.api.result.QueryStatus;
import com.mican.myapplication.api.result.TcResult;
import com.mican.myapplication.api.result.UserDetail;
import com.mican.myapplication.api.result.VersionResult;
import com.mican.myapplication.net.core.Response;

import java.util.ArrayList;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

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
    @POST("/api/api/user/login")
    Observable<Response<User>> login(@Body Register register);


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
    Observable<Response<QueryOrder>> queryOrder(@Body QueryReq queryReq);



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
    @GET("/api/order/callBack")
    Observable<Response<PayCallResult>> payCall(@Query("notificationPkg") String notificationPkg,@Query("notificationTitle") String notificationTitle,@Query("notificationText") String notificationText );



    @POST("/api/version/getVersion")
    Observable<Response<VersionResult>> checkVersion(@Body VersionReq versionReq);


}
