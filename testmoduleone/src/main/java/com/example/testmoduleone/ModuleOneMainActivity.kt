package com.example.testmoduleone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.example.modulebase.BaseProvider
import com.example.testmoduleone.databinding.ActivityModuleOneMainBinding
import com.xiaoxiao.jackutils.utils.SoftUtils

@Route(path = "/testmoduleone/MainActivity")
class ModuleOneMainActivity : AppCompatActivity() {
    var binding:ActivityModuleOneMainBinding?=null
    var tag="ModuleOneMainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this,R.layout.activity_module_one_main)
        //setContentView(R.layout.activity_module_one_main)
        initViews()
    }

    fun initViews(){
        binding!!.modlue1Btn1.setOnClickListener{
            //ARouter.getInstance().build("/testmoduletwo/MainActivity").navigation();
            ARouter.getInstance().build("/testmoduletwo/MainActivity")
                .withString("name","jack")
                .withString("data","jack is handsome")
                .navigation(this,100);
        }

        binding!!.modlue1Btn2.setOnClickListener{
            var serve=ARouter.getInstance().build("/testmoduletwo/service").navigation() as BaseProvider
            var rc=serve.getMessage("jack")
            SoftUtils.log(tag,"rc=$rc")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        // 获取解析结果
        SoftUtils.log(tag,"onActivityResult,requestCode=$requestCode")
        SoftUtils.log(tag,"onActivityResult,resultCode=$resultCode")
        SoftUtils.log(tag,"onActivityResult,data=$data")
        super.onActivityResult(requestCode, resultCode, data)

    }
}