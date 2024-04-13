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

package com.skydoves.pokedex.compose.core.designsystem.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.skydoves.pokedex.compose.core.designsystem.theme.PokedexTheme

@Composable
fun getPokemonTypeColor(type: String): Color {
  return when (type) {
    "fighting" -> PokedexTheme.colors.fighting
    "flying" -> PokedexTheme.colors.flying
    "poison" -> PokedexTheme.colors.poison
    "ground" -> PokedexTheme.colors.ground
    "rock" -> PokedexTheme.colors.rock
    "bug" -> PokedexTheme.colors.bug
    "ghost" -> PokedexTheme.colors.ghost
    "steel" -> PokedexTheme.colors.steel
    "fire" -> PokedexTheme.colors.fire
    "water" -> PokedexTheme.colors.water
    "grass" -> PokedexTheme.colors.grass
    "electric" -> PokedexTheme.colors.electric
    "psychic" -> PokedexTheme.colors.psychic
    "ice" -> PokedexTheme.colors.ice
    "dragon" -> PokedexTheme.colors.dragon
    "fairy" -> PokedexTheme.colors.fairy
    "dark" -> PokedexTheme.colors.dark
    else -> PokedexTheme.colors.gray21
  }
}
