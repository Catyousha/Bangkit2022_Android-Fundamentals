package com.tenessine.intobroadcastreceiver.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.tenessine.intobroadcastreceiver.services.DownloadService

class DownloadReceiver : BroadcastReceiver() {

  // yang akan dilakukan ketika download telah selesai
  override fun onReceive(context: Context?, intent: Intent?) {
    Log.d(DownloadService.TAG, "Download Selesai...")
    Toast.makeText(context, "Download Selesai!", Toast.LENGTH_SHORT).show()
  }
}