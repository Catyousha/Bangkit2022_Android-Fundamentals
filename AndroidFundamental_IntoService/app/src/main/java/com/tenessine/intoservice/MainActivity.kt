package com.tenessine.intoservice

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import androidx.appcompat.app.AppCompatActivity
import com.tenessine.intoservice.databinding.ActivityMainBinding
import com.tenessine.intoservice.services.MyBoundService
import com.tenessine.intoservice.services.MyJobIntentService
import com.tenessine.intoservice.services.MyService

class MainActivity : AppCompatActivity() {
  private var _binding: ActivityMainBinding? = null
  private val binding get() = _binding!!

  private var mServiceBound = false
  private lateinit var mBoundService: MyBoundService
  private val mServiceConnection = object : ServiceConnection {

    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
      val myBinder = service as MyBoundService.MyBinder
      mBoundService = myBinder.getService
      mServiceBound = true
    }

    override fun onServiceDisconnected(name: ComponentName?) {
      mServiceBound = false
    }

  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    _binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)


    binding.apply {
      btnStartService.setOnClickListener {
        val mStartServiceIntent = Intent(this@MainActivity, MyService::class.java)

        // cara default buat jalanin service (vanilla)
        startService(mStartServiceIntent)
      }

      btnStartJobIntentService.setOnClickListener {
        val mStartIntentService = Intent(this@MainActivity, MyJobIntentService::class.java)
        mStartIntentService.putExtra(MyJobIntentService.EXTRA_DURATION, 5000)

        // eksekusi job langsung di belakang layar
        MyJobIntentService.enqueueWork(this@MainActivity, mStartIntentService)
      }

      btnStartBoundService.setOnClickListener {
        val mBoundServiceIntent = Intent(this@MainActivity, MyBoundService::class.java)
        bindService(mBoundServiceIntent, mServiceConnection, BIND_AUTO_CREATE)
      }

      btnStopBoundService.setOnClickListener {
        unbindService(mServiceConnection)
      }
    }
  }

  override fun onDestroy() {
    super.onDestroy()
    if(mServiceBound) {
      unbindService(mServiceConnection)
    }
    _binding = null
  }
}