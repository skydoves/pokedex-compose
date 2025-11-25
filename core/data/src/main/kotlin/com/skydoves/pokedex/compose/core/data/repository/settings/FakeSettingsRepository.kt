package com.skydoves.pokedex.compose.core.data.repository.settings

import com.skydoves.pokedex.compose.core.model.UiTheme
import com.skydoves.pokedex.compose.core.model.UserData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeSettingsRepository : SettingsRepository {
  override fun fetchUserData(
    onComplete: () -> Unit,
    onError: (String?) -> Unit
  ): Flow<UserData> = flowOf()

  override suspend fun setUiTheme(uiTheme: UiTheme) {

  }

  override suspend fun setDynamicColors(useDynamicColors: Boolean) {

  }
}