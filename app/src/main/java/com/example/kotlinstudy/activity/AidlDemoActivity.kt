package com.example.kotlinstudy.activity

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.*
import android.util.LruCache
import androidx.databinding.DataBindingUtil
import com.example.kotlinstudy.IStudyAidlInterface
import com.example.kotlinstudy.R
import com.example.kotlinstudy.StudyInfo
import com.example.kotlinstudy.databinding.ActivityAidlDemoBinding
import com.example.kotlinstudy.utils.KotlinUtils
import java.util.*
import kotlin.random.Random

class AidlDemoActivity : RootActivity() {
    private var tag="AidlDemoActivity"
    private var bingding:ActivityAidlDemoBinding?=null
    private var msgThread:Thread?=null
    private var mLoopHandler:Handler?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_aidl_demo)
        bingding= DataBindingUtil.setContentView(this,R.layout.activity_aidl_demo)
        KotlinUtils.log(tag,"onCreate")
        initDatas()
        initService()
        initMessageLoop()
        initViews()
    }

    fun initViews(){
        bingding?.aidlBtn1?.setOnClickListener{
            sendMessage(1)
        }

        bingding?.aidlBtn2?.setOnClickListener{
            sendMessage(2)
        }
    }
    var mStudyManager: IStudyAidlInterface? = null
    private val mConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            mStudyManager = IStudyAidlInterface.Stub.asInterface(service)
            if (mStudyManager == null) {
                KotlinUtils.log(tag, "mStudyManager == null")
                return
            }
            KotlinUtils.log(tag, "remote service connected")
        }

        override fun onServiceDisconnected(name: ComponentName) {
            KotlinUtils.log(tag, "remote service disconnected")
        }
    }
    fun initService(){
        KotlinUtils.log(tag,"initService")
        var it = Intent()//(this,StudyService::class.java)
        //it.setPackage("com.example.kotlinstudy")
        //it.setAction("com.example.kotlinstudy.IStudyAidlInterface")
        it.component= ComponentName("com.example.kotlinstudy","com.example.kotlinstudy.service.StudyService")
        startService(it)
        bindService(it, mConnection, Context.BIND_AUTO_CREATE)
    }
    private fun unbindStudyService() {
        unbindService(mConnection)
        mStudyManager = null
    }
    var lruCache:LruCache<String, String>?=null
    fun initDatas(){
        lruCache = object : LruCache<String, String>(10 * 1024) {
            override fun sizeOf(key: String, data: String): Int {
                KotlinUtils.log(tag, "sizeOf,key=$key")
                KotlinUtils.log(tag, "sizeOf,data=$data")
                return data.length
            }
        }

        lruCache?.put("1", "jack")
        lruCache?.put("2", "xiao")

        KotlinUtils.log(tag, "get cache 2=" + lruCache?.get("2"))
    }

    fun handleSend(){
        var name ="click time="+java.lang.System.currentTimeMillis()
        var age= Random(35).nextInt()//35
        KotlinUtils.log(tag,"handleSend,name=${name}")
        KotlinUtils.log(tag,"handleSend,age=${age}")
        try {
            mStudyManager!!.addUser(name, age)
        }catch (e:RemoteException ){

        }


    }

    fun handleGet(){

        try {
            val userList: List<StudyInfo> = mStudyManager!!.allUser
            for (listItem in userList) {
                KotlinUtils.log(tag, "name=" + listItem.name)
                KotlinUtils.log(tag, "age=" + listItem.age)
            }
            val mmap: HashMap<String, Int> =
                mStudyManager!!.getOneUserMap(0) as HashMap<String, Int>
            if (mmap != null) {
                for (mkey in mmap.keys) {
                    KotlinUtils.log(tag, "mkey=$mkey")
                }
                for (mval in mmap.values) {
                    KotlinUtils.log(tag, "mval=$mval")
                }
            }
        }catch (e:RemoteException ){

        }
    }

    fun sendMessage(action:Int){
        var msg=Message()
        msg.what=action
        KotlinUtils.log(tag,"sendMessage,what=${msg.what}")
        mLoopHandler?.sendMessage(msg)
    }

    fun initMessageLoop(){
        msgThread = Thread{
            Looper.prepare()
            mLoopHandler= Looper.myLooper()?.let { Handler(it,object: Handler.Callback{
                override fun handleMessage(p0: Message): Boolean {
                    KotlinUtils.log(tag,"handleMessage,what=${p0.what}")
                    when(p0.what){
                        1 ->{
                            handleSend()
                        }

                        2->{
                            handleGet()
                        }

                        3->{

                        }
                    }
                    return true
                }
            }) }
            Looper.loop()
        }
        msgThread?.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindStudyService()
    }
}