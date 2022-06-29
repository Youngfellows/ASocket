package com.king.asocket.app.tcp

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.king.asocket.ASocket
import com.king.asocket.ISocket
import com.king.asocket.app.R
import com.king.asocket.app.databinding.ActivityTcpClientBinding
import com.king.asocket.app.util.AssetsReader
import com.king.asocket.tcp.TCPClient
import com.king.asocket.util.LogUtils
import java.lang.Exception

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
class TCPClientActivity : AppCompatActivity() {

    companion object {
        private val TAG: String = TCPClientActivity::class.java.simpleName
    }

    val binding by lazy {
        ActivityTcpClientBinding.inflate(layoutInflater)
    }

    var mHost = "192.168.2.36"

    var mPort = 2014

    var aSocket: ASocket? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        title = "TCP Client"
        binding.etHost.setText(mHost)
        binding.etPort.setText(mPort.toString())

    }

    override fun onDestroy() {
        aSocket?.close()
        super.onDestroy()
    }

    private fun getContext() = this

    private fun clickStart() {
        mHost = binding.etHost.text.toString()
        mPort = binding.etPort.text.toString().toInt()
        val client = TCPClient(mHost, mPort)
        aSocket = ASocket(client)
        aSocket?.let {
            it.setOnSocketStateListener(object : ISocket.OnSocketStateListener {
                override fun onStarted() {
                    binding.btnStart.isEnabled = false
                    binding.etHost.isEnabled = false
                    binding.etPort.isEnabled = false
                    binding.progressBar.isVisible = false
                    binding.btnStart.text = "已连接"
                }

                override fun onClosed() {

                }

                override fun onException(e: Exception) {

                    if (!it.isConnected) {//连接失败
                        LogUtils.d("连接失败")
                        Toast.makeText(getContext(), "连接失败", Toast.LENGTH_SHORT).show()
                        binding.btnStart.isEnabled = true
                        binding.etHost.isEnabled = true
                        binding.etPort.isEnabled = true
                        binding.progressBar.isVisible = false
                        binding.btnStart.text = "连接"
                    }
                }

            })
            it.setOnMessageReceivedListener { data ->
                binding.tvContent.append("接收：${String(data)}\n")
            }
            binding.progressBar.isVisible = true
            it.start()

        }
    }

    private fun clickSend() {
        if (!TextUtils.isEmpty(binding.etContent.text)) {
            aSocket?.let {
                val data = binding.etContent.text.toString()
                it.write(data.toByteArray())
                if (it.isStart) {
                    binding.tvContent.append("发送：${data}\n")
                }
                binding.etContent.setText("")
            }
        }
    }

    private fun clickClear() {
        binding.tvContent.text = ""
    }

    fun onClick(v: View) {
        when (v.id) {
            R.id.btnStart -> clickStart()
            R.id.btnSend -> clickSend()
            R.id.btnClear -> clickClear()
            R.id.btnBinary -> readAudio2Send()
        }
    }

    private fun readAudio2Send() {
        Log.d(TAG, "readAudio2Send:: ....")
        Thread(Runnable {
            Log.d(TAG, "readAudio2Send:: start ....")
            AssetsReader.readAssets("src.pcm", this) { data ->
                Log.d(TAG, "readAudio2Send:: $data")
                aSocket?.let {
                    it.write(data)
                    Log.d(TAG, "readAudio2Send:: send pcm: $data")
                }
            }
        }).start()

    }
}