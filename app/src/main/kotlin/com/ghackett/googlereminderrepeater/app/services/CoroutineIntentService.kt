package com.ghackett.googlereminderrepeater.app.services

import android.app.Service
import android.content.Intent
import android.os.IBinder

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel

private data class Command(val id: Int, val intent: Intent?)
abstract class CoroutineIntentService : Service() {
  protected val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
  private val commands = Channel<Command>(capacity = Channel.UNLIMITED)

  protected abstract suspend fun handleIntent(intent: Intent?)

  override fun onCreate() {
    super.onCreate()
    scope.launch {
      try {
        for (command in commands) {
          handleIntent(command.intent)
          withContext(Dispatchers.Main) {
            stopSelf(command.id)
          }
        }
      } finally {
        commands.close()
      }
    }
  }

  override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
    commands.trySend(Command(id = startId, intent = intent))
    return START_NOT_STICKY
  }

  override fun onDestroy() {
    scope.cancel()
    super.onDestroy()
  }

  override fun onBind(intent: Intent?): IBinder? = null
}
