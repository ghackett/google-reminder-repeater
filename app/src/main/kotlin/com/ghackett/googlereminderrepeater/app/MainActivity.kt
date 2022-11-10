package com.ghackett.googlereminderrepeater.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.Text
import androidx.lifecycle.ViewModel
import com.ghackett.googlereminderrepeater.app.ui.theme.AppScaffold
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@AndroidEntryPoint class MainActivity : ComponentActivity() {

  private val viewModel: MainActivityViewModel by viewModels()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      AppScaffold {
        Text(text = "Hello")
      }
    }
  }
}

@HiltViewModel class MainActivityViewModel @Inject constructor(
  
) : ViewModel()
