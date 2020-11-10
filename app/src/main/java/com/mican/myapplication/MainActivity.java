package com.mican.myapplication;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

import android.provider.Settings;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.mican.myapplication.api.UserContract;
import com.mican.myapplication.api.imp.UserContractImp;
import com.mican.myapplication.api.req.QueryReq;
import com.mican.myapplication.api.result.UserDetail;
import com.mican.myapplication.databinding.ActivityMainBinding;
import com.mican.myapplication.event.RefUserEvent;
import com.mican.myapplication.event.ServiceEvent;
import com.mican.myapplication.ui.login.LoginActivity;
import com.mican.myapplication.util.ActivityUtils;
import com.mican.myapplication.util.BarUtils;
import com.mican.myapplication.util.LogUtils;
import com.mican.myapplication.util.NotificationUtils;
import com.mican.myapplication.util.RxManager;
import com.mican.myapplication.util.ServiceUtils;
import com.mican.myapplication.util.ToastUtils;
import com.mican.myapplication.util.VersionUtils;
import com.mican.myapplication.util.rx.RxBus;
import com.mican.myapplication.util.rx.RxResponseDisposable;
import com.mican.myapplication.view.custom.CustomCallBack;
import com.mican.myapplication.view.custom.CustomDialog;
import com.mican.myapplication.view.custom.DialogShow;

import java.util.concurrent.TimeUnit;

