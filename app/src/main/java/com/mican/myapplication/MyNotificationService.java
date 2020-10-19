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
import android.widget.Toast;

import com.mican.myapplication.event.ServiceEvent;
import com.mican.myapplication.util.LogUtils;
import com.mican.myapplication.util.ToastUtils;
import com.mican.myapplication.util.rx.RxBus;

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
    private String data;
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        data = intent.getStringExtra("data");
        setForeground();
        return START_STICKY;
    }

    public MyHandler getHandler() {
        if(handler==null){
            handler= new MyHandler(Looper.getMainLooper());
        }
        return handler;
    }

    private void setForeground() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("yipay",
                    "yipay", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager service = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            service.createNotificationChannel(channel);
         }
        Notification notification =new NotificationCompat.Builder(this,"yipay")
                    .setContentTitle("易支付")
                    .setContentText("")
                    .setAutoCancel(false)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .build();
            startForeground(1,notification);
    }
    /*
    *     notificationPkg：           com.eg.android.AlipayGphone
    notificationTitle:          你已成功收款0.01元
    notificationText:           立即查看今日收款金额>>
    *
    * */

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        //        super.onNotificationPosted(sbn);
        try {
            //有些通知不能解析出TEXT内容，这里做个信息能判断
            StringBuffer stringBuffer=new StringBuffer();
            if (sbn.getNotification() != null) {
                nMessage = sbn.getNotification().tickerText;
                stringBuffer.append("nMessage"+nMessage);
                stringBuffer.append("toString"+sbn.getNotification().toString());
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
                Log.e("NotificationListener",stringBuffer.toString());
            if("com.eg.android.AlipayGphone".equals(notificationPkg)&&
                    "com.eg.android.AlipayGphone.DEFAULT_GROUP".equals(sbn.getNotification().getGroup())){
                Message obtain = Message.obtain();
                obtain.obj = nMessage;
                obtain.what = 1;
                getHandler().sendMessage(obtain);
              }
            }
        } catch (Exception e) {

            Log.e("NotificationListener","不可解析的通知");
            Toast.makeText(MyNotificationService.this, "不可解析的通知", Toast.LENGTH_SHORT).show();
        }

    }



    class MyHandler extends Handler {

        public MyHandler(Looper mainLooper) {
            super(mainLooper);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    ToastUtils.showShort(msg.obj.toString());
            }
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.getInstance().post(new ServiceEvent());
    }




}
