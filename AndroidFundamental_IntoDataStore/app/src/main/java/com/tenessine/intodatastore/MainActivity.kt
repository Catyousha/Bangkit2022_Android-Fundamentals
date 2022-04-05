package com.tenessine.intodatastore

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.tenessine.intodatastore.configs.SettingPreferences
import com.tenessine.intodatastore.databinding.ActivityMainBinding

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : AppCompatActivity() {

  private lateinit var binding: ActivityMainBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    val pref = SettingPreferences.getInstance(dataStore)
    val mainViewModel = ViewModelProvider(this, ViewModelFactory(pref)).get(MainViewModel::class.java)

    mainViewModel.getThemeSettings().observe(this){
      isDarkModeActive: Boolean ->
        if(isDarkModeActive) {
          AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
          binding.switchTheme.isChecked = true
        } else {
          AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
          binding.switchTheme.isChecked = false
        }
      }

    binding.switchTheme.setOnCheckedChangeListener { _, isChecked ->
      mainViewModel.saveThemeSetting(isChecked)
    }

  }
}