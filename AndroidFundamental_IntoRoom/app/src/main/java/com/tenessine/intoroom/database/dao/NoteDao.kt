package com.tenessine.intoroom.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.tenessine.intoroom.database.entity.Note

// interface Dao sebagai alat untuk CRUD
@Dao
interface NoteDao {
  @Insert(onConflict = OnConflictStrategy.IGNORE)
  fun insert(note: Note)

  @Update
  fun update(note: Note)

  @Delete
  fun delete(note: Note)

  @Query("SELECT * from note ORDER BY id ASC")
  fun getAllNotes(): LiveData<List<Note>>
}