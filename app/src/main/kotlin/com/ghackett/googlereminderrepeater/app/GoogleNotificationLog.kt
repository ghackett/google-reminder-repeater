package com.ghackett.googlereminderrepeater.app

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GoogleNotificationLog(
  @SerialName("maxEntryCount") val maxEntryCount: Int = 5,
  @SerialName("entries") val entries: List<GoogleNotification> = emptyList()
)

fun GoogleNotificationLog.withNewEntry(entry: GoogleNotification): GoogleNotificationLog = copy(
  entries = (listOf(entry) + entries).trimmed(maxEntryCount)
)

fun GoogleNotificationLog.withMaxEntryCount(count: Int): GoogleNotificationLog = copy(
  maxEntryCount = count,
)

fun GoogleNotificationLog.trimmed(): GoogleNotificationLog = copy(
  entries = entries.trimmed(maxEntryCount)
)

private fun List<GoogleNotification>.trimmed(maxEntryCount: Int): List<GoogleNotification> {
  val size = size
  if (maxEntryCount >= size) return this
  return dropLast(size - maxEntryCount)
}
