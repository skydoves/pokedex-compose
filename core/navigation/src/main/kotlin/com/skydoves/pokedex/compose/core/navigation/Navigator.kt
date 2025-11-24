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

package com.skydoves.pokedex.compose.core.navigation

import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey

/**
 * Navigator interface for Navigation3 back stack operations.
 */
interface PokedexNavigator {
  /**
   * Navigate to a new screen by adding it to the back stack.
   */
  fun navigate(screen: PokedexScreen)

  /**
   * Navigate back by removing the top screen from the back stack.
   * @return true if navigation was successful, false if back stack is empty or only has one item.
   */
  fun navigateUp(): Boolean
}

/**
 * Implementation of [PokedexNavigator] that operates on a [NavBackStack].
 */
class PokedexNavigatorImpl(
  private val backStack: NavBackStack<NavKey>,
) : PokedexNavigator {

  override fun navigate(screen: PokedexScreen) {
    backStack.add(screen)
  }

  override fun navigateUp(): Boolean {
    return if (backStack.size > 1) {
      backStack.removeLastOrNull() != null
    } else {
      false
    }
  }
}
