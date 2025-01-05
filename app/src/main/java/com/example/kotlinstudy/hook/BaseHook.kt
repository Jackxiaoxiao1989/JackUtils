package com.example.kotlinstudy.hook

import com.xiaoxiao.jackutils.utils.SoftUtils
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method

class BaseHook(inter: MethodInterface) : InvocationHandler {
    var mbase: MethodInterface?=null
    var tag="BaseHook"

    init{
        SoftUtils.log(tag,"init")
        mbase=inter
    }
    override fun invoke(p0: Any?, p1: Method?, p2: Array<out Any>?): Any {
        SoftUtils.log(tag,"Before method: " + p1!!.getName())
        //val result: Any = p1.invoke(mbase, p2)
        val result: Any = 1
        p1.invoke(mbase,  *(p2 ?: emptyArray()))
        SoftUtils.log(tag,"After method: " + p1!!.getName())
        return result
    }
}