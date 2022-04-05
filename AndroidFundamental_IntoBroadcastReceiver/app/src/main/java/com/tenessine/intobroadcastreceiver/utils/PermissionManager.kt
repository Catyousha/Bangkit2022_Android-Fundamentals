package com.tenessine.intobroadcastreceiver.utils

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat

object PermissionManager {
  // untuk mengenable permission
  fun check(activity: AppCompatActivity, permission: String, requestCode: Int) {
    // cek dulu udah pernah di acc atau belum, kalau udah langsung lewat
    if(ActivityCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED) return

    // acc permission yang diminta
    ActivityCompat.requestPermissions(activity, arrayOf(permission), requestCode)
  }
}