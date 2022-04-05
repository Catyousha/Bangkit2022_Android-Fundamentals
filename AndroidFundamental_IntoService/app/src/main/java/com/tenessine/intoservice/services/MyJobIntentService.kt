package com.tenessine.intoservice.services

import android.app.IntentService
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.JobIntentService


class MyJobIntentService : JobIntentService() {

  override fun onHandleWork(intent: Intent) {
    // proses berjalan di belakang layar tapi ga perlu bikin background thread
    Log.d(TAG, "onHandleWork: Mulai...")
    val duration = intent.getLongExtra(EXTRA_DURATION, 0)
    try {
      Thread.sleep(duration)
      Log.d(TAG, "onHandleWork: Selesai...")
    } catch (e: InterruptedException) {
      e.printStackTrace()
    }
  }

  override fun onDestroy() {
    super.onDestroy()
    Log.d(TAG, "onDestroy: Selesai...")
  }

  companion object {
    private const val JOB_ID = 1000
    internal const val EXTRA_DURATION = "EXTRA_DURATION"
    private val TAG = MyJobIntentService::class.java.simpleName

    fun enqueueWork(context: Context, intent: Intent) {
      // yang ngeeksekusi
      enqueueWork(context, MyJobIntentService::class.java, JOB_ID, intent)
    }
  }
}