package com.ghackett.googlereminderrepeater.app

import android.app.NotificationManager
import android.app.Service
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ServiceComponent

@Module @InstallIn(ServiceComponent::class) object ServiceModule {
  @Provides fun notificationManager(service: Service): NotificationManager =
    service.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
}
