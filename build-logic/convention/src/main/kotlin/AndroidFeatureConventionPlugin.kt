import com.android.build.api.dsl.LibraryExtension
import com.skydoves.pokedex.compose.configureAndroidCompose
import com.skydoves.pokedex.compose.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension

class AndroidFeatureConventionPlugin : Plugin<Project> {
  override fun apply(target: Project) {
    with(target) {
      pluginManager.apply {
        apply("com.android.library")
      }

      val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

      dependencies {
        add("implementation", project(":core:designsystem"))
        add("implementation", project(":core:navigation"))
        add("implementation", project(":core:data"))
        add("api", libs.findLibrary("androidx.lifecycle.viewModelCompose").get())
        add("compileOnly", project(":core:preview"))
      }

      extensions.configure<LibraryExtension> {
        configureKotlinAndroid(this)
        configureAndroidCompose(this)
      }

      extensions.getByType<KotlinAndroidProjectExtension>().apply {
        configureKotlinAndroid(this)
      }
    }
  }
}
