package com.mican.myapplication.net.interceptor;


import android.os.Build;


import com.mican.myapplication.UserManager;
import com.mican.myapplication.util.AppUtils;
import com.mican.myapplication.util.DeviceUtils;
import com.mican.myapplication.util.StringUtils;

import java.io.IOException;
import java.util.UUID;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


/**
 *
 */
public class TokenInterceptor implements Interceptor {
    public static final String REQUEST_AUTH_TOKEN = "accessToken";
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
       /* if (StringUtils.isEmpty(getAuthToken())) {
            authRequest = originalRequest.newBuilder()
                    .addHeader(REQUEST_AUTH_DEVICEDKEY, "Android")
                    .addHeader(REQUEST_AUTH_CLIENTKEY, getAuthClientKey())
                    .addHeader(REQUEST_AUTH_VERSION, AppUtils.getAppVersionName())
                    .addHeader(REQUEST_AUTH_DEVICEDID, getAuthDeviceID()).build();
        } else {
            if (originalRequest.url().encodedPath().contains("modify_access_token")) {
                authRequest = originalRequest.newBuilder()
                        .addHeader(REQUEST_AUTH_DEVICEDKEY, "Android")
                        .addHeader(REQUEST_AUTH_CLIENTKEY, getAuthClientKey())
                        .addHeader(REQUEST_AUTH_VERSION, AppUtils.getAppVersionName())
                        .addHeader(REQUEST_AUTH_DEVICEDID, getAuthDeviceID())
                        .build();
            } else {
                authRequest = originalRequest.newBuilder()
                        .addHeader(REQUEST_AUTH_TOKEN, getAuthToken())
                        .addHeader(REQUEST_AUTH_DEVICEDKEY, "Android")
                        .addHeader(REQUEST_AUTH_CLIENTKEY, getAuthClientKey())
                        .addHeader(REQUEST_AUTH_VERSION, AppUtils.getAppVersionName())
                        .addHeader(REQUEST_AUTH_DEVICEDID, getAuthDeviceID())
                        .build();
            }
        }*/
        return chain.proceed(authRequest);
    }

    private String getUserAgent() {
        StringBuilder ua = new StringBuilder();
        ua.append('/' + AppUtils.getAppInfo(AppUtils.getAppPackageName()).getVersionName() + '_'
                + AppUtils.getAppInfo(AppUtils.getAppPackageName()).getVersionCode());
        ua.append("/Android");
        ua.append("/" + android.os.Build.VERSION.RELEASE);
        ua.append("/" + android.os.Build.MODEL);
        ua.append("/" + getUniqueID());
        return ua.toString();
    }

    private static String getUniqueID() {
        String m_szDevIDShort = "35" + (Build.BOARD.length() % 10) + (Build.BRAND.length() % 10) + (Build.CPU_ABI.length() % 10) + (Build.DEVICE.length() % 10) + (Build.MANUFACTURER.length() % 10) + (Build.MODEL.length() % 10) + (Build.PRODUCT.length() % 10);
        String serial;
        try {
            serial = android.os.Build.class.getField("SERIAL").get(null).toString();
            return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
        } catch (Exception exception) {
            serial = "serial";
        }
        return "serial";
    }


}
