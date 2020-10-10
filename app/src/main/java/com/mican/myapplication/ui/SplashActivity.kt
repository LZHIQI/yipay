package com.mican.myapplication.ui
import android.content.Intent
import android.os.Bundle
import com.mican.myapplication.BaseActivity
import com.mican.myapplication.api.imp.UserContractImp
import com.mican.myapplication.databinding.ActivitySplashBinding
import com.mican.myapplication.util.ActivityUtils

class SplashActivity : BaseActivity<UserContractImp>() {
    lateinit var inflate:ActivitySplashBinding;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inflate = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(inflate.root)
        val intent=Intent(this,WelcomeActivity::class.java)
        ActivityUtils.startActivity(intent)
        finish()
    }

}