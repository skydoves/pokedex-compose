package com.skydoves.pokedex.compose.feature.settings

import androidx.compose.runtime.Stable
import androidx.lifecycle.viewModelScope
import com.skydoves.pokedex.compose.core.data.repository.userdata.UserDataRepository
import com.skydoves.pokedex.compose.core.model.UiTheme
import com.skydoves.pokedex.compose.core.model.UserData
import com.skydoves.pokedex.compose.core.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
  private val userDataRepository: UserDataRepository
) : BaseViewModel() {

  val uiState: StateFlow<SettingsUiState> = userDataRepository.userData
    .map(SettingsUiState::Success)
    .catch { SettingsUiState.Error(it.message) }
    .stateIn(
      scope = viewModelScope,
      started = WhileSubscribed(5_000),
      initialValue = SettingsUiState.Loading,
    )

  fun setUiTheme(uiTheme: UiTheme) = viewModelScope.launch {
    userDataRepository.setUiTheme(uiTheme)
  }
}

@Stable
sealed interface SettingsUiState {

  data object Loading : SettingsUiState

  data class Success(val userData: UserData) : SettingsUiState

  data class Error(val message: String?) : SettingsUiState
}

