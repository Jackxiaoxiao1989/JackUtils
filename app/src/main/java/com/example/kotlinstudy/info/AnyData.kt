package com.example.kotlinstudy.info

import com.example.kotlinstudy.utils.KotlinUtils

class AnyData<T> {
    var tag="AnyData"
    private var anyData:T?=null

    constructor(data:T){
        this.anyData=data
    }
    fun setOneAnyData(data:T){
        this.anyData=data
    }

    fun getOneAnyData():T?{
        return this.anyData
    }

    fun printAnyData(){
        when(this.anyData){
            is Int ->{
                KotlinUtils.log(tag,"this is a number,value=${this.anyData}")
            }

            is String ->{
                KotlinUtils.log(tag,"this is a String,value=${this.anyData}")
            }

            else ->{
                KotlinUtils.log(tag,"this is a object,value=${this.anyData}")
            }
        }
    }

    inner class inAnyData{
        fun printInAnyData(){
            KotlinUtils.log(tag,"inAnyData,printInAnyData,${this@AnyData.anyData}")
        }
    }
}