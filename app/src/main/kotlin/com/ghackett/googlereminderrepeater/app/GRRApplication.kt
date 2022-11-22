package com.ghackett.googlereminderrepeater.app

import android.app.Application
import android.app.NotificationManager
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@HiltAndroidApp class GRRApplication : Application()

fun Context.notificationManager(): NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

@Module @InstallIn(SingletonComponent::class) object ApplicationModule {
  @Provides fun notificationManager(@ApplicationContext context: Context) = context.notificationManager()
  @Provides @Singleton fun sharedPrefs(@ApplicationContext context: Context) =
    context.getSharedPreferences("GoogleRepeaterSharedPrefs", Context.MODE_PRIVATE)
}
