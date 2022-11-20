package com.ghackett.googlereminderrepeater.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
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

@HiltViewModel class MainActivityViewModel @Inject constructor() : ViewModel()

