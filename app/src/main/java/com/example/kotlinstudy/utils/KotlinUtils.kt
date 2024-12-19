package com.example.kotlinstudy.utils

import android.app.ActivityManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.os.Build
import android.os.Environment
import android.text.format.Time
import android.util.Log
import android.view.View
import android.widget.ScrollView
import com.example.kotlinstudy.info.DateTimeInfo
import com.tencent.bugly.crashreport.BuglyLog
import java.io.*
import java.text.DecimalFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.experimental.and

class KotlinUtils {
    var LOG_TAG="KotlinUtils"
    var tag="KotlinUtils"
    companion object {
        @Volatile
        private var sInstance: KotlinUtils? = null
        var isLog:Boolean=true
        var isBugly:Boolean=false
        var mtag: String = "KotlinUtils"
        @JvmStatic
        fun log(tag:String,data:String) {
            if(isLog) {
                if(isBugly){
                    BuglyLog.i(tag,data)
                }
                Log.i(tag, data)
            }
        }
        @JvmStatic
        fun log(data:String){
            if(isLog) {
                Log.i(mtag, data)
            }
        }

        fun getInstance(): KotlinUtils? {
            if (sInstance == null) {
                synchronized(KotlinUtils::class.java) {
                    if (sInstance == null) {
                        sInstance = KotlinUtils()
                    }
                }
            }
            return sInstance
        }
    }


    var mContext: Context? = null
    fun logout(tag: String, data: String) {
        KotlinUtils.log(tag, data)
    }

    fun bindContext(context: Context?) {
        mContext = context
    }

    fun getAppContext(): Context? {
        return mContext
    }



    fun UtilGetDate(): String? {
        val formatter = SimpleDateFormat("yyyyMMddHHmmss")
        val curDate = Date(System.currentTimeMillis()) //获取当前时间
        val str = formatter.format(curDate)
        KotlinUtils.log(LOG_TAG, "UtilGetDate=$str")
        return str
    }

