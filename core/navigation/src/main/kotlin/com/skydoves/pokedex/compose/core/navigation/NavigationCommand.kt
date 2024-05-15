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

import androidx.navigation.NavOptions

sealed class NavigationCommand {
  data object NavigateUp : NavigationCommand()
}

sealed class ComposeNavigationCommand : NavigationCommand() {
  data class NavigateToRoute(val route: String, val options: NavOptions? = null) :
    ComposeNavigationCommand()

  data class NavigateUpWithResult<T>(
    val key: String,
    val result: T,
    val route: String? = null,
  ) : ComposeNavigationCommand()

  data class PopUpToRoute(val route: String, val inclusive: Boolean) : ComposeNavigationCommand()
}
