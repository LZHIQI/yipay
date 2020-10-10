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
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;

import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;

import com.mican.myapplication.api.UserContract;
import com.mican.myapplication.api.imp.UserContractImp;
import com.mican.myapplication.api.req.QueryReq;
import com.mican.myapplication.api.result.UserDetail;
import com.mican.myapplication.databinding.ActivityMainBinding;
import com.mican.myapplication.event.ServiceEvent;
import com.mican.myapplication.ui.login.LoginActivity;
import com.mican.myapplication.util.ActivityUtils;
import com.mican.myapplication.util.BarUtils;
import com.mican.myapplication.util.GsonUtils;
import com.mican.myapplication.util.JsonUtils;
import com.mican.myapplication.util.LogUtils;
import com.mican.myapplication.util.RxManager;
import com.mican.myapplication.util.ToastUtils;
import com.mican.myapplication.util.Utils;
import com.mican.myapplication.util.rx.RxBus;
import com.mican.myapplication.util.rx.RxResponseDisposable;
import com.mican.myapplication.view.custom.CustomCallBack;
import com.mican.myapplication.view.custom.CustomDialog;
import com.mican.myapplication.view.custom.DialogShow;

import java.util.concurrent.TimeUnit;

public class MainActivity extends BaseActivity<UserContractImp> implements UserContract.View {
    RxManager rxManager= new RxManager();
    ActivityMainBinding inflate;
    CustomDialog customDialog,vipDialog,remainingDialog;
    Boolean isLoading=true;
    UserDetail userDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inflate= ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(inflate.getRoot());
        BarUtils.setStatusBarLightMode(this, true);

        refVipInfo();
    }

    private void request() {
        if(UserManager.Companion.isLogin()){
            if(isLoading){
                inflate.loading.setVisibility(View.VISIBLE);
            }
            User user = UserManager.Companion.getUser();
            QueryReq queryReq=new QueryReq();
            queryReq.id=user.getId();
            queryReq.Authorization=user.getToken();
                    mPresenter.detail(queryReq, new UserContract.View() {
                        @Override
                        public void getError(String message) {
                            ToastUtils.showShort(message);
                            goneView();
                        }

                        @Override
                        public void getSuccess(Object o) {
                            /*inflate.openParent.setVisibility(View.VISIBLE);*/
                            if(o instanceof UserDetail){
                                userDetail= (UserDetail) o;
                                renderVip();
                            }
                            goneView();
                        }
                    });
        }else {
            Intent intent= new Intent(this, LoginActivity.class);
            ActivityUtils.startActivity(getThis(), intent);
        }

    }

    private void renderVip() {
      if(userDetail.memberStatus==0){ //未开通
          customDialog   = DialogShow.showDefDialog(getThis(), new CustomCallBack() {
                      @Override
                      public void right() {
                          renderNoVip();
                      }

                      @Override
                      public void left() {


                      }
                  }, "您还未开通会员服务，收款功能无法使用，快去开通会员服务，马上收款！",
                  "温馨提示",
                  "暂不开通",
                  "前往开通",
                  false,
                  false);


      }else if(userDetail.memberStatus==1){  //已开通


          inflate.tvVipStatus.setText("会员状态：基础版365天");
          if(userDetail.memberDay<10){
              remainingDialog   = DialogShow.showDefDialog(getThis(), new CustomCallBack() {
                          @Override
                          public void right() {

                          }

                          @Override
                          public void left() {

                          }
                      }, "您的会员服务即将到期，请立即续费。避免收款服务受到影响",
                      "温馨提示",
                      "暂不续费",
                      "前往续费",
                      false,
                      false);
          }else {

          }


      }
    }

    private void renderNoVip() {
        inflate.tvVipStatus.setText("会员状态：未开通会员");
        inflate.toVip.setText("立即开通");
        inflate.toVip.setOnClickListener(view -> { //立即开通

       });
        inflate.tvUserBalance.setText(String.format("账户余额：%s元",userDetail.balance));
        inflate.tvCz.setOnClickListener(view -> { //立即充值

        });
        inflate.appidContent.setText(userDetail.appid);
        inflate.contentAppSecret.setText(userDetail.appsecret);

    }


    public  void refVipInfo(){
        getRxBus().clear();
        getRxBus().add(
                RxBus.getInstance().toObservable(ServiceEvent.class).subscribeWith(
                        new RxResponseDisposable<ServiceEvent>() {
                            @Override
                            public void rxSuccess(ServiceEvent serviceEvent) {
                                startService();
                            }
                        }
                ));
        Flowable.intervalRange(0, 300, 0, 1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(aLong -> inflate.timeUpDate.setText(String.format("会员信息更新倒计时%s秒", (300 - aLong) )))
                .doOnComplete(() -> {

                })
                .subscribe();

    }

    private void goneView() {
        if( inflate.loading.getVisibility()==View.VISIBLE){
            inflate.loading.setVisibility(View.GONE);
            isLoading=false;
        }
    }

    private void toOpen(){
       Intent intent_s = new Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS);
       startActivity(intent_s);
   }

    @Override
    protected UserContractImp setPresenter() {
        return new UserContractImp();
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
            vipDialog   = DialogShow.showDefDialog(getThis(), new CustomCallBack() {
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
            request();
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

    @Override
    public void getError(String message) {

    }

    @Override
    public void getSuccess(Object o) {

    }
}