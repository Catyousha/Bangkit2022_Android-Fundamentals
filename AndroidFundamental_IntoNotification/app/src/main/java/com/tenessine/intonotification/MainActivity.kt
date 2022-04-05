package com.tenessine.intonotification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.app.NotificationCompat
import com.tenessine.intonotification.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
  private var _binding: ActivityMainBinding? = null
  private val binding get() = _binding!!

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    _binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    binding.btnNotif.setOnClickListener {
      val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com"))
      val pendingIntentFlag = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE else 0
      val pendingIntent = PendingIntent.getActivity(
        this,
        0,
        intent,
        pendingIntentFlag
      )

      // manager untuk mengirimkan notifikasi
      val mNotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
      // membentuk isian notifikasi tunggal
      val mBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
        .setContentIntent(pendingIntent)
        .setSmallIcon(R.drawable.ic_baseline_circle_notifications_24)
        .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.ic_baseline_circle_notifications_24))
        .setContentTitle("Anjay Mabar")
        .setContentText("Halo anjay.")
        .setSubText("Subtitle")
        .setAutoCancel(true)

      // kompatibilitas pada versi Oreo keatas
      if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        // harus dibikin channel grup untuk notifikasi yang ada
        val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)
        channel.description = CHANNEL_NAME

        mBuilder.setChannelId(CHANNEL_ID)
        mNotificationManager.createNotificationChannel(channel)
      }

      // notifikasi tunggal tadi dibangun
      val notification = mBuilder.build()

      // notifikasi dikirimkan dengan ID sekian
      mNotificationManager.notify(NOTIFICATION_ID, notification)

    }
  }

  override fun onDestroy() {
    super.onDestroy()
    _binding = null
  }

  companion object {
    private const val NOTIFICATION_ID = 1
    private const val CHANNEL_ID = "channel_42"
    private const val CHANNEL_NAME = "awanama channel"
  }
}