/*
 * Designed and developed by 2024 skydoves (Jaewoong Eum)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
  @Dispatcher(PokedexAppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
) : UserDataRepository {

  override val userData: Flow<UserData> = preferencesDataSource.userData
    .flowOn(ioDispatcher)

  override suspend fun setUiTheme(uiTheme: UiTheme) {
    preferencesDataSource.setUiTheme(uiTheme)
  }
}
