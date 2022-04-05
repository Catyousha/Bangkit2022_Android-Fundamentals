package com.tenessine.intobroadcastreceiver

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.tenessine.intobroadcastreceiver.databinding.ActivityMainBinding
import com.tenessine.intobroadcastreceiver.receivers.DownloadReceiver
import com.tenessine.intobroadcastreceiver.services.DownloadService
import com.tenessine.intobroadcastreceiver.utils.PermissionManager

class MainActivity : AppCompatActivity() {
  private var _binding: ActivityMainBinding? = null
  private val binding get() = _binding!!

  private val downloadReceiver: BroadcastReceiver = DownloadReceiver()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    _binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    setUpDownloadReceiver()

    binding.apply {
      btnPermission.setOnClickListener {
        PermissionManager.check(
          this@MainActivity,
          Manifest.permission.RECEIVE_SMS,
          SMS_REQUEST_CODE
        )
      }

      btnDownload.setOnClickListener {
        val downloadServiceIntent = Intent(this@MainActivity, DownloadService::class.java)
        startService(downloadServiceIntent)
      }
    }

  }

  private fun setUpDownloadReceiver() {
    val downloadIntentReceiver = IntentFilter(ACTION_DOWNLOAD_STATUS)
    registerReceiver(downloadReceiver, downloadIntentReceiver)
  }

  // buat notify user doang kalau permission udah diijinkan
  override fun onRequestPermissionsResult(
    requestCode: Int,
    permissions: Array<out String>,
    grantResults: IntArray
  ) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    when (requestCode) {
      SMS_REQUEST_CODE -> {
        when {
          grantResults[0] == PackageManager.PERMISSION_GRANTED -> {
            // permission was granted, yay! Do the
            // SMS related task you need to do.
            Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show()
          }
          else -> {
            // permission denied, boo! Disable the
            // functionality that depends on this permission.
            Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
          }
        }
      }
    }
  }

  override fun onDestroy() {
    super.onDestroy()
    _binding = null
    unregisterReceiver(downloadReceiver)
  }

  companion object {
    private const val SMS_REQUEST_CODE = 101
    const val ACTION_DOWNLOAD_STATUS = "download_status"
  }
}