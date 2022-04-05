package com.tenessine.intoroom.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.tenessine.intoroom.database.entity.Note
import com.tenessine.intoroom.databinding.ItemNoteBinding
import com.tenessine.intoroom.helper.NoteDiffCallback
import com.tenessine.intoroom.ui.insert.NoteAddUpdateActivity

class NoteAdapter : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {
  // tempat menampung daftar note untuk ditampilkan pada recyclerview
  private val listNotes = ArrayList<Note>()

  // mengatur perubahan yang ada pada list note
  fun setListNotes(listNotes: List<Note>) {
    // this.listNotes = data lama
    // listNotes = data baru
    val diffCallback = NoteDiffCallback(this.listNotes, listNotes)
    val diffResult = DiffUtil.calculateDiff(diffCallback)

    // mengisi listNotes dengan data baru
    this.listNotes.clear()
    this.listNotes.addAll(listNotes)

    // prubahan yang ada pada listNotes akan ditangani secara otomatis
    diffResult.dispatchUpdatesTo(this)
  }

  inner class NoteViewHolder(private val binding: ItemNoteBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(note: Note) {
      binding.apply {
        tvItemTitle.text = note.title
        tvItemDescription.text = note.description
        tvItemDate.text = note.date

        cvItemNote.setOnClickListener {
          val intent = Intent(it.context, NoteAddUpdateActivity::class.java)
          intent.putExtra(NoteAddUpdateActivity.EXTRA_NOTE, note)
          it.context.startActivity(intent)
        }
      }
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
    val binding = ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    return NoteViewHolder(binding)
  }

  override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
    holder.bind(listNotes[position])
  }

  override fun getItemCount(): Int {
    return listNotes.size
  }
}