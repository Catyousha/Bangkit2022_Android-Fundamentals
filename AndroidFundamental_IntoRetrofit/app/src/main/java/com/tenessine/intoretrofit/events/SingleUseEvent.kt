package com.tenessine.intoretrofit.events

open class SingleUseEvent<out T>(private val content: T) {
  @Suppress("MemberVisibilityCanBePrivate")
  var hasBeenHandled = false
    private set

  fun getContentIfNotHandled(): T? {
    return if (hasBeenHandled) {
      null
    } else {
      hasBeenHandled = true
      content
    }
  }
}