package com.tenessine.intoloopj.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tenessine.intoloopj.databinding.ItemQuoteBinding

class QuoteAdapter(private val listQuote: ArrayList<String>) :
  RecyclerView.Adapter<QuoteAdapter.QuoteViewHolder>() {

  class QuoteViewHolder(var binding: ItemQuoteBinding) : RecyclerView.ViewHolder(binding.root)

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuoteViewHolder {
    val binding = ItemQuoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    return QuoteViewHolder(binding)
  }

  override fun getItemCount(): Int {
    return listQuote.size
  }

  override fun onBindViewHolder(holder: QuoteViewHolder, position: Int) {
    holder.binding.tvItem.text = listQuote[position]
  }
}