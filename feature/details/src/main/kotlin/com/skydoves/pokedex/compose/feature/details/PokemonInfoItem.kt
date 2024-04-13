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

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skydoves.pokedex.compose.core.designsystem.component.PokedexText
import com.skydoves.pokedex.compose.core.designsystem.theme.PokedexTheme

@Composable
internal fun PokemonInfoItem(
  title: String?,
  content: String?,
) {
  Column(horizontalAlignment = Alignment.CenterHorizontally) {
    PokedexText(
      modifier = Modifier.padding(10.dp),
      text = title.orEmpty(),
      previewText = "24.0 KG",
      color = PokedexTheme.colors.black,
      fontWeight = FontWeight.Bold,
      fontSize = 21.sp,
    )

    PokedexText(
      text = content.orEmpty(),
      previewText = "Weight",
      color = PokedexTheme.colors.white56,
      fontWeight = FontWeight.Bold,
      fontSize = 12.sp,
    )
  }
}
