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
  alias(libs.plugins.kotlinx.serialization)
}

android {
  namespace = "com.skydoves.pokedex.core.network"

  buildFeatures {
    buildConfig = true
  }
}

dependencies {
  implementation(projects.core.model)
  testImplementation(projects.core.test)

  // coroutines
  implementation(libs.kotlinx.coroutines.android)
  testImplementation(libs.kotlinx.coroutines.test)

  // network
  implementation(libs.sandwich)
  implementation(platform(libs.retrofit.bom))
  implementation(platform(libs.okhttp.bom))
  implementation(libs.bundles.retrofitBundle)
  testImplementation(libs.okhttp.mockwebserver)
  testImplementation(libs.androidx.arch.core.testing)

  // json parsing
  implementation(libs.kotlinx.serialization.json)
}
