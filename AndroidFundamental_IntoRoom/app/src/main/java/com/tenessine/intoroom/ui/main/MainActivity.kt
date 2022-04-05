package com.tenessine.intoroom.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.tenessine.intoroom.R
import com.tenessine.intoroom.adapter.NoteAdapter
import com.tenessine.intoroom.databinding.ActivityMainBinding
import com.tenessine.intoroom.factory.ViewModelFactory
import com.tenessine.intoroom.ui.insert.NoteAddUpdateActivity

class MainActivity : AppCompatActivity() {
  private var _binding: ActivityMainBinding? = null
  private val binding get() = _binding!!

  private lateinit var noteAdapter: NoteAdapter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    _binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    val viewModel = obtainViewModel(this)

    // fetch all note dari viewmodel
    viewModel.getAllNotes().observe(this) {
      if(it != null) {
        noteAdapter.setListNotes(it)
      }
    }

    showRecyclerView()
    showFab()
  }

  private fun showFab() {
    binding.fabAdd.setOnClickListener{
      val intent = Intent(this@MainActivity, NoteAddUpdateActivity::class.java)
      startActivity(intent)
    }
  }

  // get instance viewmodel
  private fun obtainViewModel(activity: AppCompatActivity): MainViewModel {
    val factory = ViewModelFactory.getInstance(activity.application)
    return ViewModelProvider(activity, factory)[MainViewModel::class.java]
  }

  private fun showRecyclerView() {
    noteAdapter = NoteAdapter()
    binding.rvNotes.apply {
      layoutManager = LinearLayoutManager(this@MainActivity)
      setHasFixedSize(true)
      adapter = noteAdapter
    }
  }

  override fun onDestroy() {
    super.onDestroy()
    _binding = null
  }
}