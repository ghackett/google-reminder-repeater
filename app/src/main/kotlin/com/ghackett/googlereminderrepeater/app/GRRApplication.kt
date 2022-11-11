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

@HiltAndroidApp class GRRApplication : Application()

@Module @InstallIn(SingletonComponent::class) object ApplicationModule {
  @Provides fun notificationManager(@ApplicationContext context: Context) =
    context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
}
