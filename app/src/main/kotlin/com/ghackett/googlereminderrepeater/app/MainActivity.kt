package com.ghackett.googlereminderrepeater.app

import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.episode6.typed2.sharedprefs.mutableStateFlow
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.plus
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
}

@HiltViewModel class MainActivityViewModel @Inject constructor(sharedPreferences: SharedPreferences) : ViewModel() {
  private val _loggingEnabled = sharedPreferences.mutableStateFlow(PrefKeys.LOGGING_ENABLED, viewModelScope + Dispatchers.Default)
  private val _log = sharedPreferences.mutableStateFlow(PrefKeys.GOOGLE_LOG, viewModelScope + Dispatchers.Default)

  val loggingEnabled = _loggingEnabled.asStateFlow()
  val log = _log.asStateFlow()

  fun setLoggingEnabled(enabled: Boolean) { _loggingEnabled.value = enabled }
  fun setLogSize(size: Int) = _log.update { it?.withMaxEntryCount(size) }
  fun trimLog() = _log.update { it?.trimmed() }
  fun clearLog() = _log.update { it?.cleared() }
}

