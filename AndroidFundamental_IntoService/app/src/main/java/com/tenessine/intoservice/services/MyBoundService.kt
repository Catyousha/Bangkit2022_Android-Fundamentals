package com.tenessine.intoservice.services

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.CountDownTimer
import android.os.IBinder
import android.util.Log

class MyBoundService : Service() {

  private var mBinder = MyBinder()
  private val startTime = System.currentTimeMillis()

  // yang digunakan komponen untuk mengakses service dalam bentuk getter
  internal inner class MyBinder: Binder() {
    val getService: MyBoundService = this@MyBoundService
  }

  // memulai service
  override fun onBind(intent: Intent): IBinder {
    Log.d(TAG, "onBind: ")
    mTimer.start()
    return mBinder
  }

  // untuk memberhentikan service
  override fun onUnbind(intent: Intent?): Boolean {
    Log.d(TAG, "onUnbind: ")
    mTimer.cancel()
    return super.onUnbind(intent)
  }

  private var mTimer: CountDownTimer = object : CountDownTimer(100000, 1000) {
    override fun onTick(l: Long) {
      val elapsedTime = System.currentTimeMillis() - startTime
      Log.d(TAG, "onTick: $elapsedTime")
    }

    override fun onFinish() {
      Log.d(TAG, "onFinish: ")
    }
  }


  companion object {
    private val TAG = MyBoundService::class.java.simpleName
  }
}