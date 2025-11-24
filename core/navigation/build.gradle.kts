plugins {
  id("skydoves.pokedex.android.library")
  id("skydoves.pokedex.android.library.compose")
  alias(libs.plugins.kotlinx.serialization)
  id("skydoves.pokedex.android.hilt")
  id("skydoves.pokedex.spotless")
}

android {
  namespace = "com.skydoves.pokedex.compose.core.navigation"
}

dependencies {
  implementation(projects.core.model)

  implementation(libs.androidx.core)
  implementation(libs.kotlinx.coroutines.android)

  // Navigation3
  api(libs.androidx.navigation3.runtime)
  api(libs.androidx.navigation3.ui)

  // json parsing
  implementation(libs.kotlinx.serialization.json)
}