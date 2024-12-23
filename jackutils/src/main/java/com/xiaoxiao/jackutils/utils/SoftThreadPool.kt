package com.xiaoxiao.jackutils.utils

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class SoftThreadPool {

    var mThreadPool: ExecutorService?=null
    companion object {
        var sInstance:SoftThreadPool?=null
        fun getInstance(): SoftThreadPool? {
            if (sInstance == null) {
                synchronized(SoftThreadPool::class.java) {
                    if (sInstance == null) {
                        sInstance = SoftThreadPool()
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