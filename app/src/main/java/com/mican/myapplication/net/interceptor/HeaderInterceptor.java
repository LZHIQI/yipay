package com.mican.myapplication.net.interceptor;

import android.text.TextUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

/**
 */
public class HeaderInterceptor implements Interceptor {


    public HeaderInterceptor() {
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        //添加统一请求头部
        Request.Builder requestBuilder = originalRequest.newBuilder();
        requestBuilder
                .addHeader("Accept-Charset", "utf-8");
                //.addHeader("User-Agent", getUserAgent());
        //判断不同请求方式，进一步自定义操作处理
        String method = originalRequest.method();
        if (TextUtils.equals("POST", method)) {
            originalRequest = requestBuilder.build();
        } else if (TextUtils.equals("GET", method)) {
            originalRequest = requestBuilder
                    .addHeader("Content-Type", "application/json;charset=UTF-8")
                    .build();
        }
        return chain.proceed(originalRequest);
    }


    private String bodyToString(final RequestBody request) {
        try {
            final RequestBody copy = request;
            final Buffer buffer = new Buffer();
            if (copy != null) {
                copy.writeTo(buffer);
            } else {
                return "";
            }
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "did not work";
        }
    }



}