    fun getTimeCompare(start: String?, end: String?): DateTimeInfo? {
        if (start == null || end == null) {
            return null
        }
        val compareTime = DateTimeInfo()
        val formatter = SimpleDateFormat("yyyyMMddHHmmss")
        try {
            //formattimes是当前系统时间
            val date1 = formatter.parse(start)
            //lastSvrTime是结束时间
            val date2 = formatter.parse(end)
            val times = (date2.time - date1.time).toInt() //这样得到的差值是微秒级别
            compareTime.day = (times / (1000 * 60 * 60 * 24)).toInt() //换算成天数
            compareTime.hour =
                ((times - compareTime.day * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60)) //换算成小时
            compareTime.min =
                ((times - compareTime.day * (1000 * 60 * 60 * 24) - compareTime.hour * (1000 * 60 * 60)) / (1000 * 60)) //换算成分钟
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return compareTime
    }

    fun parseTimesteamp(timestemp: String): DateTimeInfo? {
        KotlinUtils.log(
            LOG_TAG,
            "parseTimesteamp,timestemp=$timestemp"
        )
        val dt = DateTimeInfo()
        val yearstr = timestemp.substring(0, 4)
        KotlinUtils.log(
            LOG_TAG,
            "parseTimesteamp,yearstr=$yearstr"
        )
        val monthstr = timestemp.substring(4, 6)
        KotlinUtils.log(
            LOG_TAG,
            "parseTimesteamp,monthstr=$monthstr"
        )
        val daystr = timestemp.substring(6, 8)
        KotlinUtils.log(LOG_TAG, "parseTimesteamp,daystr=$daystr")
        val hourstr = timestemp.substring(8, 10)
        KotlinUtils.log(
            LOG_TAG,
            "parseTimesteamp,hourstr=$hourstr"
        )
        val minstr = timestemp.substring(10, 12)
        KotlinUtils.log(LOG_TAG, "parseTimesteamp,minstr=$minstr")
        val secstr = timestemp.substring(12, 14)
        KotlinUtils.log(LOG_TAG, "parseTimesteamp,secstr=$secstr")
        dt.year = yearstr.toInt()
        dt.month = monthstr.toInt()
        dt.day = daystr.toInt()
        dt.hour = hourstr.toInt()
        dt.min = minstr.toInt()
        dt.sec = secstr.toInt()
        return dt
    }

    fun createOneSteamp(): String? {
        val formatter = SimpleDateFormat("yyyyMMddHHmmss")
        val curDate = Date(System.currentTimeMillis()) //获取当前时间
        val str = formatter.format(curDate)
        KotlinUtils.log(LOG_TAG, "createOneSteamp,str=$str")
        val steamp = str.substring(0, 8) + "_" + str.substring(8, 14) + "000"
        KotlinUtils.log(LOG_TAG, "createOneSteamp steamp=$steamp")
        return steamp
    }

    fun getOneSteamp(): String? {
        val formatter = SimpleDateFormat("yyyyMMddHHmmss")
        val curDate = Date(System.currentTimeMillis()) //获取当前时间
        val str = formatter.format(curDate)
        KotlinUtils.log(LOG_TAG, "getOneSteamp,str=$str")
        val steamp = str.substring(8, 14) + "000"
        KotlinUtils.log(LOG_TAG, "getOneSteamp steamp=$steamp")
        return steamp
    }

    fun getLogSteamp(): String? {
        val formatter = SimpleDateFormat("yyyyMMddHHmmss")
        val curDate = Date(System.currentTimeMillis()) //获取当前时间
        val str = formatter.format(curDate)
        Log.i(LOG_TAG, "getOneSteamp,str=$str")
        Log.i(
            LOG_TAG,
            "getOneSteamp steamp=$str"
        )
        return str
    }

    fun UtilGetTimeString(): String? {
        val t = Time("GMT+8:00")
        t.setToNow()
        val year = t.year
        val month = t.month
        val day = t.monthDay
        val hour = t.hour
        val minute = t.minute
        val second = t.second
        val sss = 0
        val steamp =
            Integer.toString(year) + "-" + Integer.toString(month) + "-" + Integer.toString(
                day
            ) + " " + Integer.toString(hour) + ":" + Integer.toString(minute) + ":" + Integer.toString(
                second
            ) + ":" + Integer.toString(sss)
        Log.i(LOG_TAG, "UtilGetTimeSteamp=$steamp")
        return steamp
    }

    fun UtilGetTimeSteamp(): String? {
        val t = Time("GMT+8:00")
        t.setToNow()
        val year = 9999 - t.year
        val month = 99 - t.month
        val day = 99 - t.monthDay
        val hour = 99 - t.hour
        val minute = 99 - t.minute
        val second = 99 - t.second
        val sss = 999 - 0
        val steamp =
            Integer.toString(year) + Integer.toString(month) + Integer.toString(
                day
            ) + Integer.toString(hour) + Integer.toString(minute) + Integer.toString(
                second
            ) + Integer.toString(sss)
        Log.i(LOG_TAG, "UtilGetTimeSteamp=$steamp")
        return steamp
    }

    fun bytes2HexString(b: ByteArray?): String? {
        if (null == b || b.size == 0) return ""
        val r = StringBuilder()
        for (value in b) {

            var hex = Integer.toHexString((value and 0xF).toInt())
            if (hex.length == 1) {
                hex = "0$hex"
            }
            r.append(hex.toUpperCase())
        }
        return r.toString()
    }

    /**
     * 十六进制字节string2相等
     * 16进制字符串转字节数组
     *
     * @param hex 十六进制
     * @return [byte[]]
     */
    fun hexString2Bytes(hex: String?): ByteArray? {
        var hex = hex
        val empty = ByteArray(0)
        return if (hex == null || hex == "") {
            empty
        } else if (hex.length % 2 != 0) {
            empty
        } else {
            hex = hex.toUpperCase()
            val len = hex.length / 2
            val b = ByteArray(len)
            val hc = hex.toCharArray()
            for (i in 0 until len) {
                val p = 2 * i
                b[i] = (charToByte(hc[p]).toInt() shl 4 or charToByte(hc[p + 1]).toInt()) as Byte
            }
            b
        }
    }

    private fun charToByte(c: Char): Byte {
        return "0123456789ABCDEF".indexOf(c).toByte()
    }

    fun ChatUtilCreateFolder(folder_name: String) {
        Log.i("LOG_TAG", "ChatUtilCreateFolder=$folder_name")
        val dirFirstFolder = File(folder_name)
        if (!dirFirstFolder.exists()) { //如果该文件夹不存在，则进行创建
            dirFirstFolder.mkdirs() //创建文件夹
            Log.i("LOG_TAG", "ChatUtilCreateFolder create it")
        } else {
            Log.i("LOG_TAG", "ChatUtilCreateFolder already exsit")
        }
    }

    fun ChatUtilCreateFile(file_name: String?) {
        val file = File(file_name)
        if (!file.exists()) {
            try {
                file.createNewFile()
                //file is create
            } catch (e: IOException) {
                // TODO Auto-generated catch block
                //e.printStackTrace();
            }
        }
    }

    fun ChatUtilIsDir(file_name: String?): Boolean {
        var is_dir = false
        if (file_name == null) {
            return is_dir
        }
        val file = File(file_name)
        is_dir = if (file.isDirectory) {
            true
        } else {
            false
        }
        return is_dir
    }

    fun ChatUtilFileRename(old_name: String?, new_name: String?) {
        val file = File(old_name)
        file.renameTo(File(new_name))
    }
    fun timestampToDate(timestamp: Long): String? {
        val sdf =
            SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val date = Date(timestamp)
        return sdf.format(date)
    }
    fun ChatUtilDeleteFile(path: String?) {
        val file = File(path)
        if (file.exists()) {
            file.delete()
        }
    }

    /**
     * 将数据存到文件中
     *
     *
     * @param data 需要保存的数据
     * @param fileName 文件名
     */
    fun ChatUtilSaveDataToFile(
        data: ByteArray?,
        fileName: String?
    ) {
        var fileOutputStream: FileOutputStream? = null
        val bufferedWriter: BufferedWriter? = null
        val file = File(fileName)
        try {
            fileOutputStream = FileOutputStream(file)
            try {
                fileOutputStream.write(data)
                fileOutputStream.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
    }

    /**
     * 从文件中读取数据
     *
     * @param fileName 文件名
     * @return 从文件中读取的数据
     */
    fun ChatUtilReadDataFromFile(fileName: String?): ByteArray? {
        var fileInputStream: FileInputStream? = null
        val bufferedReader: BufferedReader? = null
        val stringBuilder = StringBuilder()
        var buffer: ByteArray? = null

        /**
         * 注意这里的fileName不要用绝对路径，只需要文件名就可以了，系统会自动到data目录下去加载这个文件
         */
        val file = File(fileName)
        try {
            fileInputStream = FileInputStream(file)
            buffer = ByteArray(file.length().toInt())
            try {
                fileInputStream.read(buffer)
            } catch (e: IOException) {
                e.printStackTrace()
            }
            fileInputStream.close()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        //fileInputStream = context.openFileInput(fileName);
        return buffer
    }

    fun ChatUtilGetDate(): String? {
        val formatter = SimpleDateFormat("yyyyMMddHHmmss")
        val curDate = Date(System.currentTimeMillis()) //获取当前时间
        val str = formatter.format(curDate)
        Log.i("LOG_TAG", "ChatUtilGetDate=$str")
        return str
    }

    fun ChatUtilGetFileSize(fileName: String?): Long {
        var fileSize: Long = 0
        val file = File(fileName)
        fileSize = file?.length() ?: -1
        Log.i("LOG_TAG", "ChatUtilGetFileSize,fileSize=$fileSize")
        return fileSize
    }

    fun ChatUtilSaveDataToFileAtEnd(
        data: ByteArray,
        fileName: String?
    ) {
        var fileOutputStream: FileOutputStream? = null
        val bufferedWriter: BufferedWriter? = null
        val file = File(fileName)
        Log.i("LOG_TAG", "ChatUtilSaveDataToFileAtEnd,data size=" + data.size)
        Log.i("LOG_TAG", "ChatUtilSaveDataToFileAtEnd,file size=" + file.length())
        try {
            fileOutputStream = FileOutputStream(file, true)
            try {
                fileOutputStream.write(data)
                Log.i("LOG_TAG", "ChatUtilSaveDataToFileAtEnd,write ok")
                fileOutputStream.close()
            } catch (e: IOException) {
                Log.i("LOG_TAG", "ChatUtilSaveDataToFileAtEnd,write fail")
                e.printStackTrace()
            }
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
    }

    fun getRandomInt(min: Int, max: Int): Int { //int
        //int min=1;
        //int max=100;
        val random = Random()
        return random.nextInt(max) % (max - min + 1) + min
    }

    var TwoInteger = DecimalFormat("00") //这个类用于规范显示时间，选择性使用

    fun getDisplayTime(): String? {
        val date = Date()
        val dateFormat = SimpleDateFormat("yyyyMMddHHmmss")
        val sim = dateFormat.format(date)
        val timeYear = sim.substring(0, 4).toInt()
        val timeMonth = sim.substring(4, 6).toInt()
        val timeDay = sim.substring(6, 8).toInt()
        val timeHour = sim.substring(8, 10).toInt()
        val timeMinute = sim.substring(10, 12).toInt()
        return "$timeHour:$timeMinute"
    }

    fun UtilsIsServiceRunning(
        context: Context,
        serviceName: String
    ): Boolean {
        var is_run = false
        val am =
            context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val info =
            am.getRunningServices(100)
        KotlinUtils.log(
            LOG_TAG,
            "UtilsIsServiceRunning,serviceName=$serviceName"
        )
        for (runningServiceInfo in info) {
            val name = runningServiceInfo.service.className
            KotlinUtils.log(
                LOG_TAG,
                "UtilsIsServiceRunning,name=$name"
            )
            if (name == serviceName) {
                is_run = true
                break
            }
        }
        KotlinUtils.log(
            LOG_TAG,
            "UtilsIsServiceRunning,is_run=$is_run"
        )
        return is_run
    }

    fun viewSaveToImage(view: View) {
        view.isDrawingCacheEnabled = true
        view.drawingCacheQuality = View.DRAWING_CACHE_QUALITY_HIGH
        view.drawingCacheBackgroundColor = Color.WHITE

        // 把一个View转换成图片
        val cachebmp = loadBitmapFromView(view)
        val fos: FileOutputStream
        var imagePath = ""
        try {
            // 判断手机设备是否有SD卡
            val isHasSDCard = Environment.getExternalStorageState() ==
                    Environment.MEDIA_MOUNTED
            if (isHasSDCard) {
                // SD卡根目录
                val sdRoot = Environment.getExternalStorageDirectory()
                val file = File(
                    sdRoot,
                    Calendar.getInstance().timeInMillis.toString() + ".png"
                )
                fos = FileOutputStream(file)
                imagePath = file.absolutePath
            } else throw Exception("创建文件失败!")
            cachebmp.compress(Bitmap.CompressFormat.PNG, 90, fos)
            fos.flush()
            fos.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        KotlinUtils.log("imagePath=$imagePath")
        view.destroyDrawingCache()
    }

    private fun loadBitmapFromView(v: View): Bitmap {
        val w = v.width
        val h = v.height
        val bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        val c = Canvas(bmp)
        c.drawColor(Color.WHITE)
        /** 如果不设置canvas画布为白色，则生成透明  */
        v.layout(0, 0, w, h)
        v.draw(c)
        return bmp
    }

    fun shotScrollView(scrollView: ScrollView) {
        var h = 0
        val bitmap: Bitmap? = null
        var audio_path: String? = null
        audio_path = if (Build.VERSION.SDK_INT >= 29) {
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                .absolutePath
        } else {
            Environment.getExternalStorageDirectory().absolutePath
        }
        for (i in 0 until scrollView.childCount) {
            h += scrollView.getChildAt(i).height
            scrollView.getChildAt(i)
                .setBackgroundColor(Color.parseColor("#ffffff"))
        }
        val cachebmp = Bitmap.createBitmap(scrollView.width, h, Bitmap.Config.RGB_565)
        val canvas = Canvas(cachebmp)
        scrollView.draw(canvas)
        val fos: FileOutputStream
        var imagePath = ""
        try {
            // 判断手机设备是否有SD卡
            // SD卡根目录
            val sdRoot = File(audio_path)
            val file = File(
                sdRoot,
                Calendar.getInstance().timeInMillis.toString() + ".png"
            )
            fos = FileOutputStream(file)
            imagePath = file.absolutePath
            cachebmp.compress(Bitmap.CompressFormat.PNG, 90, fos)
            fos.flush()
            fos.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        KotlinUtils.log("imagePath=$imagePath")
    }
}