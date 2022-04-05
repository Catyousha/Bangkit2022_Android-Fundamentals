package com.tenessine.intosqlite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.tenessine.intosqlite.adapters.NoteAdapter
import com.tenessine.intosqlite.databinding.ActivityMainBinding
import com.tenessine.intosqlite.db.schemas.note.NoteSchema
import com.tenessine.intosqlite.helpers.MappingHelper
import com.tenessine.intosqlite.models.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
  private lateinit var binding: ActivityMainBinding
  private lateinit var noteAdapter: NoteAdapter


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    supportActionBar?.title = "Notes"

    noteAdapter = NoteAdapter(object: NoteAdapter.OnItemClickCallback {
      override fun onItemClicked(selectedNote: Note?, position: Int?) {
        val intent = Intent(this@MainActivity, NoteAddUpdateActivity::class.java)
        intent.putExtra(NoteAddUpdateActivity.EXTRA_NOTE, selectedNote)
        intent.putExtra(NoteAddUpdateActivity.EXTRA_POSITION, position)
        resultLauncher.launch(intent)
      }
    })

    binding.rvNotes.apply {
      layoutManager = LinearLayoutManager(this@MainActivity)
      setHasFixedSize(true)
      adapter = noteAdapter
    }

    binding.fabAdd.setOnClickListener {
      val intent = Intent(this@MainActivity, NoteAddUpdateActivity::class.java)
      resultLauncher.launch(intent)
    }

    loadNotesAsync()
  }

  // show all notes on db
  private fun loadNotesAsync() {
    lifecycleScope.launch {
      binding.progressbar.visibility = View.VISIBLE

      // init helper
      val noteHelper = NoteSchema.NoteHelper.getInstance(this@MainActivity)
      noteHelper.open()

      // get all notes command
      val deferredNotes = async(Dispatchers.IO) {
        val cursor = noteHelper.queryAll()
        MappingHelper.mapCursorToArrayList(cursor)
      }

      binding.progressbar.visibility = View.INVISIBLE

      // execute!
      val notes = deferredNotes.await()

      // manage the data for adapter
      if (notes.size > 0) {
        noteAdapter.listNotes = notes
      } else {
        noteAdapter.listNotes = ArrayList()
      }
      noteHelper.close()
    }
  }

  val resultLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
    ActivityResultContracts.StartActivityForResult()
  ) {
      if(it.data != null) {
        when(it.resultCode) {
          NoteAddUpdateActivity.RESULT_ADD -> {
            val note = it.data?.getParcelableExtra<Note>(NoteAddUpdateActivity.EXTRA_NOTE) as Note
            noteAdapter.addItem(note)
            binding.rvNotes.smoothScrollToPosition(noteAdapter.itemCount -2)
          }
          NoteAddUpdateActivity.RESULT_UPDATE -> {
            val note = it.data?.getParcelableExtra<Note>(NoteAddUpdateActivity.EXTRA_NOTE) as Note
            val position = it.data?.getIntExtra(NoteAddUpdateActivity.EXTRA_POSITION, 0) as Int
            noteAdapter.updateItem(position, note)
            binding.rvNotes.smoothScrollToPosition(position)
          }
          NoteAddUpdateActivity.RESULT_DELETE -> {
            val position = it.data?.getIntExtra(NoteAddUpdateActivity.EXTRA_POSITION, 0) as Int
            noteAdapter.removeItem(position)
          }
        }
    }
  }

  companion object {
    private const val EXTRA_STATE = "EXTRA_STATE"
  }
}