package com.tenessine.intoroom.database.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.tenessine.intoroom.database.dao.NoteDao
import com.tenessine.intoroom.database.entity.Note

// class abstract sebagai database room
// kumpulan entitas yang digunakan dikumpul dalam array
@Database(entities = [Note::class], version = 1)
abstract class NoteRoomDatabase : RoomDatabase() {
  // para dao dikumpulkan dalam bentuk fungsi abstrak
  abstract fun noteDao(): NoteDao

  // singleton purposes
  companion object {
    const val DATABASE_NAME = "note_database"
    @Volatile
    private var INSTANCE: NoteRoomDatabase? = null

    @JvmStatic
    fun getDatabase(context: Context): NoteRoomDatabase {
      if(INSTANCE == null) {
        // synchronized untuk menyamakan bentuk NoteRoomDatabase di semua thread
        synchronized(NoteRoomDatabase::class.java) {

          // database dibuat disini
          INSTANCE = Room.databaseBuilder(
            context.applicationContext,
            NoteRoomDatabase::class.java,
            DATABASE_NAME
          ).build()

        }
      }
      return INSTANCE as NoteRoomDatabase
    }
  }

}