package com.tenessine.intoviewgroup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.intro_constraint_layout)
    supportActionBar?.title = "Yahaha"
  }
}