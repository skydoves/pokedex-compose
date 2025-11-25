package com.skydoves.pokedex.compose.feature.settings

import androidx.compose.runtime.Stable
import androidx.lifecycle.viewModelScope
import com.skydoves.pokedex.compose.core.data.repository.settings.SettingsRepository
import com.skydoves.pokedex.compose.core.model.UiTheme
import com.skydoves.pokedex.compose.core.viewmodel.BaseViewModel
import com.skydoves.pokedex.compose.core.viewmodel.ViewModelStateFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
  private val settingsRepository: SettingsRepository
) : BaseViewModel() {

  internal val uiState: ViewModelStateFlow<SettingsUiState> = viewModelStateFlow(SettingsUiState.Loading)

  val userData = flow {
    settingsRepository.fetchUserData(
      onComplete = { uiState.tryEmit(key, SettingsUiState.Idle) },
      onError = { uiState.tryEmit(key, SettingsUiState.Error(it)) }
    ).collect { emit(it) }
  }.stateIn(
    scope = viewModelScope,
    started = WhileSubscribed(5_000),
    initialValue = null,
  )

  fun setUiTheme(uiTheme: UiTheme) = viewModelScope.launch {
    settingsRepository.setUiTheme(uiTheme)
  }

  fun setDynamicColors(useDynamicColors: Boolean) = viewModelScope.launch {
    settingsRepository.setDynamicColors(useDynamicColors)
  }
}

@Stable
sealed interface SettingsUiState {

  data object Idle : SettingsUiState

  data object Loading : SettingsUiState

  data class Error(val message: String?) : SettingsUiState
}


