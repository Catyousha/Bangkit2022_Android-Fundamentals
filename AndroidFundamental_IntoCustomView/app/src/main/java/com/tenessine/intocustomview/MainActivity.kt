package com.tenessine.intocustomview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast

class MainActivity : AppCompatActivity() {
  private lateinit var myButton: MyButton
  private lateinit var myEditText: MyEditText

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    myButton = findViewById(R.id.myButton)
    myEditText = findViewById(R.id.myEditText)

    setMyButtonEnable()

    myEditText.addTextChangedListener(object: TextWatcher {
      override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

      }

      override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

      }

      override fun afterTextChanged(s: Editable?) {
        setMyButtonEnable()
      }
    })

    myButton.setOnClickListener {
      Toast.makeText(this@MainActivity, myEditText.text, Toast.LENGTH_SHORT).show()
    }
  }

  private fun setMyButtonEnable() {
    val result = myEditText.text
    myButton.isEnabled = result?.toString()?.isNotEmpty() ?: false
  }
}