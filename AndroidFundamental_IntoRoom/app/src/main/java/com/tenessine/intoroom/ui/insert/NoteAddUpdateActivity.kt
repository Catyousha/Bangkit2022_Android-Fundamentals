package com.tenessine.intoroom.ui.insert

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.tenessine.intoroom.R
import com.tenessine.intoroom.database.entity.Note
import com.tenessine.intoroom.databinding.ActivityNoteAddUpdateBinding
import com.tenessine.intoroom.factory.ViewModelFactory
import com.tenessine.intoroom.helper.DateHelper

class NoteAddUpdateActivity : AppCompatActivity() {
  private var _binding: ActivityNoteAddUpdateBinding? = null
  private val binding get() = _binding!!

  private lateinit var viewModel: NoteAddUpdateViewModel

  private var isEdit = false
  private var note: Note? = null


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    _binding = ActivityNoteAddUpdateBinding.inflate(layoutInflater)
    setContentView(binding.root)

    viewModel = obtainViewModel(this@NoteAddUpdateActivity)

    showStatusLabel()
    binding.btnSubmit.setOnClickListener {
      submitData()
    }

  }

  private fun submitData() {
    val title = binding.edtTitle.text.toString().trim()
    val description = binding.edtDescription.text.toString().trim()

    when {
      title.isEmpty() -> {
        binding.edtTitle.error = getString(R.string.empty)
      }
      description.isEmpty() -> {
        binding.edtDescription.error = getString(R.string.empty)
      }
      else -> {
        note.apply {
          this?.title = title
          this?.description = description
        }

        when (isEdit) {
          // model note langsung dilempar aja dalam viewmodel
          true -> {
            // update
            viewModel.update(note as Note)
          }
          false -> {
            note?.date = DateHelper.getCurrentDate()
            // insert
            viewModel.insert(note as Note)
          }
        }
        finish()
      }
    }
  }

  private fun showStatusLabel() {
    note = intent.getParcelableExtra(EXTRA_NOTE)
    when (note != null) {
      true -> {
        isEdit = true
        supportActionBar?.apply {
          title = getString(R.string.change)
          setDisplayHomeAsUpEnabled(true)
        }
        binding.apply {
          btnSubmit.text = getString(R.string.update)
          edtTitle.setText(note?.title)
          edtDescription.setText(note?.description)
        }
      }
      false -> {
        note = Note()
        supportActionBar?.apply {
          title = getString(R.string.add)
          setDisplayHomeAsUpEnabled(true)
        }
        binding.btnSubmit.text = getString(R.string.save)
      }
    }
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    if (isEdit) {
      menuInflater.inflate(R.menu.menu_form, menu)
    }
    return super.onCreateOptionsMenu(menu)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      R.id.action_delete -> showAlertDialog(ALERT_DIALOG_DELETE)
      android.R.id.home -> showAlertDialog(ALERT_DIALOG_CLOSE)
    }
    return super.onOptionsItemSelected(item)
  }

  override fun onBackPressed() {
    showAlertDialog(ALERT_DIALOG_CLOSE)
  }

  private fun showAlertDialog(type: Int) {
    val dialogTitle: String
    val dialogMessage: String

    when (type) {
      ALERT_DIALOG_CLOSE -> {
        dialogTitle = getString(R.string.cancel)
        dialogMessage = getString(R.string.message_cancel)
      }
      else -> {
        dialogTitle = getString(R.string.delete)
        dialogMessage = getString(R.string.message_delete)
      }
    }

    AlertDialog.Builder(this).apply {
      setTitle(dialogTitle)
      setMessage(dialogMessage)
      setPositiveButton(getString(R.string.yes)) { _, _ ->
        when (type) {
          ALERT_DIALOG_DELETE -> {
            // delete
            viewModel.delete(note as Note)
          }
        }
        finish()
      }
      setNegativeButton(getString(R.string.no)) { dialog, _ -> dialog.cancel() }
      show()
    }
  }

  private fun obtainViewModel(activity: AppCompatActivity): NoteAddUpdateViewModel {
    val factory = ViewModelFactory.getInstance(activity.application)
    return ViewModelProvider(activity, factory)[NoteAddUpdateViewModel::class.java]
  }

  override fun onDestroy() {
    super.onDestroy()
    _binding = null
  }

  companion object {
    const val EXTRA_NOTE = "extra_note"
    const val ALERT_DIALOG_CLOSE = 10
    const val ALERT_DIALOG_DELETE = 20
  }
}
