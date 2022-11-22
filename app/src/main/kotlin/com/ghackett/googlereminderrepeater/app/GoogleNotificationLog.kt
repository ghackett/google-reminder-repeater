package com.ghackett.googlereminderrepeater.app

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GoogleNotificationLog(
  @SerialName("maxEntryCount") val maxEntryCount: Int = 10,
  @SerialName("entries") val entries: List<GoogleNotification> = emptyList()
)

fun GoogleNotificationLog.withNewEntry(entry: GoogleNotification): GoogleNotificationLog = copy(
  entries = (listOf(entry) + entries).trimmed(maxEntryCount)
)

fun GoogleNotificationLog.withMaxEntryCount(count: Int, trim: Boolean): GoogleNotificationLog = copy(
  maxEntryCount = count,
  entries = if (trim) entries.trimmed(count) else entries
)

private fun List<GoogleNotification>.trimmed(maxEntryCount: Int): List<GoogleNotification> {
  val size = size
  if (maxEntryCount >= size) return this
  return dropLast(size - maxEntryCount)
}
