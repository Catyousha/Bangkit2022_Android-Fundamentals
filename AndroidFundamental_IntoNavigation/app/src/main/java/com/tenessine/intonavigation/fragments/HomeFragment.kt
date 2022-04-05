package com.tenessine.intonavigation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.tenessine.intonavigation.R
import com.tenessine.intonavigation.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
  private var binding: FragmentHomeBinding? = null

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    binding = FragmentHomeBinding.inflate(inflater, container, false)
    return binding?.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    binding?.btnCategory?.setOnClickListener(
      Navigation.createNavigateOnClickListener(R.id.action_homeFragment_to_categoryFragment)
    )


    binding?.btnProfile?.setOnClickListener{
      it.findNavController().navigate(R.id.action_homeFragment_to_profileActivity)
    }
  }

  override fun onDestroy() {
    super.onDestroy()
    binding = null
  }
}