package com.tenessine.intoroom.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.tenessine.intoroom.database.dao.NoteDao
import com.tenessine.intoroom.database.db.NoteRoomDatabase
import com.tenessine.intoroom.database.entity.Note
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

// repository untuk mengatur data dengan berhubungan pada DAO
class NoteRepository (application: Application) {
  // DAO
  private val mNoteDao: NoteDao

  // untuk mengeksekusi perintah DAO
  private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

  init {
    val db = NoteRoomDatabase.getDatabase(application)
    mNoteDao = db.noteDao()
  }

  // method DAO yang bersifat query tidak perlu dipasang executorService
  fun getAllNotes(): LiveData<List<Note>> = mNoteDao.getAllNotes()

  fun insert(note: Note) {
    executorService.execute { mNoteDao.insert(note) }
  }

  fun update(note: Note) {
    executorService.execute { mNoteDao.update(note) }
  }

  fun delete(note: Note) {
    executorService.execute { mNoteDao.delete(note) }
  }
}