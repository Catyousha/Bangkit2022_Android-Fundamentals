package com.tenessine.intoworkmanager

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.work.*
import com.tenessine.intoworkmanager.databinding.ActivityMainBinding
import com.tenessine.intoworkmanager.workers.MyWorker
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
  private var _binding: ActivityMainBinding? = null
  private val binding get() = _binding!!

  // menampung workManager yang akan menerima penugasan dari activity
  private lateinit var workManager: WorkManager

  // salah satu jenis request yang akan diterima oleh workManager
  private lateinit var periodicWorkRequest: PeriodicWorkRequest

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    _binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    // mengambil instance workManager, singleton
    workManager = WorkManager.getInstance(this)
    binding.apply {
      btnOneTimeTask.setOnClickListener {
        startOneTimeTask()
      }

      btnPeriodicTask.setOnClickListener {
        startPeriodicTask()
      }

      btnCancelTask.setOnClickListener {
        cancelPeriodicTask()
      }
    }

  }

  private fun cancelPeriodicTask() {
    // menghentikan worker berdasarkan id request
    workManager.cancelWorkById(periodicWorkRequest.id)
  }

  private fun startPeriodicTask() {
    binding.textStatus.text = getString(R.string.status)

    // menyimpan data
    val data = Data.Builder()
      .putString(MyWorker.EXTRA_CITY, binding.editCity.text.toString())
      .build()

    // menetapkan batasan
    val constraints = Constraints.Builder()
      .setRequiredNetworkType(NetworkType.CONNECTED)
      .build()

    // mengatur request dengan jenis PeriodicWorkRequest
    periodicWorkRequest = PeriodicWorkRequest.Builder(
      MyWorker::class.java, // worker yang akan dijalankan
      10, // nominal interval
      TimeUnit.SECONDS // satuan waktu interval
    )
      .setInputData(data)
      .setConstraints(constraints)
      .build()

    // lepas landas ke workManager
    workManager.apply {
      // pemrosesan
      enqueue(periodicWorkRequest)

      // pengawasan data dari worker
      getWorkInfoByIdLiveData(periodicWorkRequest.id)
        .observe(this@MainActivity) {
          val status = it.state.name
          binding.apply {
            textStatus.append("\n$status")
            btnCancelTask.isEnabled = false
            if (it.state == WorkInfo.State.ENQUEUED) {
              btnCancelTask.isEnabled = true
            }
          }
        }
    }

  }

  // background task pada workManager yang akan dijalankan sekali saja
  private fun startOneTimeTask() {
    binding.textStatus.text = getString(R.string.status)

    // membuat data untuk dikirimkan pada worker
    // data yang diterima kemudian diambil melalui inputData
    val data = Data.Builder()
      .putString(MyWorker.EXTRA_CITY, binding.editCity.text.toString())
      .build()

    // menampung syarat untuk menjalankan worker
    val constraints = Constraints.Builder()
      .setRequiredNetworkType(NetworkType.CONNECTED)
      .build()

    // merangkum class worker, constraint, dan data dalam satu request
    val oneTimeWorkRequest = OneTimeWorkRequest.Builder(MyWorker::class.java)
      .setConstraints(constraints)
      .setInputData(data)
      .build()

    workManager.run {

      // worker dijalankan
      enqueue(oneTimeWorkRequest)

      // mengawasi hasil dari worker yang dijalankan secara terus-menerus utk dimanfaatkan oleh UI activity
      // kayak dapetin data dari viewmodel
      getWorkInfoByIdLiveData(oneTimeWorkRequest.id)
        .observe(this@MainActivity) {
          val status = it.state.name
          binding.textStatus.append("\n$status")
        }
    }
  }

  override fun onDestroy() {
    super.onDestroy()
    _binding = null
  }
}