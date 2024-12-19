package com.example.kotlinstudy

import android.app.Application
import android.content.Context
import android.os.Process
import android.text.TextUtils
import com.example.kotlinstudy.control.FileSystemManager
import com.example.kotlinstudy.utils.KotlinCache
import com.example.kotlinstudy.utils.KotlinThreadPool
import com.example.kotlinstudy.utils.KotlinUtils
import com.scwang.smartrefresh.header.MaterialHeader
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator
import com.scwang.smartrefresh.layout.api.RefreshFooter
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import com.tencent.bugly.crashreport.CrashReport
import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException


class KotlinApplication:Application() {
    var tag="KotlinApplication"
    override fun onCreate() {
        super.onCreate()
        KotlinUtils.log(tag,"onCreate")
        initAppEnveriment();
    }
    fun initAppEnveriment(){
        KotlinThreadPool.getInstance()!!.initAll(10)
        FileSystemManager.getInstance()!!.init(this)
        KotlinCache.getInstance()!!.initAll(10)
        initBugly()
    }
    private fun initFreshlayout(){
        //设置全局的Header构建器
        //设置全局的Header构建器

        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout ->
            layout.setPrimaryColorsId(R.color.blue, android.R.color.white)//全局设置主题颜色
            ClassicsHeader(context)//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
        }
        //设置全局的Footer构建器
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(object : DefaultRefreshFooterCreator {
            override fun createRefreshFooter(
                context: Context,
                layout: RefreshLayout
            ): RefreshFooter {
                return ClassicsFooter(context).setDrawableSize(20f)
            }
        })
    }
    private fun initBugly() {
        // 获取当前包名
        val packageName = applicationContext.packageName
        // 获取当前进程名
        val processName: String? = getProcessName(Process.myPid())
        // 设置是否为上报进程
        val strategy = CrashReport.UserStrategy(applicationContext)
        strategy.isUploadProcess = false || processName == packageName
        CrashReport.initCrashReport(applicationContext, "d951242788", false, strategy)
    }
    fun getProcessName(pid: Int): String? {
        var reader: BufferedReader? = null
        try {
            reader = BufferedReader(FileReader("/proc/$pid/cmdline"))
            var processName = reader.readLine()
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim { it <= ' ' }
            }
            return processName
        } catch (throwable: Throwable) {
            throwable.printStackTrace()
        } finally {
            try {
                reader?.close()
            } catch (exception: IOException) {
                exception.printStackTrace()
            }
        }
        return null
    }
}