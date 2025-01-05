package com.xiaoxiao.jackutils.utils

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

class SoftCmd {
    companion object {
        var sInstance:SoftCmd?=null
        var tag ="SoftCmd"
        fun getInstance(): SoftCmd? {
            if (sInstance == null) {
                synchronized(SoftCmd::class.java) {
                    if (sInstance == null) {
                        sInstance = SoftCmd()
                    }
                }
            }
            return sInstance
        }
    }

    fun startCmdExc(cmd:String):String{
        /*
        var stringBuf=StringBuilder()
        SoftUtils.log(tag,"startCmdExc,cmd=$cmd")
        try{
            var probu=ProcessBuilder(cmd)
            var process=probu.start()//Runtime.getRuntime().exec(cmd)
            var inputString=InputStreamReader(process.inputStream)
            var reader=BufferedReader(inputString)
            var line: String?
            while (reader.readLine().also {
                    SoftUtils.log(tag,"startCmdExc,it=$it")
                    line = it } != null) {
                stringBuf.append(line).append("\n")
            }
            var rcode=process.waitFor()
            SoftUtils.log(tag,"startCmdExc,rcode=$rcode")
        }catch (e:Exception){
            SoftUtils.log(tag,"startCmdExc,Exception=$e")
        }
        return stringBuf.toString()*/
        try {
            // 构建命令
            val command = "ls" // 这里以列出当前目录为例
            // 执行命令
            val process = Runtime.getRuntime().exec(command)

            // 读取命令的输出
            val reader = BufferedReader(InputStreamReader(process.inputStream))
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                SoftUtils.log(tag,"startCmdExc,line=$line")
            }

            // 等待命令执行完成
            val exitCode = process.waitFor()
            println("Exited with code: $exitCode")
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        return "123"
    }

}