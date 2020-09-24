package com.mican.myapplication;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import android.widget.Toast;

import com.mican.myapplication.util.LogUtils;
import com.mican.myapplication.util.NotificationUtils;
import com.mican.myapplication.util.Utils;

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
    private final static int NOTIFICATION_ID = android.os.Process.myPid();
    private MyHandler handler = new MyHandler(Looper.getMainLooper());
    private CharSequence nMessage;
    private String data;
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        data = intent.getStringExtra("data");
        Log.e("NotificationListener","data");
        setForeground();
        return super.onStartCommand(intent, flags, startId);
    }

    private void setForeground() {
        NotificationUtils.notify("notificationPay", 1, builder -> {
            builder.setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("易支付")
                    .setContentText("")
                    .setAutoCancel(false);
            startForeground(1, builder.build());
        });
    }



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
               /* Message obtain = Message.obtain();
                obtain.obj = nMessage;
                mHandler.sendMessage(obtain);
                init();
                if (datanMessage.contains(data)) {
                    Message message = handler.obtainMessage();
                    message.what = 1;
                    handler.sendMessage(message);
                    writeData(sdf.format(new Date(System.currentTimeMillis())) + ":" + nMessage);
                }*/
            }
        } catch (Exception e) {

            Log.e("NotificationListener","不可解析的通知");
            Toast.makeText(MyNotificationService.this, "不可解析的通知", Toast.LENGTH_SHORT).show();
        }

    }


    Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            while (true)
            {
                Log.e(TAG, "" + System.currentTimeMillis());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };


    class MyHandler extends Handler {

        public MyHandler(Looper mainLooper) {
            super(mainLooper);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
//                    Toast.makeText(MyService.this,"Bingo",Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtils.e("onDestroy");

    }
}
