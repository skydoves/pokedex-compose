plugins {
  id("skydoves.pokedex.android.feature")
  id("skydoves.pokedex.android.hilt")
}

android {
  namespace = "com.skydoves.pokedex.compose.feature.details"
}

dependencies {
  testImplementation(projects.core.test)
  testImplementation(libs.kotlinx.coroutines.test)
  testImplementation(libs.junit)
  testImplementation(libs.turbine)
}