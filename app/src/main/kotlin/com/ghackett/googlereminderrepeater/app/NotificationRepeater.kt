package com.ghackett.googlereminderrepeater.app

import android.app.NotificationManager
import android.service.notification.StatusBarNotification
import javax.inject.Inject

class NotificationRepeater @Inject constructor(notificationManager: NotificationManager) {
  fun onNotificationPosted(sbn: StatusBarNotification) {

  }
}
