@file:OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)

package com.ghackett.googlereminderrepeater.app

import android.Manifest
import android.os.Build
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ghackett.googlereminderrepeater.app.notifications.NotificationListenerPermission
import com.ghackett.googlereminderrepeater.app.notifications.notificationListenerPermissionState
import com.ghackett.googlereminderrepeater.app.ui.theme.AppScaffold
import com.google.accompanist.flowlayout.FlowCrossAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
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
    Build.VERSION.SDK_INT >= 33 && postPermissionState.status.isGranted.not() -> MissingPermissionScreen(
      "The app is missing the permission to POST notifications.",
      postPermissionState::launchPermissionRequest
    )
    readPermissionState == NotificationListenerPermission.NOT_GRANTED         -> MissingPermissionScreen(
      "The app is missing the permission to READ notifications.",
      launcher::launchNotificationListenerPermissionsScreen
    )
    else                                                                      -> AppScaffold(actions = { HamburgerMenu(launcher) }) {
      ScreenContent(
        viewModel = viewModel,
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

@Composable private fun ScreenContent(viewModel: MainActivityViewModel, launcher: UiActionLauncher, unknownListenerPermission: Boolean) {
  Column(modifier = Modifier.fillMaxSize()) {
    if (unknownListenerPermission) UnknownListenerPermissionHeader(launcher)

    val log by viewModel.log.collectAsState()
    log?.let { LogContent(viewModel = viewModel, log = it) }
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
    Spacer(modifier = Modifier.height(8.dp))
  }
}

@Composable private fun UnknownListenerPermissionHeader(launcher: UiActionLauncher) {
  Text(text = "Unable to detect Notification Listener Permission. Make sure Google Reminder Repeater is granted notification access.")
  Spacer(modifier = Modifier.height(4.dp))
  PillBtn(text = "Notification Listener Permission", onClick = launcher::launchNotificationListenerPermissionsScreen)
}

@Composable private fun LogContent(viewModel: MainActivityViewModel, log: LogViewState) {
  val loggingEnabled by viewModel.loggingEnabled.collectAsState()
  LazyColumn {
    item {
      Spacer(modifier = Modifier.height(4.dp))
      LogSettingsHeader(
        loggingEnabled = loggingEnabled,
        maxEntryCount = log.logSize,
        setLoggingEnabled = viewModel::setLoggingEnabled,
        setMaxEntries = viewModel::setLogSize,
        trimLog = viewModel::trimLog,
        clearLog = viewModel::clearLog,
      )
    }
    item { Spacer(modifier = Modifier.height(12.dp)) }
    items(
      items = log.entries,
      key = { it.notification.capturedAt },
      itemContent = { LogItem(item = it) }
    )
  }
}

@Composable private fun LogSettingsHeader(
  loggingEnabled: Boolean,
  maxEntryCount: String,
  setLoggingEnabled: (Boolean) -> Unit,
  setMaxEntries: (String) -> Unit,
  trimLog: () -> Unit,
  clearLog: () -> Unit,
) {
  FlowRow(modifier = Modifier.fillMaxWidth().padding(4.dp),
    crossAxisAlignment = FlowCrossAxisAlignment.Center,
    crossAxisSpacing = 4.dp
  ) {
    LoggingEnabledCheckbox(loggingEnabled = loggingEnabled, setLoggingEnabled = setLoggingEnabled)
    Spacer(modifier = Modifier.width(8.dp))
    LogSizePreference(loggingEnabled = loggingEnabled, maxEntryCount = maxEntryCount, setMaxEntries = setMaxEntries, trimLog = trimLog)
    Spacer(modifier = Modifier.width(8.dp))
    PillBtn(text = "Clear Log", onClick = clearLog)
  }
}

@Composable private fun LoggingEnabledCheckbox(loggingEnabled: Boolean, setLoggingEnabled: (Boolean) -> Unit) {
  Surface(
    shape = RoundedCornerShape(8.dp),
    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
    modifier = Modifier
      .clickable { setLoggingEnabled(!loggingEnabled) }
      .height(64.dp),
  ) {
    Row(verticalAlignment = Alignment.CenterVertically) {
      Checkbox(checked = loggingEnabled, onCheckedChange = setLoggingEnabled)
      Spacer(modifier = Modifier.width(4.dp))
      Text(text = "Enable Logging")
      Spacer(modifier = Modifier.width(8.dp))
    }
  }
}

@Composable private fun LogSizePreference(
  loggingEnabled: Boolean,
  maxEntryCount: String,
  setMaxEntries: (String) -> Unit,
  trimLog: () -> Unit,
) {
  Surface(shape = RoundedCornerShape(8.dp), border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 4.dp)) {
      Spacer(modifier = Modifier.width(8.dp))
      Text(text = "Notifications to log:")
      TextField(
        enabled = loggingEnabled,
        value = maxEntryCount,
        onValueChange = setMaxEntries,
        singleLine = true,
        maxLines = 1,
        keyboardOptions = KeyboardOptions(
          autoCorrect = false,
          keyboardType = KeyboardType.Number,
          imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
          onDone = {
            trimLog()
            defaultKeyboardAction(ImeAction.Done)
          }
        ),
        modifier = Modifier.width(48.dp)
      )
      Spacer(modifier = Modifier.width(8.dp))
    }
  }
}

@Composable private fun LogItem(item: LogEntryViewState) = Column(modifier = Modifier.padding(horizontal = 8.dp)) {
  Surface(shadowElevation = 8.dp, shape = RoundedCornerShape(8.dp)) {
    Column(modifier = Modifier
      .fillMaxWidth()
      .padding(8.dp)) {
      Text(text = item.notification.title, style = MaterialTheme.typography.bodyLarge)
      Spacer(modifier = Modifier.height(8.dp))
      Row(modifier = Modifier.fillMaxWidth()) {
        Text(text = item.notification.text, style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.weight(1f))
        Text(text = item.timestamp, style = MaterialTheme.typography.labelMedium)
      }
    }
  }
  Spacer(modifier = Modifier.height(12.dp))
}

@Composable private fun PillBtn(text: String, onClick: () -> Unit) {
  Button(onClick = onClick, modifier = Modifier.padding(horizontal = 4.dp)) {
    Text(text = text)
  }
}

