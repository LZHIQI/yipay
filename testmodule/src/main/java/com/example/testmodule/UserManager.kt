package com.example.testmodule

import android.os.Parcel
import android.os.Parcelable
import com.tencent.mmkv.MMKV




/**
 * @name lzq
 * @class name：com.mican.myapplication
 * @class describe
 * @time 2020/9/25 1:55 PM
 * @change
 * @chang
 * @class describe
 */


public class UserManager{
     init {

     }

    companion object{
         var kv:MMKV = MMKV.defaultMMKV()
        fun isLogin():Boolean{
            return   !getUser()?.id.isNullOrBlank()
        }
       public fun setUser(user: User?){
            kv.encode("user",user)
        }
       public fun getUser():User?{
         return  kv.decodeParcelable("user",User::class.java)
       }
    }

}

public class User() : Parcelable {

    var username:String?=null
    var id:String?=null
    var password:String?=null
    var token:String?=null
    var parentId:String?=null
    var status:String?=null
    var createdAt:String?=null
    var updatedAt:String?=null

    constructor(parcel: Parcel) : this() {
        username = parcel.readString()
        id = parcel.readString()
        password = parcel.readString()
        token = parcel.readString()
        parentId = parcel.readString()
        status = parcel.readString()
        createdAt = parcel.readString()
        updatedAt = parcel.readString()
    }
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(username)
        parcel.writeString(id)
        parcel.writeString(password)
        parcel.writeString(token)
        parcel.writeString(parentId)
        parcel.writeString(status)
        parcel.writeString(createdAt)
        parcel.writeString(updatedAt)
    }

    override fun describeContents(): Int {
        return 0
    }


    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }




}