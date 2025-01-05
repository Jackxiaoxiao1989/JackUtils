package com.example.testmoduletwo

import android.content.Context
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.facade.template.IProvider
import com.example.modulebase.BaseProvider
import com.xiaoxiao.jackutils.utils.SoftUtils

@Route(path = "/testmoduletwo/service")
class ModuleTwoService: BaseProvider {
    lateinit var mcontext:Context
    var tag="ModuleTwoService"
    override fun getMessage(data: String): String {
        return data+"-haha"
    }

    override fun init(context: Context?) {
        SoftUtils.log(tag,"init")
        mcontext=context!!
    }
}