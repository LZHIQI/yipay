package com.mican.myapplication.ui.login

import android.os.Bundle
import com.mican.myapplication.BaseActivity
import com.mican.myapplication.databinding.ActivityRegistBinding
import com.mican.myapplication.util.BarUtils

class RegisterActivity : BaseActivity() {
    lateinit var inflate:ActivityRegistBinding;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inflate = ActivityRegistBinding.inflate(layoutInflater)
        setContentView(inflate.root)
        BarUtils.setStatusBarLightMode(this, true)
    }
}