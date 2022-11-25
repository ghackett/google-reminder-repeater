package com.ghackett.googlereminderrepeater.app

import com.episode6.typed2.async
import com.episode6.typed2.boolean
import com.episode6.typed2.kotlinx.serialization.json.json
import com.episode6.typed2.sharedprefs.PrefKeyNamespace

object PrefKeys : PrefKeyNamespace() {
  val LOGGING_ENABLED = key("logging_enabled").boolean(default = true)
  val GOOGLE_LOG = key("google_notification_log").json(default = GoogleNotificationLog(), GoogleNotificationLog::serializer).async()
}
