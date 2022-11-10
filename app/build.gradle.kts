plugins {
  id("com.android.application")
  id("config-android")
  id("config-compose")
  kotlin("kapt")
  id("com.google.dagger.hilt.android")
}

android {
  namespace = "com.ghackett.googlereminderrepeater.app"
  defaultConfig {
    applicationId = "com.ghackett.googlereminderrepeater.app"
    versionCode = 1
    versionName = "1.0"

  }

  buildTypes {
    getByName("release") {
      isMinifyEnabled = false
    }
  }
}

dependencies {
  implementation(libs.androidx.core.ktx)
  implementation(libs.compose.ui.core)
  implementation(libs.compose.material3.core)
  implementation(libs.androidx.lifecycle.runtime)
  implementation(libs.androidx.activity.compose)
  implementation(libs.hilt.android.core)
  kapt(libs.hilt.compiler)
}
