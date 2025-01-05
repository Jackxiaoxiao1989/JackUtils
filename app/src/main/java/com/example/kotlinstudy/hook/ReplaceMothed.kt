package com.example.kotlinstudy.hook

import com.xiaoxiao.jackutils.utils.SoftUtils

class ReplaceMothed {
    var tag="ReplaceMothed"
    fun doSomething(){
        SoftUtils.log(tag,"doSomething")
    }
}