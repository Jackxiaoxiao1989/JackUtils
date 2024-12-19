package com.example.kotlinstudy.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.kotlinstudy.R
import com.example.kotlinstudy.databinding.ActivityCustomViewBinding
import com.example.kotlinstudy.utils.KotlinUtils

class CustomViewActivity : AppCompatActivity() {
    var bingding:ActivityCustomViewBinding?=null
    var tag="CustomViewActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_custom_view)
        bingding = DataBindingUtil.setContentView(this, R.layout.activity_custom_view)
        initViews()
    }

    fun initViews(){
        bingding!!.customTitleBar.setTitleText("lady gaga")
        bingding!!.customTitleBar.setBackClickListener(object:View.OnClickListener{
            override fun onClick(p0: View?) {
                KotlinUtils.log(tag,"on back click")
            }
        })

        bingding!!.customTitleBar.setRightClickListener(object:View.OnClickListener{
            override fun onClick(p0: View?) {
                KotlinUtils.log(tag,"on right click")
            }
        })

        bingding!!.customSyncView.setSyncView(this,null,R.mipmap.content_time)
    }
}