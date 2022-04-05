package com.tenessine.intosqlite.helpers

import android.database.Cursor
import com.tenessine.intosqlite.db.schemas.note.NoteSchema
import com.tenessine.intosqlite.models.Note

object MappingHelper {

  // mengubah cursor menjadi list of note
  fun mapCursorToArrayList(notesCursor: Cursor?) : ArrayList<Note> {
    val notesList = ArrayList<Note>()
    notesCursor?.apply {
      // anggap aja looping perbaris
      while(moveToNext()) {
        val id = getInt(getColumnIndexOrThrow(NoteSchema.NoteColumns._ID))
        val title = getString(getColumnIndexOrThrow(NoteSchema.NoteColumns.TITLE))
        val description = getString(getColumnIndexOrThrow(NoteSchema.NoteColumns.DESCRIPTION))
        val date = getString(getColumnIndexOrThrow(NoteSchema.NoteColumns.DATE))
        notesList.add(Note(id, title, description, date))
      }
    }
    return notesList
  }

}