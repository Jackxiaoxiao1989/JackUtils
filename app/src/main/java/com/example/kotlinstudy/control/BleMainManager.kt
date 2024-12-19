package com.example.kotlinstudy.control

import android.bluetooth.*
import android.bluetooth.BluetoothAdapter.LeScanCallback
import android.content.Context
import android.os.Handler
import android.util.Log
import com.example.kotlinstudy.utils.KotlinUtils

import java.util.*


class BleMainManager(var mContext: Context, var mBleFunction: OnBleMainFunction) {
    var tag = "BleMainManager"
    var TAG = "BleMainManager"
    var mBluetoothDeviceAddress: String? = null
    var mBluetoothGatt: BluetoothGatt? = null
    var connectionState = 0
    var mScanning = false
     var bluetoothManager: BluetoothManager?=null
     var mBluetoothAdapter: BluetoothAdapter?=null

    fun connect(address: String): Boolean {
        if (mBluetoothAdapter == null || address == null) {
            KotlinUtils.log(TAG, "BluetoothAdapter not initialized or unspecified address.")
            return false
        }
        // Previously connected device. Try to reconnect. (先前连接的设备。 尝试重新连接)
        // Previously connected device. Try to reconnect. (先前连接的设备。 尝试重新连接)
        if (mBluetoothDeviceAddress != null && address == mBluetoothDeviceAddress && mBluetoothGatt != null) {
            KotlinUtils.log(TAG, "Trying to use an existing mBluetoothGatt for connection.")
            return if (mBluetoothGatt!!.connect()) {
                connectionState = BluetoothAdapter.STATE_CONNECTING
                true
            } else {
                false
            }
        }
        val device = mBluetoothAdapter!!.getRemoteDevice(address)
        if (device == null) {
            KotlinUtils.log(TAG, "Device not found.  Unable to connect.")
            return false
        }
        // We want to directly connect to the device, so we are setting the
        // autoConnect
        // parameter to false.
        mBluetoothGatt = device.connectGatt(mContext, false, mGattCallback) //该函数才是真正的去进行连接
        KotlinUtils.log(TAG, "Trying to create a new connection.")
        mBluetoothDeviceAddress = address
        connectionState = BluetoothAdapter.STATE_CONNECTING
        return true
    }

