package com.ghackett.googlereminderrepeater.app

import android.content.SharedPreferences
import android.os.Bundle
import android.text.format.DateFormat
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.episode6.typed2.sharedprefs.mutableStateFlow
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.plus
import java.util.*
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

internal data class LogViewState(val logSize: String, val entries: List<LogEntryViewState>)
internal data class LogEntryViewState(val notification: GoogleNotification, val timestamp: String)

@HiltViewModel class MainActivityViewModel @Inject constructor(sharedPreferences: SharedPreferences) : ViewModel() {
  private val _loggingEnabled = sharedPreferences.mutableStateFlow(PrefKeys.LOGGING_ENABLED, viewModelScope + Dispatchers.Default)
  private val _log = sharedPreferences.mutableStateFlow(PrefKeys.GOOGLE_LOG, viewModelScope + Dispatchers.Default)

  val loggingEnabled = _loggingEnabled.asStateFlow()
  internal val log = _log
    .map { it?.viewState() }
    .stateIn(viewModelScope + Dispatchers.Default, SharingStarted.Lazily, null)

  fun setLoggingEnabled(enabled: Boolean) { _loggingEnabled.value = enabled }
  fun setLogSize(sizeStr: String) = _log.update { log ->
    val numbers = sizeStr.filter { it.isDigit() }
    val newInt = numbers.takeIf { it.isNotBlank() }?.toIntOrNull() ?: 0
    log?.withMaxEntryCount(newInt)
  }
  fun trimLog() = _log.update { it?.trimmed() }
  fun clearLog() = _log.update { it?.cleared() }
}

private fun GoogleNotificationLog.viewState(): LogViewState = LogViewState(
  logSize = if (maxEntryCount == 0) "" else "$maxEntryCount",
  entries = entries.map { it.viewState() }
)

private fun GoogleNotification.viewState(): LogEntryViewState = LogEntryViewState(
  notification = this,
  timestamp = Calendar.getInstance(Locale.ENGLISH)
    .apply { timeInMillis = capturedAt }
    .let { DateFormat.format("MM/dd/yyyy hh:mm a", it) }
    .toString()
)
