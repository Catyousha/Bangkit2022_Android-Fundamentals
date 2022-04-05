package com.tenessine.intodeepnavigation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tenessine.intodeepnavigation.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
  private var _binding: ActivityDetailBinding? = null
  private val binding get() = _binding!!

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    _binding = ActivityDetailBinding.inflate(layoutInflater)
    setContentView(binding.root)

    val title = intent.getStringExtra(EXTRA_TITLE)
    val message = intent.getStringExtra(EXTRA_MESSAGE)

    binding.apply {
      tvTitle.text = title
      tvMessage.text = message
    }
  }

  override fun onDestroy() {
    super.onDestroy()
    _binding = null
  }

  companion object {
    const val EXTRA_TITLE = "EXTRA_TITLE"
    const val EXTRA_MESSAGE = "EXTRA_MESSAGE"
  }
}