package com.xiaoxiao.jackutils.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.Settings
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity


object SoftPermisson {
    /**
     * 权限列表
     */
    private val permissionList = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_PHONE_STATE,
        Manifest.permission.BLUETOOTH,
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.RECORD_AUDIO,
        Manifest.permission.SYSTEM_ALERT_WINDOW,
        Manifest.permission.REQUEST_INSTALL_PACKAGES
    )

    /***
     * 权限请求结果code
     */
    const val PERMISSIONS_REQUEST = 1
    fun checkPermission(mActivity: Activity, callback: Runnable): Boolean {
        val needPermission: MutableList<String> =
            ArrayList()
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (i in permissionList.indices) {
                if (mActivity.checkSelfPermission(permissionList[i]) != PackageManager.PERMISSION_GRANTED) {
                    needPermission.add(permissionList[i])
                }
            }
            if (!needPermission.isEmpty()) {
                val permissions =
                    needPermission.toTypedArray()
                ActivityCompat.requestPermissions(mActivity, permissions, 1)
                return false
            }
            callback.run()
            true
        } else {
            callback.run()
            true
        }
    }

    fun askFile(context: Context) {
        if (Build.VERSION.SDK_INT >= 30) {
            if (!Environment.isExternalStorageManager()) {
                val intent = Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION)
                context.startActivity(intent)
                return
            }
            //writeAndReaderFile();
            return
        }
    }

    fun askInstallUnkownPackage(context:Context){
        if(isUnknownSourcesEnabled(context)){
            //权限没有打开则提示用户去手动打开
            openInstallPermission(context)
        }
    }

    /**
     * 跳转到设置-允许安装未知来源-页面
     */
    fun openInstallPermission(context:Context ) {
        val intent = Intent(
            Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES,
            Uri.parse("package:" + SoftUtils.getPakageName(context))
        )
        context.startActivity(intent)
    }


    /**
     * 判断
     * 是否允许
     * 安装位置来源
     */
    fun  isUnknownSourcesEnabled(context:Context ):Boolean {
        try {
            return Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.INSTALL_NON_MARKET_APPS) == 1;
        } catch ( e:Settings.SettingNotFoundException) {
            return false;
        }
        /*if (ContextCompat.checkSelfPermission(context,
                Manifest.permission.REQUEST_INSTALL_PACKAGES) != PackageManager.PERMISSION_GRANTED) {
            return false
        }
        return true*/
    }
}