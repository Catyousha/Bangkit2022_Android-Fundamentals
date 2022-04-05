package com.tenessine.intobroadcastreceiver

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tenessine.intobroadcastreceiver.databinding.ActivitySmsReceiverBinding

class SMSReceiverActivity : AppCompatActivity() {
  private var _binding: ActivitySmsReceiverBinding? = null
  private val binding get() = _binding!!

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    _binding = ActivitySmsReceiverBinding.inflate(layoutInflater)

    setContentView(binding.root)

    title = getString(R.string.incoming_message)
    binding.btnClose.setOnClickListener {
      finish()
    }

    // tangkap yang dikirimkan oleh SMSReceiver
    val senderNo = intent.getStringExtra(EXTRA_SMS_NO)
    val senderMessage = intent.getStringExtra(EXTRA_SMS_MESSAGE)

    binding.apply {
      tvFrom.text = senderNo
      tvMessage.text = senderMessage
    }
  }

  override fun onDestroy() {
    super.onDestroy()
    _binding = null
  }

  companion object {
    const val EXTRA_SMS_NO = "extra_sms_no"
    const val EXTRA_SMS_MESSAGE = "extra_sms_message"
  }
}