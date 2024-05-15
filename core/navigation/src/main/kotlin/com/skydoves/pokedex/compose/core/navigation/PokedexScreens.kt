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

import androidx.navigation.NamedNavArgument
import androidx.navigation.navArgument
import com.skydoves.pokedex.compose.core.model.Pokemon

sealed class PokedexScreens(
  val route: String,
  val navArguments: List<NamedNavArgument> = emptyList(),
) {
  val name: String = route.appendArguments(navArguments)

  data object Home : PokedexScreens("home")

  data object Details : PokedexScreens(
    route = "details",
    navArguments = listOf(
      navArgument("pokemon") {
        type = PokemonType()
        nullable = false
      },
    ),
  ) {
    fun createRoute(pokemon: Pokemon) =
      name.replace("{${navArguments.first().name}}", PokemonType.encodeToString(pokemon))
  }
}

private fun String.appendArguments(navArguments: List<NamedNavArgument>): String {
  val mandatoryArguments = navArguments.filter { it.argument.defaultValue == null }
    .takeIf { it.isNotEmpty() }
    ?.joinToString(separator = "/", prefix = "/") { "{${it.name}}" }
    .orEmpty()
  val optionalArguments = navArguments.filter { it.argument.defaultValue != null }
    .takeIf { it.isNotEmpty() }
    ?.joinToString(separator = "&", prefix = "?") { "${it.name}={${it.name}}" }
    .orEmpty()
  return "$this$mandatoryArguments$optionalArguments"
}
