package com.king.asocket.app.util

import android.content.Context
import android.content.res.AssetManager
import android.util.Log
import com.king.asocket.app.callback.Builder
import java.io.*


/**
 * @author  Administrator
 * @time    2022/6/29 16:32
 * @des
 *
 * @version      $
 * @updateAuthor $
 * @updateDate   $
 * @updateDes
 */
object AssetsReader {
    private val TAG: String = AssetsReader::class.java.simpleName

    /**
     * TODO 读取assets资源
     *
     * @param fileName 文件名
     * @param context 上下文
     * @param callback 回调二进制数据
     */
    fun readAssets(fileName: String, context: Context, callback: (data: ByteArray) -> Unit) {
        Log.d(TAG, "readAssets:: fileName:$fileName")
        try {
            val assetManager: AssetManager = context.assets //获取assets资源管理器
            val inputStream: InputStream = assetManager.open(fileName)//获取文件流
            val buffer: ByteArray = ByteArray(1204)//缓冲区
            var len = 0//读取到大小
            while (inputStream.read(buffer).also { len = it } != -1) {
                Log.d(TAG, "readAssets: ")
                callback?.invoke(buffer)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    /**
     * 拷贝assets 目录文件到指定路径
     *
     * @param context
     * @param fileName 原文件名
     * @param destFile 目标文件
     * @param listener 回调
     */
    fun copyAssets(
        context: Context,
        fileName: String,
        dest: String,
        listener: Builder.() -> Unit
    ) {
        val builder: Builder = Builder()
        builder.listener()//设置回调函数
        Log.d(TAG, "readAssets:: fileName:$fileName")
        var fos: FileOutputStream? = null
        var inputStream: InputStream? = null
        try {
            fos = FileOutputStream(dest)//输出流
            val assetManager: AssetManager = context.assets //获取assets资源管理器
            inputStream = assetManager.open(fileName)//获取文件流
            val buffer: ByteArray = ByteArray(1204)//缓冲区
            var len = 0//读取到大小
            while (inputStream?.read(buffer).also { len = it } != -1) {
                fos?.write(buffer, 0, len)//写文件
            }
            fos?.flush()
            fos?.close()
            inputStream?.close()
            builder.onSuccess(File(dest))
        } catch (e: IOException) {
            e.printStackTrace()
            try {
                fos?.close()
                inputStream?.close()
            } catch (e: Exception) {

            }
            builder.onFailed(400, e.message.toString())
        }
    }
}