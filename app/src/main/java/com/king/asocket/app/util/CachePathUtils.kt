package com.king.asocket.app.util

import android.content.Context
import android.os.Environment
import android.text.TextUtils
import android.util.Log
import java.io.File


object CachePathUtils {

    private val TAG: String = this.javaClass.simpleName

    fun getCacheDirectory(context: Context, type: String): File? {
        var appCacheDir = getExternalCacheDirectory(context, type)
        if (appCacheDir == null) {
            appCacheDir = getInternalCacheDirectory(context, type)
        }
        if (appCacheDir == null) {
            Log.e(TAG, "getCacheDirectory fail ,the reason is mobile phone unknown exception !")
        } else {
            if (!appCacheDir.exists() && !appCacheDir.mkdirs()) {
                Log.e(TAG, "getCacheDirectory fail ,the reason is make directory fail !")
            }
        }
        return appCacheDir
    }

    fun getExternalCacheDirectory(context: Context, type: String): File? {
        var appCacheDir: File? = null
        if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()) {
            appCacheDir = if (TextUtils.isEmpty(type)) {
                context.externalCacheDir
            } else {
                context.getExternalFilesDir(type)
            }
            if (appCacheDir == null) {
                appCacheDir = File(
                    Environment.getExternalStorageDirectory(),
                    "Android" + File.separator + "data" + File.separator + context.packageName + File.separator + "cache" + File.separator + type
                )
            }
            if (appCacheDir == null) {
                Log.e(TAG, "getExternalDirectory fail ,the reason is sdCard unknown exception !")
            } else {
                if (!appCacheDir.exists() && !appCacheDir.mkdirs()) {
                    Log.e(TAG, "getExternalDirectory fail ,the reason is make directory fail !")
                }
            }
        } else {
            Log.e(
                TAG,
                "getExternalDirectory fail ,the reason is sdCard nonexistence or sdCard mount fail !"
            )
        }
        return appCacheDir
    }


    fun getInternalCacheDirectory(context: Context, type: String?): File? {
        var appCacheDir: File? = null
        appCacheDir = if (TextUtils.isEmpty(type)) {
            context.cacheDir // /data/data/app_package_name/cache
        } else {
            File(context.filesDir, type) // /data/data/app_package_name/files/type
        }
        if (!appCacheDir!!.exists() && !appCacheDir.mkdirs()) {
            Log.e(TAG, "getInternalDirectory fail ,the reason is make directory fail !")
        }
        return appCacheDir
    }

    fun getExternalDirectory(context: Context, type: String): File? {
        var appCacheDir: File? = null
        if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()) {
            appCacheDir = if (TextUtils.isEmpty(type)) {
                File(Environment.getExternalStorageDirectory(), context.packageName)
            } else {
                File(
                    Environment.getExternalStorageDirectory(),
                    context.packageName + File.separator + type
                )
            }
            if (appCacheDir == null) {
                Log.e(
                    TAG,
                    "getExternalDirectory fail ,the reason is sdCard unknown exception !"
                )
            } else {
                if (!appCacheDir.exists() && !appCacheDir.mkdirs()) {
                    Log.e(
                        TAG,
                        "getExternalDirectory fail ,the reason is make directory fail !"
                    )
                }
            }
        } else {
            Log.e(
                TAG,
                "getExternalDirectory fail ,the reason is sdCard nonexistence or sdCard mount fail !"
            )
        }
        return appCacheDir
    }
}