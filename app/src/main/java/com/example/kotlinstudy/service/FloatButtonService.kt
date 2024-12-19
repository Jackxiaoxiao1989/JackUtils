package com.example.kotlinstudy.service

import android.app.Service
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.PixelFormat
import android.os.IBinder
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.Button
import com.example.kotlinstudy.R
import com.example.kotlinstudy.utils.KotlinUtils


class FloatButtonService : Service() {
    private var windowManager: WindowManager? = null
    private var floatButton: Button? = null
    private var tag="FloatButtonService"
    override fun onCreate() {
        super.onCreate()
        if (checkCallingOrSelfPermission("android.permission.SYSTEM_ALERT_WINDOW") != PackageManager.PERMISSION_GRANTED) {
            // 请求权限
        }
        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
        floatButton = Button(this)
        floatButton!!.setBackgroundResource(R.mipmap.icon_save) // 设置你的悬浮按钮背景

        // 设置悬浮按钮参数
        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_PHONE,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        )

        // 设置位置
        params.gravity = Gravity.TOP or Gravity.LEFT
        params.x = 0
        params.y = 100

        // 添加按钮
        windowManager!!.addView(floatButton, params)
        floatButton!!.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                // 按钮点击事件
                KotlinUtils.log(tag,"float btn click")
            }
        })
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }
}