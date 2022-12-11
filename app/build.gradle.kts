plugins {
  id("config-android-app")
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
    versionName = "1.0.0-rc1"
  }

  buildTypes {
    getByName("release") {
      isMinifyEnabled = true
    }
  }
}

dependencies {
  implementation(libs.bundles.app)
  kapt(libs.hilt.compiler)
}
