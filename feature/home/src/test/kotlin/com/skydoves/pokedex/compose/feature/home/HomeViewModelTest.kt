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

package com.skydoves.pokedex.compose.feature.home

import app.cash.turbine.test
import com.skydoves.pokedex.compose.core.data.repository.home.FakeHomeRepository
import com.skydoves.pokedex.compose.core.test.MainCoroutinesRule
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HomeViewModelTest {

  private lateinit var viewModel: HomeViewModel

  @get:Rule
  val coroutinesRule = MainCoroutinesRule()

  @Before
  fun setup() {
    viewModel = HomeViewModel(FakeHomeRepository())
  }

  @Test
  fun initialUiState_isLoading() {
    assertEquals(HomeUiState.Loading, viewModel.uiState.value)
  }

  @Test
  fun pokemonList_emitsPokemonFromRepository() = runTest {
    viewModel.pokemonList.test {
      val items = awaitItem()
      assertTrue(items.isNotEmpty())
      assertEquals("Bulbasaur", items[0].name)
      cancelAndIgnoreRemainingEvents()
    }
  }

  @Test
  fun uiState_transitionsToIdle_afterPokemonListEmits() = runTest {
    viewModel.pokemonList.test {
      awaitItem()
      cancelAndIgnoreRemainingEvents()
    }
    assertEquals(HomeUiState.Idle, viewModel.uiState.value)
  }

  @Test
  fun fetchNextPokemonList_doesNotIncrement_whenLoading() {
    // uiState is Loading before pokemonList is collected
    assertEquals(HomeUiState.Loading, viewModel.uiState.value)
    viewModel.fetchNextPokemonList()
    // state remains Loading — page increment is blocked
    assertEquals(HomeUiState.Loading, viewModel.uiState.value)
  }

  @Test
  fun fetchNextPokemonList_incrementsPage_whenIdle() = runTest {
    viewModel.pokemonList.test {
      awaitItem()
      cancelAndIgnoreRemainingEvents()
    }
    assertEquals(HomeUiState.Idle, viewModel.uiState.value)

    viewModel.fetchNextPokemonList()

    viewModel.pokemonList.test {
      val items = awaitItem()
      assertTrue(items.isNotEmpty())
      cancelAndIgnoreRemainingEvents()
    }
  }
}
