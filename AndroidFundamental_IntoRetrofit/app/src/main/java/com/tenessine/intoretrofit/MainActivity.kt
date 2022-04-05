package com.tenessine.intoretrofit

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.tenessine.intoretrofit.adapters.ReviewAdapter
import com.tenessine.intoretrofit.databinding.ActivityMainBinding
import com.tenessine.intoretrofit.models.CustomerReviewsItem
import com.tenessine.intoretrofit.models.Restaurant
import com.tenessine.intoretrofit.viewmodels.MainViewModel

class MainActivity : AppCompatActivity() {
  private lateinit var binding: ActivityMainBinding
  private val viewModel by viewModels<MainViewModel>()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)
    supportActionBar?.hide()

    viewModel.restaurant.observe(this) {
      setRestaurantData(it)
    }
    viewModel.listReview.observe(this) {
      setReviewData(it)
    }
    viewModel.isLoading.observe(this) {
      showLoading(it)
    }
    viewModel.snackbarText.observe(this) {
      it.getContentIfNotHandled()?.let { text ->
        Snackbar.make(binding.root, text, Snackbar.LENGTH_LONG).show()
      }
    }

    showReviews()

    binding.btnSend.setOnClickListener {
      viewModel.postReview(binding.edReview.text.toString())
      val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
      imm.hideSoftInputFromWindow(it.windowToken, 0)
    }
  }

  private fun showReviews() {
    val layoutManager = LinearLayoutManager(this)
    binding.rvReview.layoutManager = layoutManager

    val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
    binding.rvReview.addItemDecoration(itemDecoration)
  }

  private fun setReviewData(customerReviews: List<CustomerReviewsItem>) {
    val listReview = ArrayList<String>()
    for (review in customerReviews) {
      listReview.add(
        """
        ${review.review}
        - ${review.name}
        """.trimIndent()
      )
    }

    val adapter = ReviewAdapter(listReview)
    binding.rvReview.adapter = adapter
    binding.edReview.setText("")
  }

  private fun setRestaurantData(restaurant: Restaurant) {
    binding.tvTitle.text = restaurant.name
    binding.tvDescription.text = restaurant.description
    Glide.with(this@MainActivity)
      .load("https://restaurant-api.dicoding.dev/images/large/${restaurant.pictureId}")
      .into(binding.ivPicture)
  }

  private fun showLoading(isLoading: Boolean) {
    if (isLoading) {
      binding.progressBar.visibility = LinearLayout.VISIBLE
    } else {
      binding.progressBar.visibility = LinearLayout.GONE
    }
  }

  companion object {
    const val TAG = "MainActivity"
    private const val RESTAURANT_ID = "uewq1zg2zlskfw1e867"
  }
}