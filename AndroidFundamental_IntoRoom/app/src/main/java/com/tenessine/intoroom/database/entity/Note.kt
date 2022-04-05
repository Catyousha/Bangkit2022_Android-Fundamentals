package com.tenessine.intoroom.database.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

// entitas sebagai model dan tabel untuk Note
@Entity
@Parcelize
data class Note (
  // primary key
  @PrimaryKey(autoGenerate = true)
  // definisi nama kolom dengan anotasi
  @ColumnInfo(name = "id")
  var id: Int = 0,

  @ColumnInfo(name = "title")
  var title: String? = null,

  @ColumnInfo(name = "description")
  var description: String? = null,

  @ColumnInfo(name = "date")
  var date: String? = null
) : Parcelable