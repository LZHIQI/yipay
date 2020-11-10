package com.example.testmodule.net.interceptor;

import android.os.Build;


import com.example.testmodule.UserManager;
import com.example.testmodule.util.AppUtils;
import com.example.testmodule.util.DeviceUtils;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


/**
 *
 */
public class TokenInterceptor implements Interceptor {
    public static final String REQUEST_AUTH_TOKEN = "Authorization";
    public static final String REQUEST_AUTH_CLIENTKEY = "clientKey";
    public static final String REQUEST_AUTH_DEVICEDKEY = "deviceKey";
    public static final String REQUEST_AUTH_VERSION = "version";
    public static final String REQUEST_AUTH_DEVICEDID = "deviceId";

    public static long getAuthTokenTime() {
        return  UserManager.Companion.getKv().decodeLong("http_request",0);
    }

    public static void setAuthTokenTime() {
        UserManager.Companion.getKv().encode("http_request",System.currentTimeMillis());

    }

    public static String getAuthToken() {
        return   UserManager.Companion.getKv().decodeString("http_token","");
    }

    public static void setAuthToken(String token) {
        UserManager.Companion.getKv().encode("http_token",token);
    }


    public static String getAuthClientKey() {
        return  UserManager.Companion.getKv().decodeString("http_request_clientKey", "mass");
    }

    public static String getAuthDeviceID() {
        return UserManager.Companion.getKv().decodeString("http_request_deviceId", DeviceUtils.getUniqueDeviceId());
    }



    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        Request authRequest = originalRequest.newBuilder().build();
        if(UserManager.Companion.isLogin()){
            authRequest = originalRequest.newBuilder()
                    .addHeader(REQUEST_AUTH_TOKEN, Objects.requireNonNull(UserManager.Companion.getUser().getToken())).build();
        }
        return chain.proceed(authRequest);
    }

    private String getUserAgent() {
        StringBuilder ua = new StringBuilder();
        ua.append('/' + AppUtils.getAppInfo(AppUtils.getAppPackageName()).getVersionName() + '_'
                + AppUtils.getAppInfo(AppUtils.getAppPackageName()).getVersionCode());
        ua.append("/Android");
        ua.append("/" + Build.VERSION.RELEASE);
        ua.append("/" + Build.MODEL);
        ua.append("/" + getUniqueID());
        return ua.toString();
    }

    private static String getUniqueID() {
        String m_szDevIDShort = "35" + (Build.BOARD.length() % 10) + (Build.BRAND.length() % 10) + (Build.CPU_ABI.length() % 10) + (Build.DEVICE.length() % 10) + (Build.MANUFACTURER.length() % 10) + (Build.MODEL.length() % 10) + (Build.PRODUCT.length() % 10);
        String serial;
        try {
            serial = Build.class.getField("SERIAL").get(null).toString();
            return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
        } catch (Exception exception) {
            serial = "serial";
        }
        return "serial";
    }


}
