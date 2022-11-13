package com.ghackett.googlereminderrepeater.app.googlenotifications

import android.service.notification.StatusBarNotification

data class GoogleNotification(val title: String, val text: String)

private const val GOOGLE_PACKAGE = "com.google.android.googlequicksearchbox"

fun StatusBarNotification.findGoogleNotification(): GoogleNotification? {
  if (packageName != GOOGLE_PACKAGE) return null
  return GoogleNotification(
    title = stringExtra("android.title") ?: return null,
    text = stringExtra("android.text") ?: return null,
  )
}

private fun StatusBarNotification.stringExtra(name: String): String? =
  notification.extras.getString(name)?.takeIf { it.isNotBlank() }?.trim()
