package com.example.kotlinstudy.info

import com.example.kotlinstudy.utils.KotlinUtils

class UpData:BaseData() {
    override var tag="UpData"
    override fun baseAbstractFun() {
        KotlinUtils.log(tag,"baseAbstractFun")
    }

}