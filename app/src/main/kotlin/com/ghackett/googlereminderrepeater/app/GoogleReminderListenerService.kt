package com.ghackett.googlereminderrepeater.app

import android.content.ComponentName
import android.content.Context
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import com.ghackett.googlereminderrepeater.app.notifications.Notifier
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

fun Context.googleReminderListenerServiceComponentName(): ComponentName = ComponentName(this, GoogleReminderListenerService::class.java)

@AndroidEntryPoint class GoogleReminderListenerService : NotificationListenerService() {

  @Inject lateinit var notifier: Notifier

  override fun onNotificationPosted(sbn: StatusBarNotification?) {
    super.onNotificationPosted(sbn)

    val notification = sbn?.findGoogleNotification() ?: return
    notifier.notify(notification)
  }
}
