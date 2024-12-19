package com.example.kotlinstudy.BluetoothChat

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemClickListener
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlinstudy.R
import com.example.kotlinstudy.utils.KotlinUtils


class DeviceList : AppCompatActivity() {
    private var mBtAdapter: BluetoothAdapter? = null
    private var mPairedDevicesArrayAdapter: ArrayAdapter<String>? = null
    private var mNewDevicesArrayAdapter: ArrayAdapter<String>? = null

    //定义广播接收者，用于处理扫描蓝牙设备后的结果
    private val mReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(
            context: Context,
            intent: Intent
        ) {
            val action = intent.action
            if (BluetoothDevice.ACTION_FOUND == action) {
                val device =
                    intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)
                if (device!!.bondState != BluetoothDevice.BOND_BONDED) {
                    mNewDevicesArrayAdapter!!.add(
                        """
                            ${device.name}
                            ${device.address}
                            """.trimIndent()
                    )
                }
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED == action) {
                if (mNewDevicesArrayAdapter!!.count == 0) {
                    val noDevices =
                        resources.getText(R.string.none_found).toString()
                    mNewDevicesArrayAdapter!!.add(noDevices)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_device_list)
        //在被调用活动里，设置返回结果码
        setResult(Activity.RESULT_CANCELED)
        init() //活动界面
    }

    private fun init() {
        KotlinUtils.log(TAG, "跳转搜索蓝牙页面")
        val scanButton =
            findViewById<Button>(R.id.button_scan)
        scanButton.setOnClickListener {
            Toast.makeText(this@DeviceList, R.string.scanning, Toast.LENGTH_LONG).show()
            doDiscovery() //搜索蓝牙设备
        }
        mPairedDevicesArrayAdapter =
            ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1)
        mNewDevicesArrayAdapter =
            ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1)
        //已配对蓝牙设备列表
        val pairedListView =
            findViewById<ListView>(R.id.paired_devices)
        pairedListView.adapter = mPairedDevicesArrayAdapter
        pairedListView.onItemClickListener = mPaireDeviceClickListener
        //未配对蓝牙设备列表
        val newDevicesListView =
            findViewById<ListView>(R.id.new_devices)
        newDevicesListView.adapter = mNewDevicesArrayAdapter
        newDevicesListView.onItemClickListener = mNewDeviceClickListener
        //动态注册广播接收者
        var filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
        registerReceiver(mReceiver, filter)
        filter = IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)
        registerReceiver(mReceiver, filter)
        mBtAdapter = BluetoothAdapter.getDefaultAdapter()
        val pairedDevices = mBtAdapter!!.getBondedDevices()
        if (pairedDevices.size > 0) {
            findViewById<View>(R.id.title_paired_devices).visibility = View.VISIBLE
            for (device in pairedDevices) {
                mPairedDevicesArrayAdapter!!.add(
                    """
                        ${device.name}
                        ${device.address}
                        """.trimIndent()
                )
            }
        } else {
            val noDevices = resources.getText(R.string.none_paired).toString()
            mPairedDevicesArrayAdapter!!.add(noDevices)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mBtAdapter != null) {
            mBtAdapter!!.cancelDiscovery()
        }
        unregisterReceiver(mReceiver)
    }

    private fun doDiscovery() {
        findViewById<View>(R.id.title_new_devices).visibility = View.VISIBLE
        if (mBtAdapter!!.isDiscovering) {
            mBtAdapter!!.cancelDiscovery()
        }
        mBtAdapter!!.startDiscovery() //开始搜索蓝牙设备并产生广播
        //startDiscovery是一个异步方法
        //找到一个设备时就发送一个BluetoothDevice.ACTION_FOUND的广播
    }

    private val mPaireDeviceClickListener =
        OnItemClickListener { av, v, arg2, arg3 ->
            mBtAdapter!!.cancelDiscovery()
            val info = (v as TextView).text.toString()
            val address = info.substring(info.length - 17)
            val intent = Intent()
            intent.putExtra(EXTRA_DEVICE_ADDRESS, address) //Mac地址
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    private val mNewDeviceClickListener =
        OnItemClickListener { av, v, arg2, arg3 ->
            mBtAdapter!!.cancelDiscovery()
            Toast.makeText(this@DeviceList, "请在蓝牙设置界面手动连接设备", Toast.LENGTH_SHORT).show()
            val intent = Intent(Settings.ACTION_BLUETOOTH_SETTINGS)
            startActivityForResult(intent, 1)
        }

    //回调方法：进入蓝牙配对设置界面返回后执行
    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        init() //刷新好友列表
    }

    companion object {
        const val TAG = "device"
        var EXTRA_DEVICE_ADDRESS = "device_address" //Mac地址
    }
}