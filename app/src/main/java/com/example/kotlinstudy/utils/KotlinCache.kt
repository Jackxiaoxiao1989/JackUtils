package com.example.kotlinstudy.utils

import android.util.LruCache
import java.util.concurrent.ExecutorService

class KotlinCache {
    var lruCache: LruCache<String, String>?=null
    var cacheArray:ArrayList<String>?=null
    var tag="KotlinCache"
    companion object {
        var sInstance:KotlinCache?=null
        fun getInstance(): KotlinCache? {
            if (sInstance == null) {
                synchronized(KotlinCache::class.java) {
                    if (sInstance == null) {
                        sInstance = KotlinCache()
                    }
                }
            }
            return sInstance
        }
    }
    fun initAll(maxSecotSize:Int){
        lruCache = object : LruCache<String, String>(maxSecotSize * 1024) {
            override fun sizeOf(key: String, data: String): Int {
                KotlinUtils.log(tag, "sizeOf,key=$key")
                KotlinUtils.log(tag, "sizeOf,data=$data")
                return data.length
            }
        }
        cacheArray=ArrayList<String>()
    }

    fun addOneCache(key:String,value:String):Int{
        lruCache!!.put(key,value)
        cacheArray!!.add(key)
        KotlinUtils.log(tag, "addOneCache,key=$key,value=$value,size=${lruCache!!.size()}")
        return lruCache!!.size()
    }

    fun getOneCache(key:String):String{
        var value=lruCache!!.get(key)
        KotlinUtils.log(tag, "getOneCache,key=$key,value=$value")
        return value

    }

    fun getAllCache():HashMap<String,String>{
        var cacheMap=HashMap<String,String>()
        for(k in cacheArray!!){
            var value=lruCache!!.get(k)
            cacheMap.put(k,value)
        }
        return cacheMap
    }

    fun getCacheCount():Int{
        return lruCache!!.putCount()
    }

    fun getCacheSize():Int{
        return lruCache!!.size()
    }
}