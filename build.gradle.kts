// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
  id("com.android.application") version(libs.versions.android.gradle.get()) apply(false)
  kotlin("android") version(libs.versions.kotlin.core.get()) apply(false)
  kotlin("kapt") version(libs.versions.kotlin.core.get()) apply(false)
  id("com.google.dagger.hilt.android") version(libs.versions.hilt.core.get()) apply(false)
}


tasks.wrapper {
  gradleVersion = libs.versions.gradle.core.get()
  distributionType = Wrapper.DistributionType.ALL
}
