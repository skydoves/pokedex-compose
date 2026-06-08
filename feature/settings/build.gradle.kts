plugins {
  id("skydoves.pokedex.android.feature")
  id("skydoves.pokedex.android.hilt")
}

android {
  namespace = "com.skydoves.pokedex.compose.feature.settings"
}

dependencies {
  testImplementation(projects.core.test)
  testImplementation(libs.kotlinx.coroutines.test)
  testImplementation(libs.junit)
  testImplementation(libs.turbine)
  testImplementation(libs.mockito.core)
  testImplementation(libs.mockito.kotlin)
}