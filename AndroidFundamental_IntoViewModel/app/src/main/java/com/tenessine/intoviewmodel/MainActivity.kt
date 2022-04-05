package com.tenessine.intoviewmodel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.tenessine.intoviewmodel.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
  private lateinit var binding: ActivityMainBinding
  private lateinit var viewModel: MainViewModel

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    viewModel = ViewModelProvider(this)[MainViewModel::class.java]

    displayResult()

    binding.btnCalculate.setOnClickListener {
      val width = binding.edtWidth.text.toString()
      val height = binding.edtHeight.text.toString()
      val length = binding.edtLength.text.toString()

      when{
        width.isEmpty() -> binding.edtWidth.error = "Width field is empty"
        height.isEmpty() -> binding.edtHeight.error = "Height field is empty"
        length.isEmpty() -> binding.edtLength.error = "Length field is empty"
        else -> {
          viewModel.calculate(width, height, length)
          displayResult()
        }
      }
    }
  }

  private fun displayResult() {
    binding.tvResult.text = viewModel.result.toString()
  }
}