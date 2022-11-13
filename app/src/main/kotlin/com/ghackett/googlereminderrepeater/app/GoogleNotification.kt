package com.ghackett.googlereminderrepeater.app

import android.app.Notification
import android.service.notification.StatusBarNotification
import com.ghackett.googlereminderrepeater.app.notifications.GoogleRepeatChannel
import com.ghackett.googlereminderrepeater.app.notifications.Notifier

data class GoogleNotification(val title: String, val text: String)

private const val GOOGLE_PACKAGE = "com.google.android.googlequicksearchbox"
private const val GOOGLE_REPEATER_NOTIFICATION_ID = 7839

fun StatusBarNotification.findGoogleNotification(): GoogleNotification? {
  if (packageName != GOOGLE_PACKAGE) return null
  return GoogleNotification(
    title = nonEmptyStringExtra("android.title") ?: return null,
    text = nonEmptyStringExtra("android.text") ?: return null,
  )
}

fun Notifier.notify(googleNotification: GoogleNotification) = notify(GoogleRepeatChannel, GOOGLE_REPEATER_NOTIFICATION_ID) {
  setSmallIcon(R.drawable.ic_launcher_foreground)
  setCategory(Notification.CATEGORY_EVENT)
  setContentTitle(googleNotification.title)
  setContentText(googleNotification.text)
}

private fun StatusBarNotification.nonEmptyStringExtra(name: String): String? =
  notification.extras.getString(name)?.takeIf { it.isNotBlank() }?.trim()
