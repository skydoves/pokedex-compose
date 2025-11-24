package com.skydoves.pokedex.compose.core.data.repository.settings

import androidx.annotation.WorkerThread
import com.skydoves.pokedex.compose.core.model.UiTheme
import com.skydoves.pokedex.compose.core.model.UserData
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {

  @WorkerThread
  fun fetchUserData(
    onComplete: () -> Unit,
    onError: (String?) -> Unit,
  ): Flow<UserData>

  suspend fun setUiTheme(uiTheme: UiTheme)

  suspend fun setDynamicColors(useDynamicColors: Boolean)
}