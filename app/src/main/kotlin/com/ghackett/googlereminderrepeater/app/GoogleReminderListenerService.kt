package com.ghackett.googlereminderrepeater.app

import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import com.ghackett.googlereminderrepeater.app.googlenotifications.GoogleNotificationRepeater
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint class GoogleReminderListenerService : NotificationListenerService() {

  @Inject lateinit var repeater: GoogleNotificationRepeater

  override fun onNotificationPosted(sbn: StatusBarNotification?) {
    super.onNotificationPosted(sbn)
    if (sbn != null) repeater.onNotificationPosted(sbn)
  }
}
