import com.skydoves.pokedex.compose.Configuration
import java.io.FileInputStream
import java.util.Properties

plugins {
  id("skydoves.pokedex.android.application")
  id("skydoves.pokedex.android.application.compose")
  id("skydoves.pokedex.android.hilt")
  id("skydoves.pokedex.spotless")
  alias(libs.plugins.kotlin.parcelize)
  alias(libs.plugins.baselineprofile)
}

android {
  namespace = "com.skydoves.pokedex.compose"

  defaultConfig {
    applicationId = "com.skydoves.pokedex.compose"
    versionCode = Configuration.versionCode
    versionName = Configuration.versionName
    testInstrumentationRunner = "com.skydoves.pokedex.compose.AppTestRunner"
  }

  signingConfigs {
    val properties = Properties()
    val localPropertyFile = project.rootProject.file("local.properties")
    if (localPropertyFile.canRead()) {
      properties.load(FileInputStream("$rootDir/local.properties"))
    }
    create("release") {
      storeFile = file(properties["RELEASE_KEYSTORE_PATH"] ?: "../keystores/pokedex.jks")
      keyAlias = properties["RELEASE_KEY_ALIAS"].toString()
      keyPassword = properties["RELEASE_KEY_PASSWORD"].toString()
      storePassword = properties["RELEASE_KEYSTORE_PASSWORD"].toString()
    }
  }

  buildTypes {
    release {
      isMinifyEnabled = true
      isShrinkResources = true
      proguardFiles("proguard-rules.pro",)
      signingConfig = signingConfigs.getByName("release")

      packaging {
        resources {
          excludes += listOf(
            "DebugProbesKt.bin",
            "kotlin-tooling-metadata.json",
            "kotlin/**",
          )
        }
      }
    }
  }

  buildFeatures {
    buildConfig = true
  }

  hilt {
    enableAggregatingTask = true
  }

  testOptions.unitTests {
    isIncludeAndroidResources = true
    isReturnDefaultValues = true
  }
}

kotlin {
  compilerOptions {
    freeCompilerArgs.addAll(
      "-Xno-param-assertions",
      "-Xno-call-assertions",
      "-Xno-receiver-assertions"
    )
  }
}

// Configure stability analyzer
composeStabilityAnalyzer {
  enabled.set(true)
}

dependencies {
  // features
  implementation(projects.feature.home)
  implementation(projects.feature.details)
  implementation(projects.feature.settings)

  // cores
  implementation(projects.core.data)
  implementation(projects.core.model)
  implementation(projects.core.designsystem)
  implementation(projects.core.navigation)

  // compose
  implementation(libs.androidx.activity.compose)
  implementation(libs.androidx.compose.ui)
  implementation(libs.androidx.compose.runtime)
  implementation(libs.androidx.compose.foundation)

  // di
  implementation(libs.hilt.android)
  ksp(libs.hilt.compiler)
  androidTestImplementation(libs.hilt.testing)
  kspAndroidTest(libs.hilt.compiler)

  // baseline profile
  implementation(libs.profileinstaller)
  baselineProfile(project(":baselineprofile"))

  // unit test
  testImplementation(libs.junit)
  testImplementation(libs.turbine)
  testImplementation(libs.androidx.test.core)
  testImplementation(libs.mockito.core)
  testImplementation(libs.mockito.kotlin)
  testImplementation(libs.kotlinx.coroutines.test)
  androidTestImplementation(libs.truth)
  androidTestImplementation(libs.androidx.junit)
  androidTestImplementation(libs.androidx.espresso)
//  androidTestImplementation(libs.android.test.runner)
}