package com.example.testmodule.api;



import com.example.testmodule.api.req.*;
import com.example.testmodule.net.core.*;

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
    @POST("api/user/register")
    Observable<Response<Void>> register(@Body Register register);



}