    private val mGattCallback: BluetoothGattCallback = object : BluetoothGattCallback() {
        //当连接上设备或者失去连接时会回调该函数
        override fun onConnectionStateChange(
            gatt: BluetoothGatt,
            status: Int,
            newState: Int
        ) {
            if (newState == BluetoothProfile.STATE_CONNECTED) { //连接成功
                KotlinUtils.log(TAG, "onConnectionStateChange,STATE_CONNECTED")
                //if(Utils.getInstance().getEnterMode()==2) {
                mBluetoothGatt!!.requestMtu(251)
                connectionState = BluetoothAdapter.STATE_CONNECTED
                //}else {
                //mBluetoothGatt.discoverServices(); //连接成功后就去找出该设备中的服务 private BluetoothGatt mBluetoothGatt;
                //mBleFunction.onConnectCallback(1);
                // }
            } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {  //连接失败
                KotlinUtils.log(TAG, "onConnectionStateChange,STATE_DISCONNECTED")
                connectionState = BluetoothAdapter.STATE_DISCONNECTED
                mBleFunction.onConnectCallback(-1)
            }
        }

        override fun onMtuChanged(gatt: BluetoothGatt, mtu: Int, status: Int) {
            super.onMtuChanged(gatt, mtu, status)
            KotlinUtils.log(TAG, "onMtuChanged,mtu=$mtu")
            if (status == BluetoothGatt.GATT_SUCCESS) {
                KotlinUtils.log(TAG, "onMtuChanged,succ")
                mBluetoothGatt!!.discoverServices() //连接成功后就去找出该设备中的服务 private BluetoothGatt mBluetoothGatt;
                mBleFunction.onConnectCallback(1)
            } else {
                KotlinUtils.log(TAG, "onMtuChanged,fail")
            }
        }

        //当设备是否找到服务时，会回调该函数
        override fun onServicesDiscovered(gatt: BluetoothGatt, status: Int) {
            if (status == BluetoothGatt.GATT_SUCCESS) {   //找到服务了
                //在这里可以对服务进行解析，寻找到你需要的服务
                KotlinUtils.log(TAG, "onServicesDiscovered,GATT_SUCCESS")
                // displayGattServices(getSupportedGattServices());
            } else {
                KotlinUtils.log(TAG, "onServicesDiscovered received: $status")
            }
        }

        //当读取设备时会回调该函数
        override fun onCharacteristicRead(
            gatt: BluetoothGatt,
            characteristic: BluetoothGattCharacteristic,
            status: Int
        ) {
            println("onCharacteristicRead")
            if (status == BluetoothGatt.GATT_SUCCESS) {
                //读取到的数据存在characteristic当中，可以通过characteristic.getValue();函数取出。然后再进行解析操作。
                //int charaProp = characteristic.getProperties();if ((charaProp | BluetoothGattCharacteristic.PROPERTY_NOTIFY) > 0)表示可发出通知。  判断该Characteristic属性
            }
        }

        //当向设备Descriptor中写数据时，会回调该函数
        override fun onDescriptorWrite(
            gatt: BluetoothGatt,
            descriptor: BluetoothGattDescriptor,
            status: Int
        ) {
            println(
                "onDescriptorWriteonDescriptorWrite = $status, descriptor =" + descriptor.uuid
                    .toString()
            )
        }

        //设备发出通知时会调用到该接口
        override fun onCharacteristicChanged(
            gatt: BluetoothGatt,
            characteristic: BluetoothGattCharacteristic
        ) {
            if (characteristic.value != null) {
                // System.out.println(characteristic.getStringValue(0));
            }
            println("--------onCharacteristicChanged-----")
            if (bleNotificationCallback != null) {
                bleNotificationCallback!!.onDataCallback(characteristic.value)
            }
        }

        override fun onReadRemoteRssi(
            gatt: BluetoothGatt,
            rssi: Int,
            status: Int
        ) {
            println("rssi = $rssi")
        }

        //当向Characteristic写数据时会回调该函数
        override fun onCharacteristicWrite(
            gatt: BluetoothGatt,
            characteristic: BluetoothGattCharacteristic,
            status: Int
        ) {
            println("--------write success----- status:$status")
        }
    }

    //此处返回获取到的服务列表
    val supportedGattServices: List<BluetoothGattService>?
        get() = if (mBluetoothGatt == null) null else mBluetoothGatt!!.services
    //此处返回获取到的服务列表

    private fun displayGattServices(gattServices: List<BluetoothGattService>?) {
        KotlinUtils.log(TAG, "displayGattServices")
        if (gattServices == null) return
        for (gattService in gattServices) { // 遍历出gattServices里面的所有服务
            val gattCharacteristics =
                gattService.characteristics
            for (gattCharacteristic in gattCharacteristics) { // 遍历每条服务里的所有Characteristic
                /*  if (gattCharacteristic.getUuid().toString().equalsIgnoreCase(需要通信的UUID)) {
                    // 有哪些UUID，每个UUID有什么属性及作用，一般硬件工程师都会给相应的文档。我们程序也可以读取其属性判断其属性。
                    // 此处可以可根据UUID的类型对设备进行读操作，写操作，设置notification等操作
                    // BluetoothGattCharacteristic gattNoticCharacteristic 假设是可设置通知的Characteristic
                    // BluetoothGattCharacteristic gattWriteCharacteristic 假设是可读的Characteristic
                    // BluetoothGattCharacteristic gattReadCharacteristic  假设是可写的Characteristic
                }*/
                KotlinUtils.log(
                    TAG,
                    "displayGattServices,gattCharacteristics=$gattCharacteristics"
                )
            }
        }
    }

