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

package com.skydoves.pokedex.compose.baselineprofile

import androidx.benchmark.macro.MacrobenchmarkScope
import androidx.test.uiautomator.By
import androidx.test.uiautomator.BySelector
import androidx.test.uiautomator.Direction
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiObject2
import androidx.test.uiautomator.Until

fun MacrobenchmarkScope.pokedexScenarios() {
  // -----------------
  // Pokedex Home
  // -----------------
  explorePokedexHome()
  navigateFromHomeToDetails()

  // -----------------
  // Pokedex Details
  // -----------------
  detailsWaitForContent()
}

fun MacrobenchmarkScope.explorePokedexHome() = device.apply {
  homeWaitForContent()
  pokedexListScrollDownUp()
}

fun MacrobenchmarkScope.homeWaitForContent() = device.apply {
  wait(Until.hasObject(By.res("PokedexList")), 15_000L)
}

fun MacrobenchmarkScope.pokedexListScrollDownUp() = device.apply {
  val channelList = waitAndFindObject(By.res("PokedexList"), 15_000L)
  flingElementDownUp(channelList)
}

fun MacrobenchmarkScope.navigateFromHomeToDetails() = device.apply {
  waitAndFindObject(By.res("Pokemon"), 15_000L).click()
  waitForIdle()
}

fun MacrobenchmarkScope.detailsWaitForContent() = device.apply {
  wait(Until.hasObject(By.res("PokedexDetails")), 15_000L)
}

internal fun UiDevice.flingElementDownUp(element: UiObject2) {
  // Set some margin from the sides to prevent triggering system navigation
  element.setGestureMargin(displayWidth / 5)

  element.fling(Direction.DOWN)
  waitForIdle()
  element.fling(Direction.UP)
}

/**
 * Waits until an object with [selector] if visible on screen and returns the object.
 * If the element is not available in [timeout], throws [AssertionError]
 */
internal fun UiDevice.waitAndFindObject(selector: BySelector, timeout: Long = 15_000L): UiObject2 {
  if (!wait(Until.hasObject(selector), timeout)) {
    throw AssertionError("Element not found on screen in ${timeout}ms (selector=$selector)")
  }

  return findObject(selector)
}
