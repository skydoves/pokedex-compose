package com.skydoves.pokedex.compose.core.model

data class UserData(
  val uiTheme: UiTheme
)

enum class UiTheme {
  FOLLOW_SYSTEM,
  DARK,
  LIGHT
}
