package com.skydoves.pokedex.compose.core.data.repository.settings

import com.skydoves.pokedex.compose.core.model.UiTheme
import com.skydoves.pokedex.compose.core.model.UserData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeSettingsRepository : SettingsRepository {
  override val userData: Flow<UserData> = flowOf(
    UserData(
      uiTheme = UiTheme.FOLLOW_SYSTEM,
      useDynamicColors = true
    )
  )

  override suspend fun setUiTheme(uiTheme: UiTheme) {

  }

  override suspend fun setDynamicColors(useDynamicColors: Boolean) {

  }
}