package com.tenessine.intodatastore

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.tenessine.intodatastore.configs.SettingPreferences
import kotlinx.coroutines.launch

class MainViewModel(private val pref: SettingPreferences) : ViewModel() {
  fun getThemeSettings(): LiveData<Boolean> {
    return pref.getThemeSetting().asLiveData()
  }
  fun saveThemeSetting(isDarkModeActive: Boolean){
    viewModelScope.launch {
      pref.saveThemeSetting(isDarkModeActive)
    }
  }
}