package com.tenessine.intoservice.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import kotlinx.coroutines.*

class MyService : Service() {

  private val serviceJob = Job()
  private val serviceScope = CoroutineScope(Dispatchers.Main + serviceJob)

  override fun onBind(intent: Intent): IBinder {
    throw UnsupportedOperationException("Not yet implemented")
  }

  override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
    Log.d(TAG, "Service dijalankan...")

    // perlu dibuat background thread agar bisa berjalan di belakang layar
    serviceScope.launch{
      delay(5000)
      stopSelf()
      Log.d(TAG, "Service dihentikan...")
    }

    // START_STICKY: Service akan dijalankan lagi jika ada memori yang mencukupi
    // nempel mulu dia
    return START_STICKY
  }

  override fun onDestroy() {
    super.onDestroy()
    serviceJob.cancel()
    Log.d(TAG, "Service dihancurkan")
  }

  companion object {
    internal val TAG = MyService::class.java.simpleName
  }
}