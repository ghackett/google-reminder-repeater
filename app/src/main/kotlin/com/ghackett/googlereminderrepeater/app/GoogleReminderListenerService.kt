package com.ghackett.googlereminderrepeater.app

import android.app.NotificationManager
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint class GoogleReminderListenerService : NotificationListenerService() {

  @Inject lateinit var notificationManager: NotificationManager

  override fun onNotificationPosted(sbn: StatusBarNotification?) {
    super.onNotificationPosted(sbn)

  }
}
