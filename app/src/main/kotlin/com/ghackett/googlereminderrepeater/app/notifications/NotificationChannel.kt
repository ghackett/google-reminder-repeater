package com.ghackett.googlereminderrepeater.app.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat

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
}

fun NotificationManager.buildNotification(context: Context, info: NotificationChannelInfo): NotificationCompat.Builder {
  if (getNotificationChannel(info.id) == null) {
    createNotificationChannel(NotificationChannel(info.id, info.name, info.importance).apply {
      description = info.description
    })
  }
  return NotificationCompat.Builder(context, info.id)
}
