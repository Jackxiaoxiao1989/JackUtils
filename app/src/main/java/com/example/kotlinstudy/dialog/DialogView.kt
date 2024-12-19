package com.example.kotlinstudy.dialog

import android.app.Dialog
import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.example.kotlinstudy.R
import com.example.kotlinstudy.info.WorkItemInfo
import com.example.kotlinstudy.utils.KotlinUtils
import com.google.gson.Gson


class DialogView(
    context: Context,
    layout: Int,
    style: Int,
    gravity: Int
) :
    Dialog(context, style) {
    var tv_title:TextView?=null
    var tv_content:TextView?=null
    var iv_return:ImageView?=null
    var TAG="DialogView"
    init {
        setContentView(layout)
        tv_title=findViewById(R.id.dialog_title)
        tv_content=findViewById(R.id.dialog_content)
        iv_return=findViewById(R.id.return_visualizationImg)
        iv_return!!.setOnClickListener(object:View.OnClickListener{
            override fun onClick(p0: View?) {
                closeDialog()
            }
        })
        val mWindow = window
        //        WindowManager.LayoutParams params = mWindow.getAttributes();
//        params.width = WindowManager.LayoutParams.MATCH_PARENT;
//        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
//        params.gravity = gravity;
//        mWindow.setAttributes(params);
    }

    fun setTitle(title:String?){
        tv_title!!.setText(title)
    }

    fun setContent(content:String?){
        var gson=Gson()
        var item:WorkItemInfo=gson.fromJson(content,WorkItemInfo::class.java)
        KotlinUtils.log(TAG,"item datatime is:${item.datetime}")
        tv_content!!.setText(content)
    }

    fun closeDialog(){
        this.dismiss()
    }
}

