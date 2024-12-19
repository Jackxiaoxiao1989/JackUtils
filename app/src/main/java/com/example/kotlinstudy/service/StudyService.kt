package com.example.kotlinstudy.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.os.RemoteException
import com.example.kotlinstudy.IStudyAidlInterface
import com.example.kotlinstudy.StudyInfo
import com.example.kotlinstudy.utils.KotlinUtils
import java.util.*

class StudyService : Service() {
    private val tag = "StudyService"
    var userList=ArrayList<StudyInfo>()
    var mHashmap = HashMap<String, Int>()
    var mStudyManager=object : IStudyAidlInterface.Stub(){

        @Throws(RemoteException::class)
        override fun getAllUser(): ArrayList<StudyInfo> {
            KotlinUtils.log(tag,"getAllUser")
            return userList
        }

        @Throws(RemoteException::class)
        override fun getOneUserMap(index: Int): HashMap<String, Int>? {
            if (userList.size <= 0) {
                KotlinUtils.log(tag, "list empty")
                return null
            }
            if (index >= userList.size) {
                KotlinUtils.log(tag, "index out of list size")
                return null
            }
            val nameKey = userList[index].name
            KotlinUtils.log(tag, "nameKey:$nameKey")
            if (nameKey != null) {
                for (mkey in mHashmap.keys) {
                    if (mkey == nameKey) {
                        val age = mHashmap[mkey]!!
                        KotlinUtils.log(tag, "find in map,age:$age")
                    }
                }
            }
            return mHashmap
        }

        @Throws(RemoteException::class)
        override fun addUser(name: String, age: Int): Int {
            KotlinUtils.log(tag, "addUser,name=$name")
            KotlinUtils.log(tag, "addUser,age=$age")
            synchronized(this) {
                val mUser = StudyInfo(name, age)
                userList.add(mUser)
                mHashmap.put(name, age)
            }
            return userList.size
        }
    }
    override fun onBind(p0: Intent?): IBinder? {
        KotlinUtils.log(tag, "onBind")
        return mStudyManager
    }

    override fun onCreate() {
        super.onCreate()
        KotlinUtils.log(tag, "onCreate")
    }
}