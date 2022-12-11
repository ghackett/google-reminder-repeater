package plugins

import org.gradle.api.Plugin
import org.gradle.api.Project

class ConfigureAndroidAppPlugin implements Plugin<Project> {
  @Override
  void apply(Project target) {
    target.with {
      plugins.with {
        apply("com.android.application")
        apply(ConfigureAndroidPlugin)
      }
      android {
        signingConfigs {
          release {
            keyAlias "signing"
            keyPassword findProperty("androidSigningKeyPassword")
            storeFile file("${project.rootProject.rootDir}/keystore.jks")
            storePassword findProperty("androidSigningKeyPassword")
          }
        }

        buildTypes {
          release {
            signingConfig signingConfigs.release
          }
        }
      }
    }
  }
}
