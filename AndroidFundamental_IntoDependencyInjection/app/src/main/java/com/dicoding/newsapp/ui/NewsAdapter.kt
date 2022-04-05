package com.dicoding.newsapp.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.newsapp.R
import com.dicoding.newsapp.data.local.entity.NewsEntity
import com.dicoding.newsapp.databinding.ItemNewsBinding
import com.dicoding.newsapp.ui.NewsAdapter.MyViewHolder
import com.dicoding.newsapp.utils.DateFormatter

class NewsAdapter(private val onBookmarkClick: (NewsEntity) -> Unit) :
// menggunakan ListAdapter agar terhubung dengan DiffUtil
  ListAdapter<NewsEntity, MyViewHolder>(DIFF_CALLBACK) {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
    val binding = ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    return MyViewHolder(binding)
  }

  override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
    val news = getItem(position)
    holder.bind(news)

    val ivBookmark = holder.binding.ivBookmark
    when (news.isBookmarked) {
      true -> ivBookmark.setImageDrawable(
        ContextCompat.getDrawable(
          ivBookmark.context,
          R.drawable.ic_bookmarked_white
        )
      )
      false -> ivBookmark.setImageDrawable(
        ContextCompat.getDrawable(
          ivBookmark.context,
          R.drawable.ic_bookmark_white
        )
      )
    }

    ivBookmark.setOnClickListener {
      onBookmarkClick(news)
    }

  }

  class MyViewHolder(val binding: ItemNewsBinding) : RecyclerView.ViewHolder(
    binding.root
  ) {
    fun bind(news: NewsEntity) {
      binding.tvItemTitle.text = news.title
      binding.tvItemPublishedDate.text = DateFormatter.formatDate(news.publishedAt)
      Glide.with(itemView.context)
        .load(news.urlToImage)
        .apply(RequestOptions.placeholderOf(R.drawable.ic_loading).error(R.drawable.ic_error))
        .into(binding.imgPoster)
      itemView.setOnClickListener {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(news.url)
        itemView.context.startActivity(intent)
      }
    }
  }

  companion object {
    // diffutil digunakan untuk mengupdate data yang ada di recyclerview
    val DIFF_CALLBACK: DiffUtil.ItemCallback<NewsEntity> =
      object : DiffUtil.ItemCallback<NewsEntity>() {

        override fun areItemsTheSame(oldNews: NewsEntity, newNews: NewsEntity): Boolean {
          return oldNews.title == newNews.title
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldNews: NewsEntity, newNews: NewsEntity): Boolean {
          return oldNews == newNews
        }
      }

  }
}