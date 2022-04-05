package com.tenessine.intosharedpreferences

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.tenessine.intosharedpreferences.configs.UserPreference
import com.tenessine.intosharedpreferences.databinding.ActivityMainBinding
import com.tenessine.intosharedpreferences.models.UserModel

class MainActivity : AppCompatActivity(), View.OnClickListener {
  private var _binding: ActivityMainBinding? = null
  private val binding get() = _binding!!

  private lateinit var mUserPreference: UserPreference
  private var isPreferenceEmpty = false
  private lateinit var userModel: UserModel

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    _binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    supportActionBar?.title = "User Preference"
    mUserPreference = UserPreference(this)
    showExistingPreference()
    binding.btnSave.setOnClickListener(this)
  }

  private val resultLauncher = registerForActivityResult(
    ActivityResultContracts.StartActivityForResult()
  ) {
    if (it.data != null && it.resultCode == FormUserPreferenceActivity.RESULT_CODE) {

      userModel = it.data?.getParcelableExtra<UserModel>(
        FormUserPreferenceActivity.EXTRA_RESULT
      ) as UserModel

      populateView(userModel)
      checkForm(userModel)
    }
  }

  private fun showExistingPreference() {
    userModel = mUserPreference.getUser()
    populateView(userModel)
    checkForm(userModel)
  }

  private fun checkForm(userModel: UserModel) {
    when {
      userModel.name.toString().isNotEmpty() -> {
        binding.btnSave.text = getString(R.string.change)
        isPreferenceEmpty = false
      }
      else -> {
        binding.btnSave.text = getString(R.string.save)
        isPreferenceEmpty = true
      }
    }
  }

  private fun populateView(userModel: UserModel) {
    binding.apply {
      tvName.text = if (userModel.name.toString().isEmpty()) "No Name" else userModel.name
      tvEmail.text = if (userModel.email.toString().isEmpty()) "No Email" else userModel.email
      tvPhone.text =
        if (userModel.phoneNumber.toString().isEmpty()) "No Phone" else userModel.phoneNumber
      tvIsLoveMu.text = if (userModel.isLove) "Yes" else "No"
      tvAge.text = userModel.age.toString().ifEmpty { "No Age" }
    }
  }

  override fun onClick(v: View?) {
    if(v?.id == R.id.btn_save) {
      val intent = Intent(this@MainActivity, FormUserPreferenceActivity::class.java)
      when {
        isPreferenceEmpty -> {
          intent.putExtra(
            FormUserPreferenceActivity.EXTRA_TYPE_FORM,
            FormUserPreferenceActivity.TYPE_ADD
          )
          intent.putExtra("USER", userModel)
        }
        else -> {
          intent.putExtra(
            FormUserPreferenceActivity.EXTRA_TYPE_FORM,
            FormUserPreferenceActivity.TYPE_EDIT
          )
          intent.putExtra("USER", userModel)
        }
      }
      resultLauncher.launch(intent)
    }
  }

  override fun onDestroy() {
    super.onDestroy()
    _binding = null
  }


}