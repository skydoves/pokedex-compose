package com.skydoves.pokedex.compose

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skydoves.pokedex.compose.MainActivityUiState.Loading
import com.skydoves.pokedex.compose.core.data.repository.userdata.UserDataRepository
import com.skydoves.pokedex.compose.core.model.UiTheme
import com.skydoves.pokedex.compose.core.model.UserData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
  userDataRepository: UserDataRepository
) : ViewModel() {

  val userData = userDataRepository.userData
    .map(MainActivityUiState::Success)
    .stateIn(
      scope = viewModelScope,
      started = SharingStarted.WhileSubscribed(5_000),
      initialValue = Loading
    )
}

sealed interface MainActivityUiState {
  data object Loading : MainActivityUiState
  data class Success(val userData: UserData) : MainActivityUiState {
    fun shouldUseDarkTheme(isSystemDarkTheme: Boolean): Boolean =
      when (userData.uiTheme) {
        UiTheme.FOLLOW_SYSTEM -> isSystemDarkTheme
        UiTheme.DARK -> true
        UiTheme.LIGHT -> false
      }
  }
}

fun MainActivityUiState.shouldKeepSplashScreen() = this is Loading

fun MainActivityUiState.shouldUseDarkTheme(isSystemDarkTheme: Boolean) = when (this) {
  Loading -> isSystemDarkTheme
  is MainActivityUiState.Success -> shouldUseDarkTheme(isSystemDarkTheme = isSystemDarkTheme)
}