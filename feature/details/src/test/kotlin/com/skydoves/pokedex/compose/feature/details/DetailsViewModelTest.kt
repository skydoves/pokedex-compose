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

package com.skydoves.pokedex.compose.feature.details

import app.cash.turbine.test
import com.skydoves.pokedex.compose.core.data.repository.details.FakeDetailsRepository
import com.skydoves.pokedex.compose.core.test.MainCoroutinesRule
import com.skydoves.pokedex.compose.core.test.MockUtil.mockPokemon
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DetailsViewModelTest {

  private lateinit var viewModel: DetailsViewModel
  private val fakeRepository = FakeDetailsRepository()

  @get:Rule
  val coroutinesRule = MainCoroutinesRule()

  @Before
  fun setup() {
    viewModel = DetailsViewModel(
      pokemon = mockPokemon(),
      detailsRepository = fakeRepository,
    )
  }

  @Test
  fun initialUiState_isLoading() {
    assertEquals(DetailsUiState.Loading, viewModel.uiState.value)
  }

  @Test
  fun initialPokemonInfo_isNull() {
    assertNull(viewModel.pokemonInfo.value)
  }

  @Test
  fun pokemonInfo_emitsExpectedData() = runTest {
    val expected = fakeRepository.mockPokemonInfo()

    viewModel.pokemonInfo.test {
      val item = awaitItem()
      assertEquals(expected.id, item?.id)
      assertEquals(expected.name, item?.name)
      assertEquals(expected.height, item?.height)
      assertEquals(expected.weight, item?.weight)
      cancelAndIgnoreRemainingEvents()
    }
  }

  @Test
  fun uiState_transitionsToIdle_afterSuccessfulFetch() = runTest {
    viewModel.pokemonInfo.test {
      awaitItem()
      cancelAndIgnoreRemainingEvents()
    }
    assertEquals(DetailsUiState.Idle, viewModel.uiState.value)
  }
}
