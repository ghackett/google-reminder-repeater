package com.ghackett.googlereminderrepeater.app.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class Notifier @Inject constructor(
  private val notificationManager: NotificationManager,
  @ApplicationContext private val appContext: Context,
) {
  fun notify(channel: NotificationChannelInfo, id: Int, action: NotificationCompat.Builder.() -> Unit) {
    notificationManager.prep(channel)
    notificationManager.notify(id, NotificationCompat.Builder(appContext, channel.id).apply(action).build())
  }
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
