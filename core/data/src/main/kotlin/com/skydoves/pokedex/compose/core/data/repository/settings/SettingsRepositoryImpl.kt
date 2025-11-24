package com.skydoves.pokedex.compose.core.data.repository.settings

import com.skydoves.pokedex.compose.core.common.network.Dispatcher
import com.skydoves.pokedex.compose.core.common.network.PokedexAppDispatchers
import com.skydoves.pokedex.compose.core.datastore.PreferencesDataSource
import com.skydoves.pokedex.compose.core.model.UiTheme
import com.skydoves.pokedex.compose.core.model.UserData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
  private val preferencesDataSource: PreferencesDataSource,
  @Dispatcher(PokedexAppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher
) : SettingsRepository {

  override fun fetchUserData(
    onComplete: () -> Unit,
    onError: (String?) -> Unit
  ): Flow<UserData> = preferencesDataSource.userData
    .catch { onError(it.message) }
    .onCompletion { onComplete() }
    .flowOn(ioDispatcher)

  override suspend fun setUiTheme(uiTheme: UiTheme) {
    preferencesDataSource.setUiTheme(uiTheme)
  }

  override suspend fun setDynamicColors(useDynamicColors: Boolean) {
    preferencesDataSource.setDynamicColors(useDynamicColors)
  }
}
