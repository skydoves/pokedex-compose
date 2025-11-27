package com.skydoves.pokedex.compose.core.data.repository.userdata

import com.skydoves.pokedex.compose.core.model.UiTheme
import com.skydoves.pokedex.compose.core.model.UserData
import kotlinx.coroutines.flow.Flow

interface UserDataRepository {

  val userData: Flow<UserData>

  suspend fun setUiTheme(uiTheme: UiTheme)
}