package com.king.asocket.app.util

import android.content.Context
import android.content.res.AssetManager
import android.util.Log
import java.io.IOException
import java.io.InputStream


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
}