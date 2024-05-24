import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import com.skydoves.pokedex.compose.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension

class AndroidApplicationConventionPlugin : Plugin<Project> {
  override fun apply(target: Project) {
    with(target) {
      with(pluginManager) {
        apply("com.android.application")
        apply("org.jetbrains.kotlin.android")
      }

      extensions.configure<BaseAppModuleExtension> {
        configureKotlinAndroid(this)
        defaultConfig.targetSdk = 34
      }

      extensions.getByType<KotlinAndroidProjectExtension>().apply {
        compilerOptions {
          allWarningsAsErrors.set(
            properties["warningsAsErrors"] as? Boolean ?: false
          )

          freeCompilerArgs.set(
            freeCompilerArgs.getOrElse(emptyList()) + listOf(
              "-Xcontext-receivers",
              "-Xopt-in=kotlin.RequiresOptIn",
              // Enable experimental coroutines APIs, including Flow
              "-Xopt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
              // Enable experimental compose APIs
              "-Xopt-in=androidx.compose.material3.ExperimentalMaterial3Api",
              "-Xopt-in=androidx.lifecycle.compose.ExperimentalLifecycleComposeApi",
              "-Xopt-in=androidx.compose.animation.ExperimentalSharedTransitionApi",
            )
          )

          jvmTarget.set(JvmTarget.JVM_17)
        }
      }
    }
  }
}
