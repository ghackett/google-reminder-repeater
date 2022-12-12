package com.ghackett.googlereminderrepeater.app.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.res.Resources
import android.os.Build
import androidx.core.app.NotificationCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class Notifier @Inject constructor(
  private val notificationManager: NotificationManager,
  @ApplicationContext private val appContext: Context,
) {

  fun notify(channel: NotificationChannelInfo, id: Int, action: NotificationCompat.Builder.(Resources) -> Unit) {
    notificationManager.run {
      prepChannel(channel)
      notify(
        id,
        NotificationCompat.Builder(appContext, channel.id)
          .apply { action(appContext.resources) }
          .build()
      )
    }
  }

  private fun prepChannel(channel: NotificationChannelInfo) {
    if (Build.VERSION.SDK_INT < 26) return
    if (notificationManager.getNotificationChannel(channel.id) == null) {
      notificationManager.createNotificationChannel(
        NotificationChannel(
          channel.id,
          appContext.getString(channel.name),
          channel.importance
        ).apply {
          description = appContext.getString(channel.description)
        }
      )
    }
  }
}
