package com.tenessine.intofragment

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit

class CategoryFragment : Fragment(R.layout.fragment_category), View.OnClickListener {

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    val btnDetailCategory: Button = view.findViewById(R.id.btn_detail_category)
    btnDetailCategory.setOnClickListener(this)
  }

  override fun onClick(v: View?) {
    when (v?.id) {
      R.id.btn_detail_category -> {
        val mDetailCategoryFragment = DetailCategoryFragment()

        val mBundle = Bundle()
        mBundle.putString(DetailCategoryFragment.EXTRA_NAME, "Lifestyle")

        val description = "Kategori ini akan berisi produk-produk lifestyle."
        mDetailCategoryFragment.arguments = mBundle
        mDetailCategoryFragment.description = description

        val mFragmentManager = parentFragmentManager
        mFragmentManager.commit {
          addToBackStack(null)
          replace(R.id.frame_container, mDetailCategoryFragment)
        }
      }
    }
  }
}
