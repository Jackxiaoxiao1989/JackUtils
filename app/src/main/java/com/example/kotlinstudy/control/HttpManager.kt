package com.example.kotlinstudy.control

import com.example.kotlinstudy.info.GetDataInfo
import com.example.kotlinstudy.info.PostDataInfo
import com.example.kotlinstudy.utils.KotlinUtils
import okhttp3.*
import java.io.*
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.ProtocolException
import java.net.URL

/*
全植入：http://http://81.69.170.85:9000/qzr/uploadData
        半植入：http://http://81.69.170.85:9000/bzr/uploadData
        自研手环：http://http://81.69.170.85:9000/zysh/uploadData
        定位：http://http://81.69.170.85:9000/dw/uploadData
        录音：http://http://81.69.170.85:9000/ly/uploadData
*/

/*
全植入：http://http://81.69.170.85:9000/qzr/uploadData
        半植入：http://http://81.69.170.85:9000/bzr/uploadData
        自研手环：http://http://81.69.170.85:9000/zysh/uploadData
        定位：http://http://81.69.170.85:9000/dw/uploadData
        录音：http://http://81.69.170.85:9000/ly/uploadData
*/
object HttpManager {
    var tag = "HttpManager"
    fun UploadDataToServerByPost(postUrl: String, data: String) {
        KotlinUtils.log(tag, "UploadDataToServerByPost")
        //获得的数据
        var resultData = ""
        var url: URL? = null
        try {
            //构造一个URL对象
            url = URL(postUrl)
        } catch (e: MalformedURLException) {
            KotlinUtils.log(tag, "MalformedURLException")
        }
        if (url != null) {
            try {
                // 使用HttpURLConnection打开连接
                val urlConn =
                    url.openConnection() as HttpURLConnection

                //因为这个是post请求,设立需要设置为true
                urlConn.doOutput = true
                urlConn.doInput = true
                // 设置以POST方式
                urlConn.requestMethod = "POST"
                // Post 请求不能使用缓存
                urlConn.useCaches = false
                urlConn.instanceFollowRedirects = true
                // 配置本次连接的Content-type，配置为application/x-www-form-urlencoded的
                urlConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded")
                // 连接，从postUrl.openConnection()至此的配置必须要在connect之前完成，
                // 要注意的是connection.getOutputStream会隐含的进行connect。
                urlConn.connect()
                //DataOutputStream流
                val out =
                    DataOutputStream(urlConn.outputStream)
                //要上传的参数
                //将要上传的内容写入流中
                out.writeBytes(data)
                //刷新、关闭
                out.flush()
                out.close()

                //得到读取的内容(流)
                val `in` =
                    InputStreamReader(urlConn.inputStream)
                // 为输出创建BufferedReader
                val buffer = BufferedReader(`in`)
                var inputLine: String? = null
                //使用循环来读取获得的数据
                while (buffer.readLine().also { inputLine = it } != null) {
                    //我们在每一行后面加上一个"\n"来换行
                    KotlinUtils.log(tag, "UploadDataToServerByPost,inputLine=$inputLine")
                    resultData += """
                        $inputLine
                        
                        """.trimIndent()
                }
                //关闭InputStreamReader
                `in`.close()
            } catch (e: UnsupportedEncodingException) {
                KotlinUtils.log(tag, "testUrlHttpPost,UnsupportedEncodingException")
                e.printStackTrace()
            } catch (e: ProtocolException) {
                KotlinUtils.log(tag, "testUrlHttpPost,ProtocolException")
                e.printStackTrace()
            } catch (e: IOException) {
                KotlinUtils.log(tag, "testUrlHttpPost,IOException")
                e.printStackTrace()
            }
        }
    }

    fun postDataToServer() {
        KotlinUtils.log(tag, "postDataToServer")
        try {
            val client = OkHttpClient()
            val formBody = MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("user_id", "1")
                .addFormDataPart("device_id", "2")
                .build()
            val serverUrl = "http://81.69.170.85:9000/dw/uploadData"
            /* Request request = new Request.Builder()
                    .url("https://api.github.com/markdown/raw")
                    .post(RequestBody.create(mediaType, requestBody))
                    .build();*/
            val request = Request.Builder()
                .url(serverUrl)
                .post(formBody)
                .build()
            val call = client.newCall(request)
            val response = call.execute()

            // 可在此处判断上传结果
            KotlinUtils.log(tag, "Http post res " + response.body().toString())
        } catch (e: Exception) {
            KotlinUtils.log(tag, "Http post err $e")
        }
    }

