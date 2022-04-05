package com.tenessine.intorecyclerview.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.TransitionOptions
import com.tenessine.intorecyclerview.R
import com.tenessine.intorecyclerview.databinding.ItemRowHeroBinding
import com.tenessine.intorecyclerview.models.Hero
import java.util.logging.Logger

class ListHeroAdapter(private val listHero: ArrayList<Hero>) : RecyclerView.Adapter<ListHeroAdapter.ListViewHolder>() {

  private lateinit var onItemClickCallback: OnItemClickCallback

  interface OnItemClickCallback {
    fun onItemClicked(data: Hero)
  }

  fun setOnItemClickCallback(callback: OnItemClickCallback) {
    this.onItemClickCallback = callback
  }

  class ListViewHolder(var binding: ItemRowHeroBinding) : RecyclerView.ViewHolder(binding.root)

  override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ListViewHolder {
    val binding = ItemRowHeroBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
    return ListViewHolder(binding)
  }

  override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
    val (name, description, photo) = listHero[position]
    holder.binding.tvItemName.text = name
    holder.binding.tvItemDescription.text = description
    Glide.with(holder.itemView.context)
      .load(photo)
      .circleCrop()
      .into(holder.binding.imgItemPhoto)

    holder.itemView.setOnClickListener {
      onItemClickCallback.onItemClicked(listHero[holder.adapterPosition])
    }
  }

  override fun getItemCount(): Int {
    return listHero.size
  }
}