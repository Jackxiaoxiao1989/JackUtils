package com.example.kotlinstudy.utils

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class KotlinThreadPool {

    var mThreadPool:ExecutorService?=null
    companion object {
        var sInstance:KotlinThreadPool?=null
        fun getInstance(): KotlinThreadPool? {
            if (sInstance == null) {
                synchronized(KotlinThreadPool::class.java) {
                    if (sInstance == null) {
                        sInstance = KotlinThreadPool()
                    }
                }
            }
            return sInstance
        }
    }
    fun initAll(fixNum:Int){
        mThreadPool= Executors.newFixedThreadPool(fixNum)
    }

    fun addOneJob(jobRun:Runnable){
        mThreadPool!!.execute(jobRun)
    }
}