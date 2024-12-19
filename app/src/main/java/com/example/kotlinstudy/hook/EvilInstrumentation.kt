package com.example.kotlinstudy.hook

import android.app.Activity
import android.app.Instrumentation
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.IBinder
import com.example.kotlinstudy.utils.KotlinUtils

class EvilInstrumentation(base: Instrumentation) :
    Instrumentation() {
    // ActivityThread中原始的对象, 保存起来
    var mBase: Instrumentation

    @Throws(
        InstantiationException::class,
        IllegalAccessException::class,
        ClassNotFoundException::class
    )
    override fun newActivity(
        cl: ClassLoader,
        className: String,
        intent: Intent
    ): Activity {
        KotlinUtils.log(TAG, "newActivity")
        return mBase.newActivity(cl, className, intent)
    }

    fun execStartActivity(
        who: Context, contextThread: IBinder, token: IBinder, target: Activity,
        intent: Intent, requestCode: Int, options: Bundle
    ): ActivityResult {

        // Hook之前, XXX到此一游!
        KotlinUtils.log(TAG,"hook start")

        // 开始调用原始的方法, 调不调用随你,但是不调用的话, 所有的startActivity都失效了.
        // 由于这个方法是隐藏的,因此需要使用反射调用;首先找到这个方法
        return try {
            val execStartActivity =
                Instrumentation::class.java.getDeclaredMethod(
                    "execStartActivity",
                    Context::class.java,
                    IBinder::class.java,
                    IBinder::class.java,
                    Activity::class.java,
                    Intent::class.java,
                    Int::class.javaPrimitiveType,
                    Bundle::class.java
                )
            execStartActivity.isAccessible = true
            execStartActivity.invoke(
                mBase, who,
                contextThread, token, target, intent, requestCode, options
            ) as ActivityResult
        } catch (e: Exception) {
            // 某该死的rom修改了  需要手动适配
            throw RuntimeException("do not support!!! pls adapt it")
        }
    }

    companion object {
        private const val TAG = "EvilInstrumentation"
    }

    init {
        KotlinUtils.log(TAG, "init")
        mBase = base
    }
}