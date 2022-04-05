package com.tenessine.intologging

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import java.lang.StringBuilder

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private var btnSetValue: Button? = null
    private lateinit var tvText: TextView
    private var names = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvText = findViewById(R.id.tv_text)
        btnSetValue = findViewById(R.id.btn_set_value)
        btnSetValue!!.setOnClickListener(this)

        names.add("Awanama")
        names.add("Bwanama")
        names.add("Cwanama")
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.btn_set_value -> {
                Log.d("LogNotes", names.toString())
                val name = StringBuilder()
                for (i in 0..2){
                    name.append(names[i]).append("\n")
                }
                tvText.text = name.toString()
            }
        }
    }
}