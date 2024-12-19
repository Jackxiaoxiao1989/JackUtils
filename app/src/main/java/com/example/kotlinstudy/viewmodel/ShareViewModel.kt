package com.example.kotlinstudy.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlinstudy.info.AnyData
import com.example.kotlinstudy.utils.KotlinUtils

class ShareViewModel: ViewModel(){
    var tag="ShareViewModel"
    var mutableLiveData:MutableLiveData<String>?=null
    init{
        KotlinUtils.log(tag,"init")
        mutableLiveData=MutableLiveData<String>()
        if(mutableLiveData==null){
            KotlinUtils.log(tag,"init is null")
        }
    }

    fun setShareData(data:String){
        mutableLiveData!!.setValue(data)
        //mutableLiveData!!.observe()
    }

    fun getShareData():String?{
       return mutableLiveData!!.getValue()
    }
}