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

package com.skydoves.pokedex.compose.core.data

import app.cash.turbine.test
import com.skydoves.pokedex.compose.core.data.repository.details.DetailsRepositoryImpl
import com.skydoves.pokedex.compose.core.database.PokemonInfoDao
import com.skydoves.pokedex.compose.core.database.entitiy.mapper.asEntity
import com.skydoves.pokedex.compose.core.network.service.PokedexClient
import com.skydoves.pokedex.compose.core.network.service.PokedexService
import com.skydoves.pokedex.compose.core.test.MainCoroutinesRule
import com.skydoves.pokedex.compose.core.test.MockUtil.mockPokemonInfo
import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.retrofit.responseOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.atLeastOnce
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import org.mockito.kotlin.whenever
import retrofit2.Response
import kotlin.time.DurationUnit
import kotlin.time.toDuration

class DetailsRepositoryTest {

  private lateinit var repository: DetailsRepositoryImpl
  private lateinit var client: PokedexClient
  private val service: PokedexService = mock()
  private val pokemonInfoDao: PokemonInfoDao = mock()

  @get:Rule
  val coroutinesRule = MainCoroutinesRule()

  @Before
  fun setup() {
    client = PokedexClient(service)
    repository = DetailsRepositoryImpl(client, pokemonInfoDao, coroutinesRule.testDispatcher)
  }

  @Test
  fun fetchPokemonInfoFromNetworkTest() = runTest {
    val mockData = mockPokemonInfo()
    whenever(pokemonInfoDao.getPokemonInfo(name_ = "bulbasaur")).thenReturn(null)
    whenever(service.fetchPokemonInfo(name = "bulbasaur")).thenReturn(
      ApiResponse.responseOf {
        Response.success(
          mockData,
        )
      },
    )

    repository.fetchPokemonInfo(name = "bulbasaur", onComplete = {}, onError = {}).test {
      val actualItem = awaitItem()
      assertEquals(mockData.id, actualItem.id)
      assertEquals(mockData.name, actualItem.name)
      assertEquals(mockData, actualItem)
      awaitComplete()
    }

    verify(pokemonInfoDao, atLeastOnce()).getPokemonInfo(name_ = "bulbasaur")
    verify(service, atLeastOnce()).fetchPokemonInfo(name = "bulbasaur")
    verify(pokemonInfoDao, atLeastOnce()).insertPokemonInfo(mockData.asEntity())
    verifyNoMoreInteractions(service)
  }

  @Test
  fun fetchPokemonInfoFromDatabaseTest() = runTest {
    val mockData = mockPokemonInfo()
    whenever(pokemonInfoDao.getPokemonInfo(name_ = "bulbasaur")).thenReturn(mockData.asEntity())
    whenever(service.fetchPokemonInfo(name = "bulbasaur")).thenReturn(
      ApiResponse.responseOf {
        Response.success(
          mockData,
        )
      },
    )

    repository.fetchPokemonInfo(
      name = "bulbasaur",
      onComplete = {},
      onError = {},
    ).test(5.toDuration(DurationUnit.SECONDS)) {
      val expectItem = awaitItem()
      assertEquals(expectItem.id, mockData.id)
      assertEquals(expectItem.name, mockData.name)
      assertEquals(expectItem, mockData)
      awaitComplete()
    }

    verify(pokemonInfoDao, atLeastOnce()).getPokemonInfo(name_ = "bulbasaur")
    verifyNoMoreInteractions(service)
  }
}
