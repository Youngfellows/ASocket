package com.king.asocket.app.callback

import java.io.File

/**
 * kotlin回调
 *
 */
interface SimpleCallback {
    fun onSuccess(response: File)
    fun onFailed(errorCode: Int, msg: String)
}