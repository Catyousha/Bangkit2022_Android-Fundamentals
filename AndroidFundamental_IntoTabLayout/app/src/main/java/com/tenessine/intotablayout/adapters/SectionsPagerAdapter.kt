package com.tenessine.intotablayout.adapters

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.tenessine.intotablayout.fragments.HomeFragment
import com.tenessine.intotablayout.fragments.ProfileFragment

class SectionsPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {

  override fun getItemCount(): Int {
    return NUM_PAGES
  }

  override fun createFragment(position: Int): Fragment {
    val fragment = HomeFragment()
    fragment.arguments = Bundle().apply {
      putInt(HomeFragment.ARG_SECTION_NUMBER, position + 1)
    }
    return fragment
  }

  companion object {
    private const val NUM_PAGES = 3
  }
}