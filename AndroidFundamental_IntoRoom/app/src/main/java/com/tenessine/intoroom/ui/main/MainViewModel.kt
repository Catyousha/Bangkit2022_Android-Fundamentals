package com.tenessine.intoroom.ui.main

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.tenessine.intoroom.database.entity.Note
import com.tenessine.intoroom.repository.NoteRepository

// menghubungkan data dalam repository pada UI
class MainViewModel(application: Application) : ViewModel() {
  private val mNoteRepository: NoteRepository = NoteRepository(application)

  // fetch semua note yang ada untuk ditampilkan dalam UI main
  fun getAllNotes(): LiveData<List<Note>> = mNoteRepository.getAllNotes()
}