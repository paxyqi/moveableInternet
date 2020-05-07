package com.bytedance.videoplayer

import android.app.Activity
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
object Utils {
    private const val REQUEST_EXTERNAL_STORAGE = 1

    private val PERMISSIONS_STORAGE = arrayOf(
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE")
    fun verifyStoragePermissions(activity: Activity?) {
        try {
            val permission = ActivityCompat.checkSelfPermission(activity!!,
                    "android.permission.READ_EXTERNAL_STORAGE")
            if (permission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}