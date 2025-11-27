package com.skydoves.pokedex.compose.core.data.repository.userdata

import com.skydoves.pokedex.compose.core.common.network.Dispatcher
import com.skydoves.pokedex.compose.core.common.network.PokedexAppDispatchers
import com.skydoves.pokedex.compose.core.datastore.PreferencesDataSource
import com.skydoves.pokedex.compose.core.model.UiTheme
import com.skydoves.pokedex.compose.core.model.UserData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class UserDataRepositoryImpl @Inject constructor(
  private val preferencesDataSource: PreferencesDataSource,
  @Dispatcher(PokedexAppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher
) : UserDataRepository {

  override val userData: Flow<UserData> = preferencesDataSource.userData
    .flowOn(ioDispatcher)

  override suspend fun setUiTheme(uiTheme: UiTheme) {
    preferencesDataSource.setUiTheme(uiTheme)
  }
}
