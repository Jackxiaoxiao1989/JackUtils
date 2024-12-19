package com.example.kotlinstudy.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinstudy.R
import com.example.kotlinstudy.adapter.WorkAdapter
import com.example.kotlinstudy.control.BleMainManager
import com.example.kotlinstudy.databinding.FragmentShowBinding
import com.example.kotlinstudy.dialog.DialogManager
import com.example.kotlinstudy.dialog.DialogView
import com.example.kotlinstudy.info.WorkItemInfo
import com.example.kotlinstudy.room.UserRoomDatabase
import com.example.kotlinstudy.utils.KotlinThreadPool
import com.example.kotlinstudy.utils.KotlinUtils
import com.google.gson.Gson


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ShowFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ShowFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var binding:FragmentShowBinding?=null
    private var workItemList=ArrayList<WorkItemInfo>()
    private var workAdapter: WorkAdapter? = null
    private lateinit var recyclerview: RecyclerView
    private var mBlemanager: BleMainManager?=null
    private var mDialogVisual:DialogView?=null
    var TAG="ShowFragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentShowBinding.inflate(inflater, container, false)
        //return inflater.inflate(R.layout.fragment_show,container,false)
        initDatas()
        initViews()
        return binding?.getRoot()
    }

    override fun onPause(){
        super.onPause()
        stopBleScan()
    }
    fun initDatas(){
        mBlemanager= BleMainManager(activity!!.applicationContext, object: BleMainManager.OnBleMainFunction{
            override fun onScanCallback(name: String?, mac: String?) {
                KotlinUtils.log(TAG,"onConnectCallback name="+name)
                KotlinUtils.log(TAG,"onConnectCallback mac="+mac)
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
                KotlinUtils.log(TAG,"onConnectCallback status="+status)
            }
        })
        workItemList.clear()


    }

    private fun enquneItem(name:String,data:String){
        var enqItem= activity!!.getDrawable(R.mipmap.content_pos)?.let { WorkItemInfo(it,name,data) }
        for(item in workItemList){
            if(item.data.equals(data)){
                return
            }
        }
        workItemList.add(enqItem!!)
        sendRefreshItemMsg()
    }

    private fun enquneItem(newitem:WorkItemInfo){
        newitem.iconDrawable= activity!!.getDrawable(R.mipmap.content_pos)
        for(item in workItemList){
            if(item.datetime.equals(newitem.datetime)){
                return
            }
        }
        workItemList.add(newitem)
        sendRefreshItemMsg()
    }

    fun initViews(){
        recyclerview=binding!!.recyclerview//findViewById(R.id.recyclerview)
        //模拟获取数据，这里获取的是已安装应用的 icon 和 包名
        //initAppList()

        //设置布局排列方式，默认垂直排列
        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerview.layoutManager = linearLayoutManager

        //添加分隔线
        recyclerview.addItemDecoration(DividerItemDecoration(activity, LinearLayoutManager.VERTICAL))

        //加载适配器
        workAdapter = WorkAdapter(workItemList,object:WorkAdapter.onItemClickListenner{
            override fun onClick(pos: Int) {
                showItemInfo(pos)
            }
        })
        recyclerview.adapter = workAdapter
        KotlinThreadPool.getInstance()!!.addOneJob(object:Runnable{
            override fun run() {
                var mEntiys=UserRoomDatabase.getInstance(activity!!)!!.getUserRoomDao()!!.allRooms()
                if(mEntiys.size>0) {
                    for (iten in mEntiys) {
                        iten.let{
                            var workitem=WorkItemInfo(iten.name!!,1)
                            workitem.datetime=it.datetime
                            workitem.name=it.name
                            workitem.data=it.data!!
                            workitem.yongmian=it.yongmian
                            workitem.famian=it.famian
                            workitem.bean=it.bean
                            workitem.rice=it.rice
                            workitem.vagetable=it.vagetable
                            workitem.meat=it.meat
                            workitem.doufu=it.doufu
                            workitem.yimingmilk=it.yimingmilk
                            workitem.milk=it.milk
                            workitem.egg=it.egg
                            workitem.gas=it.gas
                            workitem.mianfen=it.mianfen
                            workitem.house=it.house
                            workitem.cash=it.cash
                            workitem.wechat=it.wechat
                            workitem.alipay=it.alipay
                            workitem.vip=it.vip
                            workitem.turnover=it.turnover
                            workitem.balance=it.balance
                            workitem.waimai=it.waimai
                            workitem.datetime=it.datetime
                            enquneItem(workitem)
                        }
                    }
                }
            }

        })
        mDialogVisual = DialogManager.instance!!.initView(activity, R.layout.my_dialog_test, Gravity.BOTTOM);
        mDialogVisual!!.setCanceledOnTouchOutside(false);
    }

    fun sendRefreshItemMsg(){
        val message = Message()
        message.what = 1
        handler.sendMessage(message)
    }
    private val handler: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                1 -> {
                    KotlinUtils.log(TAG,"enquneItem size=${workItemList.size}")
                    workAdapter!!.notifyItemChanged(workItemList.size-1)
                }
                2 -> {

                }
            }
        }
    }
    private fun startBleScan(){
        mBlemanager!!.scanLeDevice(true)
    }
    private fun stopBleScan(){
        mBlemanager!!.scanLeDevice(false)
    }
    fun handleActivityEvent(pos:Int,msg:WorkItemInfo?){
        KotlinUtils.log(TAG,"handleActivityEvent pos=${pos}")
        when(pos){
            1->startBleScan()
            2->msg!!.let { enquneItem(msg)}
        }
    }
    fun showItemInfo(pos:Int){
        var item:WorkItemInfo=workItemList.get(pos)
        /*var showStr="datatime:"+item.datetime
        showStr+="/yongmian:"+item.yongmian
        showStr+="/famian:"+item.famian
        showStr+="/egg:"+item.egg
        showStr+="/meat:"+item.meat
        showStr+="/vagetable:"+item.vagetable
        showStr+="/gas:"+item.gas
        showStr+="/doufu:"+item.doufu
        showStr+="/house:"+item.house
        showStr+="/cash:"+item.cash
        showStr+="/vip:"+item.vip
        showStr+="/wechat:"+item.wechat
        showStr+="/alipay:"+item.alipay
        showStr+="/turnover:"+item.turnover
        showStr+="/balance:"+item.balance
        showStr+="/yimingmilk:"+item.yimingmilk
        showStr+="/milk:"+item.milk
        showStr+="/rice:"+item.rice
        showStr+="/waimai:"+item.waimai*/
        var showGson= Gson()
        var showStr=showGson.toJson(item)
        KotlinUtils.log(TAG,"showItemInfo is:$showStr")
        mDialogVisual!!.setTitle("jack")
        mDialogVisual!!.setContent(showStr)
        mDialogVisual!!.show()
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ShowFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ShowFragment().apply {
                KotlinUtils.log("ShowFragment","newInstance")
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}