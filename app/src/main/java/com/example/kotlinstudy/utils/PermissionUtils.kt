package com.example.kotlinstudy.utils

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import java.util.*


object PermissionUtils {
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
        Manifest.permission.SYSTEM_ALERT_WINDOW
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
}