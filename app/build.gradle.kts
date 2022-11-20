plugins {
  id("com.android.application")
  id("config-android")
  id("config-compose")
  kotlin("kapt")
  kotlin("plugin.serialization")
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
  implementation(libs.bundles.app)
  kapt(libs.hilt.compiler)
}
