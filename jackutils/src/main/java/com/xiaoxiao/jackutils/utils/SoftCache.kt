package com.xiaoxiao.jackutils.utils

import android.util.LruCache

class SoftCache {
    var lruCache: LruCache<String, String>?=null
    var cacheArray:ArrayList<String>?=null
    var tag="SoftCache"
    companion object {
        var sInstance:SoftCache?=null
        fun getInstance(): SoftCache? {
            if (sInstance == null) {
                synchronized(SoftCache::class.java) {
                    if (sInstance == null) {
                        sInstance = SoftCache()
                    }
                }
            }
            return sInstance
        }
    }
    fun initAll(maxSecotSize:Int){
        lruCache = object : LruCache<String, String>(maxSecotSize * 1024) {
            override fun sizeOf(key: String, data: String): Int {
                SoftUtils.log(tag, "sizeOf,key=$key")
                SoftUtils.log(tag, "sizeOf,data=$data")
                return data.length
            }
        }
        cacheArray=ArrayList<String>()
    }

    fun addOneCache(key:String,value:String):Int{
        lruCache!!.put(key,value)
        cacheArray!!.add(key)
        SoftUtils.log(tag, "addOneCache,key=$key,value=$value,size=${lruCache!!.size()}")
        return lruCache!!.size()
    }

    fun getOneCache(key:String):String{
        var value=lruCache!!.get(key)
        SoftUtils.log(tag, "getOneCache,key=$key,value=$value")
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