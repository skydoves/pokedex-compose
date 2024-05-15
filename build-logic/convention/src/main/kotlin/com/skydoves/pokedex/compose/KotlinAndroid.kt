package com.skydoves.pokedex.compose

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

/**
 * Configure base Kotlin with Android options
 */
internal fun Project.configureKotlinAndroid(
  commonExtension: CommonExtension<*, *, *, *, *, *>,
) {
  commonExtension.apply {
    compileSdk = 34

    defaultConfig {
      minSdk = 21
    }

    compileOptions{
      sourceCompatibility = JavaVersion.VERSION_17
      targetCompatibility = JavaVersion.VERSION_17
    }

    packaging {
      resources {
        excludes += "/META-INF/{AL2.0,LGPL2.1}"
        excludes += "/META-INF/LICENSE.md"
        excludes += "/META-INF/LICENSE-notice.md"
      }
    }
  }
  configureKotlinAndroid()
}

private fun Project.configureKotlinAndroid() {
  tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
      jvmTarget = "17"

      freeCompilerArgs += listOf(
        "-Xcontext-receivers",
        "-opt-in=kotlin.RequiresOptIn",
        // Enable experimental coroutines APIs, including Flow
        "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
        // Enable experimental compose APIs
        "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api",
        "-opt-in=androidx.lifecycle.compose.ExperimentalLifecycleComposeApi",
        "-opt-in=androidx.compose.animation.ExperimentalSharedTransitionApi",
      )
    }
  }

  extensions.configure<KotlinProjectExtension> {
    jvmToolchain(17)
  }
}


