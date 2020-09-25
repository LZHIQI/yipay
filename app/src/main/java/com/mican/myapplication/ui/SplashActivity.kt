package com.mican.myapplication.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mican.myapplication.BaseActivity
import com.mican.myapplication.MainActivity
import com.mican.myapplication.R
import com.mican.myapplication.UserManager
import com.mican.myapplication.ui.login.LoginActivity
import com.mican.myapplication.util.ActivityUtils

class SplashActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
       if(UserManager.isLogin()){
           val intent=Intent(getThis(),MainActivity::class.java)
           ActivityUtils.startActivity(getThis(),intent)
       }else{
           val intent=Intent(getThis(),LoginActivity::class.java)
           ActivityUtils.startActivity(getThis(),intent)
       }

    }
}