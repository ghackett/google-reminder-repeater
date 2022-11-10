package com.ghackett.googlereminderrepeater.app.notifications


import android.app.Notification
import android.app.NotificationManager
import android.content.Context
import android.graphics.BitmapFactory
import android.service.notification.StatusBarNotification
import androidx.core.app.NotificationCompat
import com.ghackett.googlereminderrepeater.app.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


private const val GOOGLE_PACKAGE = "com.google.android.googlequicksearchbox"
private const val NOTIFICATION_ID = 7839
class NotificationRepeater @Inject constructor(
  private val notificationManager: NotificationManager,
  @ApplicationContext private val appContext: Context,
) {

  fun onNotificationPosted(sbn: StatusBarNotification) {
    if (sbn.packageName != GOOGLE_PACKAGE) return
    val title = sbn.notification.extras.getString("android.title")?.trim()?.takeIf { it.isNotBlank() } ?: return
    val text = sbn.notification.extras.getString("android.text")?.trim()?.takeIf { it.isNotBlank() } ?: return

    notificationManager.notify(
      NOTIFICATION_ID,
      notificationManager.buildNotification(appContext, GoogleRepeatChannel)
        .setLargeIcon(BitmapFactory.decodeResource(appContext.resources, R.drawable.ic_launcher_foreground))
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .setCategory(Notification.CATEGORY_EVENT)
        .setContentTitle(title)
        .setContentText(text)
        .build()
    )
  }

}


