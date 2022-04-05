package com.tenessine.intoroom.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tenessine.intoroom.ui.insert.NoteAddUpdateViewModel
import com.tenessine.intoroom.ui.main.MainViewModel

// viewModel ga bisa nerima parameter, makanya dibikin factory
class ViewModelFactory private constructor(private val mApplication: Application) : ViewModelProvider.NewInstanceFactory() {

  @Suppress("UNCHECKED_CAST")
  // kembalikan viewmodel yang sesuai dan pasang parameternya
  override fun<T: ViewModel> create(modelClass: Class<T>): T {
    // MainViewModel
    if(modelClass.isAssignableFrom(MainViewModel::class.java)) {
      return MainViewModel(mApplication) as T
    }
    // NoteAddUpdateViewModel
    else if (modelClass.isAssignableFrom(NoteAddUpdateViewModel::class.java)) {
      return NoteAddUpdateViewModel(mApplication) as T
    }

    throw IllegalArgumentException("Unknown ViewModel class")
  }

  // singleton purposes
  companion object {

    @Volatile
    private var INSTANCE: ViewModelFactory? = null

    @JvmStatic
    fun getInstance(application: Application): ViewModelFactory {
      if(INSTANCE == null) {
        synchronized(ViewModelFactory::class.java){
          INSTANCE = ViewModelFactory(application)
        }
      }
      return INSTANCE as ViewModelFactory
    }
  }
}