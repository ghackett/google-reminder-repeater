package com.ghackett.googlereminderrepeater.app

import android.service.notification.StatusBarNotification
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GoogleNotification(
  @SerialName("title") val title: String,
  @SerialName("text") val text: String,
  @SerialName("capturedAt") val capturedAt: Long = System.currentTimeMillis(),
)

private const val GOOGLE_PACKAGE = "com.google.android.googlequicksearchbox"

fun StatusBarNotification.findGoogleNotification(): GoogleNotification? {
  if (packageName != GOOGLE_PACKAGE) return null
  return GoogleNotification(
    title = nonEmptyStringExtra("android.title") ?: return null,
    text = nonEmptyStringExtra("android.text") ?: return null,
  )
}

private fun StatusBarNotification.nonEmptyStringExtra(name: String): String? =
  notification.extras.getString(name)?.takeIf { it.isNotBlank() }?.trim()
