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

package com.skydoves.pokedex.compose.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.scene.DialogSceneStrategy
import androidx.navigation3.ui.LocalNavAnimatedContentScope
import androidx.navigation3.ui.NavDisplay
import com.skydoves.compose.stability.runtime.TraceRecomposition
import com.skydoves.pokedex.compose.core.navigation.LocalComposeNavigator
import com.skydoves.pokedex.compose.core.navigation.PokedexNavigatorImpl
import com.skydoves.pokedex.compose.core.navigation.PokedexScreen
import com.skydoves.pokedex.compose.feature.details.PokedexDetails
import com.skydoves.pokedex.compose.feature.home.PokedexHome
import com.skydoves.pokedex.compose.feature.settings.PokedexSettings

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
@TraceRecomposition
fun PokedexNavHost() {
  val backStack = rememberNavBackStack(PokedexScreen.Home)
  val dialogStrategy = remember { DialogSceneStrategy<NavKey>() }
  val navigator = remember(backStack) { PokedexNavigatorImpl(backStack) }

  CompositionLocalProvider(
    LocalComposeNavigator provides navigator,
  ) {
    SharedTransitionLayout {
      NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        sceneStrategy = dialogStrategy,
        entryDecorators = listOf(rememberSaveableStateHolderNavEntryDecorator()),
        entryProvider = entryProvider<NavKey> {
          entry<PokedexScreen.Home> {
            PokedexHome(
              sharedTransitionScope = this@SharedTransitionLayout,
              animatedContentScope = LocalNavAnimatedContentScope.current,
            )
          }

          entry<PokedexScreen.Details> { screen ->
            PokedexDetails(
              sharedTransitionScope = this@SharedTransitionLayout,
              animatedContentScope = LocalNavAnimatedContentScope.current,
              pokemon = screen.pokemon,
            )
          }

          entry<PokedexScreen.Settings>(
            metadata = DialogSceneStrategy.dialog(),
          ) {
            PokedexSettings()
          }
        },
      )
    }
  }
}
