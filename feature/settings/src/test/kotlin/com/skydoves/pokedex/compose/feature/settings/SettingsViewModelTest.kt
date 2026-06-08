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

package com.skydoves.pokedex.compose.feature.settings

import app.cash.turbine.test
import com.skydoves.pokedex.compose.core.model.UiTheme
import com.skydoves.pokedex.compose.core.model.UserData
import com.skydoves.pokedex.compose.core.test.MainCoroutinesRule
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class SettingsViewModelTest {

  private lateinit var viewModel: SettingsViewModel
  private val userDataRepository: com.skydoves.pokedex.compose.core.data.repository.userdata.UserDataRepository = mock()
  private val userDataFlow = MutableStateFlow(UserData(uiTheme = UiTheme.FOLLOW_SYSTEM))

  @get:Rule
  val coroutinesRule = MainCoroutinesRule()

  @Before
  fun setup() {
    whenever(userDataRepository.userData).thenReturn(userDataFlow)
    viewModel = SettingsViewModel(userDataRepository)
  }

  @Test
  fun initialUiState_isLoading() {
    assertEquals(SettingsUiState.Loading, viewModel.uiState.value)
  }

  @Test
  fun uiState_emitsSuccess_withDefaultUserData() = runTest {
    viewModel.uiState.test {
      assertEquals(
        SettingsUiState.Success(UserData(UiTheme.FOLLOW_SYSTEM)),
        awaitItem(),
      )
      cancelAndIgnoreRemainingEvents()
    }
  }

  @Test
  fun uiState_updatesWhenUiThemeChanges() = runTest {
    viewModel.uiState.test {
      assertEquals(SettingsUiState.Success(UserData(UiTheme.FOLLOW_SYSTEM)), awaitItem())

      userDataFlow.update { it.copy(uiTheme = UiTheme.DARK) }
      assertEquals(SettingsUiState.Success(UserData(UiTheme.DARK)), awaitItem())

      userDataFlow.update { it.copy(uiTheme = UiTheme.LIGHT) }
      assertEquals(SettingsUiState.Success(UserData(UiTheme.LIGHT)), awaitItem())

      cancelAndIgnoreRemainingEvents()
    }
  }

  @Test
  fun setUiTheme_dark_delegatesToRepository() = runTest {
    viewModel.setUiTheme(UiTheme.DARK)
    verify(userDataRepository).setUiTheme(UiTheme.DARK)
  }

  @Test
  fun setUiTheme_light_delegatesToRepository() = runTest {
    viewModel.setUiTheme(UiTheme.LIGHT)
    verify(userDataRepository).setUiTheme(UiTheme.LIGHT)
  }

  @Test
  fun setUiTheme_followSystem_delegatesToRepository() = runTest {
    viewModel.setUiTheme(UiTheme.FOLLOW_SYSTEM)
    verify(userDataRepository).setUiTheme(UiTheme.FOLLOW_SYSTEM)
  }
}
