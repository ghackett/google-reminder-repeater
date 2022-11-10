package com.ghackett.googlereminderrepeater.app

import android.app.NotificationManager
import android.service.notification.StatusBarNotification
import javax.inject.Inject

private const val GOOGLE_PACKAGE = "com.google.android.googlequicksearchbox"
class NotificationRepeater @Inject constructor(notificationManager: NotificationManager) {
  fun onNotificationPosted(sbn: StatusBarNotification) {
    if (sbn.packageName != GOOGLE_PACKAGE) return


  }
}
