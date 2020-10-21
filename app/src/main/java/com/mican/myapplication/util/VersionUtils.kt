package com.mican.myapplication.util


import com.mican.myapplication.BaseActivity
import com.mican.myapplication.api.UserContract
import com.mican.myapplication.api.imp.UserContractImp
import com.mican.myapplication.api.result.VersionResult
import com.mican.myapplication.view.custom.DialogShow


/**
 * @name lzq
 * @class name：com.mican.media_module.utils
 * @class describe
 * @time 2020/10/13 9:51 AM
 * @change
 * @chang
 * @class describe
 */


public class  VersionUtils {

   companion object {
       fun checkVersion0(activity: BaseActivity<*>, isShowProgress: Boolean) {
           if (isShowProgress) {
               activity.showProgressDialog("正在检测新版本.....")
           }
           val contractImp = UserContractImp()
           contractImp.attachView(activity, null)
           contractImp.checkVersion(object : UserContract.View {
               override fun getError(message: String?) {
               }

               override fun getSuccess(o: Any?) {
                   activity.dismissProgressDialog()
                   if (o is VersionResult) {

                       if (UpdateUtils.isUpdate(o.versionCode)) {
                           DialogShow.showUpdateApp0(activity, o)
                       } else {
                           ToastUtils.showShort("当前已经是最新版本")
                       }
                   } else {
                       ToastUtils.showShort("网络请求错误,请重试～！")
                   }
               }
           })
       }



       fun checkVersion1(activity: BaseActivity<*>) {
           val contractImp= UserContractImp()
           contractImp.attachView(null,null)
           contractImp.checkVersion( object : UserContract.View {
               override fun getError(message: String?) {
               }

               override fun getSuccess(o: Any?) {
                   if(o is VersionResult){
                       if (UpdateUtils.isUpdate( o.versionCode)) {
                           DialogShow.showUpdateApp0(activity,o)
                       }
                   }
               }
           })
       }


   }

}