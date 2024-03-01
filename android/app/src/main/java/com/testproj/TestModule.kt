package com.testproj

import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.facebook.react.bridge.NativeModule
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.facebook.react.bridge.Callback

data class MockUsageStats(val packageName: String, val lastTimeUsed: Long)

fun getLastTimeUsed(usageStatsList: List<MockUsageStats>, packageName: String): Long? {
    var lastTimeUsed: Long? = null
    for (usageStats in usageStatsList) {
        if (usageStats.packageName == packageName) {
            lastTimeUsed = usageStats.lastTimeUsed
        }
    }
    return lastTimeUsed
}

class TestModule(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP_MR1)
    private val usageStatsManager = reactContext.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager

    override fun getName(): String {
        return "TestModule"
    }

    @ReactMethod
    fun testFunctionSample(callback: Callback) {
        callback.invoke("TESTING STUFF");
    };

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP_MR1)
    @ReactMethod
    fun testFunction(callback: Callback) {
        val endTime = System.currentTimeMillis()
        val startTime = endTime - (24 * 60 * 60 * 1000) // 24 hours ago

        val packageName = "com.google.android.youtube" //hardcoded só youtube
        val lastTimeUsed = getLastTimeUsed(packageName, startTime, endTime)

        if (lastTimeUsed != null) {
            val tempo = Math.toIntExact(lastTimeUsed)
            val message = "Last time used of $packageName: $tempo"
            Log.d("TestModule", message)
            callback.invoke(message)
        } else {
            val message = "$packageName was not used during the specified time range."
            Log.d("TestModule", message)
            callback.invoke(message)
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP_MR1)
    private fun getLastTimeUsed(packageName: String, startTime: Long, endTime: Long): Long? {
        val usageStatsList = usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, startTime, endTime)
        for (usageStats in usageStatsList) {
            if (usageStats.packageName == packageName) {
                return usageStats.lastTimeUsed
            }
        }
        return null
    }
}