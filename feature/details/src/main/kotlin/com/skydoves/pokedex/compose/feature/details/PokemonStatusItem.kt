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

package com.skydoves.pokedex.compose.feature.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skydoves.pokedex.compose.core.designsystem.component.PokedexProgressBar
import com.skydoves.pokedex.compose.core.designsystem.theme.PokedexTheme

@Composable
internal fun PokemonStatusItem(
  modifier: Modifier = Modifier,
  pokedexStatus: PokedexStatus,
) {
  Row(
    modifier = modifier,
    horizontalArrangement = Arrangement.SpaceEvenly,
  ) {
    Text(
      modifier = Modifier
        .padding(start = 32.dp)
        .widthIn(min = 20.dp),
      text = pokedexStatus.type,
      color = PokedexTheme.colors.white70,
      fontWeight = FontWeight.Bold,
      fontSize = 12.sp,
    )

    PokedexProgressBar(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp),
      progress = pokedexStatus.progress,
      color = pokedexStatus.color,
      label = pokedexStatus.label,
    )
  }
}