public class MainActivity extends BaseActivity<UserContractImp> implements UserContract.View {
    RxManager rxManager= new RxManager();
    ActivityMainBinding inflate;
    CustomDialog customDialog,vipDialog,remainingDialog,balanceDialog,notificationDialog;
    Boolean isLoading=true;
    UserDetail userDetail;
    Disposable disposable;
    boolean isShowNoVip=true;
    Disposable subscribe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inflate= ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(inflate.getRoot());
        BarUtils.setStatusBarLightMode(this, true);
        VersionUtils.Companion.checkVersion1(this);
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
                            goneView();
                            if(subscribe!=null){
                                getRxBus().unbind(subscribe);
                            }
                            subscribe = Flowable.intervalRange(0, 20, 0, 1, TimeUnit.SECONDS)
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .doOnNext(aLong -> {
                                    })
                                    .doOnComplete(() -> {
                                        request();
                                    })
                                    .subscribe();
                            getRxBus().add(subscribe);
                        }

                        @Override
                        public void getSuccess(Object o) {
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
          if(isShowNoVip){
              if(vipDialog!=null)vipDialog.dismiss();
              vipDialog = DialogShow.showDefDialog(getThis(), new CustomCallBack() {
                          @Override
                          public void right() {
                              vipDialog.dismiss();
                              isShowNoVip=false;
                              renderNoVip();
                          }
                          @Override
                          public void left() {
                              vipDialog.dismiss();
                              isShowNoVip=false;
                              renderNoVip();
                          }
                      }, "您还未开通会员服务，收款功能无法使用，快去开通会员服务，马上收款！",
                      "温馨提示",
                      "暂不开通",
                      "前往开通",
                      false,
                      false);
          }else {
              renderNoVip();
          }
      }else if(userDetail.memberStatus==1){  //已开通
          if(0<userDetail.day&&userDetail.day<10){
              inflate.tvVipStatus.setText(String.format("会员状态：%s%s天",userDetail.mealVersion,userDetail.day));
              if(remainingDialog!=null)remainingDialog.dismiss();
              remainingDialog   = DialogShow.showDefDialog(getThis(), new CustomCallBack() {
                          @Override
                          public void right() {
                              remainingDialog.dismiss();
                          }

                          @Override
                          public void left() {
                              remainingDialog.dismiss();
                          }
                      }, "您的会员服务即将到期，请立即续费。避免收款服务受到影响",
                      "温馨提示",
                      "暂不续费",
                      "前往续费",
                      false,
                      false);
          }else if (userDetail.day<=0) {
              inflate.tvVipStatus.setText("会员已过期");
              if(remainingDialog!=null)remainingDialog.dismiss();
              remainingDialog   = DialogShow.showDefDialog(getThis(), new CustomCallBack() {
                          @Override
                          public void right() {
                              remainingDialog.dismiss();
                          }

                          @Override
                          public void left() {
                              remainingDialog.dismiss();
                          }
                      }, "您的会员服务已到期，请立即续费。功能在续费成功后才能正常使用。",
                      "温馨提示",
                      "暂不续费",
                      "前往续费",
                      false,
                      false);
          }else {
              inflate.tvVipStatus.setText(String.format("会员状态：%s%s天",userDetail.mealVersion,userDetail.day));
          }
          inflate.tvUserBalance.setText(String.format("账户余额：%s元",userDetail.balance));
          if (userDetail.balance<=10) {
              if(balanceDialog!=null)balanceDialog.dismiss();
              balanceDialog = DialogShow.showDefDialog(getThis(), new CustomCallBack() {
                          @Override
                          public void right() {
                              balanceDialog.dismiss();
                          }

                          @Override
                          public void left() {
                              balanceDialog.dismiss();
                          }
                      }, "您的账户余额少于10元，请立即充值。避免系统账户无法扣除交易手续费，无法正常使用。",
                      "温馨提示",
                      "暂不充值",
                      "前往充值",
                      false,
                      false);
          }

          inflate.appidContent.setText(userDetail.appid);
          inflate.contentAppSecret.setText(userDetail.appsecret);
          renderCommend();
          refBaseInfo();
      }
    }

    private void renderNoVip() {
        if( inflate.openParent.getVisibility()==View.GONE){
            inflate.openParent.setVisibility(View.VISIBLE);
        }
        inflate.tvVipStatus.setText("会员状态：未开通会员");
        inflate.toVip.setText("立即开通");
        inflate.toVip.setOnClickListener(view -> { //立即开通
            if(UserManager.Companion.isLogin()) {
                User user = UserManager.Companion.getUser();
                openBrowser(getThis(),"http://pc.wmipay.com/#/merchantManage?token="+user.getToken());
            }
        });
        inflate.tvUserBalance.setText(String.format("账户余额：%s元",userDetail.balance));
        inflate.tvCz.setOnClickListener(view -> { //立即充值
            if(UserManager.Companion.isLogin()) {
                User user = UserManager.Companion.getUser();
                openBrowser(getThis(),"http://pc.wmipay.com/#/merchantManage?token="+user.getToken());
            }
        });
        inflate.appidContent.setText(userDetail.appid);
        inflate.contentAppSecret.setText(userDetail.appsecret);
        renderCommend();
        refBaseInfo();
    }

    private void renderCommend() {
        inflate.copyAppid.setOnClickListener(view -> {
            ToastUtils.showShort("复制成功");
            copy(inflate.appidContent.getText().toString());
        });
        inflate.copySecret.setOnClickListener(view -> {
            ToastUtils.showShort("复制成功");
            copy(inflate.contentAppSecret.getText().toString());
        });
        inflate.link1Copy.setOnClickListener(view -> {
            ToastUtils.showShort("复制成功");
            copy(inflate.link1.getText().toString());
        });
        inflate.link2Copy.setOnClickListener(view -> {
            ToastUtils.showShort("复制成功");
            copy(inflate.link2.getText().toString());
        });
        inflate.link3Copy.setOnClickListener(view -> {
            ToastUtils.showShort("复制成功");
            User user = UserManager.Companion.getUser();
            if(user!=null){
                copy(inflate.link3.getText().toString()+"?token="+user.getToken());
            }else {
                copy(inflate.link3.getText().toString());
            }
        });
        inflate.link4Copy.setOnClickListener(view -> {
            ToastUtils.showShort("复制成功");
            copy(inflate.link4.getText().toString());
        });


        inflate.webLink1.setOnClickListener(view -> {
            openBrowser(getThis(),inflate.link1.getText().toString());
        });

        inflate.webLink2.setOnClickListener(view -> {
            openBrowser(getThis(),inflate.link2.getText().toString());
        });
        inflate.webLink3.setOnClickListener(view -> {
            openBrowser(getThis(),inflate.link3.getText().toString());
        });
        inflate.webLink4.setOnClickListener(view -> {
            openBrowser(getThis(),inflate.link4.getText().toString());
        });
       inflate.timeUpDate.setOnClickListener(view -> {
            request();
      });
    }
    public  void openBrowser(Context context, String url) {
        final Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        // 注意此处的判断intent.resolveActivity()可以返回显示该Intent的Activity对应的组件名
        // 官方解释 : Name of the component implementing an activity that can display the intent
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            final ComponentName componentName = intent.resolveActivity(context.getPackageManager());
            //  LogUtil.d("suyan = " + componentName.getClassName());
            context.startActivity(Intent.createChooser(intent, "请选择浏览器"));
        } else {
            // GlobalMethod.showToast(context, "链接错误或无浏览器");
        }
    }

    public  void refBaseInfo(){
        getRxBus().clear();
        startService();

        rxManager.add(
                RxBus.getInstance().toObservable(RefUserEvent.class).subscribeWith(
               new RxResponseDisposable<RefUserEvent>(){
                   @Override
                   public void rxSuccess(RefUserEvent refUserEvent) {
                       request();
                   }
               }
            ));
        getRxBus().add(
                RxBus.getInstance().toObservable(ServiceEvent.class).subscribeWith(
                        new RxResponseDisposable<ServiceEvent>() {
                            @Override
                            public void rxSuccess(ServiceEvent serviceEvent) {
                                startService();
                            }
                        }
                ));
        if(disposable!=null) disposable.dispose();
        disposable = Flowable.intervalRange(0, 10, 0, 1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(aLong -> inflate.timeUpDate.setText(String.format("会员信息更新倒计时%s秒", (300 - aLong))))
                .doOnComplete(() -> {
                    refBaseInfo();
                })
                .subscribe();

        getRxBus().add(disposable);
    }

    private void goneView() {
        if( inflate.loading.getVisibility()==View.VISIBLE){
            inflate.loading.setVisibility(View.GONE);
            isLoading=false;
        }
    }

    private void toOpen(){
        Intent intent_s=null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP_MR1) {
            intent_s = new Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS);
        } else {
            intent_s = new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
        }
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
            if(customDialog!=null)customDialog.dismiss();
            customDialog  = DialogShow.showDefDialog(getThis(), new CustomCallBack() {
                        @Override
                        public void right() {
                            toOpen();
                        }

                        @Override
                        public void left() {
                            customDialog.dismiss();
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
            request();
        }
        inflate.btnOpen.setOnClickListener(view -> {
            if(!isEnabled()){
                LogUtils.e("btnOpen");
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
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(customDialog!=null){
            customDialog.dismiss();
        }
        if(vipDialog!=null){
            vipDialog.dismiss();
        }
        if(remainingDialog!=null){
            remainingDialog.dismiss();
        }
        if(balanceDialog!=null){
            balanceDialog.dismiss();
        }
        if(notificationDialog!=null){
            notificationDialog.dismiss();
        }
        rxManager.clear();
    }

    public void startService(){
        if(!NotificationUtils.areNotificationsEnabled()){
            if(notificationDialog!=null) {
               if(notificationDialog.isShowing())return;
            };
            notificationDialog  = DialogShow.showMkDialog(getThis(), new CustomCallBack() {
                        @Override
                        public void right() {
                            Intent intent = new Intent();
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
                                intent.putExtra("android.provider.extra.APP_PACKAGE", getPackageName());
                            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {  //5.0
                                intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
                                intent.putExtra("app_package", getPackageName());
                                intent.putExtra("app_uid", getApplicationInfo().uid);
                                startActivity(intent);
                            } else {
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                                intent.setData(Uri.fromParts("package", getPackageName(), null));
                            }
                            startActivity(intent);
                        }

                        @Override
                        public void left() {

                        }
                    }, "您未授权当前APP开启通知栏权限，请开启后再继续操作",
                    "温馨提示",
                    "开启",
                    false,
                    false);
        }else {
            if(notificationDialog!=null) notificationDialog.dismiss();
            if(!isServiceRunning()){
                Intent intent = new Intent(MainActivity.this, MyNotificationService.class);//启动服务
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    startForegroundService(intent);//启动服务
                }else {
                    startService(intent);
                }
            }
        }
    }

    void copy(String copy){
        //获取剪贴板管理器：
        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
// 创建普通字符型ClipData
        ClipData mClipData = ClipData.newPlainText(null, copy);
// 将ClipData内容放到系统剪贴板里。
        cm.setPrimaryClip(mClipData);
    }

    @Override
    public void getError(String message) {

    }

    @Override
    public void getSuccess(Object o) {

    }

    /**
     * 判断服务是否开启
     *      服务的完整路径(例:com.example.service)
     * @return
     */
    public  boolean isServiceRunning() {
       return ServiceUtils.isServiceRunning(MyNotificationService.class);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            goBackGround();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void goBackGround() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK) ;
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }



}