package com.example.kotlinstudy.info

import com.example.kotlinstudy.utils.KotlinUtils

abstract class BaseData {
    open var tag="BaseData"
    abstract fun baseAbstractFun()
    fun baseFun(){
        KotlinUtils.log(tag,"baseFun")
    }
}