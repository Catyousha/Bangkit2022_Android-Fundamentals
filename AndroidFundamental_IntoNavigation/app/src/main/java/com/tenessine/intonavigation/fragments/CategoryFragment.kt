package com.tenessine.intonavigation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.tenessine.intonavigation.R
import com.tenessine.intonavigation.databinding.FragmentCategoryBinding

class CategoryFragment : Fragment() {
  private var binding: FragmentCategoryBinding? = null

  override fun onCreateView(
      inflater: LayoutInflater,
      container: ViewGroup?,
      savedInstanceState: Bundle?
  ): View {
    binding = FragmentCategoryBinding.inflate(inflater, container, false)
    return binding!!.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    binding!!.btnCategoryLifestyle.setOnClickListener {
      val toDetailCategoryFragment = CategoryFragmentDirections.actionCategoryFragmentToDetailCategoryFragment()
      toDetailCategoryFragment.name = "Lifestyle"
      toDetailCategoryFragment.stock = 7
      it.findNavController().navigate(toDetailCategoryFragment)
    }
  }

  override fun onDestroy() {
    super.onDestroy()
    binding = null
  }

}
