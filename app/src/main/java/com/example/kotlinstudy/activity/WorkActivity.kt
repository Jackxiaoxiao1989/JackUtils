package com.example.kotlinstudy.activity


import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinstudy.R
import com.example.kotlinstudy.adapter.MyAdapter
import com.example.kotlinstudy.adapter.WorkAdapter
import com.example.kotlinstudy.control.BleMainManager
import com.example.kotlinstudy.databinding.ActivityWorkBinding
import com.example.kotlinstudy.info.AccountInfo
import com.example.kotlinstudy.info.AppInfo
import com.example.kotlinstudy.info.WorkItemInfo
import com.example.kotlinstudy.utils.DataSettingsUtils
import com.example.kotlinstudy.utils.KotlinUtils
import com.google.zxing.integration.android.IntentIntegrator
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.interfaces.OnSelectListener
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import com.tencent.bugly.crashreport.CrashReport


class WorkActivity : RootActivity() {

    private var myAdapter: MyAdapter? = null
    private var tag="WorkActivity"
    private var appList = ArrayList<AppInfo>()
    private lateinit var recyclerview: RecyclerView
    private var mBlemanager: BleMainManager?=null
    private var binding:ActivityWorkBinding?=null
    private var workItemList=ArrayList<WorkItemInfo>()
    private var workAdapter: WorkAdapter? = null
    private var floatButton: Button?=null
    private var refreshHandler: Handler?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_work)
        //setContentView(R.layout.activity_work)
        initDatas()
        initViews()
    }

    private fun initDatas(){
        var mdataset= DataSettingsUtils()
        var accountset=AccountInfo()
        accountset.age=10
        accountset.name="jack"
        accountset.number="13918345010"
        mdataset.writeAccountInfo(this,accountset)

        var accountget: AccountInfo=mdataset.readAccountInfo(this)
        KotlinUtils.log(tag,"accountget name: "+accountget.name)
        KotlinUtils.log(tag,"accountget number: "+accountget.number)
        KotlinUtils.log(tag,"accountget age: "+accountget.age)

        mBlemanager=BleMainManager(this, object:BleMainManager.OnBleMainFunction{
            override fun onScanCallback(name: String?, mac: String?) {
                KotlinUtils.log(tag,"onConnectCallback name="+name)
                KotlinUtils.log(tag,"onConnectCallback mac="+mac)
                var temp_name:String?=name
                var temp_mac:String?=mac
                if(temp_name==null){
                    temp_name="unkonw"
                }
                if(temp_mac==null){
                    temp_mac="unkonw"
                }

                enquneItem(temp_name!!,temp_mac!!);
            }

            override fun onConnectCallback(status: Int) {
                KotlinUtils.log(tag,"onConnectCallback status="+status)
            }
        })
        workItemList.clear()


        var mthread=Thread(object:Runnable{
            override fun run() {
                Looper.prepare()
                refreshHandler= Handler(Looper.myLooper()!!,object:Handler.Callback{
                    override fun handleMessage(p0: Message): Boolean {
                        KotlinUtils.log(tag,"handleMessage what=${p0.what}")
                        when(p0.what){
                            1->{
                                //enquneItem(System.currentTimeMillis().toString(),"ddd")
                            }
                        }
                        return true
                    }
                })
                Looper.loop()
            }
        })
        mthread.start()
    }

    private fun sendRefreshMsg(id:Int){
        var msg=Message()
        msg.what=id
        refreshHandler?.sendMessage(msg)
    }

    private fun startBleScan(){
        mBlemanager!!.scanLeDevice(true)
    }

    private fun enquneItem(name:String,data:String){
        var enqItem= getDrawable(R.mipmap.content_pos)?.let { WorkItemInfo(it,name,data) }
        for(item in workItemList){
            if(item.data.equals(data)){
                return
            }
        }
        workItemList.add(enqItem!!)
        KotlinUtils.log(tag,"enquneItem size=${workItemList.size}")
        workAdapter!!.notifyItemChanged(workItemList.size-1)
    }

    private fun initViews(){
        recyclerview=binding!!.recyclerview//findViewById(R.id.recyclerview)
        //模拟获取数据，这里获取的是已安装应用的 icon 和 包名
        //initAppList()

        //设置布局排列方式，默认垂直排列
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerview.layoutManager = linearLayoutManager

        //添加分隔线
        recyclerview.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))

        //加载适配器
        workAdapter = WorkAdapter(workItemList,object:WorkAdapter.onItemClickListenner{
            override fun onClick(pos: Int) {
                TODO("Not yet implemented")
            }
        })
        recyclerview.adapter = workAdapter

        binding?.fab?.setImageResource(R.mipmap.content_pos)
        binding?.fab?.setBackgroundResource(R.mipmap.content_pos)
        binding?.fab?.setOnClickListener(object:OnClickListener{
            override fun onClick(p0: View?) {
                KotlinUtils.log(tag,"fab click")
                showPopup()
            }
        })
        binding?.refreshLayout!!.setOnRefreshListener(object:OnRefreshListener{
            override fun onRefresh(refreshLayout: RefreshLayout) {
                KotlinUtils.log(tag,"onRefresh")
                enquneItem(System.currentTimeMillis().toString(),"ddd")
                binding?.refreshLayout!!.finishRefresh(true)
            }
        })
        binding?.refreshLayout!!.setOnLoadMoreListener(object:OnLoadMoreListener{
            override fun onLoadMore(refreshLayout: RefreshLayout) {
                KotlinUtils.log(tag,"onLoadMore")
                enquneItem(System.currentTimeMillis().toString(),"ddd")
                binding?.refreshLayout!!.finishLoadMore(true)
                //sendRefreshMsg(1)
            }
        })
    }

    private fun showPopup(){
        XPopup.Builder(this)
            .isDarkTheme(false)
            .isDestroyOnDismiss(true)
            //对于只使用一次的弹窗，推荐设置这个
            .asBottomList("select one", arrayOf("jack","qrcode"),
                intArrayOf(R.mipmap.content_report,R.mipmap.content_pos),
                object:OnSelectListener{
                    override fun onSelect(position: Int, text: String?) {
                        KotlinUtils.log(tag,"asBottomList select position=$position,text=$text")
                        //CrashReport.testJavaCrash();
                        when(position){
                            0->showToast("this is $text speaking")
                            1->openZxing()
                        }
                    }
                }
            )
            .show()
        /*
        XPopup.Builder(this)
            .isDarkTheme(false)
            .isDestroyOnDismiss(true)
            //对于只使用一次的弹窗，推荐设置这个
            .asBottomList("Please select one",
                arrayOf("jack","dave"),
                object:OnSelectListener{
                override fun onSelect(position: Int, text: String?) {
                    KotlinUtils.log(tag,"asBottomList select position=$position,text=$text")
                }
            })
            .show()*/
       /* var popup=XPopup.Builder(this)
            .isDarkTheme(false)
            .isDestroyOnDismiss(true)
            //对于只使用一次的弹窗，推荐设置这个
            .asLoading()
            .show()
            .delayDismissWith(2000,object:Runnable{
                override fun run() {
                    KotlinUtils.log(tag,"asLoading timeout")
                }
            })*/
       /* var popup=XPopup.Builder(this)
            .isDarkTheme(false)
            .isDestroyOnDismiss(true)
            //对于只使用一次的弹窗，推荐设置这个
            .asConfirm("title","content",object:OnConfirmListener{
                override fun onConfirm() {
                    KotlinUtils.log(tag,"asConfirm")
                }
            })
            .show()*/


    }

    private fun openZxing(){
// 创建IntentIntegrator对象
        var intentIntegrator =IntentIntegrator(this)
            //.setCaptureActivity(CustomCaptureActivity.class)// 自定义Activity
                .setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES)// 扫码的类型,可选：一维码，二维码，一/二维码
                .setPrompt("请将二维码置于取景框内扫描")// 设置提示语
                .setCameraId(0)// 选择摄像头,可使用前置或者后置
                .setBeepEnabled(true);// 是否开启声音,扫完码之后会"哔"的一声
        // 开始扫描
        intentIntegrator.initiateScan();
    }
    private fun initAppList(){
        val list : List<ApplicationInfo> = packageManager.getInstalledApplications(PackageManager.MATCH_SYSTEM_ONLY)

        for (i in list.indices){
            appList.add(AppInfo(list[i].loadIcon(packageManager), list[i].packageName))
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        getMenuInflater().inflate(R.menu.option_work_menu, menu);
        return true
    }

     override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)
        when (item.itemId) {
            R.id.work_back->finish()
            android.R.id.home -> finish()
            R.id.work_new->finish()
            R.id.work_scan->startBleScan()
        }
        return true
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        // 获取解析结果
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                KotlinUtils.log(tag,"取消扫描")
                Toast.makeText(this, "取消扫描", Toast.LENGTH_LONG).show()
            } else {
                KotlinUtils.log(tag,"扫描内容:" + result.contents)
                Toast.makeText(this, "扫描内容:" + result.contents, Toast.LENGTH_LONG).show()
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}
