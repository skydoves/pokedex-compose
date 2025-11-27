package com.skydoves.pokedex.compose.core.data.repository.userdata

import com.skydoves.pokedex.compose.core.model.UiTheme
import com.skydoves.pokedex.compose.core.model.UserData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeUserDataRepository : UserDataRepository {
  override val userData: Flow<UserData> = flowOf(
    UserData(uiTheme = UiTheme.FOLLOW_SYSTEM)
  )

  override suspend fun setUiTheme(uiTheme: UiTheme) {

  }
}