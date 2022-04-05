package com.tenessine.intobroadcastreceiver.services

import android.app.IntentService
import android.content.Intent
import android.content.Context
import android.util.Log
import androidx.core.app.JobIntentService
import com.tenessine.intobroadcastreceiver.MainActivity


class DownloadService : JobIntentService() {

  override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
    if(intent != null) {
      enqueueWork(this, this::class.java, JOB_ID, intent)
    }

    return super.onStartCommand(intent, flags, startId)
  }

  override fun onHandleWork(intent: Intent) {
    Log.d(TAG, "Download dijalankan...")
    try {
      Thread.sleep(5000)
    } catch (e: InterruptedException) {
      e.printStackTrace()
    }

    val notifyFinishIntent = Intent(MainActivity.ACTION_DOWNLOAD_STATUS)
    sendBroadcast(notifyFinishIntent)
  }

  companion object {
    const val JOB_ID = 101
    val TAG: String = DownloadService::class.java.simpleName
  }
}