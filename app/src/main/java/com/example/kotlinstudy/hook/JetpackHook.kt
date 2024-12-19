package com.example.kotlinstudy.hook

import android.app.Activity
import android.app.Instrumentation
import com.example.kotlinstudy.utils.KotlinUtils

class JetpackHook {
    fun attarchActivity(activity: Activity?) {
        try {
            val field =
                Activity::class.java.getDeclaredField("mInstrumentation")
            field.isAccessible = true
            val instrumentation = field[activity] as Instrumentation
            val instrumentationProxy: Instrumentation = EvilInstrumentation(instrumentation)
            field[activity] = instrumentationProxy
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @Throws(Exception::class)
    fun attachContext() {
        // 先获取到当前的ActivityThread对象
        val activityThreadClass =
            Class.forName("android.app.ActivityThread")
        val currentActivityThreadMethod =
            activityThreadClass.getDeclaredMethod("currentActivityThread")
        currentActivityThreadMethod.isAccessible = true
        val currentActivityThread = currentActivityThreadMethod.invoke(null)

        // 拿到原始的 mInstrumentation字段
        val mInstrumentationField =
            activityThreadClass.getDeclaredField("mInstrumentation")
        mInstrumentationField.isAccessible = true
        val mInstrumentation :Instrumentation=mInstrumentationField.get(currentActivityThread) as Instrumentation
            //mInstrumentationField[currentActivityThread] as Instrumentation

        // 创建代理对象
        val evilInstrumentation: Instrumentation = EvilInstrumentation(mInstrumentation)

        // 偷梁换柱
        mInstrumentationField[currentActivityThread] = evilInstrumentation
    }

    companion object {
        private var jetpackHook: JetpackHook? = null
        private const val tag = "JetpackHook"
        val instance: JetpackHook?
            get() {
                synchronized(JetpackHook::class.java) {
                    if (jetpackHook == null) {
                        jetpackHook = JetpackHook()
                    }
                    return jetpackHook
                }
            }
    }

    init {
        KotlinUtils.log(tag, "consctrct")
    }
}