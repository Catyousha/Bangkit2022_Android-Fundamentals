package com.tenessine.intonavigation.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.tenessine.intonavigation.R
import com.tenessine.intonavigation.databinding.FragmentDetailCategoryBinding

class DetailCategoryFragment : Fragment() {
  private var binding: FragmentDetailCategoryBinding? = null


  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = FragmentDetailCategoryBinding.inflate(inflater, container, false)
    return binding!!.root
  }

  @SuppressLint("SetTextI18n")
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    val bundleArgs = DetailCategoryFragmentArgs.fromBundle(arguments as Bundle)
    val dataName = bundleArgs.name
    val dataStock = bundleArgs.stock

    binding!!.tvCategoryName.text = dataName
    binding!!.tvCategoryDescription.text = "Stock : $dataStock"

    binding!!.btnProfile.setOnClickListener{
      it.findNavController().navigate(R.id.action_detailCategoryFragment_to_homeFragment)
    }

  }

  override fun onDestroy() {
    super.onDestroy()
    binding = null
  }
}