package com.example.kotlinstudy.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.kotlinstudy.R
import com.example.kotlinstudy.databinding.ActivityJetpackBinding
import com.example.kotlinstudy.info.AnyData
import com.example.kotlinstudy.room.*
import com.example.kotlinstudy.utils.KotlinCache
import com.example.kotlinstudy.utils.KotlinScope
import com.example.kotlinstudy.utils.KotlinThreadPool
import com.example.kotlinstudy.utils.KotlinUtils
import com.example.kotlinstudy.viewmodel.ShareViewModel
import java.util.*
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import java.util.concurrent.ThreadFactory
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.collections.HashSet
import kotlinx.coroutines.*

class JetpackActivity<T> : AppCompatActivity() {
    private var bingding:ActivityJetpackBinding? = null
    private var tag="JetpackActivity"
    private var myDatabase:UserRoomDatabase?=null;
    private var myDataDao: UserRoomDao?=null;
    private var msgThread:Thread?=null
    private var mLoopHandler:Handler?=null
    private var handle2:Handler?=null
    private var mModel:ShareViewModel?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bingding=DataBindingUtil.setContentView(this,R.layout.activity_jetpack)
        //setContentView(R.layout.activity_jetpack)
        KotlinUtils.log(tag,"onCreate")
        initDatas()
        initViews()
        initViewModel()
    }

    fun initViewModel(){
        mModel=ViewModelProvider(this).get(ShareViewModel::class.java)
        mModel!!.mutableLiveData!!.observe(this, object:Observer<String>{
            override fun onChanged(t: String?) {
                KotlinUtils.log(tag,"onChanged t=$t")
            }
        })
    }
    fun initDatas(){
        myDatabase=UserRoomDatabase.getInstance(this)
        myDataDao=myDatabase?.getUserRoomDao()
        msgThread = Thread{
                Looper.prepare()
            mLoopHandler= Looper.myLooper()?.let { Handler(it,object:Handler.Callback{
                override fun handleMessage(p0: Message): Boolean {
                    KotlinUtils.log(tag,"mLoopHandler，handleMessage,what=${p0.what}")
                    KotlinUtils.log(tag,"mLoopHandler handleMessage,target=${p0.target}")
                    when(p0.what){
                        1 ->{
                            handleRefresh()
                        }

                        2->{
                            handleInsert()
                        }

                        3->{
                            handleDelete()
                        }
                    }
                    return true
                }
            }) }
            handle2=Handler(Looper.myLooper()!!,object:Handler.Callback{
                override fun handleMessage(p0: Message): Boolean {
                    KotlinUtils.log(tag,"handle2 handleMessage,what=${p0.what}")
                    KotlinUtils.log(tag,"handle2 handleMessage,target=${p0.target}")
                    when(p0.what){
                        1->{
                            handleRefresh()
                        }

                        2->{
                            handleMapListSet()
                        }

                        3->{
                            handleThreadPool()
                        }

                        4->{
                            handleCache()
                        }

                        5->{
                            handleViewModel()
                        }
                    }
                    return true
                }

            })
            /*
            handle2= Looper.myLooper()?.let { Handler(it,object:Handler.Callback{
                override fun handleMessage(p0: Message): Boolean {
                    KotlinUtils.log(tag,"handle2 handleMessage,what=${p0.what}")
                    KotlinUtils.log(tag,"handle2 handleMessage,target=${p0.target}")
                    when(p0.what){
                        1->{
                            handleRefresh()
                        }

                        2->{
                            handleMapListSet()
                        }

                        3->{
                            handleThreadPool()
                        }

                        4->{
                            handleCache()
                        }

                        5->{
                            handleViewModel()
                        }
                    }
                    return true
                }
            }) }!!
             */
            Looper.loop()
        }
        msgThread?.start()
    }

    fun sendMessage(action:Int){
        var msg=Message()
        msg.what=action
        KotlinUtils.log(tag,"sendMessage,what=${msg.what}")
        mLoopHandler?.sendMessage(msg)
    }

    fun sendMessage2(action:Int){
        var msg=Message()
        msg.what=action
        KotlinUtils.log(tag,"sendMessage2,what=${msg.what}")
        handle2?.sendMessage(msg)
    }

    fun initViews(){
        bingding?.jetpackBtn1?.setOnClickListener{
            KotlinUtils.log(tag,"btn1,click")
            sendMessage2(1)
        }

        bingding?.jetpackBtn2?.setOnClickListener{
            KotlinUtils.log(tag,"btn2,click")
            sendMessage(2)
        }

        bingding?.jetpackBtn3?.setOnClickListener{
            KotlinUtils.log(tag,"btn3,click")
            sendMessage(3)
        }

        bingding?.jetpackBtn4?.setOnClickListener{
            KotlinUtils.log(tag,"btn4,click")
            sendMessage2(2)
        }

        bingding?.jetpackBtn5?.setOnClickListener{
            KotlinUtils.log(tag,"btn5,click")
            sendMessage2(3)
        }

        bingding?.jetpackBtn6?.setOnClickListener{
            KotlinUtils.log(tag,"btn6,click")
            sendMessage2(4)
        }

        bingding?.jetpackBtn7?.setOnClickListener{
            KotlinUtils.log(tag,"btn7,click")
            //sendMessage2(5)
            mModel!!.setShareData(System.currentTimeMillis().toString())
            bingding!!.setUser(mModel)
            var value=mModel!!.getShareData()
            KotlinUtils.log(tag,"btn7,click value=$value")
        }
        bingding?.jetpackBtn8?.setOnClickListener{
            KotlinUtils.log(tag,"btn8,click")
            testCoroutines()
        }

    }

    fun handleRefresh(){
       var allUsers:List<UserRoom>? =myDataDao?.allRooms()
        var showInfo:String?=null
        if(allUsers!=null) {
            for (user in allUsers) {
                KotlinUtils.log(tag,"handleRefresh,name=${user.name}")
                KotlinUtils.log(tag,"handleRefresh,age=${user.age}")
                KotlinUtils.log(tag,"handleRefresh,id=${user.id}")
                showInfo=showInfo+"id:${user.id};name:${user.name};age:${user.age};"
            }
        }
        bingding?.jetpackTv?.setText(showInfo)
    }

    fun handleInsert(){
        var name=bingding?.jetpackEt?.text.toString()
        KotlinUtils.log(tag,"handleInsert,name=$name")
        var myRoom:UserRoom= UserRoom(name,35)
        myDataDao?.insertRooms(myRoom)
    }

    fun handleDelete(){
        var idStr=bingding?.jetpackEt?.text.toString()
        KotlinUtils.log(tag,"handleDelete,idStr=$idStr")
        var id=idStr.toInt()
        KotlinUtils.log(tag,"handleDelete,id=$id")
        myDataDao?.deleteRoomsById(id)
    }

    fun handleMapListSet(){
        KotlinUtils.log(tag,"handleMapListSet")
        var hashMap=HashMap<String,Int>()
        hashMap.put("jack",35)
        hashMap.put("xiao",36)

      for( mkey in hashMap.keys){
          KotlinUtils.log(tag,"handleMapListSet,mkey=$mkey")
          var value=hashMap.get(mkey)
          KotlinUtils.log(tag,"handleMapListSet,value=$value")
      }

        var mySet=HashSet<String>()
        mySet.add("jack")
        mySet.add("xiao")
        mySet.add("jack")
        for(set in mySet){
            KotlinUtils.log(tag,"handleMapListSet,set=$set")
        }

        var myArr=ArrayList<Int>()
        myArr.add(1)
        myArr.add(3)
        myArr.add(5)
        myArr.add(1)

        for(arr in myArr){
            KotlinUtils.log(tag,"handleMapListSet,arr=$arr")
        }

        var queue=LinkedList<String>()
        queue.offer("a")
        queue.offer("b")
        queue.offer("c")
        queue.offer("d")
        queue.offer("e")

        for(que in queue){
            KotlinUtils.log(tag,"handleMapListSet,que=$que")
        }
        var poll=queue.poll()  //get first one and remove
        KotlinUtils.log(tag,"handleMapListSet,poll=$poll")
        for(que in queue){
            KotlinUtils.log(tag,"handleMapListSet,que=$que")
        }
        var element=queue.element() // only get first one
        KotlinUtils.log(tag,"handleMapListSet,element=$element")
        for(que in queue){
            KotlinUtils.log(tag,"handleMapListSet,que=$que")
        }
        var peek=queue.peek()  // only get first one
        KotlinUtils.log(tag,"handleMapListSet,peek=$peek")
        for(que in queue){
            KotlinUtils.log(tag,"handleMapListSet,que=$que")
        }
    }

    fun handleThreadPool(){
        KotlinUtils.log(tag,"handleThreadPool")
        /*
        var fixThread= Executors.newFixedThreadPool(10)

        fixThread.execute(object:Runnable{
            override fun run() {
                for(i in 1..100){
                    KotlinUtils.log(tag,"ThreadPool run,i=$i")
                }
            }
        })
*/
        KotlinThreadPool.getInstance()!!.addOneJob(object:Runnable{
            override fun run() {
                for(i in 1..100 step 2){
                    KotlinUtils.log(tag,"ThreadPool run,i=$i")
                }
            }
        })
    }
