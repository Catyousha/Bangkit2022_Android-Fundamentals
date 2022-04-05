package com.tenessine.intofragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast


class DetailCategoryFragment : Fragment(R.layout.fragment_detail_category) {
  lateinit var tvCategoryName: TextView
  lateinit var tvCategoryDescription: TextView
  lateinit var btnProfile: Button
  lateinit var btnShowDialog: Button

  var description: String? = null

  companion object {
    var EXTRA_NAME = "extra_name"
    var EXTRA_DESCRIPTION = "extra_description"
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    tvCategoryName = view.findViewById(R.id.tv_category_name)
    tvCategoryDescription = view.findViewById(R.id.tv_category_description)
    btnProfile = view.findViewById(R.id.btn_profile)
    btnShowDialog = view.findViewById(R.id.btn_show_dialog)

    if(savedInstanceState != null) {
      val descFromBundle = savedInstanceState.getString(EXTRA_DESCRIPTION)
      description = descFromBundle
    }
    if (arguments != null) {
      val categoryName = arguments?.getString(EXTRA_NAME)
      tvCategoryName.text = categoryName
      tvCategoryDescription.text = description
    }

    btnShowDialog.setOnClickListener {
      val mOptionDialogFragment = OptionDialogFragment()
      val mFragmentManager = childFragmentManager
      mOptionDialogFragment.show(mFragmentManager, OptionDialogFragment::class.java.simpleName)
    }

    btnProfile.setOnClickListener {
      val mIntent = Intent(requireActivity(), ProfileActivity::class.java)
      startActivity(mIntent)
    }
  }

  internal var optionDialogListener = object : OptionDialogFragment.OnOptionDialogListener {
    override fun onOptionChosen(text: String?) {
      Toast.makeText(requireActivity(), text, Toast.LENGTH_SHORT).show()
    }
  }
}