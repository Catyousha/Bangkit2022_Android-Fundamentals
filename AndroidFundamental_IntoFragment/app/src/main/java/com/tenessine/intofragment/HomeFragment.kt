package com.tenessine.intofragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.commit

class HomeFragment : Fragment(R.layout.fragment_home), View.OnClickListener {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btnCategory: Button = view.findViewById(R.id.btn_category)
        btnCategory.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.btn_category -> {
                val mCategoryFragment = CategoryFragment()
                val mFragmentManager = parentFragmentManager
                mFragmentManager.commit {
                    addToBackStack(null)
                    replace(R.id.frame_container, mCategoryFragment)
                }
            }
        }
    }
}