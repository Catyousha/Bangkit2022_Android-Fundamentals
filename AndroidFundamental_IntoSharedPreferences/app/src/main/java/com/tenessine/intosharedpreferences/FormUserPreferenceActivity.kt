package com.tenessine.intosharedpreferences

import android.content.Intent
import android.content.Intent.EXTRA_USER
import android.os.Bundle
import android.text.TextUtils
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.tenessine.intosharedpreferences.configs.UserPreference
import com.tenessine.intosharedpreferences.databinding.ActivityFormUserPreferenceBinding
import com.tenessine.intosharedpreferences.models.UserModel

class FormUserPreferenceActivity : AppCompatActivity(), View.OnClickListener {
  private var _binding: ActivityFormUserPreferenceBinding? = null
  private val binding get() = _binding!!

  private lateinit var userModel: UserModel

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    _binding = ActivityFormUserPreferenceBinding.inflate(layoutInflater)
    setContentView(binding.root)

    userModel = intent.getParcelableExtra<UserModel>("USER") as UserModel

    val formType = intent.getIntExtra(EXTRA_TYPE_FORM, 0)
    var actionBarTitle = ""
    var btnTitle = ""

    when (formType) {
      TYPE_ADD -> {
        actionBarTitle = "Add User"
        btnTitle = "Add"
      }
      TYPE_EDIT -> {
        actionBarTitle = "Edit User"
        btnTitle = "Edit"
        showPreferenceInForm()
      }
    }

    supportActionBar?.apply {
      title = actionBarTitle
      setDisplayHomeAsUpEnabled(true)
    }
    binding.btnSave.apply {
      text = btnTitle
      setOnClickListener(this@FormUserPreferenceActivity)
    }
  }

  private fun showPreferenceInForm() {
    binding.apply {
      edtName.setText(userModel.name)
      edtEmail.setText(userModel.email)
      edtAge.setText(userModel.age.toString())
      edtPhone.setText(userModel.phoneNumber)
      if (userModel.isLove) {
        this.rbYes.isChecked = true
      } else {
        this.rbNo.isChecked = true
      }
    }
  }

  override fun onClick(v: View?) {
    if (v?.id == R.id.btn_save) {
      val name = binding.edtName.text.toString().trim()
      val email = binding.edtEmail.text.toString().trim()
      val phone = binding.edtPhone.text.toString().trim()
      val age = binding.edtAge.text.toString().trim()
      val isLoveMu = binding.rgLoveMu.checkedRadioButtonId == R.id.rb_yes

      if (name.isEmpty()) {
        binding.edtName.error = FIELD_REQUIRED
        return
      }
      if (email.isEmpty()) {
        binding.edtEmail.error = FIELD_REQUIRED
        return
      }
      if (!isValidEmail(email)) {
        binding.edtEmail.error = FIELD_IS_NOT_VALID
        return
      }
      if (age.isEmpty()) {
        binding.edtAge.error = FIELD_REQUIRED
        return
      }
      if (phone.isEmpty()) {
        binding.edtPhone.error = FIELD_REQUIRED
        return
      }
      if (!TextUtils.isDigitsOnly(phone)) {
        binding.edtPhone.error = FIELD_IS_NOT_VALID
        return
      }

      saveUser(name, email, phone, age, isLoveMu)
      val resultIntent = Intent()
      resultIntent.putExtra(EXTRA_USER, userModel)
      setResult(RESULT_CODE, resultIntent)

      finish()
    }
  }

  private fun saveUser(name: String, email: String, phone: String, age: String, isLoveMu: Boolean) {
    val userPreferences = UserPreference(this)
    userModel.name = name
    userModel.email = email
    userModel.phoneNumber = phone
    userModel.age = Integer.parseInt(age)
    userModel.isLove = isLoveMu

    userPreferences.setUser(userModel)
    Toast.makeText(this, "Data Tersimpan", Toast.LENGTH_SHORT).show()
  }

  private fun isValidEmail(email: String): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    if(item.itemId == android.R.id.home) {
      finish()
    }
    return super.onOptionsItemSelected(item)
  }

  override fun onDestroy() {
    super.onDestroy()
    _binding = null
  }

  companion object {
    const val EXTRA_TYPE_FORM = "extra_type_form"
    const val EXTRA_RESULT = "extra_result"
    const val RESULT_CODE = 101

    const val TYPE_ADD = 1
    const val TYPE_EDIT = 2

    private const val FIELD_REQUIRED = "Field is required"
    private const val FIELD_DIGIT_ONLY = "Field must be digit only"
    private const val FIELD_IS_NOT_VALID = "Field is not valid"
  }


}