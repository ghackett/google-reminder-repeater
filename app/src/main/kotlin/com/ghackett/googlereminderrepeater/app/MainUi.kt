package com.ghackett.googlereminderrepeater.app

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ghackett.googlereminderrepeater.app.ui.theme.AppScaffold
import com.google.accompanist.flowlayout.FlowRow

@Composable fun MainUI(
  launcher: UiActionLauncher,
) {
  AppScaffold {
    Column(modifier = Modifier
      .fillMaxSize()
      .padding(4.dp)
      .verticalScroll(rememberScrollState())) {
      SetupButtons(launcher)
    }
  }
}

@Composable private fun SetupButtons(launcher: UiActionLauncher) {
  FlowRow(
    modifier = Modifier
      .fillMaxSize()
      .padding(4.dp)
  ) {
    PillBtn(text = "App Details", onClick = launcher::launchAppDetailsScreen)
    PillBtn(text = "App Notification Settings", onClick = launcher::launchNotificationSettingsScreen)
    PillBtn(text = "Notification Listener Permission", onClick = launcher::launchNotificationListenerPermissionsScreen)
    PillBtn(text = "Send Test Notification", onClick = launcher::sendTestNotification)
  }
}

@Composable private fun PillBtn(text: String, onClick: () -> Unit) {
  Button(onClick = onClick, modifier = Modifier.padding(horizontal = 4.dp)) {
    Text(text = text)
  }
}
