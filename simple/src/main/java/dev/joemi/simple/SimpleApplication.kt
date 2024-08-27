package dev.joemi.simple

import android.app.Application
import com.blankj.utilcode.util.LogUtils

class SimpleApplication : Application() {

    companion object {
        const val TAG = "SimpleApp"
    }

    override fun onCreate() {
        super.onCreate()

        LogUtils.getConfig().apply {
            globalTag = TAG
        }
    }
}