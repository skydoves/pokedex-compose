package com.skydoves.pokedex.compose

import com.android.build.api.dsl.CommonExtension
import java.io.File
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType

/**
 * Configure Compose-specific options
 */
internal fun Project.configureAndroidCompose(
  commonExtension: CommonExtension<*, *, *, *, *, *>,
) {
  val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

  commonExtension.apply {
    buildFeatures {
      compose = true
    }

    composeOptions {
      kotlinCompilerExtensionVersion = libs.findVersion("androidxComposeCompiler").get().toString()
    }

    kotlinOptions {
      freeCompilerArgs = freeCompilerArgs + buildComposeMetricsParameters()
    }
  }
}

private fun Project.buildComposeMetricsParameters(): List<String> = buildList {
  val enableMetricsProvider = project.providers.gradleProperty("enableComposeCompilerMetrics")
  val enableMetrics = (enableMetricsProvider.orNull == "true")
  if (enableMetrics) {
    val metricsFolder = project.layout.buildDirectory.dir("compose-metrics").get()
    add("-P")
    add(
      "plugin:androidx.compose.compiler.plugins.kotlin:metricsDestination=$metricsFolder"
    )
  }

  val enableReportsProvider = project.providers.gradleProperty("enableComposeCompilerReports")
  val enableReports = (enableReportsProvider.orNull == "true")
  if (enableReports) {
    val reportsFolder = project.layout.buildDirectory.dir("compose-reports").get()
    add("-P")
    add(
      "plugin:androidx.compose.compiler.plugins.kotlin:reportsDestination=$reportsFolder"
    )
  }
}
