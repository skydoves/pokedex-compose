plugins {
  id("skydoves.pokedex.android.library")
  id("skydoves.pokedex.android.library.compose")
  id("org.jetbrains.kotlin.plugin.serialization")
  id("skydoves.pokedex.android.hilt")
  id("skydoves.pokedex.spotless")
}

android {
  namespace = "com.skydoves.pokedex.compose.core.navigation"
}

dependencies {
  implementation(projects.core.model)

  implementation(libs.kotlinx.coroutines.android)

  implementation(libs.androidx.compose.ui)
  implementation(libs.androidx.compose.animation)
  api(libs.androidx.navigation.compose)

  // json parsing
  implementation(libs.kotlinx.serialization.json)
}