package com.mican.myapplication;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;

import com.mican.myapplication.event.ServiceEvent;
import com.mican.myapplication.util.LogUtils;
import com.mican.myapplication.util.RxManager;
import com.mican.myapplication.util.rx.RxBus;
import com.mican.myapplication.util.rx.RxResponseDisposable;

public class MainActivity extends AppCompatActivity {
    RxManager rxManager= new RxManager();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rxManager.add(
                RxBus.getInstance().toObservable(ServiceEvent.class).subscribeWith(
               new RxResponseDisposable<ServiceEvent>() {
                   @Override
                   public void rxSuccess(ServiceEvent serviceEvent) {
                       startService();
                   }
               }
            ));
        startService();
        findViewById(R.id.bt1).setOnClickListener(view -> {
            if(!isEnabled()){
                Intent intent_s = new Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS);
                startActivity(intent_s);
            }else {

            }
        });
    }


    private boolean isEnabled() {
        String pkgName = getPackageName();
        final String flat = Settings.Secure.getString(getContentResolver(), "enabled_notification_listeners");
        if (!TextUtils.isEmpty(flat)) {
            final String[] names = flat.split(":");
            for (int i = 0; i < names.length; i++) {
                final ComponentName cn = ComponentName.unflattenFromString(names[i]);
                if (cn != null) {
                    if (TextUtils.equals(pkgName, cn.getPackageName())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        rxManager.clear();
    }

    public void startService(){

        Intent intent = new Intent(MainActivity.this, MyNotificationService.class);//启动服务
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(intent);//启动服务
        }else {
            startService(intent);
        }
    }
}