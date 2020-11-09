package com.mican.myapplication.util

import com.mican.myapplication.api.UserContract
import com.mican.myapplication.api.imp.UserContractImp
import com.mican.myapplication.api.req.PayCallReq
import com.mican.myapplication.event.RefUserEvent
import com.mican.myapplication.util.rx.RxBus

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
            contractImp.payCall(payCallReq,object : UserContract.View {
                override fun getError(message: String?) {
                    ToastUtils.showShort("请求失败："+GsonUtils.toJson(payCallReq))
                    ToastUtils.showLong(message)
                }
                override fun getSuccess(o: Any?) {
                    val s = "请求成功：" + "PayCallReq:\n" + GsonUtils.toJson(payCallReq) +"\nresult:\n"+ GsonUtils.toJson(o)
                    ToastUtils.showShort(s)
                    RxBus.getInstance().post(RefUserEvent())
                }
            })
        }


    }

}