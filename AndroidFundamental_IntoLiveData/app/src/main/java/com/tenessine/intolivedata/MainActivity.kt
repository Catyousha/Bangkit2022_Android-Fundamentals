package com.tenessine.intolivedata

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.tenessine.intolivedata.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
  private lateinit var binding: ActivityMainBinding
  private lateinit var mLiveDataTimerViewModel: MainViewModel

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    mLiveDataTimerViewModel = ViewModelProvider(this)[MainViewModel::class.java]
    subscribe()
  }

  private fun subscribe() {
    val elapsedTimeObserver = Observer<Long?> {
      val newText = this@MainActivity.resources.getString(R.string.seconds, it)
      binding.timerTextview.text = newText
    }
    mLiveDataTimerViewModel.getElapsedTime().observe(this, elapsedTimeObserver)
  }
}