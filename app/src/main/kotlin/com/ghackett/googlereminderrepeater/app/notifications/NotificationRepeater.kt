package com.ghackett.googlereminderrepeater.app.notifications


import android.app.Notification
import android.service.notification.StatusBarNotification
import com.ghackett.googlereminderrepeater.app.R
import javax.inject.Inject


private const val GOOGLE_PACKAGE = "com.google.android.googlequicksearchbox"
private const val NOTIFICATION_ID = 7839

class NotificationRepeater @Inject constructor(
  private val notifier: Notifier,
) {

  fun onNotificationPosted(sbn: StatusBarNotification) {
    if (sbn.packageName != GOOGLE_PACKAGE) return
    val title = sbn.stringExtra("android.title") ?: return
    val text = sbn.stringExtra("android.text") ?: return

    notifier.notify(GoogleRepeatChannel, NOTIFICATION_ID) {
      setSmallIcon(R.drawable.ic_launcher_foreground)
      setCategory(Notification.CATEGORY_EVENT)
      setContentTitle(title)
      setContentText(text)
    }
  }
}

private fun StatusBarNotification.stringExtra(name: String): String? =
  notification.extras.getString(name)?.takeIf { it.isNotBlank() }?.trim()
