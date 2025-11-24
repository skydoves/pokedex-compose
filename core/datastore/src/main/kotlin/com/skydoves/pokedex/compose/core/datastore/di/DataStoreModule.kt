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
    @Dispatcher(PokedexAppDispatchers .IO) dispatcher: CoroutineDispatcher,
    @PokedexAppScope scope: CoroutineScope,
    userPreferencesSerializer: UserPreferencesSerializer,
  ): DataStore<UserPreferences> = DataStoreFactory
    .create(
      serializer = userPreferencesSerializer,
      scope = CoroutineScope(scope.coroutineContext + dispatcher),
      produceFile = { context.dataStoreFile(fileName = "user_preferences.pb") }
    )
}