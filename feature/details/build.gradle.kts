plugins {
  id("skydoves.pokedex.android.library")
  id("skydoves.pokedex.android.library.compose")
  id("skydoves.pokedex.android.hilt")
  id("skydoves.pokedex.spotless")
}

android {
  namespace = "com.skydoves.pokedex.compose.feature.details"
}

dependencies {
  // core
  implementation(projects.core.designsystem)
  implementation(projects.core.navigation)
  implementation(projects.core.viewmodel)
  implementation(projects.core.data)
  compileOnly(projects.core.preview)
}