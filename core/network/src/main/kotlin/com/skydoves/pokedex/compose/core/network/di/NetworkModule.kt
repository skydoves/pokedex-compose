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

package com.skydoves.pokedex.compose.core.network.di

import com.skydoves.pokedex.compose.core.network.service.PokedexClient
import com.skydoves.pokedex.compose.core.network.service.PokedexService
import com.skydoves.pokedex.core.network.BuildConfig
import com.skydoves.sandwich.retrofit.adapters.ApiResponseCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {

  @Singleton
  @Provides
  fun provideJson(): Json = Json {
    ignoreUnknownKeys = true
  }

  @Provides
  @Singleton
  fun provideOkHttpClient(): OkHttpClient {
    return OkHttpClient.Builder()
      .apply {
        if (BuildConfig.DEBUG) {
          this.addNetworkInterceptor(
            HttpLoggingInterceptor().apply {
              level = HttpLoggingInterceptor.Level.BODY
            },
          )
        }
      }
      .build()
  }

  @Provides
  @Singleton
  fun provideRetrofit(json: Json, okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
      .client(okHttpClient)
      .baseUrl("https://pokeapi.co/api/v2/")
      .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
      .addCallAdapterFactory(ApiResponseCallAdapterFactory.create())
      .build()
  }

  @Provides
  @Singleton
  fun providePokedexService(retrofit: Retrofit): PokedexService {
    return retrofit.create(PokedexService::class.java)
  }

  @Provides
  @Singleton
  fun providePokedexClient(pokedexService: PokedexService): PokedexClient {
    return PokedexClient(pokedexService)
  }
}
