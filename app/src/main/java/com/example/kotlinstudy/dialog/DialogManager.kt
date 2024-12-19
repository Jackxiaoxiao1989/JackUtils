package com.example.kotlinstudy.dialog

import android.content.Context
import android.view.Gravity
import com.example.kotlinstudy.R


//class DialogManager private constructor(){
    class DialogManager{
        fun initView(context: Context?, layout: Int): DialogView {
        return DialogView(context!!, layout, R.style.CustomDialog, Gravity.CENTER)
    }

    fun initView(context: Context?, layout: Int, gravity: Int): DialogView {
        return DialogView(context!!, layout, R.style.mydialog, gravity)
    }

    // 显示弹框
    fun show(view: DialogView?) {
        if (view != null) {
            if (!view.isShowing()) {
                view.show()
            }
        }
    }

    // 隐藏弹框
    fun hide(view: DialogView?) {
        if (view != null) {
            if (view.isShowing()) {
                view.dismiss()
            }
        }
    }

    companion object {
        @Volatile
        private var mInstance: DialogManager? = null
        val instance: DialogManager?
            get() {
                if (mInstance == null) {
                    synchronized(DialogManager::class.java) {
                        if (mInstance == null) {
                            mInstance = DialogManager()
                        }
                    }
                }
                return mInstance
            }
    }
}