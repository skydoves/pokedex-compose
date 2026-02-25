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

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skydoves.pokedex.compose.core.data.repository.details.DetailsRepository
import com.skydoves.pokedex.compose.core.model.Pokemon
import com.skydoves.pokedex.compose.core.model.PokemonInfo
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn

@HiltViewModel(assistedFactory = DetailsViewModel.Factory::class)
class DetailsViewModel @AssistedInject constructor(
  @Assisted private val pokemon: Pokemon,
  private val detailsRepository: DetailsRepository,
) : ViewModel() {

  @AssistedFactory
  interface Factory {
    fun create(pokemon: Pokemon): DetailsViewModel
  }

  internal val uiState: StateFlow<DetailsUiState>
    field = MutableStateFlow<DetailsUiState>(DetailsUiState.Loading)

  val pokemonInfo: StateFlow<PokemonInfo?> = flow {
    detailsRepository.fetchPokemonInfo(
      name = pokemon.nameField.replaceFirstChar { it.lowercase() },
      onComplete = { uiState.tryEmit(DetailsUiState.Idle) },
      onError = { uiState.tryEmit(DetailsUiState.Error(it)) },
    ).collect { emit(it) }
  }.stateIn(
    scope = viewModelScope,
    started = SharingStarted.WhileSubscribed(5_000),
    initialValue = null,
  )
}

@Stable
internal sealed interface DetailsUiState {

  data object Idle : DetailsUiState

  data object Loading : DetailsUiState

  data class Error(val message: String?) : DetailsUiState
}