    fun postString(urlPath: String, postData: String) {
        val mediaType = MediaType.parse("JSON")
        KotlinUtils.log(tag, "postString, urlPath=$urlPath")
        KotlinUtils.log(tag, "postString, requestBody=$postData")
        val request = Request.Builder()
            .url(urlPath)
            .post(RequestBody.create(mediaType, postData))
            .build()
        val okHttpClient = OkHttpClient()
        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                KotlinUtils.log(tag, "onFailure: " + e.message)
            }

            @Throws(IOException::class)
            override fun onResponse(
                call: Call,
                response: Response
            ) {
                KotlinUtils.log(
                    tag,
                    response.protocol()
                        .toString() + " " + response.code() + " " + response.message()
                )
                val headers = response.headers()
                for (i in 0 until headers.size()) {
                    KotlinUtils.log(tag, headers.name(i) + ":" + headers.value(i))
                }
                KotlinUtils.log(tag, "onResponse: " + response.body()!!.string())
            }
        })
    }

    fun postMyString(urlPath: String?, postData: String?) {
        //创建一个OkHttpClient对象
        val okHttpClient = OkHttpClient()
        //创建一个RequestBody(参数1：数据类型 参数2传递的json串)
        val requestBody =
            RequestBody.create(MediaType.parse("application/json; charset=utf-8"), postData)
        //创建一个请求对象
        val request = Request.Builder()
            .url(urlPath) //.addHeader("authorization", "")
            .post(requestBody)
            .build()
        //发送请求获取响应
        okHttpClient.newCall(request).enqueue(object : Callback {
            @Throws(IOException::class)
            override fun onResponse(
                arg0: Call,
                response: Response
            ) {
                KotlinUtils.log(tag, "onResponse: " + response.body()!!.string())
            }

            override fun onFailure(
                arg0: Call,
                arg1: IOException
            ) {
                KotlinUtils.log(tag, "onResponse: fail")
            }
        })
    }

    fun httpUrlConnection(urlPath: String, postData: String) {
        try {
            KotlinUtils.log(tag, "pathUrl=$urlPath")
            //建立连接
            val url = URL(urlPath)
            val httpConn =
                url.openConnection() as HttpURLConnection

            //设置连接属性
            httpConn.doOutput = true //使用 URL 连接进行输出
            httpConn.doInput = true //使用 URL 连接进行输入
            httpConn.useCaches = false //忽略缓存
            httpConn.requestMethod = "POST" //设置URL请求方法

            //设置请求属性
            //获得数据字节数据，请求数据流的编码，必须和下面服务器端处理请求流的编码一致
            val requestStringBytes = postData.toByteArray()
            httpConn.setRequestProperty("Content-length", "" + requestStringBytes.size)
            httpConn.setRequestProperty("Content-Type", "application/octet-stream")
            httpConn.setRequestProperty("Connection", "Keep-Alive") // 维持长连接
            httpConn.setRequestProperty("Charset", "UTF-8")
            //
            //String name=URLEncoder.encode("黄武艺","utf-8");
            //httpConn.setRequestProperty("NAME", name);

            //建立输出流，并写入数据
            val outputStream = httpConn.outputStream
            outputStream.write(requestStringBytes)
            outputStream.close()
            //获得响应状态
            val responseCode = httpConn.responseCode
            KotlinUtils.log(tag, "responseCode=$responseCode")
            if (HttpURLConnection.HTTP_OK == responseCode) { //连接成功

                //当正确响应时处理数据
                val sb = StringBuffer()
                var readLine: String?
                val responseReader: BufferedReader
                //处理响应流，必须与服务器响应流输出的编码一致
                responseReader =
                    BufferedReader(InputStreamReader(httpConn.inputStream))
                while (responseReader.readLine().also { readLine = it } != null) {
                    sb.append(readLine).append("\n")
                }
                responseReader.close()
                KotlinUtils.log(tag, "response=$sb")
                //tv.setText(sb.toString());
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    @Throws(IOException::class)
    fun uploadFile(url: String, file: File): String {
        val client = OkHttpClient()
        KotlinUtils.log(tag, "uploadFile,url=$url")
        val requestBody: RequestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart(
                "audiofile", file.name,
                RequestBody.create(MediaType.parse("multipart/form-data"), file)
            )
            .build()
        val request = Request.Builder()
            .url(url)
            .post(requestBody)
            .build()
        val response = client.newCall(request).execute()
        return if (!response.isSuccessful) {
            throw IOException("Unexpected code $response")
        } else {
            response.body()!!.string()
        }
    }

    @Throws(IOException::class)
    fun uploadFile(url: String, file: File, info: PostDataInfo): String {
        val client = OkHttpClient()
        KotlinUtils.log(tag, "uploadFile,url=$url")
        KotlinUtils.log(tag, "uploadFile,uid=" + info.uid)
        KotlinUtils.log(tag, "uploadFile,tt=" + info.timesteamp)
        KotlinUtils.log(tag, "uploadFile,sign=" + info.sginkey)
        val requestBody: RequestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart(
                "file", file.name,
                RequestBody.create(MediaType.parse("multipart/form-data"), file)
            )
            .build()
        val request = Request.Builder()
            .url(url)
            .post(requestBody)
            .header("tt", info.timesteamp)
            .header("uid", info.uid)
            .header("sign", " ")
            .build()
        val response = client.newCall(request).execute()
        return if (!response.isSuccessful) {
            throw IOException("Unexpected code $response")
        } else {
            response.body()!!.string()
        }
    }

    /**
     * @param url          下载连接
     * @param destFileDir  下载的文件储存目录
     * @param destFileName 下载文件名称，后面记得拼接后缀，否则手机没法识别文件类型
     * @param listener     下载监听
     */
    fun downloadFile(
        url: String,
        info: GetDataInfo,
        destFileDir: String?,
        destFileName: String?,
        listener: OnDownloadListener
    ) {
        val request = Request.Builder()
            .url(url + "?report_id=" + info.report_id)
            .header("tt", info.timesteamp)
            .header("uid", info.uid)
            .header("sign", info.sginkey) //.header("report_id",info.report_id)
            .build()
        val client = OkHttpClient()
        try {
            val response = client.newCall(request).execute()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        //异步请求
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                // 下载失败监听回调
                listener.onDownloadFailed(e)
            }

            @Throws(IOException::class)
            override fun onResponse(
                call: Call,
                response: Response
            ) {
                var `is`: InputStream? = null
                val buf = ByteArray(2048)
                var len = 0
                var fos: FileOutputStream? = null

                //储存下载文件的目录
                val dir = File(destFileDir)
                if (!dir.exists()) {
                    dir.mkdirs()
                }
                val file = File(dir, destFileName)
                try {
                    `is` = response.body()!!.byteStream()
                    val total = response.body()!!.contentLength()
                    fos = FileOutputStream(file)
                    var sum: Long = 0
                    while (`is`.read(buf).also { len = it } != -1) {
                        fos.write(buf, 0, len)
                        sum += len.toLong()
                        val progress = (sum * 1.0f / total * 100).toInt()
                        //下载中更新进度条
                        listener.onDownloading(progress)
                    }
                    fos.flush()
                    //下载完成
                    listener.onDownloadSuccess(file)
                } catch (e: Exception) {
                    listener.onDownloadFailed(e)
                } finally {
                    try {
                        `is`?.close()
                        fos?.close()
                    } catch (e: IOException) {
                    }
                }
            }
        })
    }

    interface OnDownloadListener {
        /**
         * 下载成功之后的文件
         */
        fun onDownloadSuccess(file: File?)

        /**
         * 下载进度
         */
        fun onDownloading(progress: Int)

        /**
         * 下载异常信息
         */
        fun onDownloadFailed(e: Exception?)
    }
}