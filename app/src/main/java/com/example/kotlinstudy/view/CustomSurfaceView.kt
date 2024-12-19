package com.example.kotlinstudy.view


import android.content.Context
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.example.kotlinstudy.utils.KotlinThreadPool
import com.example.kotlinstudy.utils.KotlinUtils

class CustomSurfaceView:SurfaceView , SurfaceHolder.Callback {
    private var mholder:SurfaceHolder?=null
    private var tag="CustomSurfaceView"
    constructor(context: Context?) : super(context) {
        mholder = this.getHolder()
        mholder!!.addCallback(this)
        KotlinUtils.log(tag,"constructor 1111")
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        mholder = this.getHolder()
        mholder!!.addCallback(this)
        KotlinUtils.log(tag,"constructor 22222")
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        mholder = this.getHolder()
        mholder!!.addCallback(this)
        KotlinUtils.log(tag,"constructor 3333")
        init()
    }

    private fun init(){

    }
    override fun surfaceChanged(p0: SurfaceHolder, p1: Int, p2: Int, p3: Int) {
        KotlinUtils.log(tag,"surfaceChanged")
    }

    override fun surfaceDestroyed(p0: SurfaceHolder) {
        KotlinUtils.log(tag,"surfaceDestroyed")
    }

    override fun surfaceCreated(p0: SurfaceHolder) {
        KotlinUtils.log(tag,"surfaceCreated")
        initUiThread()
    }

    fun updateUi(){
        KotlinUtils.log(tag,"updateUi")
    }
    fun initUiThread(){
        KotlinThreadPool.getInstance()!!.addOneJob(object:Runnable{
            override fun run() {
                while(true){
                    updateUi()
                    Thread.sleep(1000)
                }
            }

        })
    }
}