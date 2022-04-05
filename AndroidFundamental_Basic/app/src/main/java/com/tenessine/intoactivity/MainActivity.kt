package com.tenessine.intoactivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.tenessine.intoactivity.databinding.BruhBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: BruhBinding

    companion object {
        private const val STATE_RESULT = "state_result"
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState.putString(STATE_RESULT, binding.tvResult.text.toString())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = BruhBinding.inflate(layoutInflater);
        setContentView(binding.root)

        if(savedInstanceState != null){
            val result = savedInstanceState.getString(STATE_RESULT)
            binding.tvResult.text = result
        }

        binding.btnCalculate.setOnClickListener(this)
    }

    private fun validateFilled(editText: EditText): Boolean {
        val inputValue = editText.text.toString().trim()
        if(inputValue.isEmpty()){
            editText.error = "Field ini tidak boleh kosong!"
            return false
        }
        return true
    }

    override fun onClick(v: View?) {
        if (v != null) {
            if (v.id == R.id.btn_calculate) {
                var isEmptyFields = false
                var volume = 1.0
                val fields = arrayListOf<EditText>(
                    binding.edtLength,
                    binding.edtWidth,
                    binding.edtHeight
                )
                for(field in fields) {
                    if ( !validateFilled(field)){
                        isEmptyFields = true
                    } else {
                        volume *= field.text.toString().trim().toDouble()
                    }
                }
                if (!isEmptyFields){
                    binding.tvResult.text = volume.toString()
                }
            }
        }
    }
}