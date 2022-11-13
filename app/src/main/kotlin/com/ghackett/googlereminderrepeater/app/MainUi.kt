package com.ghackett.googlereminderrepeater.app

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.ghackett.googlereminderrepeater.app.ui.theme.AppScaffold

@Composable fun MainUI(
  launcher: UiActionLauncher,
) {
  AppScaffold {
    Column(
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally,
      modifier = Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState())
    ) {
      Button(onClick = launcher::launchNotificationListenerPermissionsScreen) {
        Text(text = "Notification Listener Permission")
      }
      Button(onClick = launcher::launchNotificationSettingsScreen) {
        Text(text = "App Notification Settings")
      }
      Button(onClick = launcher::launchAppDetailsScreen) {
        Text(text = "App Details")
      }
      Button(onClick = launcher::sendTestNotification) {
        Text(text = "Send Test Notification")
      }
    }
  }
}
