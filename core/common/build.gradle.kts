plugins {
  alias(libs.plugins.kotlin.jvm)
  alias(libs.plugins.ksp)
}

dependencies {
  implementation(libs.hilt.core)
  implementation(libs.kotlinx.coroutines.core)

  ksp(libs.hilt.compiler)
}

