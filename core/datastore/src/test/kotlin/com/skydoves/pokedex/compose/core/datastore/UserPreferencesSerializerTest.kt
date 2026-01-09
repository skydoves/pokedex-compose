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

import androidx.datastore.core.CorruptionException
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream

class UserPreferencesSerializerTest {

  @Test
  fun defaultUserPreferences_isEmpty() {
    val serializer = UserPreferencesSerializer()
    assertEquals(
      userPreferences { },
      serializer.defaultValue,
    )
  }

  @Test
  fun writingAndReadingUserPreferences_outputsCorrectValue() = runTest {
    val serializer = UserPreferencesSerializer()

    val expected = userPreferences {
      uiThemeConfig = UiThemeConfigProto.UI_THEME_CONFIG_FOLLOW_SYSTEM
    }

    val outputStream = ByteArrayOutputStream()

    expected.writeTo(outputStream)

    val inputStream = ByteArrayInputStream(outputStream.toByteArray())

    val actual = serializer.readFrom(input = inputStream)

    assertEquals(expected, actual)
  }

  @Test(expected = CorruptionException::class)
  fun readingInvalidUserPreferences_throwsCorruptionException() = runTest {
    val serializer = UserPreferencesSerializer()

    serializer.readFrom(ByteArrayInputStream(byteArrayOf(0)))
  }
}
