package com.tenessine.intoreadwrite.helpers

import android.content.Context
import com.tenessine.intoreadwrite.models.FileModel

internal object FileHelper {

  fun writeToFile(fileModel: FileModel, context: Context) {
    context.openFileOutput(fileModel.filename, Context.MODE_PRIVATE).use {
      it.write(fileModel.data?.toByteArray())
    }

  }

  fun readFromFile(context: Context, filename: String): FileModel {

    val fileModel = FileModel()

    fileModel.filename = filename
    fileModel.data = context.openFileInput(filename).bufferedReader().useLines {
      it.fold("") { some, text ->
        "$some$text"
      }
    }

    return fileModel
  }

}