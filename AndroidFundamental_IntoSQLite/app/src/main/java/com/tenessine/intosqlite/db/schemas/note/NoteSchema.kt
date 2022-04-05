package com.tenessine.intosqlite.db.schemas.note

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import com.tenessine.intosqlite.db.DatabaseContract
import java.sql.SQLException

// skema untuk tabel note
class NoteSchema {

  // kerangka tabel
  internal class NoteColumns : BaseColumns {
    companion object {
      const val TABLE_NAME = "note"
      const val _ID = "_id"
      const val TITLE = "title"
      const val DESCRIPTION = "description"
      const val DATE = "date"
    }
  }

  // helper untuk CRUD tabel
  internal class NoteHelper(context: Context) {
    private var dbHelper: DatabaseContract.DatabaseHelper = DatabaseContract.DatabaseHelper(context)
    private lateinit var database: SQLiteDatabase

    @Throws(SQLException::class)
    fun open() {
      // saat tabel diakses, database di-open sebagai writeable
      database = dbHelper.writableDatabase
    }

    fun close() {
      dbHelper.close()
      if (database.isOpen) {
        database.close()
      }
    }

    // untuk command SELECT * FROM note
    fun queryAll(): Cursor {
      return database.query(
        NoteColumns.TABLE_NAME, // nama tabel
        null, // kolom-kolom tertentu, null berarti semua kolom
        null, // klausa where
        null, // bind parameter
        null, // group by
        null, // having
        "${NoteColumns._ID} DESC", // order by
        null, // limit
      )
    }

    // untuk command SELECT * FROM note WHERE _id = ?
    fun queryById(id: String): Cursor {
      return database.query(
        NoteColumns.TABLE_NAME, // nama tabel
        null, // kolom-kolom tertentu, null berarti semua kolom
        "${NoteColumns._ID} = ?", // klausa where
        arrayOf(id), // bind parameter
        null, // group by
        null, // having
        null, // order by
        null, // limit
      )
    }

    // untuk command INSERT INTO note (title, description, date) VALUES (?, ?, ?)
    fun insert(values: ContentValues?): Long {
      return database.insert(NoteColumns.TABLE_NAME, null, values)
    }

    // untuk command UPDATE note SET title = ?, description = ?, date = ? WHERE _id = ?
    fun update(id: String, values: ContentValues?): Int {
      return database.update(NoteColumns.TABLE_NAME, values, "${NoteColumns._ID} = ?", arrayOf(id))
    }

    // untuk command DELETE FROM note WHERE _id = ?
    fun deleteById(id: String): Int {
      return database.delete(NoteColumns.TABLE_NAME, "${NoteColumns._ID} = $id", null)
    }

    // untuk singleton purposes
    companion object {
      private var INSTANCE: NoteHelper? = null
      fun getInstance(context: Context): NoteHelper =
        INSTANCE ?: synchronized(this) {
          INSTANCE ?: NoteHelper(context)
        }
    }
  }

  companion object {
    const val SQL_CREATE_TABLE_NOTE = "CREATE TABLE ${NoteColumns.TABLE_NAME}" +
        "(${NoteColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
        "${NoteColumns.TITLE} TEXT NOT NULL," +
        "${NoteColumns.DESCRIPTION} TEXT NOT NULL," +
        "${NoteColumns.DATE} TEXT NOT NULL)"

    const val SQL_DROP_TABLE_NOTE = "DROP TABLE IF EXISTS $${NoteColumns.TABLE_NAME}"
  }
}