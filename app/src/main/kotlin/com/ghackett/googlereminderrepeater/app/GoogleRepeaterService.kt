package com.ghackett.googlereminderrepeater.app

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import com.episode6.typed2.bundles.BundleKeyNamespace
import com.episode6.typed2.bundles.getExtra
import com.episode6.typed2.bundles.setExtra
import com.episode6.typed2.kotlinx.serialization.bundlizer.bundlized
import com.ghackett.googlereminderrepeater.app.notifications.Notifier
import com.ghackett.googlereminderrepeater.app.services.CoroutineIntentService
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

fun Context.repeatNotification(notification: GoogleNotification) {
  startService(Intent().apply {
    component = ComponentName(this@repeatNotification, GoogleRepeaterService::class.java)
    setExtra(Extras.NOTIFICATION, notification)
  })
}

private object Extras : BundleKeyNamespace("com.ghackett.googlereminderrepeater.app.GoogleRepeaterService") {
  val NOTIFICATION = key("google_notification").bundlized(GoogleNotification::serializer)
}

@AndroidEntryPoint class GoogleRepeaterService : CoroutineIntentService() {
  @Inject lateinit var notifier: Notifier

  override suspend fun handleIntent(intent: Intent?) {
    val notification = intent?.getExtra(Extras.NOTIFICATION) ?: return
    notifier.notify(notification)
  }
}
