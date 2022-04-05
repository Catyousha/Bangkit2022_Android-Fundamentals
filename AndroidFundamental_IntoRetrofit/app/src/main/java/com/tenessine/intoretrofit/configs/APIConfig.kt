package com.tenessine.intoretrofit.configs

import androidx.viewbinding.BuildConfig
import com.tenessine.intoretrofit.services.APIService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class APIConfig {
  companion object {
    fun getApiService(): APIService {
      val loggingInterceptor = HttpLoggingInterceptor()
      loggingInterceptor.level =
        if (BuildConfig.DEBUG)
          HttpLoggingInterceptor.Level.BODY
        else
          HttpLoggingInterceptor.Level.NONE

      val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

      val retrofit = Retrofit.Builder()
        .baseUrl("https://restaurant-api.dicoding.dev/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

      return retrofit.create(APIService::class.java)
    }
  }
}