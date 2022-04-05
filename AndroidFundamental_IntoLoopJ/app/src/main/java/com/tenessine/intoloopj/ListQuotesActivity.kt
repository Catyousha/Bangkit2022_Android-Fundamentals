package com.tenessine.intoloopj

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.tenessine.intoloopj.adapters.QuoteAdapter
import com.tenessine.intoloopj.databinding.ActivityListQuotesBinding
import cz.msebera.android.httpclient.Header
import org.json.JSONArray

class ListQuotesActivity : AppCompatActivity() {
  private lateinit var binding: ActivityListQuotesBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityListQuotesBinding.inflate(layoutInflater)
    setContentView(binding.root)

    supportActionBar?.title = "List of Quotes"

    val layoutManager = LinearLayoutManager(this)
    binding.listQuotes.layoutManager = layoutManager

    val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
    binding.listQuotes.addItemDecoration(itemDecoration)

    getListQuotes()
  }

  private fun getListQuotes() {
    binding.progressBar.visibility = LinearLayout.VISIBLE
    val client = AsyncHttpClient()
    val url = "https://quote-api.dicoding.dev/list"

    client.get(url, null, object : AsyncHttpResponseHandler() {
      override fun onSuccess(
        statusCode: Int,
        headers: Array<out Header>?,
        responseBody: ByteArray?
      ) {
        binding.progressBar.visibility = LinearLayout.GONE

        val listQuote = ArrayList<String>()
        val result = String(responseBody!!)
        Log.d(TAG, result)

        try {
          val jsonArray = JSONArray(result)
          for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            val quote = jsonObject.getString("en")
            val author = jsonObject.getString("author")
            listQuote.add("$quote\n\n- $author")
          }

          val adapter = QuoteAdapter(listQuote)
          binding.listQuotes.adapter = adapter

        } catch (e: Exception) {
          Toast.makeText(this@ListQuotesActivity, e.message, Toast.LENGTH_SHORT).show()
          e.printStackTrace()
        }
      }

      override fun onFailure(
        statusCode: Int,
        headers: Array<out Header>?,
        responseBody: ByteArray?,
        error: Throwable?
      ) {
        binding.progressBar.visibility = LinearLayout.INVISIBLE
        val errorMessage = when (statusCode) {
          401 -> "$statusCode : Bad Request"
          403 -> "$statusCode : Forbidden"
          404 -> "$statusCode : Not Found"
          else -> "$statusCode : ${error?.message}"
        }
        Toast.makeText(this@ListQuotesActivity, errorMessage, Toast.LENGTH_SHORT).show()
      }

    })
  }

  companion object {
    private val TAG = ListQuotesActivity::class.java.simpleName
  }
}