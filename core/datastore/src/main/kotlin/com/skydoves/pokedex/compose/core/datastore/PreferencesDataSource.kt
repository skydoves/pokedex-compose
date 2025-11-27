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

import androidx.datastore.core.DataStore
import com.skydoves.pokedex.compose.core.model.UiTheme
import com.skydoves.pokedex.compose.core.model.UserData
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PreferencesDataSource @Inject constructor(
  private val userPreferences: DataStore<UserPreferences>,
) {
  val userData = userPreferences.data
    .map {
      UserData(
        uiTheme = it.uiThemeConfig.asUiTheme(),
      )
    }

  suspend fun setUiTheme(uiTheme: UiTheme) {
    userPreferences.updateData {
      it.copy {
        uiThemeConfig = uiTheme.asUiThemeConfigProto()
      }
    }
  }
}

fun UiThemeConfigProto.asUiTheme(): UiTheme = when (this) {
  UiThemeConfigProto.UI_THEME_CONFIG_UNSPECIFIED -> UiTheme.FOLLOW_SYSTEM
  UiThemeConfigProto.UI_THEME_CONFIG_FOLLOW_SYSTEM -> UiTheme.FOLLOW_SYSTEM
  UiThemeConfigProto.UI_THEME_CONFIG_LIGHT -> UiTheme.LIGHT
  UiThemeConfigProto.UI_THEME_CONFIG_DARK -> UiTheme.DARK
  UiThemeConfigProto.UNRECOGNIZED -> UiTheme.FOLLOW_SYSTEM
}

fun UiTheme.asUiThemeConfigProto(): UiThemeConfigProto = when (this) {
  UiTheme.FOLLOW_SYSTEM -> UiThemeConfigProto.UI_THEME_CONFIG_FOLLOW_SYSTEM
  UiTheme.LIGHT -> UiThemeConfigProto.UI_THEME_CONFIG_LIGHT
  UiTheme.DARK -> UiThemeConfigProto.UI_THEME_CONFIG_DARK
}
