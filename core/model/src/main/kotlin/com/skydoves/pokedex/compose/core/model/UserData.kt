package com.skydoves.pokedex.compose.core.model

data class UserData(
  val uiTheme: UiTheme,
  val useDynamicColors: Boolean
)

enum class UiTheme {
  FOLLOW_SYSTEM,
  DARK,
  LIGHT
}
