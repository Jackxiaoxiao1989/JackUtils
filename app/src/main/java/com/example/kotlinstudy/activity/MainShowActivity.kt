package com.example.kotlinstudy.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.Window
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.example.kotlinstudy.R
import com.example.kotlinstudy.eventbus.EventMsg
import com.example.kotlinstudy.fragment.*
import com.example.kotlinstudy.info.WorkItemInfo
import com.example.kotlinstudy.utils.KotlinUtils
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.*

class MainShowActivity : AppCompatActivity(), View.OnClickListener {
    var tag="MainShowActivity"
    //声明ViewPager
    private var mViewPager: ViewPager? = null

    //适配器
    private var mAdapter: FragmentPagerAdapter? = null

    //装载Fragment的集合
    private var mFragments: ArrayList<Fragment>? = null

    //四个Tab对应的布局
    private var mTabShow: LinearLayout? = null
    private var mTabSetting: LinearLayout? = null
    private var mTabciji: LinearLayout? = null
    private var mTabxuewei: LinearLayout? = null
    private var mTabgaishan: LinearLayout? = null

    //四个Tab对应的ImageButton
    private var mImgShow: ImageView? = null
    private var mImgSetting: ImageView? = null
    private var mImgciji: ImageView? = null
    private var mImgxuewei: ImageView? = null
    private var mImggaishan: ImageView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_main_show)
        initViews() //初始化控件

        initEvents() //初始化事件

        initDatas() //初始化数据

    }

    //初始化控件
    private fun initViews() {
        mViewPager = findViewById<View>(R.id.id_viewpager) as ViewPager
        mTabShow = findViewById<View>(R.id.id_tab_show) as LinearLayout
        mTabSetting = findViewById<View>(R.id.id_tab_setting) as LinearLayout
        mTabciji = findViewById<View>(R.id.id_tab_ciji) as LinearLayout
        mTabxuewei = findViewById<View>(R.id.id_tab_xuewei) as LinearLayout
        mTabgaishan = findViewById<View>(R.id.id_tab_gaishan) as LinearLayout
        mImgShow = findViewById<View>(R.id.id_tab_show_img) as ImageView
        mImgSetting = findViewById<View>(R.id.id_tab_setting_img) as ImageView
        mImgciji = findViewById<View>(R.id.id_tab_ciji_img) as ImageView
        mImgxuewei = findViewById<View>(R.id.id_tab_xuewei_img) as ImageView
        mImggaishan = findViewById<View>(R.id.id_tab_gaishan_img) as ImageView
        resetImgs()
        mImgShow?.setImageResource(R.mipmap.tab_show_pressed)

    }

    //将四个ImageButton设置为灰色
    private fun resetImgs() {
        mImgShow!!.setImageResource(R.mipmap.tab_show_normal)
        mImgSetting!!.setImageResource(R.mipmap.tab_settings_normal)
        mImgciji!!.setImageResource(R.mipmap.tab_ciji_normal)
        mImgxuewei!!.setImageResource(R.mipmap.tab_xuewei_normal)
        mImggaishan!!.setImageResource(R.mipmap.tab_gaishan_normal)
    }
    private fun initEvents() {
        //设置四个Tab的点击事件
        mTabShow!!.setOnClickListener(this)
        mTabSetting!!.setOnClickListener(this)
        mTabciji!!.setOnClickListener(this)
        mTabxuewei!!.setOnClickListener(this)
        mTabgaishan!!.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        //先将四个ImageButton置为灰色

        //先将四个ImageButton置为灰色
        resetImgs()
        KotlinUtils.log(tag, "onClick,i=" + p0?.getId())
        KotlinUtils.log(tag, "onClick,id_tab_ciji=" + R.id.id_tab_ciji)
        KotlinUtils.log(tag, "onClick,id_tab_xuewei=" + R.id.id_tab_xuewei)
        KotlinUtils.log(tag, "onClick,id_tab_gaishan=" + R.id.id_tab_gaishan)
        //根据点击的Tab切换不同的页面及设置对应的ImageButton为绿色
        when (p0?.getId()) {
            R.id.id_tab_show -> selectTab(0)
            R.id.id_tab_ciji -> selectTab(1)
            R.id.id_tab_xuewei -> selectTab(2)
            R.id.id_tab_gaishan -> selectTab(3)
            R.id.id_tab_setting -> selectTab(4)
        }
    }
    private fun selectTab(i: Int) {
        //根据点击的Tab设置对应的ImageButton为绿色
        KotlinUtils.log(tag, "onClick,i=$i")
        when (i) {
            0 -> mImgShow!!.setImageResource(R.mipmap.tab_show_pressed)
            1 -> mImgciji!!.setImageResource(R.mipmap.tab_ciji_pressed)
            2 -> mImgxuewei!!.setImageResource(R.mipmap.tab_xuewei_pressed)
            3 -> mImggaishan!!.setImageResource(R.mipmap.tab_gaishan_pressed)
            4 -> mImgSetting!!.setImageResource(R.mipmap.tab_settings_pressed)
        }
        //设置当前点击的Tab所对应的页面
        mViewPager!!.currentItem = i
    }

    fun ShowFragment.haha():String{
        return "haha"
    }
    var mShow: ShowFragment? = null
    var mCiji: CijiFragment? = null
    var mXuewei: XueweiFragment? = null
    var mGaishan: GaishanFragment? = null
    var mSettings: SettingsFragment? = null
    private fun initDatas() {
        mFragments = ArrayList<Fragment>()
        //将四个Fragment加入集合中
        mShow = ShowFragment()
        ShowFragment.newInstance("123","456")
        var gg=mShow!!.haha()
        KotlinUtils.log(tag,"gg=$gg")
        mCiji = CijiFragment()
        mXuewei = XueweiFragment()
        mGaishan = GaishanFragment()
        mSettings = SettingsFragment()
        mFragments?.add(mShow!!)
        mFragments?.add(mCiji!!)
        mFragments?.add(mXuewei!!)
        mFragments?.add(mGaishan!!)
        mFragments?.add(mSettings!!)

        //初始化适配器
        mAdapter = object : FragmentPagerAdapter(supportFragmentManager) {
            override fun getItem(position: Int): Fragment { //从集合中获取对应位置的Fragment
                return mFragments!!.get(position)
            }

            override fun getCount(): Int { //获取集合中Fragment的总数
                return mFragments!!.size
            }
        }
        //不要忘记设置ViewPager的适配器
        mViewPager!!.adapter = mAdapter
        //设置ViewPager的切换监听
        mViewPager!!.addOnPageChangeListener(object : OnPageChangeListener {
            //页面滚动事件
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            //页面选中事件
            override fun onPageSelected(position: Int) {
                //设置position对应的集合中的Fragment
                mViewPager!!.currentItem = position
                resetImgs()
                selectTab(position)
            }

            //页面滚动状态改变事件
            override fun onPageScrollStateChanged(state: Int) {}
        })
        //pos_bar.setProgress(1,30);
        //sports_bar.setProgress(0,11);
        //////////////////////////
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
            R.id.work_new->handleNew()
            R.id.work_scan->sendMsgToFragment(1,1,null)
        }
        return true
    }

    fun sendMsgToFragment(whre:Int,type:Int,msg:WorkItemInfo?){
        when(whre){
            1->mShow!!.handleActivityEvent(type,msg)
        }
    }

    fun handleNew(){
        var it:Intent=Intent()
        it.setClass(this,WrokDeteilActivity::class.java)
        startActivityForResult(it,1001)
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    // 接受事件消息1
    @Subscribe(threadMode = ThreadMode.MAIN, priority = 1)
    fun onReceiveMsg(msg: EventMsg) {
        KotlinUtils.log(tag, "onReceiveMsg: where：" + msg.where)
        KotlinUtils.log(tag, "onReceiveMsg: pos：" + msg.pos)
        KotlinUtils.log(tag, "onReceiveMsg: message：" + msg.message)

    }

    // 接受事件消息2
    @Subscribe(threadMode = ThreadMode.MAIN, priority = 2, sticky = false)
    fun getEventMsg(msg: EventMsg?) {
        KotlinUtils.log(tag, "getEventMsg: " + Thread.currentThread().name)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, it: Intent?) {
            var name=it?.getStringExtra("name")
            var data=it?.getStringExtra("data")
            KotlinUtils.log(tag, "onActivityResult: name：" + name)
            KotlinUtils.log(tag, "onActivityResult: data：" + data)
            var emsg=WorkItemInfo(1,2,name!!,data!!)
            emsg.run {
                this.yongmian=it?.getIntExtra("yongmian",0)!!

                this.famian=it?.getIntExtra("famian",0)
                this.bean=it?.getIntExtra("bean",0)
                this.rice=it?.getIntExtra("rice",0)
                this.meat=it?.getIntExtra("meat",0)
                this.vagetable=it?.getIntExtra("vagetable",0)
                this.doufu=it?.getIntExtra("doufu",0)
                this.yimingmilk=it?.getIntExtra("yimingmilk",0)
                this.milk=it?.getIntExtra("milk",0)
                this.egg=it?.getIntExtra("egg",0)
                this.mianfen=it?.getIntExtra("mianfen",0)
                this.gas=it?.getIntExtra("gas",0)
                this.house=it?.getIntExtra("house",0)
                this.cash=it?.getIntExtra("cash",0)
                this.wechat=it?.getIntExtra("wechat",0)
                this.alipay=it?.getIntExtra("alipay",0)
                this.vip=it?.getIntExtra("vip",0)
                this.waimai=it?.getIntExtra("waimai",0)
                this.turnover=it?.getIntExtra("turnover",0)
                this.balance=it?.getIntExtra("balance",0)
        }
        sendMsgToFragment(1,2,emsg)
        super.onActivityResult(requestCode, resultCode, it)
    }
}