package com.tenessine.intoretrofit.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tenessine.intoretrofit.databinding.ItemReviewBinding

class ReviewAdapter(private val listReview: List<String>) : RecyclerView.Adapter<ReviewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
      val binding = ItemReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
      return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
      return listReview.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.tvItem.text = listReview[position]
    }

    class ViewHolder(val binding: ItemReviewBinding) : RecyclerView.ViewHolder(binding.root)
}
