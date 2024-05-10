plugins {
  id("skydoves.pokedex.android.library")
  id("skydoves.pokedex.android.library.compose")
  id("skydoves.pokedex.android.hilt")
  id("skydoves.pokedex.spotless")
  id("org.jetbrains.kotlin.plugin.serialization")
}

android {
  namespace = "com.skydoves.pokedex.compose.core.navigation"
}

dependencies {
  implementation(projects.core.model)

  implementation(libs.kotlinx.coroutines)

  implementation(libs.androidx.compose.ui)
  implementation(libs.androidx.compose.animation)
  api(libs.androidx.navigation.compose)

  implementation(libs.moshi)
  implementation(libs.kotlinx.serializable.json)
}