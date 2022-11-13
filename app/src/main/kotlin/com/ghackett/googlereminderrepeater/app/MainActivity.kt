package com.ghackett.googlereminderrepeater.app

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.ghackett.googlereminderrepeater.app.notifications.Notifier
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint class MainActivity : ComponentActivity() {

  @Inject lateinit var notifier: Notifier

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      MainUI(
        launchNotificationListenerPermissionsScreen = ::launchNotificationListenerPermissionsScreen,
        launchNotificationSettingsScreen = ::launchNotificationSettingsScreen,
        launchAppDetailsScreen = ::launchAppDetailsScreen,
        sendTestNotification = ::sendTestNotification
      )
    }
  }

  private fun launchNotificationListenerPermissionsScreen() {
    startActivity(Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS))
  }

  private fun launchNotificationSettingsScreen() {
    startActivity(Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS).apply {
      putExtra(Settings.EXTRA_APP_PACKAGE, this@MainActivity.packageName)
    })
  }

  private fun launchAppDetailsScreen() {
    startActivity(Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
      putExtra(Settings.EXTRA_APP_PACKAGE, this@MainActivity.packageName)
      data = Uri.parse("package:${this@MainActivity.packageName}")
    })
  }

  private fun sendTestNotification() = notifier.notify(GoogleNotification(
    title = "Test notification title",
    text = "Test notification text"
  ))
}
