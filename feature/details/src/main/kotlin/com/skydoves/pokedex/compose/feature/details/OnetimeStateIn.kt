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
@file:OptIn(ExperimentalCoroutinesApi::class)

package com.skydoves.pokedex.compose.feature.details

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingCommand
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.dropWhile
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.transformLatest

/**
 * Create an upstream cold flow as a StateFlow that triggers the upstream operation only once,
 * preventing re-execution no matter how many times it's subscribed.
 * After the initial emission, it will simply replay the latest cached value.
 *
 * @param scope the coroutine scope in which sharing is started.
 * @param stopTimeout configures a delay (in milliseconds) between the disappearance of the last
 * subscriber and the stopping of the sharing coroutine. It defaults to zero (stop immediately).
 * @param replayExpiration configures a delay (in milliseconds) between the stopping of
 * the sharing coroutine and the resetting of the replay cache (which makes the cache empty for the
 * [shareIn] operator and resets the cached value to the original `initialValue`
 * for the [stateIn] operator). It defaults to `Long.MAX_VALUE` (keep replay cache forever,
 * never reset buffer). Use zero value to expire the cache immediately.
 * @param initialValue the initial value of the state flow. This value is also used when the
 * state flow is reset using the [SharingStarted]. [WhileSubscribed] strategy with the
 * [replayExpiration] parameter.
 */
public fun <T> Flow<T>.onetimeStateIn(
  scope: CoroutineScope,
  stopTimeout: Long = 0,
  replayExpiration: Long = Long.MAX_VALUE,
  initialValue: T,
): StateFlow<T> {
  return stateIn(
    scope = scope,
    started = OnetimeWhileSubscribed(
      stopTimeout = stopTimeout,
      replayExpiration = replayExpiration,
    ),
    initialValue,
  )
}

/**
 * This is a companion extension of [SharingStarted], which is a [SharingStarted] strategy
 * designed to limit upstream emissions to only once. After the initial emission,
 * it remains inactive until an active subscriber reappears.
 *
 * @param stopTimeout configures a delay (in milliseconds) between the disappearance of the last
 * subscriber and the stopping of the sharing coroutine. It defaults to zero (stop immediately).
 * @param replayExpiration configures a delay (in milliseconds) between the stopping of
 * the sharing coroutine and the resetting of the replay cache (which makes the cache empty for the
 * [shareIn] operator and resets the cached value to the original `initialValue`
 * for the [stateIn] operator). It defaults to `Long.MAX_VALUE` (keep replay cache forever,
 * never reset buffer). Use zero value to expire the cache immediately.
 */
public fun SharingStarted.Companion.OnetimeWhileSubscribed(
  stopTimeout: Long,
  replayExpiration: Long = Long.MAX_VALUE,
): OnetimeWhileSubscribed {
  return OnetimeWhileSubscribed(
    stopTimeout = stopTimeout,
    replayExpiration = replayExpiration,
  )
}

/**
 * `OnetimeWhileSubscribed` is a [SharingStarted] strategy designed to limit upstream emissions to
 * only once. After the initial emission, it remains inactive until an active subscriber reappears.
 *
 * @param stopTimeout configures a delay (in milliseconds) between the disappearance of the last
 * subscriber and the stopping of the sharing coroutine. It defaults to zero (stop immediately).
 * @param replayExpiration configures a delay (in milliseconds) between the stopping of
 * the sharing coroutine and the resetting of the replay cache (which makes the cache empty for the
 * [shareIn] operator and resets the cached value to the original `initialValue`
 * for the [stateIn] operator). It defaults to `Long.MAX_VALUE` (keep replay cache forever,
 * never reset buffer). Use zero value to expire the cache immediately.
 */
public class OnetimeWhileSubscribed(
  private val stopTimeout: Long,
  private val replayExpiration: Long,
) : SharingStarted {

  private val hasCollected: MutableStateFlow<Boolean> = MutableStateFlow(false)

  init {
    require(stopTimeout >= 0) { "stopTimeout($stopTimeout ms) cannot be negative" }
    require(replayExpiration >= 0) { "replayExpiration($replayExpiration ms) cannot be negative" }
  }

  override fun command(subscriptionCount: StateFlow<Int>): Flow<SharingCommand> =
    combine(hasCollected, subscriptionCount) { collected, counts ->
      collected to counts
    }
      .transformLatest { pair ->
        val (collected, count) = pair
        if (count > 0 && !collected) {
          emit(SharingCommand.START)
          hasCollected.value = true
        } else {
          delay(stopTimeout)
          if (replayExpiration > 0) {
            emit(SharingCommand.STOP)
            delay(replayExpiration)
          }
        }
      }
      .dropWhile {
        it != SharingCommand.START
      } // don't emit any STOP/RESET_BUFFER to start with, only START
      .distinctUntilChanged() // just in case somebody forgets it, don't leak our multiple sending of START

  override fun toString(): String {
    val params = buildList(2) {
      if (stopTimeout > 0) add("stopTimeout=${stopTimeout}ms")
      if (replayExpiration < Long.MAX_VALUE) add("replayExpiration=${replayExpiration}ms")
    }
    return "SharingStarted.WhileSubscribed(${params.joinToString()})"
  }

  // equals & hashcode to facilitate testing, not documented in public contract
  override fun equals(other: Any?): Boolean =
    other is OnetimeWhileSubscribed &&
      stopTimeout == other.stopTimeout &&
      replayExpiration == other.replayExpiration

  override fun hashCode(): Int = stopTimeout.hashCode() * 31 + replayExpiration.hashCode()
}
