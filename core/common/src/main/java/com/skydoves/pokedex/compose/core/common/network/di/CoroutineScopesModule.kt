package com.skydoves.pokedex.compose.core.common.network.di

import com.skydoves.pokedex.compose.core.common.network.Dispatcher
import com.skydoves.pokedex.compose.core.common.network.PokedexAppDispatchers
import com.skydoves.pokedex.compose.core.common.network.PokedexAppScope
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object CoroutineScopesModule {

    @Provides
    @Singleton
    @PokedexAppScope
    fun providesCoroutineScope(
      @Dispatcher(PokedexAppDispatchers.IO) dispatcher: CoroutineDispatcher,
    ): CoroutineScope = CoroutineScope(SupervisorJob() + dispatcher)
}