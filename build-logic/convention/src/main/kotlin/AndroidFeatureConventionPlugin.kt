import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import com.skydoves.pokedex.compose.configureAndroidCompose
import com.skydoves.pokedex.compose.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class AndroidFeatureConventionPlugin : Plugin<Project> {
  override fun apply(target: Project) {
    with(target) {
      pluginManager.apply {
        apply("com.android.library")
        apply("org.jetbrains.kotlin.android")
      }

      dependencies {
        add("implementation", project(":core:designsystem"))
        add("implementation", project(":core:navigation"))
        add("implementation", project(":core:viewmodel"))
        add("implementation", project(":core:data"))
        add("compileOnly", project(":core:preview"))
      }

      extensions.configure<LibraryExtension> {
        configureKotlinAndroid(this)
        configureAndroidCompose(this)
        defaultConfig.targetSdk = 34
      }
    }
  }
}
