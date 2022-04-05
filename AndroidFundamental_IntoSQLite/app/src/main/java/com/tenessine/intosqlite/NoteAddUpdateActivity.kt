package com.tenessine.intosqlite

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.tenessine.intosqlite.databinding.ActivityNoteAddUpdateBinding
import com.tenessine.intosqlite.db.schemas.note.NoteSchema
import com.tenessine.intosqlite.models.Note
import java.text.SimpleDateFormat
import java.util.*

class NoteAddUpdateActivity : AppCompatActivity(), View.OnClickListener {
  private lateinit var binding: ActivityNoteAddUpdateBinding

  private var isEdit = false
  private var note: Note? = null
  private var position: Int = 0

  private lateinit var noteHelper: NoteSchema.NoteHelper

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityNoteAddUpdateBinding.inflate(layoutInflater)
    setContentView(binding.root)

    // inisialisasi tabel note
    noteHelper = NoteSchema.NoteHelper.getInstance(applicationContext)
    noteHelper.open()

    note = intent.getParcelableExtra(EXTRA_NOTE)
    if(note != null) {
      position = intent.getIntExtra(EXTRA_POSITION, 0)
      isEdit = true
    } else {
      note = Note()
    }

    if(isEdit) {
      note?.let {
        binding.edtTitle.setText(it.title)
        binding.edtDescription.setText(it.description)
      }
    }

    supportActionBar?.apply {
      title = if(isEdit) "Ubah" else "Tambah"
      setDisplayHomeAsUpEnabled(true)
    }

    binding.btnSubmit.apply{
      text = if(isEdit) "Update" else "Simpan"
      setOnClickListener(this@NoteAddUpdateActivity)
    }

  }

  override fun onClick(v: View?) {
    when(v?.id){
      R.id.btn_submit -> {
        val title = binding.edtTitle.text.toString().trim()
        val description = binding.edtDescription.text.toString().trim()

        if(title.isEmpty()) {
          binding.edtTitle.error = "Field ini tidak boleh kosong"
          return
        }

        note?.title = title
        note?.description = description

        val intent = Intent()
        intent.putExtra(EXTRA_NOTE, note)
        intent.putExtra(EXTRA_POSITION, position)

        // ContentValues sebagai wadah penampungan untuk tabel kedepannya
        val values = ContentValues()
        values.put(NoteSchema.NoteColumns.TITLE, title)
        values.put(NoteSchema.NoteColumns.DESCRIPTION, description)

        when(isEdit){
          true -> {
            // langsung execute update
            val result = noteHelper.update(note?.id.toString(), values)

            // result menandakan status keberhasilan, kalau 0 gagal
            if(result > 0) {
              setResult(RESULT_UPDATE, intent)
              finish()
            } else {
              Toast.makeText(this@NoteAddUpdateActivity, "Gagal mengupdate data", Toast.LENGTH_SHORT).show()
            }
          }
          else -> {
            note?.date = getCurrentDate()
            values.put(NoteSchema.NoteColumns.DATE, getCurrentDate())

            // langsung execute insert
            val result = noteHelper.insert(values)

            // result menandakan status keberhasilan, kalau 0 gagal
            if(result > 0){
              note?.id = result.toInt()
              setResult(RESULT_ADD, intent)
              finish()
            } else {
              Toast.makeText(this@NoteAddUpdateActivity, "Gagal menambahkan data", Toast.LENGTH_SHORT).show()
            }
          }
        }
      }
    }
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    if(isEdit) {
      menuInflater.inflate(R.menu.menu_form, menu)
    }
    return super.onCreateOptionsMenu(menu)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when(item.itemId) {
      R.id.action_delete -> showAlertDialog(ALERT_DIALOG_DELETE)
    }
    return super.onOptionsItemSelected(item)
  }

  private fun showAlertDialog(type: Int) {
    val isDialogClose = type == ALERT_DIALOG_CLOSE
    val dialogTitle: String
    val dialogMessage: String

    when(isDialogClose) {
      true -> {
        dialogTitle = "Batal"
        dialogMessage = "Apakah anda ingin membatalkan perubahan pada form?"
      }
      false -> {
        dialogTitle = "Hapus Note"
        dialogMessage = "Apakah anda yakin ingin menghapus note ini?"
      }
    }

    val alertDialogBuilder = AlertDialog.Builder(this)
    alertDialogBuilder.apply {
      setTitle(dialogTitle)
      setMessage(dialogMessage)
      setCancelable(false)
      setPositiveButton("Ya") { dialog, id ->
        if(isDialogClose) {
          finish()
        } else {
          // langsung execute delete
          val result = noteHelper.deleteById(note?.id.toString()).toLong()

          // result menandakan status keberhasilan, kalau 0 gagal
          if(result > 0) {
            val intent = Intent()
            intent.putExtra(EXTRA_POSITION, position)
            setResult(RESULT_DELETE, intent)
            finish()
          } else {
            Toast.makeText(this@NoteAddUpdateActivity, "Gagal menghapus data", Toast.LENGTH_SHORT).show()
          }
        }
      }
      setNegativeButton("Tidak") { dialog, id -> dialog.cancel() }
      create().show()
    }
  }

  private fun getCurrentDate(): String? {
    val dateFormat = SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault())
    val date = Date()
    return dateFormat.format(date)
  }

  companion object {
    const val EXTRA_NOTE = "extra_note"
    const val EXTRA_POSITION = "extra_position"
    const val RESULT_ADD = 101
    const val RESULT_UPDATE = 201
    const val RESULT_DELETE = 301
    const val ALERT_DIALOG_CLOSE = 10
    const val ALERT_DIALOG_DELETE = 20
  }

}