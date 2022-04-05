package com.tenessine.intosqlite.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.tenessine.intosqlite.db.schemas.note.NoteSchema

// tempat database diurus
class DatabaseContract {

  // alat untuk menangani database
  internal class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    // membuat database
    // mengeksekusi perintah-perintah SQL untuk masing-masing tabel
    override fun onCreate(db: SQLiteDatabase) {
      db.execSQL(NoteSchema.SQL_CREATE_TABLE_NOTE)
    }

    // memperbarui database
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
      db?.execSQL(NoteSchema.SQL_DROP_TABLE_NOTE)
      onCreate(db ?: return)
    }

    companion object {
      private const val DATABASE_NAME = "dbnoteapp"
      private const val DATABASE_VERSION = 1
    }
  }

}