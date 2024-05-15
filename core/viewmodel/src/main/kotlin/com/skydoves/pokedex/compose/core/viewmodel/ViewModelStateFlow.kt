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

package com.skydoves.pokedex.compose.core.viewmodel

import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * The ViewModel state flow currently relies solely on delegating [MutableStateFlow]
 * inside the [BaseViewModel] to prevent modification of values from outside the ViewModels.
 *
 * However, this class should be transitioned to utilize explicit backing fields
 * (referenced in [this proposal](https://github.com/Kotlin/KEEP/blob/explicit-backing-fields-re/proposals/explicit-backing-fields.md))
 * once Kotlin 2.0 stable version is released and the new Compose compiler is compatible with Kotlin 2.0.
 */
class ViewModelStateFlow<T>(private val key: ViewModelKey, value: T) : MutableStateFlow<T> {

  private val mutableStateFlow: MutableStateFlow<Map<ViewModelKey, T>> = MutableStateFlow(
    mapOf(key to value),
  )

  override val subscriptionCount: StateFlow<Int>
    get() = mutableStateFlow.subscriptionCount

  suspend fun emit(key: ViewModelKey, value: T) {
    if (key != this.key) {
      throw IllegalArgumentException(
        "Used different key to emit new value: $value!" +
          "Don't manipulate key value or try to emit out of ViewModels",
      )
    }

    mutableStateFlow.emit(mapOf(key to value))
  }

  @RestrictedApi
  override suspend fun emit(value: T) =
    throw IllegalAccessError("Use `emitValue` function instead of this")

  fun tryEmit(key: ViewModelKey, value: T): Boolean {
    if (key != this.key) {
      throw IllegalArgumentException(
        "Used different key to emit new value: $value!" +
          "Don't manipulate key value or try to emit out of ViewModels",
      )
    }

    return mutableStateFlow.tryEmit(mapOf(key to value))
  }

  @RestrictedApi
  override fun tryEmit(value: T): Boolean =
    throw IllegalAccessError("Use `tryEmitValue` function instead of this")

  override fun resetReplayCache() = mutableStateFlow.resetReplayCache()

  override var value: T
    get() = mutableStateFlow.value.getValue(key)
    set(value) {
      mutableStateFlow.value = mapOf(key to value)
    }

  override fun compareAndSet(expect: T, update: T): Boolean =
    mutableStateFlow.compareAndSet(mapOf(key to expect), mapOf(key to update))

  override val replayCache: List<T>
    get() = mutableStateFlow.replayCache.map { value }

  override suspend fun collect(collector: FlowCollector<T>): Nothing = mutableStateFlow.collect {
    collector.emit(it.getValue(key))
  }
}
