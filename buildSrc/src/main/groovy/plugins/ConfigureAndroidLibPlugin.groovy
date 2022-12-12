package plugins

import org.gradle.api.Plugin
import org.gradle.api.Project

class ConfigureAndroidLibPlugin implements Plugin<Project> {
  @Override
  void apply(Project target) {
    target.with {
      plugins.with {
        apply("com.android.library")
        apply(ConfigureAndroidPlugin)
      }
    }
  }
}
