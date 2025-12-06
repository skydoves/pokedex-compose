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

package com.skydoves.pokedex.compose.core.datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.skydoves.pokedex.compose.core.common.network.Dispatcher
import com.skydoves.pokedex.compose.core.common.network.PokedexAppDispatchers
import com.skydoves.pokedex.compose.core.common.network.PokedexAppScope
import com.skydoves.pokedex.compose.core.datastore.UserPreferences
import com.skydoves.pokedex.compose.core.datastore.UserPreferencesSerializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

  @Provides
  @Singleton
  fun providesUserPreferencesDataStore(
    @ApplicationContext context: Context,
    @Dispatcher(PokedexAppDispatchers.IO) dispatcher: CoroutineDispatcher,
    @PokedexAppScope scope: CoroutineScope,
    userPreferencesSerializer: UserPreferencesSerializer,
  ): DataStore<UserPreferences> = DataStoreFactory
    .create(
      serializer = userPreferencesSerializer,
      scope = CoroutineScope(scope.coroutineContext + dispatcher),
      produceFile = { context.dataStoreFile(fileName = "user_preferences.pb") },
    )
}
