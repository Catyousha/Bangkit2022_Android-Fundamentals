package com.tenessine.intosqlite.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tenessine.intosqlite.databinding.ItemNoteBinding
import com.tenessine.intosqlite.models.Note

class NoteAdapter(private val onItemClickCallback: OnItemClickCallback) :
  RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

  // manage data to mocking live preview on UI
  var listNotes = ArrayList<Note>()
    set(listNotes) {
      if (listNotes.size > 0) {
        this.listNotes.clear()
      }
      this.listNotes.addAll(listNotes)
    }

  interface OnItemClickCallback {
    fun onItemClicked(selectedNote: Note?, position: Int?)
  }

  fun addItem(note: Note) {
    this.listNotes.add(note)
    notifyItemInserted(this.listNotes.size - 1)
  }

  fun updateItem(position: Int, note: Note) {
    this.listNotes[position] = note
    notifyItemChanged(position, note)
  }

  fun removeItem(position: Int) {
    this.listNotes.removeAt(position)
    notifyItemRemoved(position)
    notifyItemRangeChanged(position, this.listNotes.size)
  }

  inner class NoteViewHolder(private val binding: ItemNoteBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(note: Note) {
      binding.apply {
        tvItemTitle.text = note.title
        tvItemDescription.text = note.description
        tvItemDate.text = note.date
        cvItemNote.setOnClickListener {
          onItemClickCallback.onItemClicked(note, adapterPosition)
        }
      }
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteAdapter.NoteViewHolder {
    val binding = ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    return NoteViewHolder(binding)
  }

  override fun onBindViewHolder(holder: NoteAdapter.NoteViewHolder, position: Int) {
    holder.bind(listNotes[position])
  }

  override fun getItemCount(): Int {
    return this.listNotes.size
  }
}