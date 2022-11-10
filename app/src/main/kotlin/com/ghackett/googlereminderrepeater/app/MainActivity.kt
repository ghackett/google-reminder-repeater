package com.ghackett.googlereminderrepeater.app

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.ghackett.googlereminderrepeater.app.ui.theme.AppScaffold
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint class MainActivity : ComponentActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      AppScaffold {
        Column(
          verticalArrangement = Arrangement.Center,
          horizontalAlignment = Alignment.CenterHorizontally,
          modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())
        ) {
          Button(onClick = ::launchNotificationListenerPermissionsScreen) {
            Text(text = "Notification Listener Permission")
          }
          Button(onClick = ::launchNotificationSettingsScreen) {
            Text(text = "App Notification Settings")
          }
          Button(onClick = ::launchAppDetailsScreen) {
            Text(text = "App Details")
          }
        }
      }
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
}
