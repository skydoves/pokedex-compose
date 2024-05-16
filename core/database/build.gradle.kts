/*
 * Designed and developed by 2024 skydoves (Jaewoong Eum)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

plugins {
  id("skydoves.pokedex.android.library")
  id("skydoves.pokedex.android.hilt")
  id("skydoves.pokedex.spotless")
  alias(libs.plugins.ksp)
}

android {
  namespace = "com.skydoves.pokedex.compose.core.database"

  defaultConfig {
    // The schemas directory contains a schema file for each version of the Room database.
    // This is required to enable Room auto migrations.
    // See https://developer.android.com/reference/kotlin/androidx/room/AutoMigration.
    ksp {
      arg("room.schemaLocation", "$projectDir/schemas")
    }
  }

  sourceSets.getByName("test") {
    assets.srcDir(files("$projectDir/schemas"))
  }
}

dependencies {
  implementation(projects.core.model)
  testImplementation(projects.core.test)

  // coroutines
  implementation(libs.kotlinx.coroutines.android)
  testImplementation(libs.kotlinx.coroutines.test)

  // database
  implementation(libs.androidx.room.runtime)
  implementation(libs.androidx.room.ktx)
  ksp(libs.androidx.room.compiler)
  testImplementation(libs.androidx.arch.core.testing)

  // json parsing
  implementation(libs.kotlinx.serialization.json)

  // unit test
  testImplementation(libs.junit)
  testImplementation(libs.androidx.test.core)
  testImplementation(libs.robolectric)
}