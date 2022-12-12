plugins {
  `java-gradle-plugin`
}

gradlePlugin {
  plugins {
    create("ConfigureJvmPlugin") {
      id = "config-jvm"
      implementationClass = "plugins.ConfigureJvmPlugin"
    }
    create("ConfigureAndroidComposePlugin") {
      id = "config-compose"
      implementationClass = "plugins.ConfigureAndroidComposePlugin"
    }
    create("ConfigureAndroidAppPlugin") {
      id = "config-android-app"
      implementationClass = "plugins.ConfigureAndroidAppPlugin"
    }
    create("ConfigureAndroidLibPlugin") {
      id = "config-android-lib"
      implementationClass = "plugins.ConfigureAndroidLibPlugin"
    }
  }
}
