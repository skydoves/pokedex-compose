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

package com.skydoves.pokedex.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.CompositionLocalProvider
import com.skydoves.pokedex.compose.core.navigation.AppComposeNavigator
import com.skydoves.pokedex.compose.core.navigation.LocalComposeNavigator
import com.skydoves.pokedex.compose.core.navigation.PokedexScreen
import com.skydoves.pokedex.compose.ui.PokedexMain
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

  @Inject
  internal lateinit var composeNavigator: AppComposeNavigator<PokedexScreen>

  override fun onCreate(savedInstanceState: Bundle?) {
    enableEdgeToEdge()
    super.onCreate(savedInstanceState)

    setContent {
      CompositionLocalProvider(
        LocalComposeNavigator provides composeNavigator,
      ) {
        PokedexMain(composeNavigator = composeNavigator)
      }
    }
  }
}
