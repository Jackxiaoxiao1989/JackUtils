package com.example.kotlinstudy.activity

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.CalendarView
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.databinding.DataBindingUtil
import com.example.kotlinstudy.R
import com.example.kotlinstudy.constants.DeteilMode
import com.example.kotlinstudy.databinding.ActivityWorkBinding
import com.example.kotlinstudy.databinding.ActivityWrokDeteilBinding
import com.example.kotlinstudy.room.UserRoom
import com.example.kotlinstudy.room.UserRoomDatabase
import com.example.kotlinstudy.utils.KotlinThreadPool
import com.example.kotlinstudy.utils.KotlinUtils

class WrokDeteilActivity : RootActivity() {
    var binding:ActivityWrokDeteilBinding?=null
    var deteilMode:Int=DeteilMode.DETEIL_MODE_NEW.ordinal;
    var name:String?=null
    var data:String?=null
    var tag="WrokDeteilActivity"
    var mcontext=this
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_wrok_deteil)
        binding= DataBindingUtil.setContentView(this,R.layout.activity_wrok_deteil)
        intent.getIntExtra("mode",0)
        initDatas()
        initViews();
    }

    fun initDatas(){

    }

    fun initViews(){
        binding!!.deteilCan.setOnDateChangeListener(object:CalendarView.OnDateChangeListener{
            override fun onSelectedDayChange(p0: CalendarView, p1: Int, p2: Int, p3: Int) {
                KotlinUtils.log(tag,"on CalendarView change $p1-$p2-$p3")
            }
        })

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            binding!!.deteilDate.setOnDateChangedListener(object:DatePicker.OnDateChangedListener{
                override fun onDateChanged(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
                    KotlinUtils.log(tag,"on DatePicker change $p1-$p2-$p3")
                }
            })
            //binding!!.deteilDate.visibility=View.GONE
        }

        binding!!.deteilTime.setOnTimeChangedListener(object:TimePicker.OnTimeChangedListener{
            override fun onTimeChanged(p0: TimePicker?, p1: Int, p2: Int) {
                KotlinUtils.log(tag,"on TimePicker change $p1:$p2")
            }
        })
        binding!!.deteilBtn1.setOnClickListener(object:View.OnClickListener{
            override fun onClick(p0: View?) {
                handleFinsh()
            }

        })

        binding!!.deteilBtn2.setOnClickListener(object:View.OnClickListener{
            override fun onClick(p0: View?) {
                handleFinsh()
            }

        })
    }

    fun handleFinsh(){
        var name=binding!!.deteilEt1.getText().toString()
        var data=binding!!.deteilEt2.getText().toString()
        var yongmian=binding!!.deteilEtYongmian.getText().toString().toInt()
        var famian=binding!!.deteilEtFamian.getText().toString().toInt()
        var bean=binding!!.deteilEtBean.getText().toString().toInt()
        var rice=binding!!.deteilEtRice.getText().toString().toInt()
        var meat=binding!!.deteilEtMeat.getText().toString().toInt()
        var vagetable=binding!!.deteilEtVagetable.getText().toString().toInt()
        var doufu=binding!!.deteilEtDoufu.getText().toString().toInt()
        var yimingmilk=binding!!.deteilEtYimingmilk.getText().toString().toInt()
        var milk=binding!!.deteilEtMilk.getText().toString().toInt()
        var egg=binding!!.deteilEtEgg.getText().toString().toInt()
        var mianfen=binding!!.deteilEtMianfen.getText().toString().toInt()
        var gas=binding!!.deteilEtGas.getText().toString().toInt()
        var house=binding!!.deteilEtHouse.getText().toString().toInt()
        var cash=binding!!.deteilEtCash.getText().toString().toInt()
        var wechat=binding!!.deteilEtWechat.getText().toString().toInt()
        var alipay=binding!!.deteilEtAlipay.getText().toString().toInt()
        var vip=binding!!.deteilEtVip.getText().toString().toInt()
        var waimai=binding!!.deteilEtWaimai.getText().toString().toInt()
        var turnover=binding!!.deteilEtTurnover.getText().toString().toInt()
        var balance=binding!!.deteilEtBalance.getText().toString().toInt()
        var timelong=binding!!.deteilCan.getDate()
        var datetime=KotlinUtils.getInstance()!!.timestampToDate(timelong)
        KotlinUtils.log(tag,"datetime=$datetime");
        KotlinUtils.log(tag,"data=$data");
        var it: Intent=Intent()
        it.run {
            putExtra("name",name)
            putExtra("data",data)
            putExtra("yongmian",yongmian)
            putExtra("famian",famian)
            putExtra("bean",bean)
            putExtra("rice",rice)
            putExtra("vagetable",vagetable)
            putExtra("meat",meat)
            putExtra("doufu",doufu)
            putExtra("yimingmilk",yimingmilk)
            putExtra("milk",milk)
            putExtra("egg",egg)
            putExtra("gas",gas)
            putExtra("mianfen",mianfen)
            putExtra("house",house)
            putExtra("cash",cash)
            putExtra("wechat",wechat)
            putExtra("alipay",alipay)
            putExtra("vip",vip)
            putExtra("turnover",turnover)
            putExtra("balance",balance)
            putExtra("waimai",waimai)
            putExtra("datetime",datetime)
        }

        var workEntiy=UserRoom(name,22,data)
        workEntiy.run {
            this.yongmian=yongmian
            this.famian=famian
            this.bean=bean
            this.rice=rice
            this.vagetable=vagetable
            this.meat=meat
            this.doufu=doufu
            this.yimingmilk=yimingmilk
            this.milk=milk
            this.egg=egg
            this.gas=gas
            this.mianfen=mianfen
            this.house=house
            this.cash=cash
            this.wechat=wechat
            this.alipay=alipay
            this.vip=vip
            this.turnover=turnover
            this.balance=balance
            this.waimai=waimai
            this.datetime=datetime
        }
        KotlinThreadPool.getInstance()!!.addOneJob(object:Runnable{
            override fun run() {
                UserRoomDatabase.getInstance(mcontext)!!.getUserRoomDao()!!.insertRooms(workEntiy)
            }

        })

        setResult(1001,it)
        finish()
    }
}