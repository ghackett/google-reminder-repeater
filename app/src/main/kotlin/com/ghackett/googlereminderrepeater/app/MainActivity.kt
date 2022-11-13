package com.ghackett.googlereminderrepeater.app

import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@AndroidEntryPoint class MainActivity : ComponentActivity() {

  @Inject lateinit var launcher: UiActionLauncher

  private val viewModel: MainActivityViewModel by viewModels()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      MainUI(viewModel, launcher)
    }
  }

  override fun onResume() {
    super.onResume()
    viewModel.onResume(this)
  }
}

@HiltViewModel class MainActivityViewModel @Inject constructor(
  private val notificationManager: NotificationManager,
) : ViewModel() {

  private val _readPermissionState = MutableStateFlow(false)
  val readPermissionState: StateFlow<Boolean> = _readPermissionState.asStateFlow()

  fun onResume(context: Context) {
    _readPermissionState.value = when {
      Build.VERSION.SDK_INT >= 27 -> notificationManager.isNotificationListenerAccessGranted(context.googleReminderListenerServiceComponentName())
      else                        -> true
    }
  }
}
