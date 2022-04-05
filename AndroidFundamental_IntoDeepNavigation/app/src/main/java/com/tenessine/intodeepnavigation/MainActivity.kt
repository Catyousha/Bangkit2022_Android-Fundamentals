package com.tenessine.intodeepnavigation

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.tenessine.intodeepnavigation.databinding.ActivityMainBinding
import java.nio.channels.Channel

class MainActivity : AppCompatActivity() {
  private var _binding: ActivityMainBinding? = null
  private val binding get() = _binding!!

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    _binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    binding.btnOpenDetail.setOnClickListener {
      val detailIntent = Intent(this@MainActivity, DetailActivity::class.java)
      detailIntent.apply {
        putExtra(DetailActivity.EXTRA_TITLE, getString(R.string.detail_title))
        putExtra(DetailActivity.EXTRA_MESSAGE, getString(R.string.detail_message))
      }
      startActivity(detailIntent)
    }

    showNotification(
      this@MainActivity,
      getString(R.string.notification_title),
      getString(R.string.notification_message),
      NOTIFICATION_ID
    )
  }

  private fun showNotification(
    context: Context,
    title: String,
    message: String,
    notifId: Int
  ) {
    val CHANNEL_ID = "channel_42"
    val CHANNEL_NAME = "meaning_of_life"

    val notifDetailIntent = Intent(this@MainActivity, DetailActivity::class.java)
    notifDetailIntent.apply {
      putExtra(DetailActivity.EXTRA_TITLE, title)
      putExtra(DetailActivity.EXTRA_MESSAGE, message)
    }

    val pendingIntent = TaskStackBuilder.create(this)
      .addParentStack(DetailActivity::class.java)
      .addNextIntent(notifDetailIntent)
      .getPendingIntent(NOTIFICATION_ID, PendingIntent.FLAG_UPDATE_CURRENT)

    val notificationManagerCompat =
      context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

    val builder = NotificationCompat.Builder(context, CHANNEL_ID)
    builder.apply {
      setContentTitle(title)
      setSmallIcon(R.drawable.ic_baseline_email_24)
      setContentText(message)
      color = ContextCompat.getColor(context, android.R.color.black)
      setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
      setSound(alarmSound)
      setContentIntent(pendingIntent)
      setAutoCancel(true)
    }

    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      val channel = NotificationChannel(
        CHANNEL_ID,
        CHANNEL_NAME,
        NotificationManager.IMPORTANCE_DEFAULT
      )
      channel.apply {
        enableVibration(true)
        vibrationPattern = longArrayOf(1000, 1000, 1000, 1000, 1000)
      }
      builder.setChannelId(CHANNEL_ID)
      notificationManagerCompat.createNotificationChannel(channel)
    }

    val notification = builder.build()
    notificationManagerCompat.notify(notifId, notification)

  }

  override fun onDestroy() {
    super.onDestroy()
    _binding = null
  }

  companion object {
    private const val NOTIFICATION_ID = 110
  }
}