package com.mican.myapplication;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;

import com.mican.myapplication.databinding.ActivityMainBinding;
import com.mican.myapplication.event.ServiceEvent;
import com.mican.myapplication.util.BarUtils;
import com.mican.myapplication.util.LogUtils;
import com.mican.myapplication.util.RxManager;
import com.mican.myapplication.util.rx.RxBus;
import com.mican.myapplication.util.rx.RxResponseDisposable;
import com.mican.myapplication.view.custom.CustomCallBack;
import com.mican.myapplication.view.custom.CustomDialog;
import com.mican.myapplication.view.custom.DialogShow;

public class MainActivity extends BaseActivity {
    RxManager rxManager= new RxManager();
    ActivityMainBinding inflate;
    CustomDialog customDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inflate= ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(inflate.getRoot());
        BarUtils.setStatusBarLightMode(this, true);
        rxManager.add(
                RxBus.getInstance().toObservable(ServiceEvent.class).subscribeWith(
               new RxResponseDisposable<ServiceEvent>() {
                   @Override
                   public void rxSuccess(ServiceEvent serviceEvent) {
                       startService();
                   }
               }
            ));

    }
   private void toOpen(){
       Intent intent_s = new Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS);
       startActivity(intent_s);
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
    protected void onResume() {
        super.onResume();
        if(customDialog!=null&&isEnabled()){
            customDialog.dismiss();
        }
        renderView();
    }

    private void renderView() {
        startService();
        if(!isEnabled()){
            inflate.btnOpen.setVisibility(View.VISIBLE);
            inflate.openParent.setVisibility(View.GONE);
            customDialog   = DialogShow.showDefDialog(getThis(), new CustomCallBack() {
                        @Override
                        public void right() {
                            toOpen();
                        }

                        @Override
                        public void left() {

                        }
                    }, "当前你未授权当前APP读取通知栏权限，请前往授权后再继续操作",
                    "温馨提示",
                    "权限设置攻略",
                    "前往授权",
                    false,
                    false);
        }else {
            inflate.btnOpen.setVisibility(View.GONE);
            inflate.openParent.setVisibility(View.VISIBLE);
        }
        inflate.btnOpen.setOnClickListener(view -> {
            if(!isEnabled()){
                toOpen();
            }
        });
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        LogUtils.e("sssssssssssssss");

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

    void copy(){
        //获取剪贴板管理器：
        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
// 创建普通字符型ClipData
        ClipData mClipData = ClipData.newPlainText("Label", "这里是要复制的文字");
// 将ClipData内容放到系统剪贴板里。
        cm.setPrimaryClip(mClipData);
    }
}