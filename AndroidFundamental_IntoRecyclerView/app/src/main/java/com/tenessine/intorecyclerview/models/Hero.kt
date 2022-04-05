package com.tenessine.intorecyclerview.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Hero(
  var name: String,
  var description: String,
  var photo: String,
) : Parcelable
