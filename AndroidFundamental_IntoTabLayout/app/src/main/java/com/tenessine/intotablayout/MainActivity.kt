package com.tenessine.intotablayout

import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.tenessine.intotablayout.adapters.SectionsPagerAdapter
import com.tenessine.intotablayout.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
  private lateinit var binding: ActivityMainBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    val sectionsPagerAdapter = SectionsPagerAdapter(this)
    binding.viewPager.adapter = sectionsPagerAdapter

    TabLayoutMediator(binding.tabs, binding.viewPager) {
      tab, position -> tab.text = resources.getString(TAB_TITLES[position])
    }.attach()
  }

  companion object {
    @StringRes
    private val TAB_TITLES = intArrayOf(
      R.string.tab_text_1,
      R.string.tab_text_2,
      R.string.tab_text_3
    )
  }
}