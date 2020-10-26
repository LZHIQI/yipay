package com.mican.myapplication.util

import android.content.Context
import com.mican.myapplication.BaseActivity
import com.mican.myapplication.api.UserContract
import com.mican.myapplication.api.imp.UserContractImp
import com.mican.myapplication.api.req.PayCallReq
import com.mican.myapplication.api.result.PayCallResult
import com.mican.myapplication.api.result.VersionResult
import com.mican.myapplication.view.custom.DialogShow

/**
 * @name lzq
 * @class name：com.mican.myapplication.util
 * @class describe
 * @time 2020/10/26 6:11 PM
 * @change
 * @chang
 * @class describe
 */

public class  PayCallUtils {


    companion object {
        fun payCall(payCallReq: PayCallReq) {
            val contractImp= UserContractImp()
            contractImp.attachView(null,null)
            contractImp.payCall( payCallReq,object : UserContract.View {
                override fun getError(message: String?) {
                }

                override fun getSuccess(o: Any?) {
                    if(o is PayCallResult){

                    }
                }
            })
        }


    }

}