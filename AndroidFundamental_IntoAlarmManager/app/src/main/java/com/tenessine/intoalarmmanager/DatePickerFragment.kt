package com.tenessine.intoalarmmanager

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import java.util.*


// memanfaatkan DatePickerDialog untuk ditampilkan
class DatePickerFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {

  // interface listener, yang akan dipanggil saat pilihan tanggal disimpan
  // dibuat oleh Activity yang menggunakan DatePickerFragment
  private var mListener: DialogDateListener? = null

  // mendapatkan referensi ke Activity yang menggunakan DatePickerFragment
  // dan mengimplementasikan interface DialogDateListener
  override fun onAttach(context: Context) {
    super.onAttach(context)
    mListener = context as DialogDateListener
  }

  // menghapus listener saat fragment dihancurkan
  override fun onDetach() {
    super.onDetach()
    mListener = null
  }

  // membuat DatePickerDialog
  // menggunakan Calendar untuk mengambil tanggal saat ini
  override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    return DatePickerDialog(activity as Context, this, year, month, day)
  }

  // menyimpan pilihan tanggal yang dipilih pada DatePickerDialog
  override fun onDateSet(view: DatePicker, year: Int, month: Int, dayOfMonth: Int) {
    mListener?.onDialogDateSet(tag, year, month, dayOfMonth)
  }

  // interface yang akan dipanggil saat pilihan tanggal disimpan
  interface DialogDateListener {
    fun onDialogDateSet(tag: String?, year: Int, month: Int, dayOfMonth: Int)
  }
}