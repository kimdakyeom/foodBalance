package com.dkkim.anew.Activity

import android.app.Application
import android.bluetooth.BluetoothSocket
import java.io.InputStream
import java.io.OutputStream

class MainApplication : Application() {

    init {
        instance = this
    }
    companion object {
        var mmInStream: InputStream? = null
        var mmOutStream: OutputStream? = null
        var m_bluetoothSocket: BluetoothSocket? = null
        lateinit var instance: MainApplication
    }

    override fun onCreate() {
        super.onCreate()
    }
}