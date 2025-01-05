package com.example.kotlinstudy

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import com.alibaba.android.arouter.launcher.ARouter
import com.example.kotlinstudy.BluetoothChat.BluetoothChat
import com.example.kotlinstudy.activity.*
import com.example.kotlinstudy.databinding.ActivityMainBinding
import com.example.kotlinstudy.info.AInterface
import com.example.kotlinstudy.info.Person
import com.example.kotlinstudy.info.Student3
import com.example.kotlinstudy.info.UpData
import com.example.kotlinstudy.utils.KotlinUtils
import com.example.kotlinstudy.utils.PermissionUtils
import com.xiaoxiao.jackutils.network.SoftHttp
import com.xiaoxiao.jackutils.utils.SoftPermisson
import com.xiaoxiao.jackutils.utils.SoftScope
import com.xiaoxiao.jackutils.utils.SoftUtils
import java.io.File


class MainActivity : AppCompatActivity() {
    var tag:String="MainActivity";
    lateinit var btn_work: Button
    private var binding:ActivityMainBinding?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        KotlinUtils.log(tag,"onCreate")
        SoftUtils.log(tag,"onCreate")
        //setContentView(R.layout.activity_main)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initViews();
        PermissionUtils.checkPermission(this,object: Runnable{
            override fun run() {
                TODO("Not yet implemented")
            }
        })
        SoftPermisson.askFile(this)
        //SoftPermisson.askInstallUnkownPackage(this)
        //test_kt()
    }

    fun initViews(){
        var showStr=getString(R.string.version_name)+"\n"+getString(R.string.device_name)+"\n"+SoftUtils.getAppVersion(this)
        binding!!.mainTv1.setText(showStr)
        btn_work=binding!!.workBtn//findViewById(R.id.work_btn)
        btn_work.setText("work")
        btn_work.setOnClickListener(object: OnClickListener{
            override fun onClick(p0: View?) {
                KotlinUtils.log(tag,"work btn click")
                openWorkWindow()
            }

        })

        binding?.fragmentBtn?.setOnClickListener(object:OnClickListener{
            override fun onClick(p0: View?) {
                KotlinUtils.log(tag,"fragment btn click")
                openFragmentWindow()
            }
        })

        binding?.jetpackBtn?.setOnClickListener{
            KotlinUtils.log(tag,"jetpack btn click")
            openJetpacktWindow()
        }

        binding?.aidlBtn?.setOnClickListener{
            KotlinUtils.log(tag,"aidl_btn btn click")
            openAidltWindow()
        }

        binding?.viewBtn?.setOnClickListener{
            KotlinUtils.log(tag,"view_btn btn click")
            openCustomViewWindow()
        }

        binding?.btChatBtn?.setOnClickListener{
            KotlinUtils.log(tag,"bt_chat_btn btn click")
            openBtChatWindow()
        }

        binding?.navigationBtn?.setOnClickListener{
            KotlinUtils.log(tag,"navigation btn click")
            openNavigationWindow()
        }

        binding!!.fotaBtn.setOnClickListener{
            KotlinUtils.log(tag,"fota btn click")
            handleFota()
        }
       /* val mhook: JetpackHook?= JetpackHook.instance
        mhook?.attarchActivity(this)
        try {
            mhook?.attachContext()
        } catch (ex: Exception) {
            KotlinUtils.log(tag, "hook fail")
        }*/
        var updata=UpData()
        updata.baseFun()
        updata.baseAbstractFun()

        var kotlinuitlsClass = Class.forName("com.example.kotlinstudy.utils.KotlinUtils")
        var logMethod = kotlinuitlsClass.getDeclaredMethod("logout",String::class.java,String::class.java)
        logMethod.invoke(kotlinuitlsClass.newInstance(),"jack","reflect log")
        /*
        if(kotlinuitlsClass==null){
            KotlinUtils.log(tag,"kotlinuitlsClass is null")
        }else {
            var logMethod = kotlinuitlsClass.getDeclaredMethod("UtilGetDate")
            if(logMethod==null) {
                KotlinUtils.log(tag,"logMethod is null")
            }else {
                logMethod.isAccessible = true
                var datetime = logMethod.invoke(kotlinuitlsClass.newInstance())
                KotlinUtils.log(tag, "datetime=$datetime")
            }
        }
         */

        var lMethod=KotlinUtils::class.java.getDeclaredMethod("log",String::class.java,String::class.java)
        lMethod.invoke(null,"jack","static func")

        var loMethod=KotlinUtils::class.java.getDeclaredMethod("logout",String::class.java,String::class.java)
        lMethod.invoke(KotlinUtils::class.java.newInstance(),"jack","normal func")

    }

    fun openCustomViewWindow(){
        var mintent: Intent = Intent()
        mintent.setClass(this, CustomViewActivity::class.java)
        startActivity(mintent)
    }

    fun openWorkWindow(){
        var mintent: Intent = Intent()
        mintent.setClass(this, WorkActivity::class.java)
        startActivity(mintent)
    }

    fun openFragmentWindow(){
        var mintent: Intent = Intent()
        mintent.setClass(this, MainShowActivity::class.java)
        startActivity(mintent)
    }

    fun openJetpacktWindow(){
        var mintent: Intent = Intent()
        mintent.setClass(this, JetpackActivity::class.java)
        //startActivity(mintent)
        ARouter.getInstance().build("/app/JetpackActivity").navigation();
    }

    fun openAidltWindow(){
        var mintent: Intent = Intent()
        mintent.setClass(this, AidlDemoActivity::class.java)
        startActivity(mintent)
    }

    fun openBtChatWindow(){
        var mintent: Intent = Intent()
        mintent.setClass(this, BluetoothChat::class.java)
        startActivity(mintent)
    }

    fun openNavigationWindow(){
        var mintent: Intent = Intent()
        mintent.setClass(this, CustomerNavigationActivity::class.java)
        startActivity(mintent)
    }

    fun handleFota(){
        var url="https://api.github.com/users/zhangnangua"
        SoftScope.builder().setAction(object:SoftScope.ScopeAction{
            override fun doAction() {
                var res=SoftHttp.sendGetRequest(url)
                KotlinUtils.log(tag,"res=$res")
            }
        }).launch()

        SoftHttp.sendGetRequestBySync(url,object:SoftHttp.OnHttpSync{
            override fun onSync(data: String) {
                KotlinUtils.log(tag,"onSync data=$data")
            }
        })
        /* var apkUri: Uri;
        var apkFile= File("storage/emulated/0/Download/JackUtilsFile/app-debug.apk")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            apkUri = FileProvider.getUriForFile(this,
                BuildConfig.APPLICATION_ID + ".fileprovider", apkFile);
        } else {
            apkUri = Uri.fromFile(apkFile);
        }
        //apkUri = Uri.fromFile(apkFile);
        var intent:Intent = Intent(Intent.ACTION_VIEW);
        intent.setData(apkUri);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);*/
    }
    fun test_kt(){
        KotlinUtils.log(tag,"in")
        var aa :String="1234"
        KotlinUtils.log(tag,"aa="+aa)
        for(l in aa){
            KotlinUtils.log(tag,"l="+l)
        }
        var pp1: Person =Person("jack")
        Log.d(tag,"create person over")
        pp1.mAge=35

        //var pp2:Person =Person("xiao",18)

       var mst : Student3 =Student3()
        mst.setInterface(object: AInterface {
            override var gege: String
                get() = TODO("Not yet implemented")
                set(value) {}

            override fun changeFun1() {
                TODO("Not yet implemented")
            }

        })

        var ppp:TestDataData=TestDataData("name")
        KotlinUtils.log(tag,"ppp.data="+ppp.data)

        KotlinUtils.log(tag,"out")
    }

    fun sum (a:Int,b:Int):Int{
        return a+b;
    }
}

data class TestDataData (var data:String){

}

// 对象声明 object声名一个单例对象
object Singleton{
    // 属性
    val name = "xia"
    // 方法
    fun doPrint(age: Int){
        println("$name ---- $age")
    }
}

// 伴生对象 companion修饰一个类似java中static方法或者变量
class MyConstant {
    companion object {
        var num: Int = 100

        fun doPrint() {
            println("method test")
        }
    }
}


// 泛型举例
class FanStyle<T>(t : T) {
    var value = t

    fun <T> doPrintln(t: T) {
        when (t) {
            is Int -> println("整型数字: $t")
            is String -> println("字符串大写: ${t.toUpperCase()}")
            else -> println(" 不是整型，也不是字符串")
        }
    }
}

// 泛型举例






