package com.tenessine.intobackgroundthread

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.lifecycleScope
import com.tenessine.intobackgroundthread.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {
  private lateinit var binding: ActivityMainBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    binding.btnStart.setOnClickListener{
      lifecycleScope.launch(Dispatchers.Default) {
        for (i in 0..10) {
          delay(500)
          withContext(Dispatchers.Main) {
            when(val percentage = i * 10) {
              100 -> binding.tvStatus.setText(R.string.task_completed)
              else -> binding.tvStatus.text = String.format(getString(R.string.compressing), percentage)
            }
          }
        }
      }
    }
  }
}