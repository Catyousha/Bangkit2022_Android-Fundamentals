package com.tenessine.intoworkmanager.workers

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Looper
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.loopj.android.http.AsyncHttpResponseHandler
import com.loopj.android.http.SyncHttpClient
import com.tenessine.intoworkmanager.R
import cz.msebera.android.httpclient.Header
import org.json.JSONObject
import java.text.DecimalFormat

// ekstensi pada Worker yang akan menjalankan background task
class MyWorker(
  context: Context,
  workerParams: WorkerParameters
) : Worker(context, workerParams) {

  // menampung hasil dari background task
  private var resultStatus: Result? = null

  // fungsi utama pada worker yang akan dijalankan
  override fun doWork(): Result {
    // menerima data dari activity melalui inputData, mirip dengan Intent
    val dataCity = inputData.getString(EXTRA_CITY)
    return getCurrentWeather(dataCity)
  }

  // proses yang dijalankan
  private fun getCurrentWeather(city: String?): Result {
    Log.d(TAG, "getCurrentWeather: Mulai...")
    Looper.prepare()

    val client = SyncHttpClient()
    val url = "https://api.openweathermap.org/data/2.5/weather?q=$city&appid=$APP_ID"

    // fetch api biasa, hasilnya tampung ke resultStatus
    client.post(url, object : AsyncHttpResponseHandler() {
      override fun onSuccess(
        statusCode: Int,
        headers: Array<out Header>?,
        responseBody: ByteArray?
      ) {
        val result = String(responseBody!!)
        Log.d(TAG, "onSuccess: $result")
        try {
          val responseObject = JSONObject(result)
          val currentWeather: String =
            responseObject.getJSONArray("weather").getJSONObject(0).getString("main")
          val description: String =
            responseObject.getJSONArray("weather").getJSONObject(0).getString("description")
          val tempInKelvin = responseObject.getJSONObject("main").getDouble("temp")
          val tempInCelcius = tempInKelvin - 273
          val temperature: String = DecimalFormat("##.##").format(tempInCelcius)

          val title = "Current Weather in $city"
          val message = "$currentWeather, $description with $temperature celcius"

          // hasil dari background task, berupa notifikasi yang ditampilkan ke user
          showNotification(title, message)
          resultStatus = Result.success()
        } catch (e: Exception) {
          showNotification("Get Current Weather Not Success", e.message)
          Log.e(TAG, "onSuccess: ", e.cause)
          resultStatus = Result.failure()
        }
      }

      override fun onFailure(
        statusCode: Int,
        headers: Array<out Header>?,
        responseBody: ByteArray?,
        error: Throwable?
      ) {
        Log.d(TAG, "onFailure: $error")
        showNotification("Get Current Weather Not Success", error?.message)
        resultStatus = Result.failure()
      }
    })

    return resultStatus as Result
  }

  // notifikasi biasa
  private fun showNotification(
    title: String,
    description: String?
  ) {
    val notificationManager =
      applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    val notification: NotificationCompat.Builder =
      NotificationCompat.Builder(applicationContext, CHANNEL_ID)
        .setSmallIcon(R.drawable.ic_baseline_circle_notifications_24)
        .setContentTitle(title)
        .setContentText(description)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setDefaults(NotificationCompat.DEFAULT_ALL)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      val channel =
        NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH)
      notification.setChannelId(CHANNEL_ID)
      notificationManager.createNotificationChannel(channel)
    }
    notificationManager.notify(NOTIFICATION_ID, notification.build())
  }

  companion object {
    private val TAG = MyWorker::class.java.simpleName
    const val APP_ID = "d8cfabd2563edfc4cfb3b38a3a8425ae"
    const val EXTRA_CITY = "city"
    const val NOTIFICATION_ID = 1
    const val CHANNEL_ID = "channel_01"
    const val CHANNEL_NAME = "weather channel"
  }

}