//var mainScope=MainScope()
    private var numInc=0;
    fun handleCache(){
    KotlinCache.getInstance()!!.addOneCache(numInc.toString(),System.currentTimeMillis().toString())
        numInc++;
/*
        var allCount=KotlinCache.getInstance()!!.getCacheCount()
        KotlinUtils.log(tag,"handleCache,allCount=$allCount")
        if(allCount>0){
            for(k in 0..(allCount-1)) {
                var value=KotlinCache.getInstance()!!.getOneCache(k.toString())
                KotlinUtils.log(tag,"handleCache,value=$value")
            }
        }
*/
        var allMap=KotlinCache.getInstance()!!.getAllCache()
        for(m in allMap){
            KotlinUtils.log(tag,"handleCache,key==${m.key}")
            KotlinUtils.log(tag,"handleCache,value==${m.value}")
        }
        var mAnyData1=AnyData(12)
        mAnyData1.let {
            it.printAnyData()
        }
        //mAnyData1.printAnyData()
        var mAnyData2=AnyData("JACK")
        var rec=with(mAnyData2){
            printAnyData()
            1
        }

        var mAnyData3=AnyData("dave")
        mAnyData3.run(){
            printAnyData()
        }

        var mAnyData4=AnyData("xiao")
        mAnyData4.apply(){
            printAnyData()
        }

        var mAnyData5=AnyData(99)
        mAnyData5.also(){
            it.printAnyData()
        }
    }

    fun handleViewModel(){
        mModel!!.setShareData(System.currentTimeMillis().toString())
    }

    fun testCoroutines(){
        KotlinUtils.log(tag,"testCoroutines,start")
        var scope="123"
        CoroutineScope(Dispatchers.Main).launch{
            KotlinUtils.log(tag,"testCoroutines,in main")
            val result = withContext(Dispatchers.IO) {
                // 这里执行耗时的操作，例如网络请求或数据库读取
                KotlinUtils.log(tag,"testCoroutines,in scope")
                scope="456"
                Thread.sleep(2000)
                KotlinUtils.log(tag,"testCoroutines,end scope")
            }
            KotlinUtils.log(tag,"testCoroutines,end main")
        }
        KotlinUtils.log(tag,"testCoroutines,end $scope")

        KotlinScope().builder().setAction(object:KotlinScope.ScopeAction{
            override fun doAction() {
                Thread.sleep(3000)
                KotlinUtils.log(tag,"test long time work")
            }

        }).launch()
        
    }
}