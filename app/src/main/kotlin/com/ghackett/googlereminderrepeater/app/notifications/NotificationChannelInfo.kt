package com.ghackett.googlereminderrepeater.app.notifications

import android.app.NotificationManager
import androidx.annotation.StringRes
import com.ghackett.googlereminderrepeater.app.R

sealed interface NotificationChannelInfo {
  val id: String
  @get:StringRes val name: Int
  @get:StringRes val description: Int
  val importance: Int get() = NotificationManager.IMPORTANCE_DEFAULT
}

object GoogleRepeatChannel : NotificationChannelInfo {
  override val id = "GoogleRepeat"
  @get:StringRes override val name = R.string.notification_channel_name
  @get:StringRes override val description = R.string.notification_channel_description
  override val importance = NotificationManager.IMPORTANCE_HIGH
}
