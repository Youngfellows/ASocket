package com.king.asocket.app.callback

import java.io.File


/**
 * 定义个Builder,简化回调接口
 *
 */
class Builder : SimpleCallback {

    private lateinit var onSuccessVal: (response: File) -> Unit  //定义一个函数变量
    private lateinit var onFailedVal: (errorCode: Int, msg: String) -> Unit  //定义一个函数变量

    /**
     * 给函数变量赋值
     *
     * @param listener
     */
    fun onSuccessFun(listener: (response: File) -> Unit) {
        this.onSuccessVal = listener;
    }

    /**
     * 给函数变量赋值
     *
     * @param listener
     */
    fun onFailedFun(listener: (errorCode: Int, msg: String) -> Unit) {
        this.onFailedVal = listener;
    }

    /**
     * 成功回调方法
     *
     * @param response
     */
    override fun onSuccess(response: File) {
        onSuccessVal.invoke(response)
    }

    /**
     * 失败回调方法
     *
     * @param errorCode
     * @param msg
     */
    override fun onFailed(errorCode: Int, msg: String) {
        onFailedVal.invoke(errorCode, msg)
    }
}