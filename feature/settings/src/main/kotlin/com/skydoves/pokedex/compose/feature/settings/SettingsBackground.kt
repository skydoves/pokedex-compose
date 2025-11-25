package com.skydoves.pokedex.compose.feature.settings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import com.kmpalette.palette.graphics.Palette
import com.skydoves.pokedex.compose.core.designsystem.theme.PokedexTheme

@Composable
internal fun Palette?.paletteBackgroundColor(): State<Color> {
  val defaultBackground = PokedexTheme.colors.background
  return remember(this) {
    derivedStateOf {
      val rgb = this?.dominantSwatch?.rgb
      if (rgb != null) {
        Color(rgb)
      } else {
        defaultBackground
      }
    }
  }
}