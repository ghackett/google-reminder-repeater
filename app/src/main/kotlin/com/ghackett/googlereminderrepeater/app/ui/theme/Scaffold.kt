@file:OptIn(ExperimentalMaterial3Api::class)

package com.ghackett.googlereminderrepeater.app.ui.theme

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.ghackett.googlereminderrepeater.app.R

@Composable fun AppScaffold(
  title: String = stringResource(id = R.string.app_name),
  navigationIcon: @Composable () -> Unit = {},
  actions: @Composable RowScope.() -> Unit = {},
  content: @Composable () -> Unit,
) {
  AppScaffold(
    title = { Text(text = title) },
    navigationIcon = navigationIcon,
    actions = actions,
    content = content,
  )
}

@Composable fun AppScaffold(
  title: @Composable () -> Unit,
  navigationIcon: @Composable () -> Unit = {},
  actions: @Composable RowScope.() -> Unit = {},
  content: @Composable () -> Unit,
) {
  AppTheme {
    Scaffold(
      topBar = {
        SmallTopAppBar(
          title = title,
          navigationIcon = navigationIcon,
          actions = actions,
          colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
          )
        )
      },
      content = { padding ->
        Box(modifier = Modifier.padding(padding)) {
          content()
        }
      },
    )
  }
}

@Composable fun BackButton(goUpNavigator: () -> Unit, tint: Color = MaterialTheme.colorScheme.onPrimary) =
  IconButton(onClick = goUpNavigator) {
    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = stringResource(R.string.go_back), tint = tint)
  }
