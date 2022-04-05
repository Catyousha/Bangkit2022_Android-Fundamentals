package com.tenessine.intoalarmmanager

import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import java.util.*

// memanfaatkan TimerPickerDialog untuk ditampilkan
class TimePickerFragment : DialogFragment(), TimePickerDialog.OnTimeSetListener {
  private var mListener: DialogTimeListener? = null

  // interface listener, yang akan dipanggil saat pilihan waktu disimpan
  // dibuat oleh Activity yang menggunakan TimePickerFragment
  override fun onAttach(context: Context) {
    super.onAttach(context)
    mListener = context as DialogTimeListener
  }

  // menghapus listener saat fragment dihancurkan
  override fun onDetach() {
    super.onDetach()
    mListener = null
  }

  // membuat TimePickerDialog
  // menggunakan Calendar untuk mengambil waktu saat ini
  override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
    val calendar = Calendar.getInstance()
    val hour = calendar.get(Calendar.HOUR_OF_DAY)
    val minute = calendar.get(Calendar.MINUTE)
    val formatHour24 = true
    return TimePickerDialog(activity, this, hour, minute, formatHour24)
  }

  // menyimpan waktu yang dipilih pada TimePickerDialog
  override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
    mListener?.onDialogTimeSet(tag, hourOfDay, minute)
  }

  // interface listener, yang akan dipanggil saat pilihan waktu disimpan
  interface DialogTimeListener {
    fun onDialogTimeSet(tag: String?, hourOfDay: Int, minute: Int)
  }

}