package com.tenessine.intoalarmmanager

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.tenessine.intoalarmmanager.databinding.ActivityMainBinding
import com.tenessine.intoalarmmanager.receivers.AlarmReceiver
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity(), DatePickerFragment.DialogDateListener,
  TimePickerFragment.DialogTimeListener {
  private var _binding: ActivityMainBinding? = null
  private val binding get() = _binding!!

  private lateinit var alarmReceiver: AlarmReceiver

  @RequiresApi(Build.VERSION_CODES.M)
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    _binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    binding.apply {
      btnOnceDate.setOnClickListener {
        val datePickerFragment = DatePickerFragment()
        datePickerFragment.show(supportFragmentManager, DATE_PICKER_TAG)
      }

      btnOnceTime.setOnClickListener {
        val timePickerFragmentOne = TimePickerFragment()
        timePickerFragmentOne.show(supportFragmentManager, TIME_PICKER_ONCE_TAG)
      }

      btnSetOnceAlarm.setOnClickListener {
        val onceDate = binding.tvOnceDate.text.toString()
        val onceTime = binding.tvOnceTime.text.toString()
        val onceMessage = binding.edtOnceMessage.text.toString()

        alarmReceiver.setOneTimeAlarm(
          this@MainActivity,
          AlarmReceiver.TYPE_ONE_TIME,
          onceDate,
          onceTime,
          onceMessage
        )
      }

      btnRepeatingTime.setOnClickListener {
        val timePickerFragment = TimePickerFragment()
        timePickerFragment.show(supportFragmentManager, TIME_PICKER_REPEAT_TAG)
      }

      btnSetRepeatingAlarm.setOnClickListener {
        val repeatTime = binding.tvRepeatingTime.text.toString()
        val repeatMessage = binding.edtRepeatingMessage.text.toString()
        alarmReceiver.setRepeatingAlarm(
          this@MainActivity,
          AlarmReceiver.TYPE_REPEATING,
          repeatTime,
          repeatMessage
        )
      }

      btnCancelRepeatingAlarm.setOnClickListener {
        alarmReceiver.cancelAlarm(
          this@MainActivity,
          AlarmReceiver.TYPE_REPEATING,
        )
      }
    }

    alarmReceiver = AlarmReceiver()
  }

  override fun onDialogDateSet(tag: String?, year: Int, month: Int, dayOfMonth: Int) {
    val calendar = Calendar.getInstance()
    calendar.set(year, month, dayOfMonth)

    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    binding.tvOnceDate.text = dateFormat.format(calendar.time)
  }

  override fun onDialogTimeSet(tag: String?, hourOfDay: Int, minute: Int) {
    val calendar = Calendar.getInstance()
    calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
    calendar.set(Calendar.MINUTE, minute)

    val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    when (tag) {
      TIME_PICKER_ONCE_TAG -> binding.tvOnceTime.text = timeFormat.format(calendar.time)
      TIME_PICKER_REPEAT_TAG -> binding.tvRepeatingTime.text = timeFormat.format(calendar.time)
      else -> {}
    }
  }

  override fun onDestroy() {
    super.onDestroy()
    _binding = null
  }

  companion object {
    private const val DATE_PICKER_TAG = "DatePicker"
    private const val TIME_PICKER_ONCE_TAG = "TimePickerOnce"
    private const val TIME_PICKER_REPEAT_TAG = "TimePickerRepeat"
  }


}