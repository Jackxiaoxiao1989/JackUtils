package com.example.kotlinstudy.BluetoothChat

import android.Manifest
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.*
import android.widget.TextView.OnEditorActionListener
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.kotlinstudy.R


class BluetoothChat : AppCompatActivity() {
    private var mTitle: TextView? = null
    private var mConversationView: ListView? = null
    private var mOutEditText: EditText? = null
    private var mSendButton: Button? = null
    private var mConnectedDeviceName: String? = null
    private var mConversationArrayAdapter: ArrayAdapter<String>? = null
    private var mOutStringBuffer: StringBuffer? = null
    private var mBluetoothAdapter: BluetoothAdapter? = null
    private var mChatService: ChatService? = null
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bluetooth_chat)
        //getSupportActionBar().hide();  //隐藏标题栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                    1
                )
            }
        }
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true)
            actionBar.setDisplayHomeAsUpEnabled(true)
        }
        mTitle = findViewById(R.id.title_left_text)
        mTitle!!.setText(R.string.app_name)
        mTitle = findViewById(R.id.title_right_text)
        // 得到本地蓝牙适配器
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, "蓝牙不可用", Toast.LENGTH_LONG).show()
            finish()
            return
        }
        if (!mBluetoothAdapter!!.isEnabled) { //若当前设备蓝牙功能未开启
            val enableIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT) //
        } else {
            if (mChatService == null) {
                setupChat() //创建会话
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.size > 0) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "未授权，蓝牙搜索功能将不可用！", Toast.LENGTH_SHORT).show()
            }
        }
    }

    @Synchronized
    public override fun onResume() {
        super.onResume()
        if (mChatService != null) {
            if (mChatService!!.getState() === ChatService.STATE_NONE) {
                mChatService!!.start()
            }
        }
    }

    private fun setupChat() {
        mConversationArrayAdapter =
            ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1)
        mConversationView = findViewById(R.id.`in`)
        mConversationView!!.setAdapter(mConversationArrayAdapter)
        mOutEditText = findViewById(R.id.edit_text_out)
        mOutEditText!!.setOnEditorActionListener(mWriteListener)
        mSendButton = findViewById(R.id.button_send)
        mSendButton!!.setOnClickListener(View.OnClickListener {
            val view = findViewById<TextView>(R.id.edit_text_out)
            val message = view.text.toString()
            sendMessage(message)
        })
        //创建服务对象
        mChatService = ChatService(this, mHandler)
        mOutStringBuffer = StringBuffer("")
    }

    public override fun onDestroy() {
        super.onDestroy()
        if (mChatService != null) mChatService!!.stop()
    }

    private fun ensureDiscoverable() { //修改本机蓝牙设备的可见性
        //打开手机蓝牙后，能被其它蓝牙设备扫描到的时间不是永久的
        if (mBluetoothAdapter!!.scanMode != BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
            val discoverableIntent = Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE)
            //设置在300秒内可见（能被扫描）
            discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300)
            startActivity(discoverableIntent)
            Toast.makeText(this, "已经设置本机蓝牙设备的可见性，对方可搜索了。", Toast.LENGTH_SHORT).show()
        }
    }

    private fun sendMessage(message: String) {
        if (mChatService!!.getState() !== ChatService.STATE_CONNECTED) {
            Toast.makeText(this, R.string.not_connected, Toast.LENGTH_SHORT).show()
            return
        }
        if (message.length > 0) {
            val send = message.toByteArray()
            mChatService!!.write(send)
            mOutStringBuffer!!.setLength(0)
            mOutEditText!!.setText(mOutStringBuffer)
        }
    }

    private val mWriteListener =
        OnEditorActionListener { view, actionId, event ->
            if (actionId == EditorInfo.IME_NULL && event.action == KeyEvent.ACTION_UP) {
                //软键盘里的回车也能发送消息
                val message = view.text.toString()
                sendMessage(message)
            }
            true
        }

    //使用Handler对象在UI主线程与子线程之间传递消息
    private val mHandler: Handler = object : Handler() {
        //消息处理
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                MESSAGE_STATE_CHANGE -> when (msg.arg1) {
                    ChatService.STATE_CONNECTED -> {
                        mTitle!!.setText(R.string.title_connected_to)
                        mTitle!!.append(mConnectedDeviceName)
                        mConversationArrayAdapter!!.clear()
                    }
                    ChatService.STATE_CONNECTING -> mTitle!!.setText(R.string.title_connecting)
                    ChatService.STATE_LISTEN, ChatService.STATE_NONE -> mTitle!!.setText(R.string.title_not_connected)
                }
                MESSAGE_WRITE -> {
                    val writeBuf = msg.obj as ByteArray
                    val writeMessage = String(writeBuf)
                    mConversationArrayAdapter!!.add("我:  $writeMessage")
                }
                MESSAGE_READ -> {
                    val readBuf = msg.obj as ByteArray
                    val readMessage = String(readBuf, 0, msg.arg1)
                    mConversationArrayAdapter!!.add(
                        mConnectedDeviceName + ":  "
                                + readMessage
                    )
                }
                MESSAGE_DEVICE_NAME -> {
                    mConnectedDeviceName =
                        msg.data.getString(DEVICE_NAME)
                    Toast.makeText(
                        applicationContext,
                        "链接到 $mConnectedDeviceName",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                MESSAGE_TOAST -> Toast.makeText(
                    applicationContext,
                    msg.data.getString(TOAST), Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    //返回进入好友列表操作后的数回调方法
    public override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_CONNECT_DEVICE -> if (resultCode == Activity.RESULT_OK) {
                val address =
                    data!!.extras!!.getString(DeviceList.EXTRA_DEVICE_ADDRESS)
                val device = mBluetoothAdapter!!.getRemoteDevice(address)
                mChatService!!.connect(device)
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(this, "未选择任何好友！", Toast.LENGTH_SHORT).show()
            }
            REQUEST_ENABLE_BT -> if (resultCode == Activity.RESULT_OK) {
                setupChat()
            } else {
                Toast.makeText(this, R.string.bt_not_enabled_leaving, Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)
        when (item.itemId) {
            R.id.home -> finish()
            R.id.scan -> {
                //启动DeviceList这个Activity
                val serverIntent = Intent(this@BluetoothChat, DeviceList::class.java)
                startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE)
                return true
            }
            R.id.discoverable -> {
                ensureDiscoverable()
                return true
            }
            R.id.back -> {
                finish()
                System.exit(0)
                return true
            }
        }
        return true
    }

    companion object {
        const val MESSAGE_STATE_CHANGE = 1
        const val MESSAGE_READ = 2
        const val MESSAGE_WRITE = 3
        const val MESSAGE_DEVICE_NAME = 4
        const val MESSAGE_TOAST = 5
        const val DEVICE_NAME = "device_name"
        const val TOAST = "toast"
        private const val REQUEST_CONNECT_DEVICE = 1 //请求连接设备
        private const val REQUEST_ENABLE_BT = 2
    }
}