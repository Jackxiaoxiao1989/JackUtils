package com.example.kotlinstudy.info

import android.graphics.drawable.Drawable
import com.example.kotlinstudy.eventbus.EventMsg
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class WorkItemInfo:EventMsg {
    @Transient //不要被识别为json字段
    var iconDrawable : Drawable?=null
    @Expose//需要被识别为json的字段
    @SerializedName("Name")//序列化，混淆
    var name:String?=null
    @Expose//需要被识别为json的字段
    @SerializedName("Datetime")//序列化，混淆
    var datetime: String? = null
    @Expose//需要被识别为json的字段
    @SerializedName("Famian")//序列化，混淆
    var famian=0
    @Expose//需要被识别为json的字段
    @SerializedName("Yongmian")//序列化，混淆
    var yongmian=0
    @Expose//需要被识别为json的字段
    @SerializedName("Bean")//序列化，混淆
    var bean=0
    @Expose//需要被识别为json的字段
    @SerializedName("Rice")//序列化，混淆
    var rice=0
    @Expose//需要被识别为json的字段
    @SerializedName("Meat")//序列化，混淆
    var meat=0
    @Expose//需要被识别为json的字段
    @SerializedName("Vagetable")//序列化，混淆
    var vagetable=0
    @Expose//需要被识别为json的字段
    @SerializedName("Yimingmilk")//序列化，混淆
    var yimingmilk=0
    @Expose//需要被识别为json的字段
    @SerializedName("Milk")//序列化，混淆
    var milk=0
    @Expose//需要被识别为json的字段
    @SerializedName("Egg")//序列化，混淆
    var egg=0
    @Expose//需要被识别为json的字段
    @SerializedName("Doufu")//序列化，混淆
    var doufu=0
    @Expose//需要被识别为json的字段
    @SerializedName("Mianfen")//序列化，混淆
    var mianfen=0
    @Expose//需要被识别为json的字段
    @SerializedName("Gas")//序列化，混淆
    var gas=0
    @Expose//需要被识别为json的字段
    @SerializedName("House")//序列化，混淆
    var house=0
    @Expose//需要被识别为json的字段
    @SerializedName("Wechat")//序列化，混淆
    var wechat=0
    @Expose//需要被识别为json的字段
    @SerializedName("Alipay")//序列化，混淆
    var alipay=0
    @Expose//需要被识别为json的字段
    @SerializedName("Cash")//序列化，混淆
    var cash=0
    @Expose//需要被识别为json的字段
    @SerializedName("Vip")//序列化，混淆
    var vip=0
    @Expose//需要被识别为json的字段
    @SerializedName("Waimai")//序列化，混淆
    var waimai=0
    @Expose//需要被识别为json的字段
    @SerializedName("Turnover")//序列化，混淆
    var turnover=0
    @Expose//需要被识别为json的字段
    @SerializedName("Balance")//序列化，混淆
    var balance=0
    @Transient //不要被识别为json字段
    var blank_int1=0
    @Transient //不要被识别为json字段
    var blank_int2=0
    @Transient //不要被识别为json字段
    var blank_int3=0
    @Transient //不要被识别为json字段
    var blank_str1: String? = null
    @Transient //不要被识别为json字段
    var blank_str2: String? = null
    @Transient //不要被识别为json字段
    var blank_str3: String? = null
    constructor(iconDrawable : Drawable, name:String,data:String){
        this.name=name;
        this.iconDrawable=iconDrawable;
        this.data=data
    }

    constructor(message: String, type: Int) {
        this.message = message
        this.type = type
    }

    constructor(where: Int, pos: Int, message: String) {
        this.message = message
        this.where = where
        this.pos = pos
    }
    constructor(where: Int, pos: Int, message: String,data:String):super(where,pos,message,data) {
        this.message = message
        this.where = where
        this.pos = pos
        this.data = data
    }
}