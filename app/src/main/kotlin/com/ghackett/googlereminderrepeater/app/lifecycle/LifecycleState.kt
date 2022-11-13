package com.ghackett.googlereminderrepeater.app.lifecycle

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

fun Lifecycle.eventFlow(): Flow<Lifecycle.Event> = callbackFlow {
  val observer = LifecycleEventObserver { _, event ->
    trySend(event)
  }
  addObserver(observer)
  awaitClose { removeObserver(observer) }
}
