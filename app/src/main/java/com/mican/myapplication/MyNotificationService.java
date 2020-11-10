package com.mican.myapplication;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.mican.myapplication.api.req.PayCallReq;
import com.mican.myapplication.data.NotificationResult;
import com.mican.myapplication.event.ServiceEvent;
import com.mican.myapplication.util.GsonUtils;
import com.mican.myapplication.util.JsonUtils;
import com.mican.myapplication.util.LogUtils;
import com.mican.myapplication.util.PayCallUtils;
import com.mican.myapplication.util.StringUtils;
import com.mican.myapplication.util.ToastUtils;
import com.mican.myapplication.util.Utils;
import com.mican.myapplication.util.rx.RxBus;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.core.app.NotificationCompat;


/**
 * @name lzq
 * @class name：com.mican.myapplication
 * @class describe
 * @time 2020/9/5 10:29 AM
 * @change
 * @chang
 * @class describe
 */
public class MyNotificationService extends NotificationListenerService {
    private static final String TAG = "MyNotificationService";
    private MyHandler handler;
    private CharSequence nMessage;
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        setForeground();
        getHandler();
        return START_STICKY;
    }

    public MyHandler getHandler() {
        if(handler==null){
            handler= new MyHandler(Looper.getMainLooper());
        }
        return handler;
    }

    private void setForeground() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(Utils.getApp(),"yipay");
        builder.setContentTitle("易支付");
        builder.setAutoCancel(false);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("yipay",
                    "微妙付",NotificationManager.IMPORTANCE_HIGH);
            NotificationManager service = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            service.createNotificationChannel(channel);
            //LED灯
            channel.enableLights(false);
            //震动
            channel.enableVibration(false);
            if (service != null) {
                service.createNotificationChannel(channel);
            }
            builder.setChannelId(channel.getId());
        }
       startForeground(1, builder.build());
    }
    /*
    *  notificationPkg：           com.eg.android.AlipayGphone
       notificationTitle:          立即查看今日收款金额>>
       notificationText:          你已成功收款0.01元
    *
    * */

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        //        super.onNotificationPosted(sbn);
        try {
             if(sbn!=null) LogUtils.e(sbn.toString());
            //有些通知不能解析出TEXT内容，这里做个信息能判断
            StringBuilder stringBuffer=new StringBuilder();
            if (sbn.getNotification() != null) {
                getNotiInfo(sbn.getNotification());
                nMessage = sbn.getNotification().tickerText;
                stringBuffer.append("nMessage"+nMessage);
                stringBuffer.append("\n Str：  "+sbn.getNotification().toString());
                Bundle extras = sbn.getNotification().extras;
                // 获取接收消息APP的包名
                String notificationPkg = sbn.getPackageName();
                // 获取接收消息的抬头
                String notificationTitle = extras.getString(Notification.EXTRA_TITLE);
                // 获取接收消息的内容
                String notificationText = extras.getString(Notification.EXTRA_TEXT);
                stringBuffer.append("\nnotificationPkg：           "+notificationPkg);
                stringBuffer.append("\nnotificationTitle:          "+notificationTitle);
                stringBuffer.append("\nnotificationText:           "+notificationText);
                LogUtils.e(stringBuffer);
            if("com.eg.android.AlipayGphone".equals(notificationPkg)||"com.tencent.mm".equals(notificationPkg)){
                PayCallReq paycllReq = new PayCallReq();
                paycllReq.notificationPkg=notificationPkg;
                paycllReq.notificationTitle=notificationTitle ;
                paycllReq.notificationText= notificationText;
                paycllReq.testContent=sbn.getNotification().toString();
                if(StringUtils.isEmpty(paycllReq.notificationTitle))return;
                PayCallUtils.Companion.payCall(paycllReq);

              }else {
                PayCallReq paycllReq = new PayCallReq();
                paycllReq.notificationPkg=notificationPkg;
                paycllReq.notificationTitle="非支付app通知";
                paycllReq.testContent=sbn.getNotification().toString();
                Message message=Message.obtain();
                message.what =0;
                message.obj = paycllReq;
                getHandler().sendMessage(message);
             }
            }
        } catch (Exception e) {
            Log.e("NotificationListener","不可解析的通知");
            e.printStackTrace();
            Message message=Message.obtain();
            message.what =2;
            getHandler().sendMessage(message);
        }

    }


    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.getInstance().post(new ServiceEvent());
    }

    class MyHandler extends Handler {

        public MyHandler(Looper mainLooper) {
            super(mainLooper);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    ToastUtils.showLong(GsonUtils.toJson(msg.obj));
                    break;
                case 0:
                    if(msg.obj!=null){
                        ToastUtils.showShort(GsonUtils.toJson(msg.obj));
                    }
                    break;
                case 2:
                    ToastUtils.showLong("通知解析报错");
                    break;
            }
        }

    }
    @Override
    public void onNotificationRemoved(StatusBarNotification sbn, RankingMap rankingMap) {
    }
    @Override
    public StatusBarNotification[] getActiveNotifications() {
        return super.getActiveNotifications();
    }
    @Override
    public void onListenerConnected() {
        super.onListenerConnected();
        LogUtils.e("onListenerConnected");
    }

    @Override
    public void onListenerDisconnected() {
        super.onListenerDisconnected();
        LogUtils.e("onListenerDisconnected");
    }

    private Map<String, Object> getNotiInfo(Notification notification) {
        int key = 0;
        if (notification == null)
            return null;
        RemoteViews views = notification.contentView;
        if (views == null)
            return null;
        Class secretClass = views.getClass();

        try {
            Map<String, Object> text = new HashMap<>();

            Field outerFields[] = secretClass.getDeclaredFields();
            for (int i = 0; i < outerFields.length; i++) {
                if (!outerFields[i].getName().equals("mActions"))
                    continue;

                outerFields[i].setAccessible(true);

                ArrayList<Object> actions = (ArrayList<Object>) outerFields[i].get(views);
                for (Object action : actions) {
                    Field innerFields[] = action.getClass().getDeclaredFields();
                    Object value = null;
                    Integer type = null;
                    for (Field field : innerFields) {
                        field.setAccessible(true);
                        if (field.getName().equals("value")) {
                            value = field.get(action);
                        } else if (field.getName().equals("type")) {
                            type = field.getInt(action);
                        }
                    }
                    // 经验所得 type 等于9 10为短信title和内容，不排除其他厂商拿不到的情况
                    if (type != null && (type == 9 || type == 10)) {
                        if (key == 0) {
                            text.put("title", value != null ? value.toString() : "");
                        } else if (key == 1) {
                            text.put("text", value != null ? value.toString() : "");
                        } else {
                            text.put(Integer.toString(key), value != null ? value.toString() : null);
                        }
                        key++;
                    }
                }
                key = 0;

            }
            return text;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
