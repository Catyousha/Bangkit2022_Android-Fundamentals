package com.tenessine.intoalarmmanager.receivers

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.tenessine.intoalarmmanager.R
import java.text.SimpleDateFormat
import java.util.*

// yang melakukan aksi ketika alarm disetel dan alarm berjalan
class AlarmReceiver : BroadcastReceiver() {

  // menerima data ketika alarm sudah terpicu
  override fun onReceive(context: Context, intent: Intent) {

    val type = intent.getStringExtra(EXTRA_TYPE)
    val message = intent.getStringExtra(EXTRA_MESSAGE)

    val title = if (type.equals(TYPE_ONE_TIME, ignoreCase = true))
      TYPE_ONE_TIME
    else
      TYPE_REPEATING

    val notifId = if (type.equals(TYPE_ONE_TIME, ignoreCase = true))
      ID_ONETIME
    else
      ID_REPEATING

    // outputnya adalah menampilkan dalam notifikasi ini
    showAlarmNotification(context, title, message ?: "", notifId)
  }

  // yang dipanggil ketika ingin set alarm dalam activity
  @RequiresApi(Build.VERSION_CODES.M)
  fun setOneTimeAlarm(
    context: Context,
    type: String,
    date: String,
    time: String,
    message: String
  ) {
    if (isDateInvalid(date, DATE_FORMAT) || isDateInvalid(time, TIME_FORMAT)) return

    // membuat alarm manager
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    // memasukkan data ke dalam intent untuk diterima
    val intent = Intent(context, AlarmReceiver::class.java)
    intent.putExtra(EXTRA_MESSAGE, message)
    intent.putExtra(EXTRA_TYPE, type)

    // mengolah data dari inputan field menjadi bentuk date
    Log.v("ONE TIME", "$date $time")
    val dateArray = date.split("-").toTypedArray()
    val timeArray = time.split(":").toTypedArray()

    val calendar = Calendar.getInstance()
    calendar.apply {
      set(Calendar.YEAR, Integer.parseInt(dateArray[0]))
      set(Calendar.MONTH, Integer.parseInt(dateArray[1]) - 1)
      set(Calendar.DAY_OF_MONTH, Integer.parseInt(dateArray[2]))
      set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]))
      set(Calendar.MINUTE, Integer.parseInt(timeArray[1]))
      set(Calendar.SECOND, 0)
    }

    // membuat pending intent untuk alarm manager
    val pendingIntent = PendingIntent.getBroadcast(
      context,
      ID_ONETIME,
      intent,
      PendingIntent.FLAG_IMMUTABLE
    )

    // set alarm manager yang berisi:
    // calendar: waktu alarm
    // pendingIntent: isi data (title & message)
    alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
    showToast(context, "$type berhasil di set", message)
  }

  // sama aja buat set alarm, tapi alarmnya berulang
  @RequiresApi(Build.VERSION_CODES.M)
  fun setRepeatingAlarm(
    context: Context,
    type: String,
    time: String,
    message: String
  ) {
    if(isDateInvalid(time, TIME_FORMAT)) return

    // membuat alarm manager
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    // masukkin data-data
    val intent = Intent(context, AlarmReceiver::class.java)
    intent.putExtra(EXTRA_MESSAGE, message)
    intent.putExtra(EXTRA_TYPE, type)

    // mengolah data dari inputan field menjadi bentuk date
    val timeArray = time.split(":".toRegex()).dropLastWhile{it.isEmpty()}.toTypedArray()
    val calendar = Calendar.getInstance()
    calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]))
    calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]))
    calendar.set(Calendar.SECOND, 0)

    // membuat pending intent untuk alarm manager
    val pendingIntent = PendingIntent.getBroadcast(
      context,
      ID_REPEATING,
      intent,
      PendingIntent.FLAG_IMMUTABLE
    )

    // set alarm manager yang berisi:
    // calendar: waktu alarm
    // pendingIntent: isi data (title & message)
    alarmManager.setInexactRepeating(
      AlarmManager.RTC_WAKEUP,
      calendar.timeInMillis,
      AlarmManager.INTERVAL_DAY,
      pendingIntent
    )
    Toast.makeText(context, "Repeating alarm set up", Toast.LENGTH_SHORT).show()
  }

  // untuk menghapus alarm
  @RequiresApi(Build.VERSION_CODES.M)
  fun cancelAlarm(context: Context, type: String){
    // membuat alarm manager
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    // membuat data intent yang menjadi filter
    val intent = Intent(context, AlarmReceiver::class.java)
    val requestCode = if (type.equals(TYPE_ONE_TIME, ignoreCase = true))
      ID_ONETIME
    else
      ID_REPEATING

    // mencari pending intent dengan request code dan intent yang sama
    val pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, PendingIntent.FLAG_IMMUTABLE)
    // batalkan pending intent
    pendingIntent.cancel()

    // batalkan alarm secara keseluruhan
    alarmManager.cancel(pendingIntent)

    Toast.makeText(context, "$type berhasil di cancel", Toast.LENGTH_SHORT).show()
  }

  // notifikasi yang muncul ketika alarm sudah terpicu
  // output akhir dari alarm
  private fun showAlarmNotification(
    context: Context,
    title: String,
    message: String,
    notifId: Int
  ) {
    val channelId = "channel_alarm"
    val channelName = "AlarmManager channel"

    val notificationManagerCompat =
      context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
    val builder = NotificationCompat.Builder(context, channelId)
      .setSmallIcon(R.drawable.ic_baseline_access_time_24)
      .setContentTitle(title)
      .setContentText(message)
      .setColor(ContextCompat.getColor(context, android.R.color.transparent))
      .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
      .setSound(alarmSound)

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      val channel = NotificationChannel(
        channelId,
        channelName,
        NotificationManager.IMPORTANCE_DEFAULT
      )
      channel.apply {
        enableVibration(true)
        vibrationPattern = longArrayOf(1000, 1000, 1000, 1000, 1000)
      }
      builder.setChannelId(channelId)
      notificationManagerCompat.createNotificationChannel(channel)
    }

    val notification = builder.build()
    notificationManagerCompat.notify(notifId, notification)
  }

  private fun isDateInvalid(date: String, format: String): Boolean {
    return try {
      val df = SimpleDateFormat(format, Locale.getDefault())
      df.isLenient = false
      df.parse(date)
      false
    } catch (e: Exception) {
      e.printStackTrace()
      true
    }
  }

  private fun showToast(context: Context, title: String, message: String?) {
    Toast.makeText(context, "$title -- $message", Toast.LENGTH_LONG).show()
  }

  companion object {
    const val TYPE_ONE_TIME = "OneTimeAlarm"
    const val TYPE_REPEATING = "RepeatingAlarm"
    const val EXTRA_MESSAGE = "message"
    const val EXTRA_TYPE = "type"

    private const val ID_ONETIME = 100
    private const val ID_REPEATING = 101

    private const val DATE_FORMAT = "yyyy-MM-dd"
    private const val TIME_FORMAT = "HH:mm"
  }
}