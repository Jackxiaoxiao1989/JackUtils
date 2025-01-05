package com.example.kotlinstudy.hook

import com.xiaoxiao.jackutils.utils.SoftUtils

class FakeMethod(base:BaseMethod):BaseMethod() {
    var mbase:BaseMethod?=null
    var mtag="FakeMethod"
    init{
        SoftUtils.log(mtag,"init")
        mbase=base;
    }
    override fun saySomething(){
        SoftUtils.log(mtag,"before hook")
        mbase!!.saySomething()
        SoftUtils.log(mtag,"after hook")
    }
}