package com.example.testmoduletwo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.databinding.DataBindingUtil
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.example.testmoduletwo.databinding.ActivityModuleTwoMainBinding

@Route(path = "/testmoduletwo/MainActivity")
class ModuleTwoMainActivity : AppCompatActivity() {
    var binding:ActivityModuleTwoMainBinding?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_module_two_main)
        //setContentView(R.layout.activity_module_two_main)
        initViews()
    }

    fun initViews(){
        //var btn1=findViewById<Button>(R.id.modlue2_btn1)
        binding!!.modlue2Btn1.setOnClickListener{
            //ARouter.getInstance().build("/ModuleTwo/MainActivity").navigation();
            var it=Intent()
            it.putExtra("final","222")
            setResult(110,it)
            finish()
        }
    }
}