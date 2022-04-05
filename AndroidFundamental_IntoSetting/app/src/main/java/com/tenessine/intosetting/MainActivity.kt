package com.tenessine.intosetting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.commit
import com.tenessine.intosetting.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
  private var _binding: ActivityMainBinding? = null
  private val binding get() = _binding!!

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    _binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    supportFragmentManager.commit {
      add(R.id.setting_holder, MyPreferenceFragment())
    }
  }

  override fun onDestroy() {
    super.onDestroy()
    _binding = null
  }
}