    fun setCharacteristicNotification(
        characteristic: BluetoothGattCharacteristic,
        enabled: Boolean
    ) {
        if (mBluetoothAdapter == null || mBluetoothGatt == null) {
            Log.w(TAG, "BluetoothAdapter not initialized")
            return
        }
        mBluetoothGatt!!.setCharacteristicNotification(characteristic, enabled)
        /* BluetoothGattDescriptor descriptor = null;//characteristic.getDescriptor(UUID
        // .fromString(SampleGattAttributes.CLIENT_CHARACTERISTIC_CONFIG));
       if (descriptor != null) {
            System.out.println("write descriptor");
            descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
            mBluetoothGatt.writeDescriptor(descriptor);
        }*/
        val descriptorList =
            characteristic.descriptors
        if (descriptorList != null && descriptorList.size > 0) {
            for (descriptor in descriptorList) {
                if (enabled) {
                    descriptor.value = BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE
                } else {
                    descriptor.value = BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE
                }
                mBluetoothGatt!!.writeDescriptor(descriptor)
            }
        }
    }

    fun readCharacteristic(characteristic: BluetoothGattCharacteristic?) {
        if (mBluetoothAdapter == null || mBluetoothGatt == null) {
            Log.w(TAG, "BluetoothAdapter not initialized")
            return
        }
        mBluetoothGatt!!.readCharacteristic(characteristic)
    }

    fun wirteCharacteristic(characteristic: BluetoothGattCharacteristic?) {
        if (mBluetoothAdapter == null || mBluetoothGatt == null) {
            Log.w(TAG, "BluetoothAdapter not initialized")
            return
        }
        mBluetoothGatt!!.writeCharacteristic(characteristic)
    }

    fun getServiceByUUID(uuid: UUID?): BluetoothGattService {
        return mBluetoothGatt!!.getService(uuid)
    }

    fun getCharacteristicByUUID(
        service: BluetoothGattService,
        uuid: UUID?
    ): BluetoothGattCharacteristic {
        return service.getCharacteristic(uuid)
    }

    fun writeCharacteristicNow(
        characteristic: BluetoothGattCharacteristic,
        data: ByteArray?
    ) {
        characteristic.value = data
        mBluetoothGatt!!.writeCharacteristic(characteristic)
    }

    fun readCharacteristicNow(characteristic: BluetoothGattCharacteristic): ByteArray {
        mBluetoothGatt!!.readCharacteristic(characteristic)
        return characteristic.value
    }

    var mHandler = Handler()
    private val mLeScanCallback =
        LeScanCallback { device, rssi, scanRecord -> //在这里可以把搜索到的设备保存起来
            //device.getName();获取蓝牙设备名字
            //device.getAddress();获取蓝牙设备mac地址
            //KotlinUtils.log(tag, "scan,name=" + device.getName());
            //KotlinUtils.log(tag, "scan,mac=" + device.getAddress());
            Log.i(tag, "scan,name=" + device.name)
            Log.i(tag, "scan,mac=" + device.address)
            mBleFunction.onScanCallback(device.name, device.address)
        }

    fun scanLeDevice(enable: Boolean) {
        if (enable) {
            // Stops scanning after a pre-defined scan period.
            mHandler.postDelayed({
                mScanning = false
                mBluetoothAdapter!!.stopLeScan(mLeScanCallback)
            }, 60 * 1000.toLong()) //10秒后停止搜索
            mScanning = true
            mBluetoothAdapter!!.startLeScan(mLeScanCallback) //开始搜索
        } else {
            mScanning = false
            mBluetoothAdapter!!.stopLeScan(mLeScanCallback) //停止搜索
        }
    }

    fun disconnectBle() {
        if (mBluetoothGatt != null) {
            KotlinUtils.log(tag, "disconnectBle")
            mBluetoothGatt!!.disconnect()
            connectionState = BluetoothAdapter.STATE_DISCONNECTED
        }
    }

    interface OnBleMainFunction {
        fun onScanCallback(name: String?, mac: String?)
        fun onConnectCallback(status: Int)
    }

    interface OnBleNotificationCallback {
        fun onDataCallback(data: ByteArray?)
    }

    var bleNotificationCallback: OnBleNotificationCallback? = null
    fun setNotificationCallback(callback: OnBleNotificationCallback?) {
        bleNotificationCallback = callback
    }

    init {
        bluetoothManager =
            mContext.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        mBluetoothAdapter = bluetoothManager!!.adapter
    }
}