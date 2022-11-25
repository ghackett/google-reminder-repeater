package com.ghackett.googlereminderrepeater.app

import android.app.Notification
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import com.episode6.typed2.bundles.BundleKeyNamespace
import com.episode6.typed2.bundles.getExtra
import com.episode6.typed2.bundles.setExtra
import com.episode6.typed2.kotlinx.serialization.bundlizer.bundlized
import com.episode6.typed2.sharedprefs.get
import com.episode6.typed2.sharedprefs.update
import com.ghackett.googlereminderrepeater.app.notifications.GoogleRepeatChannel
import com.ghackett.googlereminderrepeater.app.notifications.Notifier
import com.ghackett.googlereminderrepeater.app.services.CoroutineIntentService
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

private const val GOOGLE_REPEATER_NOTIFICATION_ID = 7839

private object Extras : BundleKeyNamespace("com.ghackett.googlereminderrepeater.app.GoogleRepeaterService") {
  val NOTIFICATION = key("google_notification").bundlized(GoogleNotification::serializer)
}

fun Context.repeatGoogleNotification(notification: GoogleNotification) {
  startService(Intent().apply {
    component = ComponentName(this@repeatGoogleNotification, GoogleRepeaterService::class.java)
    setExtra(Extras.NOTIFICATION, notification)
  })
}

@AndroidEntryPoint class GoogleRepeaterService : CoroutineIntentService() {
  @Inject lateinit var notifier: Notifier
  @Inject lateinit var sharedPrefs: SharedPreferences

  override suspend fun handleIntent(intent: Intent?) {
    val notification = intent?.getExtra(Extras.NOTIFICATION) ?: return

    notifier.notify(notification)
    if (sharedPrefs.get(PrefKeys.LOGGING_ENABLED)) {
      sharedPrefs.update(PrefKeys.GOOGLE_LOG, commit = true) { it.withNewEntry(notification) }
    }
  }
}

private fun Notifier.notify(googleNotification: GoogleNotification) = notify(GoogleRepeatChannel, GOOGLE_REPEATER_NOTIFICATION_ID) {
  setSmallIcon(R.drawable.ic_launcher_foreground)
  setCategory(Notification.CATEGORY_EVENT)
  setContentTitle(googleNotification.title)
  setContentText(googleNotification.text)
}
