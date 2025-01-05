package com.example.kotlinstudy.hook

class RealClass {
    companion object {
        var baseMethod:BaseMethod= BaseMethod()
    }

    fun startSay(){
        baseMethod.saySomething()
    }
}