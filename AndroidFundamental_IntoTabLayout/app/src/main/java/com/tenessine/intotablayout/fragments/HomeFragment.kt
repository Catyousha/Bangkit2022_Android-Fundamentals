package com.tenessine.intotablayout.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tenessine.intotablayout.R
import com.tenessine.intotablayout.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {
  private lateinit var binding: FragmentHomeBinding

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    binding = FragmentHomeBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    val index = arguments?.getInt(ARG_SECTION_NUMBER, 0)
    binding.sectionLabel.text = getString(R.string.content_tab_text, index)
  }

  companion object {
    const val ARG_SECTION_NUMBER = "section_number"
  }
}