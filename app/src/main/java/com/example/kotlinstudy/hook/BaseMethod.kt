package com.example.kotlinstudy.hook

import com.xiaoxiao.jackutils.utils.SoftUtils

open class BaseMethod:MethodInterface{
    var tag="BaseMethod"
    open fun saySomething(){
        SoftUtils.log(tag,"saySomething")
    }

    override fun doSomething() {
        SoftUtils.log(tag,"doSomething")
    }
}