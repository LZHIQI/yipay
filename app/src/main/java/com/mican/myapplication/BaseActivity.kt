package com.mican.myapplication

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * @name lzq
 * @class name：com.mican.myapplication
 * @class describe
 * @time 2020/9/25 2:11 PM
 * @change
 * @chang
 * @class describe
 */
abstract class  BaseActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

   fun getThis():Activity{
       return this
    }
}