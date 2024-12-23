package com.xiaoxiao.jackutils.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SoftScope {
    var scopeAction:ScopeAction?=null
    var tag="SoftScope"

    companion object {
    fun builder():SoftScope{
        return SoftScope()
    }
}
    fun setAction(action:ScopeAction):SoftScope{
        this.scopeAction=action
        return this
    }

    fun launch(){
        CoroutineScope(Dispatchers.Main).launch{
            SoftUtils.log(tag,"launch,start")
            val result = withContext(Dispatchers.IO) {
                // 这里执行耗时的操作，例如网络请求或数据库读取
                SoftUtils.log(tag,"launch,in scope")
                scopeAction?.doAction()
                SoftUtils.log(tag,"launch,end scope")
            }
            SoftUtils.log(tag,"launch,end")
        }
    }

    interface ScopeAction{
        fun doAction()
    }
}