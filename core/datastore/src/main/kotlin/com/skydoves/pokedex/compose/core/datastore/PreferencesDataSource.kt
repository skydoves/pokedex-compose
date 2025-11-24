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
        useDynamicColors = it.useDynamicColors
      )
    }

  suspend fun setUiTheme(uiTheme: UiTheme) {
    userPreferences.updateData {
      it.copy {
        uiThemeConfig = uiTheme.asUiThemeConfigProto()
      }
    }
  }

  suspend fun setDynamicColors(useDynamicColors: Boolean) {
    userPreferences.updateData {
      it.copy {
        this.useDynamicColors = useDynamicColors
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