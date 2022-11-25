@file:OptIn(ExperimentalPermissionsApi::class)

package com.ghackett.googlereminderrepeater.app

import android.Manifest
import android.os.Build
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ghackett.googlereminderrepeater.app.notifications.NotificationListenerPermission
import com.ghackett.googlereminderrepeater.app.notifications.notificationListenerPermissionState
import com.ghackett.googlereminderrepeater.app.ui.theme.AppScaffold
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

@Composable fun MainUI(
  viewModel: MainActivityViewModel,
  launcher: UiActionLauncher,
) {
  val postPermissionState = rememberPermissionState(permission = Manifest.permission.POST_NOTIFICATIONS)
  val readPermissionState by notificationListenerPermissionState()

  when {
    postPermissionState.status.isGranted.not()                        -> MissingPermissionScreen(
      "The app is missing the permission to POST notifications.",
      postPermissionState::launchPermissionRequest
    )
    readPermissionState == NotificationListenerPermission.NOT_GRANTED -> MissingPermissionScreen(
      "The app is missing the permission to READ notifications.",
      launcher::launchNotificationListenerPermissionsScreen
    )
    else                                                              -> AppScaffold(actions = { HamburgerMenu(launcher) }) {
      ScreenContent(
        launcher = launcher,
        unknownListenerPermission = readPermissionState == NotificationListenerPermission.UNKNOWN,
      )
    }
  }
}

@Composable private fun HamburgerMenu(launcher: UiActionLauncher) {
  var expanded by remember { mutableStateOf(false) }
  val launch: (() -> Unit) -> () -> Unit = {
    {
      it.invoke()
      expanded = false
    }
  }
  Box(modifier = Modifier.wrapContentSize(Alignment.TopStart)) {
    IconButton(onClick = { expanded = true }) {
      Icon(
        Icons.Default.Menu,
        tint = MaterialTheme.colorScheme.onPrimary,
        contentDescription = "Menu"
      )
    }

    DropdownMenu(
      expanded = expanded,
      onDismissRequest = { expanded = false }
    ) {
      DropdownMenuItem(text = { Text("Notification Listener Permission") },
        onClick = launch(launcher::launchNotificationListenerPermissionsScreen))
      if (Build.VERSION.SDK_INT >= 26) {
        DropdownMenuItem(text = { Text("App Notification Settings") }, onClick = launch(launcher::launchNotificationSettingsScreen))
      } else {
        DropdownMenuItem(text = { Text("App Details") }, onClick = launch(launcher::launchAppDetailsScreen))
      }
      DropdownMenuItem(text = { Text("Send Test Notification") }, onClick = launch(launcher::sendTestNotification))
    }
  }
}

@Composable private fun ScreenContent(launcher: UiActionLauncher, unknownListenerPermission: Boolean) {
  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(4.dp)
  ) {
    if (unknownListenerPermission) UnknownListenerPermissionHeader(launcher)

  }
}

@Composable private fun MissingPermissionScreen(text: String, launchPermissionRequest: () -> Unit) = AppScaffold {
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


@Composable private fun UnknownListenerPermissionHeader(launcher: UiActionLauncher) {
  Text(text = "Unable to detect Notification Listener Permission. Make sure Google Reminder Repeater is granted notification access.")
  Spacer(modifier = Modifier.height(4.dp))
  PillBtn(text = "Notification Listener Permission", onClick = launcher::launchNotificationListenerPermissionsScreen)
}

@Composable private fun PillBtn(text: String, onClick: () -> Unit) {
  Button(onClick = onClick, modifier = Modifier.padding(horizontal = 4.dp)) {
    Text(text = text)
  }
}
