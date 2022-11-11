package com.ghackett.googlereminderrepeater.app.notifications

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.res.Resources
import androidx.core.app.NotificationCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class Notifier @Inject constructor(
  private val notificationManager: NotificationManager,
  @ApplicationContext val appContext: Context,
) {
  fun notify(channel: NotificationChannelInfo, id: Int, notification: Notification) {
    notificationManager.run {
      prep(channel)
      notify(id, notification)
    }
  }
}

inline fun Notifier.notify(channel: NotificationChannelInfo, id: Int, action: NotificationCompat.Builder.(Resources) -> Unit) {
  notify(
    channel = channel,
    id = id,
    notification = NotificationCompat.Builder(appContext, channel.id)
      .apply { action(appContext.resources) }
      .build()
  )
}

private fun NotificationManager.prep(channel: NotificationChannelInfo) {
  if (getNotificationChannel(channel.id) == null) {
    createNotificationChannel(
      NotificationChannel(channel.id, channel.name, channel.importance).apply {
        description = channel.description
      }
    )
  }
}
