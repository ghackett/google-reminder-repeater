package com.ghackett.googlereminderrepeater.app.notifications

import android.content.Context
import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import com.ghackett.googlereminderrepeater.app.googleReminderListenerServiceComponentName
import com.ghackett.googlereminderrepeater.app.lifecycle.eventFlow
import com.ghackett.googlereminderrepeater.app.notificationManager
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map

enum class NotificationListenerPermission { GRANTED, NOT_GRANTED, UNKNOWN }

@Composable fun notificationListenerPermissionState(): State<NotificationListenerPermission> {
  val context = LocalContext.current
  val lifecycle = LocalLifecycleOwner.current.lifecycle
  val flow = remember {
    lifecycle.eventFlow()
      .filter { it == Lifecycle.Event.ON_RESUME }
      .map { context.isNotificationListenerAccessGranted() }
  }
  return flow.collectAsState(initial = context.isNotificationListenerAccessGranted())
}

private fun Context.isNotificationListenerAccessGranted(): NotificationListenerPermission {
  if (Build.VERSION.SDK_INT < 27) return NotificationListenerPermission.UNKNOWN

  return when (notificationManager().isNotificationListenerAccessGranted(googleReminderListenerServiceComponentName())) {
    true  -> NotificationListenerPermission.GRANTED
    false -> NotificationListenerPermission.NOT_GRANTED
  }
}
