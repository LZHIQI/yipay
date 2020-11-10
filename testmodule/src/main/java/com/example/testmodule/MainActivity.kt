package com.example.testmodule

import android.content.ComponentName
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.text.TextUtils
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.testmodule.util.NotificationUtils
import com.example.testmodule.util.ServiceUtils
import com.example.testmodule.view.custom.CustomCallBack
import com.example.testmodule.view.custom.CustomDialog
import com.example.testmodule.view.custom.DialogShow

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        renderView()
    }

    private fun toOpen() {
        var intent_s: Intent? = null
        intent_s = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS)
        } else {
            Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS")
        }
        startActivity(intent_s)
    }

    private fun isEnabled(): Boolean {
        val pkgName = packageName
        val flat = Settings.Secure.getString(contentResolver, "enabled_notification_listeners")
        if (!TextUtils.isEmpty(flat)) {
            val names = flat.split(":".toRegex()).toTypedArray()
            for (i in names.indices) {
                val cn = ComponentName.unflattenFromString(names[i])
                if (cn != null) {
                    if (TextUtils.equals(pkgName, cn.packageName)) {
                        return true
                    }
                }
            }
        }
        return false
    }
    private fun renderView() {
        startService()
        if (!isEnabled()) {
            var customDialog = DialogShow.showDefDialog(this, object : CustomCallBack {
               override  fun right() {
                    toOpen()
                }
                override fun left() {
                }
            }, "当前你未授权当前APP读取通知栏权限，请前往授权后再继续操作",
                    "温馨提示",
                    "权限设置攻略",
                    "前往授权",
                    false,
                    true)
        }
    }

    fun isServiceRunning(): Boolean {
        return ServiceUtils.isServiceRunning(MyNotificationService::class.java)
    }
  var notificationDialog: CustomDialog?=null
   fun startService() {
        if (!NotificationUtils.areNotificationsEnabled()) {
            if (notificationDialog != null) {
                if (notificationDialog?.isShowing != false) return
            }
            notificationDialog = DialogShow.showMkDialog(this, object : CustomCallBack {
              override  fun right() {
                    val intent = Intent()
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        intent.action = "android.settings.APP_NOTIFICATION_SETTINGS"
                        intent.putExtra("android.provider.extra.APP_PACKAGE", packageName)
                    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {  //5.0
                        intent.action = "android.settings.APP_NOTIFICATION_SETTINGS"
                        intent.putExtra("app_package", packageName)
                        intent.putExtra("app_uid", applicationInfo.uid)
                        startActivity(intent)
                    } else {
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        intent.action = "android.settings.APPLICATION_DETAILS_SETTINGS"
                        intent.data = Uri.fromParts("package", packageName, null)
                    }
                    startActivity(intent)
                }

                override fun left() {}
            }, "您未授权当前APP开启通知栏权限，请开启后再继续操作",
                    "温馨提示",
                    "开启",
                    false,
                    true)
        } else {
            notificationDialog?.dismiss()
            if (!isServiceRunning()) {
                val intent = Intent(this@MainActivity, MyNotificationService::class.java) //启动服务
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    startForegroundService(intent) //启动服务
                } else {
                    startService(intent)
                }
            }
        }
    }
}