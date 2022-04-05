package com.tenessine.intoroom.ui.insert

import android.app.Application
import androidx.lifecycle.ViewModel
import com.tenessine.intoroom.database.entity.Note
import com.tenessine.intoroom.repository.NoteRepository

// menampilkan data dari repository untuk UI
class NoteAddUpdateViewModel(application: Application) : ViewModel() {
  private val mNoteRepository = NoteRepository(application)

  // eksekusi masing-masing command CRUD
  fun insert(note: Note) {
    mNoteRepository.insert(note)
  }

  fun update(note: Note) {
    mNoteRepository.update(note)
  }

  fun delete(note: Note) {
    mNoteRepository.delete(note)
  }
}