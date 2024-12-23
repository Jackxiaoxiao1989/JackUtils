package com.xiaoxiao.jackutils.control

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Environment
import android.provider.Settings
import android.util.Log
import com.xiaoxiao.jackutils.utils.SoftUtils

class SoftFileSystem {
    private val tag = "SoftFileSystem"

    var mContext: Context? = null
    var root_folder = "JackUtilsFile"
    var file_folder_origin = "origin"
    var file_folder_web = "web"
    var file_folder_log = "log"
    companion object {
        @Volatile
        private var sInstance: SoftFileSystem? = null
        fun getInstance(): SoftFileSystem? {
            if (sInstance == null) {
                synchronized(SoftFileSystem::class.java) {
                    if (sInstance == null) {
                        sInstance = SoftFileSystem()
                    }
                }
            }
            return sInstance
        }
    }

    fun init(context: Context?) {
        mContext = context
        SoftUtils.log(tag, "init")
        createFileFolder()
        fun askFile() {
            if (Build.VERSION.SDK_INT >= 30) {
                if (!Environment.isExternalStorageManager()) {
                    val intent = Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION)
                    context!!.startActivity(intent)
                    return
                }
                //writeAndReaderFile();
                return
            }
        }
    }

    fun createFileFolder() {
        SoftUtils.log(tag, "createFileFolder")
        SoftUtils.ChatUtilCreateFolder(getMonitorRootFilePath())
        //SoftUtils.ChatUtilCreateFolder(getMonitorWebFilePath())
        //SoftUtils.ChatUtilCreateFolder(getMonitorOriginFilePath())
        SoftUtils.ChatUtilCreateFolder(getMonitorLogFilePath())
    }

    fun getMonitorRootFilePath(): String {
        var audio_path: String? = null
        audio_path = if (Build.VERSION.SDK_INT >= 29) {
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).absolutePath
        } else {
            Environment.getExternalStorageDirectory().absolutePath
        }
        audio_path += "/$root_folder"
        SoftUtils.log(tag, "getMonitorFilePath,path = $audio_path")
        return audio_path
    }

    fun getMonitorWebFilePath(): String {
        var audio_path: String? = null
        audio_path = if (Build.VERSION.SDK_INT >= 29) {
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).absolutePath
        } else {
            Environment.getExternalStorageDirectory().absolutePath
        }
        audio_path += "/$root_folder/$file_folder_web"
        SoftUtils.log(tag, "getMonitorWebFilePath,path = $audio_path")
        return audio_path
    }

    fun getMonitorOriginFilePath(): String {
        var audio_path: String? = null
        audio_path = if (Build.VERSION.SDK_INT >= 29) {
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).absolutePath
        } else {
            Environment.getExternalStorageDirectory().absolutePath
        }
        audio_path += "/$root_folder/$file_folder_origin"
        SoftUtils.log(tag, "getMonitorOriginFilePath,path = $audio_path")
        return audio_path
    }

    fun getMonitorLogFilePath(): String {
        var audio_path: String? = null
        audio_path = if (Build.VERSION.SDK_INT >= 29) {
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).absolutePath
        } else {
            Environment.getExternalStorageDirectory().absolutePath
        }
        audio_path += "/$root_folder/$file_folder_log"
        Log.i(tag, "getMonitorLogFilePath,path = $audio_path")
        return audio_path
    }
/*
    var timett: Long = 0
    fun parseCsvFile(filepath: String?): ArrayList<DataBaseInfo>? {
        val dblist: ArrayList<DataBaseInfo> = ArrayList<DataBaseInfo>()
        val i = 0 // 用于标记打印的条数
        try {
            val csv = File(filepath) // CSV文件路径
            val br = BufferedReader(FileReader(csv))
            //br.readLine();
            var line = ""
            /**
             * 这里读取csv文件中的前10条数据
             * 如果要读取第10条到30条数据,只需定义i初始值为9,wile中i<10改为i>=9&&i<30即可,其他范围依次类推
             */
            //while ((line = br.readLine()) != null && i<10) { // 这里读取csv文件中的前10条数据
            while (br.readLine().also { line = it } != null) { // 这里读取csv文件中的前10条数据
                //i++;
                println("第" + i + "行：" + line) // 输出每一行数据
                /**
                 * csv格式每一列内容以逗号分隔,因此要取出想要的内容,以逗号为分割符分割字符串即可,
                 * 把分割结果存到到数组中,根据数组来取得相应值
                 */
                val buffer = line.split(",".toRegex()).dropLastWhile { it.isEmpty() }
                    .toTypedArray() // 以逗号分隔
                println("第" + i + "行：" + buffer[0]) // 取第一列数据
                println("第" + i + "行：" + buffer[1])
                println("第" + i + "行：" + buffer[2])
                val dbitem = DataBaseInfo()
                dbitem.content_stages = buffer[0].toInt()
                dbitem.content_pos = buffer[1].toInt()
                dbitem.content_sports = buffer[2].toInt()
                val tt = System.currentTimeMillis()
                timett = if (tt <= timett) {
                    timett + 1
                } else {
                    tt
                }
                dbitem.content_timestemp = java.lang.Long.toString(timett)
                Loggers.info(tag, "parseCsvFile,content_timestemp = " + dbitem.content_timestemp)
                dblist.add(dbitem)
            }
            br.close()
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
        return dblist
    }

    fun parseNoiseCsvFile(filepath: String?, dblist: ArrayList<NoiseDataInfo?>?): Boolean {
        //ArrayList<Float> dblist=new ArrayList<Float>();
        Loggers.info(tag, "parseNoiseCsvFile,filepath = $filepath")
        if (filepath == null || dblist == null) {
            Loggers.info(tag, "parseNoiseCsvFile,error1 return")
            return false
        }
        val csv = File(filepath) // CSV文件路径
        if (!csv.exists()) {
            Loggers.info(tag, "parseNoiseCsvFile,error2 return")
            return false
        }
        var i = 0 // 用于标记打印的条数
        try {
            val br = BufferedReader(FileReader(csv))
            //br.readLine();
            var line = ""
            /**
             * 这里读取csv文件中的前10条数据
             * 如果要读取第10条到30条数据,只需定义i初始值为9,wile中i<10改为i>=9&&i<30即可,其他范围依次类推
             */
            //while ((line = br.readLine()) != null && i<10) { // 这里读取csv文件中的前10条数据
            while (br.readLine().also { line = it } != null) { // 这里读取csv文件中的前10条数据
                Loggers.info(tag, "第" + i + "行：" + line) // 输出每一行数据
                /**
                 * csv格式每一列内容以逗号分隔,因此要取出想要的内容,以逗号为分割符分割字符串即可,
                 * 把分割结果存到到数组中,根据数组来取得相应值
                 */
                val buffer = line.split(",".toRegex()).dropLastWhile { it.isEmpty() }
                    .toTypedArray() // 以逗号分隔
                // Loggers.info(tag,"第" + i + "行：" + buffer[0]);// 取第一列数据
                val noise_time = buffer[0].toFloat()
                Loggers.info(tag, "parseNoiseCsvFile,noise_time = $noise_time")
                val dinfo = NoiseDataInfo()
                dinfo.noise_time = noise_time
                dblist.add(dinfo)
                i++
            }
            br.close()
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }
 */
}