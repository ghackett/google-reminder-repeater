package com.ghackett.googlereminderrepeater.app

import android.annotation.TargetApi
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.provider.Settings.EXTRA_NOTIFICATION_LISTENER_COMPONENT_NAME
import com.ghackett.googlereminderrepeater.app.notifications.Notifier
import javax.inject.Inject

class UiActionLauncher @Inject constructor(
  private val activity: Activity,
  private val notifier: Notifier,
) {

  fun launchNotificationListenerPermissionsScreen() {
    if (Build.VERSION.SDK_INT >= 30) {
      activity.startActivity(Intent(Settings.ACTION_NOTIFICATION_LISTENER_DETAIL_SETTINGS).apply {
        putExtra(EXTRA_NOTIFICATION_LISTENER_COMPONENT_NAME, activity.googleReminderListenerServiceComponentName().flattenToString())
      })
    } else {
      activity.startActivity(Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS))
    }
  }

  @TargetApi(26)
  fun launchNotificationSettingsScreen() {
    activity.startActivity(Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS).apply {
      putExtra(Settings.EXTRA_APP_PACKAGE, activity.packageName)
    })
  }

  fun launchAppDetailsScreen() {
    activity.startActivity(Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
      putExtra(Settings.EXTRA_APP_PACKAGE, activity.packageName)
      data = Uri.parse("package:${activity.packageName}")
    })
  }

  fun sendTestNotification() = notifier.notify(GoogleNotification(
    title = "Test notification title",
    text = "Test notification text"
  ))
}
