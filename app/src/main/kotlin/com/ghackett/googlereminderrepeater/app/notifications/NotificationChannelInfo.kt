package com.ghackett.googlereminderrepeater.app.notifications

import android.app.NotificationManager

sealed interface NotificationChannelInfo {
  val id: String
  val name: String
  val description: String
  val importance: Int get() = NotificationManager.IMPORTANCE_DEFAULT
}

object GoogleRepeatChannel : NotificationChannelInfo {
  override val id = "GoogleRepeat"
  override val name = "Google Repeater Notification"
  override val description = "Repeated notifications from the google app"
  override val importance = NotificationManager.IMPORTANCE_HIGH
}
