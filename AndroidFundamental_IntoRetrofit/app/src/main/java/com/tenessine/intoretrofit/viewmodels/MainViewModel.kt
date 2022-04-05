package com.tenessine.intoretrofit.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tenessine.intoretrofit.configs.APIConfig
import com.tenessine.intoretrofit.events.SingleUseEvent
import com.tenessine.intoretrofit.models.CustomerReviewsItem
import com.tenessine.intoretrofit.models.PostReviewResponse
import com.tenessine.intoretrofit.models.Restaurant
import com.tenessine.intoretrofit.models.RestaurantResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

  private val _restaurant = MutableLiveData<Restaurant>()
  val restaurant: LiveData<Restaurant> = _restaurant

  private val _listReview = MutableLiveData<List<CustomerReviewsItem>>()
  val listReview: LiveData<List<CustomerReviewsItem>> = _listReview

  private val _isLoading = MutableLiveData<Boolean>()
  val isLoading: LiveData<Boolean> = _isLoading

  private val _snackbarText = MutableLiveData<SingleUseEvent<String>>()
  val snackbarText: LiveData<SingleUseEvent<String>> = _snackbarText

  init {
    findRestaurant()
  }

  private fun findRestaurant() {
    _isLoading.value = true
    val service = APIConfig.getApiService()
    val client = service.getRestaurant(RESTAURANT_ID)
    client.enqueue(object : Callback<RestaurantResponse> {
      override fun onResponse(
        call: Call<RestaurantResponse>,
        response: Response<RestaurantResponse>
      ) {
        _isLoading.value = false
        val respBody = response.body()
        if (response.isSuccessful) {
          _restaurant.value = respBody?.restaurant
          _listReview.value = respBody?.restaurant?.customerReviews
        } else {
          Log.e(TAG, "onFailure: ${response.errorBody()}")
        }
      }

      override fun onFailure(call: Call<RestaurantResponse>, t: Throwable) {
        _isLoading.value = false
        Log.e(TAG, "onFailure: ${t.message.toString()}")
      }

    })
  }

  fun postReview(review: String) {
    _isLoading.value = true
    val service = APIConfig.getApiService()
    val client = service.postReview(RESTAURANT_ID, "Awanama", review)
    client.enqueue(object: Callback<PostReviewResponse> {
      override fun onResponse(
        call: Call<PostReviewResponse>,
        response: Response<PostReviewResponse>
      ) {
        _isLoading.value = false
        val respBody = response.body()
        if (response.isSuccessful) {
          _listReview.value = respBody?.customerReviews
          _snackbarText.value = SingleUseEvent(respBody?.message.toString())
        } else {
          Log.e(TAG, "onFailure: ${response.message()}")
        }
      }

      override fun onFailure(call: Call<PostReviewResponse>, t: Throwable) {
        _isLoading.value = false
        Log.e(TAG, "onFailure: ${t.message.toString()}")
      }

    })
  }


  companion object {
    private const val TAG = "MainViewModel"
    private const val RESTAURANT_ID = "uewq1zg2zlskfw1e867"
  }
}