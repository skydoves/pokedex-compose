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

import androidx.annotation.VisibleForTesting
import androidx.annotation.WorkerThread
import com.skydoves.pokedex.compose.core.database.PokemonDao
import com.skydoves.pokedex.compose.core.database.entitiy.mapper.asDomain
import com.skydoves.pokedex.compose.core.database.entitiy.mapper.asEntity
import com.skydoves.pokedex.compose.core.model.Pokemon
import com.skydoves.pokedex.compose.core.network.Dispatcher
import com.skydoves.pokedex.compose.core.network.PokedexAppDispatchers
import com.skydoves.pokedex.compose.core.network.service.PokedexClient
import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.message
import com.skydoves.sandwich.onFailure
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@VisibleForTesting
class HomeRepositoryImpl @Inject constructor(
  private val pokedexClient: PokedexClient,
  private val pokemonDao: PokemonDao,
  @Dispatcher(PokedexAppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
) : HomeRepository {

  @WorkerThread
  override fun fetchPokemonList(
    page: Int,
    onStart: () -> Unit,
    onComplete: () -> Unit,
    onLastPageReached: () -> Unit,
    onError: (String?) -> Unit,
  ) = flow {
    var pokemons = pokemonDao.getPokemonList(page).asDomain()
    if (pokemons.isEmpty()) {
      /**
       * fetches a list of [Pokemon] from the network and getting [ApiResponse] asynchronously.
       * @see [suspendOnSuccess](https://github.com/skydoves/sandwich#apiresponse-extensions-for-coroutines)
       */
      val response = pokedexClient.fetchPokemonList(page = page)
      response.suspendOnSuccess {
        if (data.next == null) {
          onLastPageReached()
        }

        pokemons = data.results
        pokemons.forEach { pokemon -> pokemon.page = page }
        pokemonDao.insertPokemonList(pokemons.asEntity())
        emit(pokemonDao.getAllPokemonList(page).asDomain())
      }.onFailure { // handles the all error cases from the API request fails.
        onError(message())
      }
    } else {
      emit(pokemonDao.getAllPokemonList(page).asDomain())
    }
  }.onStart { onStart() }.onCompletion { onComplete() }.flowOn(ioDispatcher)
}
