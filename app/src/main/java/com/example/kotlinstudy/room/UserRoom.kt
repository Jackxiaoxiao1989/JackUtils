package com.example.kotlinstudy.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey


@Entity(tableName = "UserRoom")
class UserRoom {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id", typeAffinity = ColumnInfo.INTEGER)
    var id = 0

    @ColumnInfo(name = "name", typeAffinity = ColumnInfo.TEXT)
    var name: String? = null

    @ColumnInfo(name = "age", typeAffinity = ColumnInfo.INTEGER)
    var age = 0

    @ColumnInfo(name = "sex", typeAffinity = ColumnInfo.INTEGER)
    var sex = 0

    @ColumnInfo(name = "data", typeAffinity = ColumnInfo.TEXT)
    var data: String? = null

    @ColumnInfo(name = "datetime", typeAffinity = ColumnInfo.TEXT)
    var datetime: String? = null

    @ColumnInfo(name = "famian", typeAffinity = ColumnInfo.INTEGER)
    var famian=0

    @ColumnInfo(name = "yongmian", typeAffinity = ColumnInfo.INTEGER)
    var yongmian=0

    @ColumnInfo(name = "bean", typeAffinity = ColumnInfo.INTEGER)
    var bean=0
    @ColumnInfo(name = "rice", typeAffinity = ColumnInfo.INTEGER)
    var rice=0
    @ColumnInfo(name = "meat", typeAffinity = ColumnInfo.INTEGER)
    var meat=0
    @ColumnInfo(name = "vagetable", typeAffinity = ColumnInfo.INTEGER)
    var vagetable=0
    @ColumnInfo(name = "yimingmilk", typeAffinity = ColumnInfo.INTEGER)
    var yimingmilk=0
    @ColumnInfo(name = "milk", typeAffinity = ColumnInfo.INTEGER)
    var milk=0
    @ColumnInfo(name = "egg", typeAffinity = ColumnInfo.INTEGER)
    var egg=0
    @ColumnInfo(name = "doufu", typeAffinity = ColumnInfo.INTEGER)
    var doufu=0
    @ColumnInfo(name = "mianfen", typeAffinity = ColumnInfo.INTEGER)
    var mianfen=0
    @ColumnInfo(name = "gas", typeAffinity = ColumnInfo.INTEGER)
    var gas=0
    @ColumnInfo(name = "house", typeAffinity = ColumnInfo.INTEGER)
    var house=0
    @ColumnInfo(name = "wechat", typeAffinity = ColumnInfo.INTEGER)
    var wechat=0
    @ColumnInfo(name = "alipay", typeAffinity = ColumnInfo.INTEGER)
    var alipay=0
    @ColumnInfo(name = "cash", typeAffinity = ColumnInfo.INTEGER)
    var cash=0
    @ColumnInfo(name = "vip", typeAffinity = ColumnInfo.INTEGER)
    var vip=0
    @ColumnInfo(name = "waimai", typeAffinity = ColumnInfo.INTEGER)
    var waimai=0
    @ColumnInfo(name = "turnover", typeAffinity = ColumnInfo.INTEGER)
    var turnover=0
    @ColumnInfo(name = "balance", typeAffinity = ColumnInfo.INTEGER)
    var balance=0
    @ColumnInfo(name = "blank_int1", typeAffinity = ColumnInfo.INTEGER)
    var blank_int1=0
    @ColumnInfo(name = "blank_int2", typeAffinity = ColumnInfo.INTEGER)
    var blank_int2=0
    @ColumnInfo(name = "blank_int3", typeAffinity = ColumnInfo.INTEGER)
    var blank_int3=0
    @ColumnInfo(name = "blank_str1", typeAffinity = ColumnInfo.TEXT)
    var blank_str1: String? = null
    @ColumnInfo(name = "blank_str2", typeAffinity = ColumnInfo.TEXT)
    var blank_str2: String? = null
    @ColumnInfo(name = "blank_str3", typeAffinity = ColumnInfo.TEXT)
    var blank_str3: String? = null

    @Ignore // 告诉room忽略这个构造方法
    constructor(id: Int, name: String?, age: Int) {
        this.id = id
        this.name = name
        this.age = age
    }

    @Ignore // 告诉room忽略这个构造方法
    constructor(id: Int) {
        this.id = id
    }


    @Ignore
    constructor(name: String?, age: Int) {
        this.name = name
        this.age = age
    }
    // 指定room用的构造方法
    constructor(name: String?, age: Int,data:String?) {
        this.name = name
        this.age = age
        this.data=data
    }
}