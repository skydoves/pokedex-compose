import com.diffplug.gradle.spotless.SpotlessExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class SpotlessConventionPlugin : Plugin<Project> {
  override fun apply(target: Project) {
    with(target) {
      pluginManager.apply("com.diffplug.spotless")

      extensions.configure<SpotlessExtension> {
        val buildDirectory = layout.buildDirectory.asFileTree
        kotlin {
          target("**/*.kt")
          targetExclude(buildDirectory)
          ktlint().editorConfigOverride(
            mapOf(
              "indent_size" to "2",
              "continuation_indent_size" to "2",
              "ktlint_function_naming_ignore_when_annotated_with" to "Composable"
            )
          )
          licenseHeaderFile(rootProject.file("spotless/spotless.license.kt"))
          trimTrailingWhitespace()
          endWithNewline()
        }
        format("kts") {
          target("**/*.kts")
          targetExclude(buildDirectory)
          licenseHeaderFile(rootProject.file("spotless/spotless.license.kt"), "(^(?![\\/ ]\\*).*$)")
        }
        format("xml") {
          target("**/*.xml")
          targetExclude(buildDirectory)
          licenseHeaderFile(rootProject.file("spotless/spotless.license.xml"), "(<[^!?])")
        }
      }
    }
  }
}
