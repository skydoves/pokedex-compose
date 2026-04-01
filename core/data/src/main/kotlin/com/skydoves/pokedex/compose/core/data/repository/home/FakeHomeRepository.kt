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

package com.skydoves.pokedex.compose.core.data.repository.home

import com.skydoves.pokedex.compose.core.model.Pokemon
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onEach

class FakeHomeRepository : HomeRepository {
  override fun fetchPokemonList(
    page: Int,
    onStart: () -> Unit,
    onComplete: () -> Unit,
    onLastPageReached: () -> Unit,
    onError: (String?) -> Unit,
  ): Flow<List<Pokemon>> = flowOf(
    listOf(
      Pokemon(page = 0, nameField = "bulbasaur", url = "https://pokeapi.co/api/v2/pokemon/1/"),
      Pokemon(page = 0, nameField = "charmander", url = "https://pokeapi.co/api/v2/pokemon/4/"),
      Pokemon(page = 0, nameField = "squirtle", url = "https://pokeapi.co/api/v2/pokemon/7/"),
      Pokemon(page = 0, nameField = "pikachu", url = "https://pokeapi.co/api/v2/pokemon/25/"),
      Pokemon(page = 0, nameField = "eevee", url = "https://pokeapi.co/api/v2/pokemon/133/"),
      Pokemon(page = 0, nameField = "snorlax", url = "https://pokeapi.co/api/v2/pokemon/143/"),
      Pokemon(page = 0, nameField = "mewtwo", url = "https://pokeapi.co/api/v2/pokemon/150/"),
      Pokemon(page = 0, nameField = "gengar", url = "https://pokeapi.co/api/v2/pokemon/94/"),
    ),
  ).onEach { onComplete() }
}
