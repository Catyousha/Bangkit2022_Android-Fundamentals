package com.tenessine.intointent

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.tenessine.intointent.intentpages.DataActivity
import com.tenessine.intointent.intentpages.ObjectActivity
import com.tenessine.intointent.intentpages.SecondActivity
import com.tenessine.intointent.models.Person

class MainActivity : AppCompatActivity(), View.OnClickListener {
  private lateinit var tvResult: TextView

  @SuppressLint("SetTextI18n")
  private val resultLauncher =
      registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == MoveForResultActivity.RESULT_CODE && result.data != null) {
          val selectedValue =
              result.data?.getIntExtra(MoveForResultActivity.EXTRA_SELECTED_VALUE, 0)
          tvResult.text = "Hasil : $selectedValue"
        }
      }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    val btnMoveActivity: Button = findViewById(R.id.btn_move_activity)
    btnMoveActivity.setOnClickListener(this)

    val btnMoveWithDataActivity: Button = findViewById(R.id.btn_move_activity_data)
    btnMoveWithDataActivity.setOnClickListener(this)

    val btnMoveWithObject: Button = findViewById(R.id.btn_move_activity_object)
    btnMoveWithObject.setOnClickListener(this)

    val btnDialPhone: Button = findViewById(R.id.btn_dial_number)
    btnDialPhone.setOnClickListener(this)

    val btnMoveForResult: Button = findViewById(R.id.btn_move_for_result)
    btnMoveForResult.setOnClickListener(this)

    tvResult = findViewById(R.id.tv_result)
  }

  override fun onClick(v: View?) {
    when (v?.id) {
      R.id.btn_move_activity -> {
        val moveIntent = Intent(this@MainActivity, SecondActivity::class.java)
        startActivity(moveIntent)
      }
      R.id.btn_move_activity_data -> {
        val moveWithDataIntent = Intent(this@MainActivity, DataActivity::class.java)
        moveWithDataIntent.putExtra(DataActivity.EXTRA_NAME, "Awanama")
        moveWithDataIntent.putExtra(DataActivity.EXTRA_AGE, 42)
        startActivity(moveWithDataIntent)
      }
      R.id.btn_move_activity_object -> {
        val person =
            Person(
                "Awanama",
                20,
                "awan@example.com",
                "Teyvat",
            )
        val moveWithObjectIntent = Intent(this@MainActivity, ObjectActivity::class.java)
        moveWithObjectIntent.putExtra(ObjectActivity.EXTRA_PERSON, person)
        startActivity(moveWithObjectIntent)
      }
      R.id.btn_dial_number -> {
        val phoneNumber = "08111111111"
        val dialPhoneIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber"))
        startActivity(dialPhoneIntent)
      }
      R.id.btn_move_for_result -> {
        val moveForResultIntent = Intent(this@MainActivity, MoveForResultActivity::class.java)
        resultLauncher.launch(moveForResultIntent)
      }
    }
  }
}
