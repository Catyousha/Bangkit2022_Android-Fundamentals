package com.tenessine.intocustomview

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import androidx.core.view.setPadding

class MyEditText : AppCompatEditText, View.OnTouchListener {
  internal lateinit var mClearButtonImage: Drawable

  constructor(context: Context) : super(context) {
    init()
  }
  constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
    init()
  }
  constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
    init()
  }

  override fun onDraw(canvas: Canvas) {
    super.onDraw(canvas)
    hint = "Masukkan nama anda"

    textAlignment = View.TEXT_ALIGNMENT_CENTER
  }


  private fun init() {
    setHintTextColor(ContextCompat.getColor(context, R.color.white))
    setTextColor(ContextCompat.getColor(context, R.color.white))
    background = ContextCompat.getDrawable(context, R.drawable.bg_edit)
    mClearButtonImage = ContextCompat.getDrawable(context, R.drawable.ic_baseline_close_24) as Drawable
    setOnTouchListener(this)

    addTextChangedListener(object: TextWatcher {
      override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

      }

      override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        if(s.toString().isNotEmpty()) showClearButton()
      }

      override fun afterTextChanged(s: Editable?) {

      }

    })
  }


  private fun showClearButton() {
    setCompoundDrawablesWithIntrinsicBounds(null, null, mClearButtonImage, null)
  }

  private fun hideClearButton() {
    setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)
  }

  override fun onTouch(v: View?, event: MotionEvent?): Boolean {
    if(compoundDrawables[2] != null) {
      val clearButtonStart: Float
      val clearButtonEnd: Float
      var isClearButtonClicked = false

      when(layoutDirection) {
        View.LAYOUT_DIRECTION_RTL -> {
          clearButtonEnd = (mClearButtonImage.intrinsicWidth + paddingStart).toFloat()
          if (event != null) {
            when {
              event.x < clearButtonEnd -> isClearButtonClicked = true
            }
          }
        }
        else -> {
          clearButtonStart = (width - paddingEnd - mClearButtonImage.intrinsicWidth).toFloat()
          if (event != null) {
            when {
              event.x > clearButtonStart -> isClearButtonClicked = true
            }
          }
        }
      }

      when {
        isClearButtonClicked -> if (event != null) {
          mClearButtonImage = ContextCompat.getDrawable(context, R.drawable.ic_baseline_close_24) as Drawable
          return when (event.action) {
            MotionEvent.ACTION_DOWN -> {
              showClearButton()
              true
            }
            MotionEvent.ACTION_UP -> {
              text?.clear()
              hideClearButton()
              true
            }
            else -> false
          }
        }
        else -> false
      }
    }
    return false
  }
}