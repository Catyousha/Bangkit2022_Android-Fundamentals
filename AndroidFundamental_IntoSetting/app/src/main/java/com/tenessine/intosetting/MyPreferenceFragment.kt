package com.tenessine.intosetting

import android.content.SharedPreferences
import android.os.Bundle
import androidx.preference.EditTextPreference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference

class MyPreferenceFragment : PreferenceFragmentCompat(),
  SharedPreferences.OnSharedPreferenceChangeListener {
  private lateinit var NAME: String
  private lateinit var EMAIL: String
  private lateinit var AGE: String
  private lateinit var PHONE: String
  private lateinit var LOVE: String

  private lateinit var namePreference: EditTextPreference
  private lateinit var emailPreference: EditTextPreference
  private lateinit var agePreference: EditTextPreference
  private lateinit var phonePreference: EditTextPreference
  private lateinit var isLoveMuPreference: SwitchPreference

  override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
    addPreferencesFromResource(R.xml.preferences)
    init()
    setSummaries()
  }

  private fun setSummaries() {
    val sh = preferenceManager.sharedPreferences
    namePreference.summary = sh?.getString(NAME, DEFAULT_VALUE)
    emailPreference.summary = sh?.getString(EMAIL, DEFAULT_VALUE)
    agePreference.summary = sh?.getString(AGE, DEFAULT_VALUE)
    phonePreference.summary = sh?.getString(PHONE, DEFAULT_VALUE)
    isLoveMuPreference.isChecked = sh?.getBoolean(LOVE, false) ?: false
  }

  private fun init() {
    NAME = getString(R.string.key_name)
    EMAIL = getString(R.string.key_email)
    AGE = getString(R.string.key_age)
    PHONE = getString(R.string.key_phone)
    LOVE = getString(R.string.key_love)

    namePreference = findPreference<EditTextPreference>(NAME) as EditTextPreference
    emailPreference = findPreference<EditTextPreference>(EMAIL) as EditTextPreference
    agePreference = findPreference<EditTextPreference>(AGE) as EditTextPreference
    phonePreference = findPreference<EditTextPreference>(PHONE) as EditTextPreference
    isLoveMuPreference = findPreference<SwitchPreference>(LOVE) as SwitchPreference
  }

  override fun onResume() {
    super.onResume()
    preferenceScreen.sharedPreferences?.registerOnSharedPreferenceChangeListener(this)
  }

  override fun onPause() {
    super.onPause()
    preferenceScreen.sharedPreferences?.unregisterOnSharedPreferenceChangeListener(this)
  }

  override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
    when(key) {
      NAME -> namePreference.summary = sharedPreferences?.getString(NAME, DEFAULT_VALUE)
      EMAIL -> emailPreference.summary = sharedPreferences?.getString(EMAIL, DEFAULT_VALUE)
      AGE -> agePreference.summary = sharedPreferences?.getString(AGE, DEFAULT_VALUE)
      PHONE -> phonePreference.summary = sharedPreferences?.getString(PHONE, DEFAULT_VALUE)
      LOVE -> isLoveMuPreference.isChecked = sharedPreferences?.getBoolean(LOVE, false) ?: false
    }
  }

  companion object {
    private const val DEFAULT_VALUE = "Tidak Ada"
  }
}