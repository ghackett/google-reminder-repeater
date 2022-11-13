@file:OptIn(ExperimentalPermissionsApi::class)

package com.ghackett.googlereminderrepeater.app

import android.Manifest
import android.os.Build
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ghackett.googlereminderrepeater.app.ui.theme.AppScaffold
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

@Composable fun MainUI(
  viewModel: MainActivityViewModel,
  launcher: UiActionLauncher,
) {
  AppScaffold {
    Column(modifier = Modifier.fillMaxSize()) {
      val postPermissionState = rememberPermissionState(permission = Manifest.permission.POST_NOTIFICATIONS)
      val readPermissionState by viewModel.readPermissionState.collectAsState()
      when {
        postPermissionState.status.isGranted.not() -> MissingPermission(
          "The app is missing the permission to POST notifications.",
          postPermissionState::launchPermissionRequest
        )
        readPermissionState.not() -> MissingPermission(
          "The app is missing the permission to READ notifications.",
          launcher::launchNotificationListenerPermissionsScreen
        )
        else                                       -> SetupButtons(launcher = launcher)
      }
    }
  }
}

@Composable private fun SetupButtons(launcher: UiActionLauncher) {
  FlowRow(
    modifier = Modifier
      .fillMaxSize()
      .padding(4.dp)
  ) {
    PillBtn(text = "Notification Listener Permission", onClick = launcher::launchNotificationListenerPermissionsScreen)
    if (Build.VERSION.SDK_INT >= 26) {
      PillBtn(text = "App Notification Settings", onClick = launcher::launchNotificationSettingsScreen)
    } else {
      PillBtn(text = "App Details", onClick = launcher::launchAppDetailsScreen)
    }
    PillBtn(text = "Send Test Notification", onClick = launcher::sendTestNotification)
  }
}

@Composable private fun PillBtn(text: String, onClick: () -> Unit) {
  Button(onClick = onClick, modifier = Modifier.padding(horizontal = 4.dp)) {
    Text(text = text)
  }
}

@Composable private fun MissingPermission(text: String, launchPermissionRequest: () -> Unit) {
  Column(
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = Modifier
      .fillMaxSize()
      .padding(8.dp)
      .verticalScroll(rememberScrollState()),
  ) {
    Text(text = text,
      modifier = Modifier.fillMaxWidth(),
      textAlign = TextAlign.Center)
    Spacer(modifier = Modifier.height(8.dp))
    PillBtn(text = "Grant permission", onClick = launchPermissionRequest)
  }
}
