package com.example.kotlinstudy.utils

import android.app.Activity
import android.content.Context
import com.example.kotlinstudy.R
import com.example.kotlinstudy.info.AccountInfo

class DataSettingsUtils {
    var tag="DataSettingsUtils"

    val ACCOUNT_DATA_INFO :String="account_info"

    fun writeAccountInfo(context: Context, info: AccountInfo) {
        val editor = context.getSharedPreferences(
            ACCOUNT_DATA_INFO,
            Activity.MODE_PRIVATE
        ).edit()
        //editor.putInt("name", info.name);
        editor.putString("name", info.name)
        editor.putString("number", info.number)
        editor.putInt("age",info.age)
        KotlinUtils.log(tag, "writeAccountInfo,name=" + info.name)
        KotlinUtils.log(tag, "writeAccountInfo,number=" + info.number)
        KotlinUtils.log(tag, "writeAccountInfo,age=" + info.age)
        editor.commit()
    }

    fun readAccountInfo(context: Context): AccountInfo {
        val info = AccountInfo()
        val mSp = context.getSharedPreferences(
            ACCOUNT_DATA_INFO,
            Activity.MODE_PRIVATE
        )
        //int mode=mSp.getInt("mode",1);
        info.name = mSp.getString("name", context.getString(R.string.default_name))!!
        info.number = mSp.getString("number", "12318345010")!!
        info.age=mSp.getInt("age",0)
        KotlinUtils.log(tag, "readAccountInfo,name=" + info.name)
        KotlinUtils.log(tag,  "readAccountInfo,number=" + info.number)
        KotlinUtils.log(tag,  "readAccountInfo,age=" + info.age)
        return info
    }

}