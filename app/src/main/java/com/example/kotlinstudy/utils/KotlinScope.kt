package com.example.kotlinstudy.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class KotlinScope {
    var scopeAction:ScopeAction?=null
    var tag="KotlinScope"
    fun builder():KotlinScope{
        return KotlinScope()
    }

    fun setAction(action:ScopeAction):KotlinScope{
        this.scopeAction=action
        return this
    }

    fun launch(){
        CoroutineScope(Dispatchers.Main).launch{
            KotlinUtils.log(tag,"launch,start")
            val result = withContext(Dispatchers.IO) {
                // 这里执行耗时的操作，例如网络请求或数据库读取
                KotlinUtils.log(tag,"launch,in scope")
                scopeAction?.doAction()
                KotlinUtils.log(tag,"launch,end scope")
            }
            KotlinUtils.log(tag,"launch,end")
        }
    }

    interface ScopeAction{
        fun doAction()
    }
}