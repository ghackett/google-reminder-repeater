package com.ghackett.googlereminderrepeater.app.googlenotifications


import android.app.Notification
import android.service.notification.StatusBarNotification
import com.ghackett.googlereminderrepeater.app.R
import com.ghackett.googlereminderrepeater.app.notifications.GoogleRepeatChannel
import com.ghackett.googlereminderrepeater.app.notifications.Notifier
import javax.inject.Inject


private const val NOTIFICATION_ID = 7839

class GoogleNotificationRepeater @Inject constructor(
  private val notifier: Notifier,
) {

  fun onNotificationPosted(sbn: StatusBarNotification) = sbn.findGoogleNotification()?.run {
    notifier.notify(GoogleRepeatChannel, NOTIFICATION_ID) {
      setSmallIcon(R.drawable.ic_launcher_foreground)
      setCategory(Notification.CATEGORY_EVENT)
      setContentTitle(title)
      setContentText(text)
    }
  }
}
