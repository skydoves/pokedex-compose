plugins {
  id("skydoves.pokedex.android.library")
  id("skydoves.pokedex.android.library.compose")
  id("skydoves.pokedex.android.hilt")
  id("skydoves.pokedex.spotless")
}

android {
  namespace = "com.skydoves.pokedex.compose.feature.preview"
}

dependencies {
  // core
  implementation(projects.core.designsystem)
  implementation(projects.core.navigation)
  implementation(projects.core.model)
}