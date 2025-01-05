package com.example.modulebase

import android.content.Context
import com.alibaba.android.arouter.facade.template.IProvider

interface BaseProvider:IProvider {
    fun getMessage(data :String):String
}