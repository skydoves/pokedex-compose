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

package com.skydoves.pokedex.compose.core.datastore

import com.skydoves.pokedex.compose.core.model.UiTheme
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class PreferencesDataSourceTest {

  private val testScope = TestScope(UnconfinedTestDispatcher())

  private lateinit var subject: PreferencesDataSource

  @Before
  fun setup() {
    subject = PreferencesDataSource(
      userPreferences = InMemoryDataStore(UserPreferences.getDefaultInstance()),
    )
  }

  @Test
  fun shouldThemeIsFollowSystemByDefault() = testScope.runTest {
    assertEquals(UiTheme.FOLLOW_SYSTEM, subject.userData.first().uiTheme)
  }

  @Test
  fun userShouldThemeIsDarkWhenSet() = testScope.runTest {
    subject.setUiTheme(uiTheme = UiTheme.DARK)
    assertEquals(UiTheme.DARK, subject.userData.first().uiTheme)
  }
}
