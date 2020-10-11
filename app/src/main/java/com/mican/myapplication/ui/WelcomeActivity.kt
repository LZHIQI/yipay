package com.mican.myapplication.ui

import android.content.Intent
import android.os.Bundle
import com.mican.myapplication.BaseActivity
import com.mican.myapplication.BasePresenter
import com.mican.myapplication.MainActivity
import com.mican.myapplication.UserManager
import com.mican.myapplication.databinding.ActivityWelcomeBinding
import com.mican.myapplication.ui.login.LoginActivity
import com.mican.myapplication.util.ActivityUtils
import com.mican.myapplication.util.BarUtils
import com.mican.myapplication.util.LogUtils
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit

class WelcomeActivity : BaseActivity<BasePresenter<*>>() {
    lateinit var inflate:ActivityWelcomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BarUtils.setStatusBarLightMode(this, true)
         inflate = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(inflate.root)
        interval()
        inflate.btnRouter.setOnClickListener {
            toRouter()
        }
    }
    var subscribe: Disposable?=null
    fun interval(){
        subscribe= Flowable.intervalRange(0, 6, 0, 1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext {
                    inflate.tvCode.text = "" + (5 - it) + "s"
                }
                .doOnComplete {
                    toRouter()
                }
                .subscribe()
    }

    fun toRouter(){
        if(UserManager.isLogin()){
            val intent= Intent(getThis(), MainActivity::class.java)
            ActivityUtils.startActivity(getThis(), intent)
            finish()
        }else{
            val intent= Intent(getThis(), LoginActivity::class.java)
            ActivityUtils.startActivity(getThis(), intent)
            finish()
        }
        subscribe?.dispose()
    }

}