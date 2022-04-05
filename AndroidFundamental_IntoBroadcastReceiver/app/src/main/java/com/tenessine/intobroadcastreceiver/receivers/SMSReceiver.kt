package com.tenessine.intobroadcastreceiver.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.telephony.SmsMessage
import android.util.Log
import com.tenessine.intobroadcastreceiver.SMSReceiverActivity

class SMSReceiver : BroadcastReceiver() {

  override fun onReceive(context: Context, intent: Intent) {
    // sms yang diterima dibungkus dalam intent.extras
    val bundle = intent.extras
    try {
      if (bundle == null) return

      // pengambilan data sms masuk (pdu) dari intent
      val pdusObj = bundle.get("pdus") as Array<*>

      // seluruh sms yang masuk diperiksa
      for (aPdusObj in pdusObj) {
        // isi pesan sms diproses dalam method getIncomingMessage
        val currentMsg = getIncomingMessage(aPdusObj as Any, bundle)
        val senderNum = currentMsg.displayOriginatingAddress
        val message = currentMsg.displayMessageBody
        Log.d(TAG, "senderNum: $senderNum; message: $message")

        // lempar data sms ke SMSReceiverActivity dan tampilkan
        val showSMSIntent = Intent(context, SMSReceiverActivity::class.java)

        //flag untuk menampilkan activity paling depan
        showSMSIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        showSMSIntent.putExtra(SMSReceiverActivity.EXTRA_SMS_NO, senderNum)
        showSMSIntent.putExtra(SMSReceiverActivity.EXTRA_SMS_MESSAGE, message)

        context.startActivity(showSMSIntent)
      }
    } catch (e: Exception) {
      Log.d(TAG, "Exception smsReceiver $e")
    }
  }

  // menerjemahkan pdu ke sms
  private fun getIncomingMessage(aObject: Any, bundle: Bundle): SmsMessage {
    val currentSMS: SmsMessage
    val format = bundle.getString("format")
    currentSMS = if (Build.VERSION.SDK_INT >= 23) {
      SmsMessage.createFromPdu(aObject as ByteArray, format)
    } else {
      // api 23 ke bawah ga pakai format tertentu
      SmsMessage.createFromPdu(aObject as ByteArray)
    }
    return currentSMS
  }

  companion object {
    private val TAG = SMSReceiver::class.java.simpleName
  }
}