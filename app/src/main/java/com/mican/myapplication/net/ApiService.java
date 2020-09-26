package com.mican.myapplication.net;

import android.text.TextUtils;


import com.mican.myapplication.BuildConfig;
import com.mican.myapplication.net.configure.HttpCache;
import com.mican.myapplication.net.convert.NullOnEmptyConverterFactory;
import com.mican.myapplication.net.interceptor.CacheInterceptor;
import com.mican.myapplication.net.interceptor.HeaderInterceptor;
import com.mican.myapplication.net.interceptor.HttpLogInterceptor;
import com.mican.myapplication.net.interceptor.TokenInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiService {
     public static final String BASE_API_URL_FORMAL = "https://dev.yibenjiankang.com:9906/";//用户版API正式环境
     public static final String BASE_API_URL_TEST = "https://devali.imecan.cn:9900/imecan-api/";//用户版API测试环境
  //   public static final String BASE_API_URL_TEST = "https://dev.yibenjiankang.com:9906/";//用户版API测试环境
    private static final int CONNECT_TIME = 15;
    private static final int WRITE_TIME = 20;
    private static final int READ_TIME = 20;

    private static final HttpLogInterceptor formattingJsonLogInterceptor = new HttpLogInterceptor().setLevel(HttpLogInterceptor.Level.BODY);
    private static final HeaderInterceptor headerInterceptor = new HeaderInterceptor();
    private static final TokenInterceptor tokenInterceptor = new TokenInterceptor();
    private static final CacheInterceptor cacheInterceptor = new CacheInterceptor();
    private static final OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .addInterceptor(headerInterceptor)
            .addInterceptor(tokenInterceptor)
            .addInterceptor(formattingJsonLogInterceptor)
            .cache(HttpCache.getCache())
            .addNetworkInterceptor(cacheInterceptor)
            .addInterceptor(cacheInterceptor)
            .connectTimeout(CONNECT_TIME, TimeUnit.SECONDS)//TimeOut
            .readTimeout(READ_TIME, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIME, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)//失败重连
            .build();

    public static <T> T createApi(Class<T> clazz) {
        return createApi(clazz, null);
    }

    public static <T> T createApi(Class<T> clazz, String baseUrl) {
        return createApi(clazz, baseUrl, null);
    }

    public static <T> T createApi(Class<T> clazz, String baseUrl, Converter.Factory factory) {
        Retrofit.Builder builder = new Retrofit.Builder();
        if (TextUtils.isEmpty(baseUrl)) {
            if (BuildConfig.DEBUG) {
                builder.baseUrl(BASE_API_URL_TEST);
            } else {
                builder.baseUrl(BASE_API_URL_FORMAL);
            }
        } else {
            builder.baseUrl(baseUrl);
        }
        builder.client(okHttpClient)
                .addConverterFactory(new NullOnEmptyConverterFactory())
                .addConverterFactory(factory == null ? GsonConverterFactory.create() : factory)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create());
        Retrofit retrofit = builder.build();
        return retrofit.create(clazz);
    }


}
