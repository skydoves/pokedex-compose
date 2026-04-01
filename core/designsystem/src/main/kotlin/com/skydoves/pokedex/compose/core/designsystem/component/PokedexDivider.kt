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

package com.skydoves.pokedex.compose.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skydoves.pokedex.compose.core.designsystem.theme.PokedexTheme

@Composable
fun PokedexDivider(
  modifier: Modifier = Modifier,
  thickness: Dp = 1.dp,
  horizontalPadding: Dp = 16.dp,
  label: String? = null,
) {
  if (label != null) {
    Row(
      modifier = modifier.fillMaxWidth().padding(horizontal = horizontalPadding),
      verticalAlignment = Alignment.CenterVertically,
    ) {
      Box(
        modifier = Modifier.weight(
          1f,
        ).height(thickness).background(PokedexTheme.colors.black.copy(alpha = 0.12f)),
      )
      androidx.compose.material3.Text(
        text = label,
        modifier = Modifier.padding(horizontal = 8.dp),
        fontSize = 10.sp,
        color = PokedexTheme.colors.black.copy(alpha = 0.3f),
      )
      Box(
        modifier = Modifier.weight(
          1f,
        ).height(thickness).background(PokedexTheme.colors.black.copy(alpha = 0.12f)),
      )
    }
  } else {
    Box(
      modifier = modifier
        .fillMaxWidth()
        .padding(horizontal = horizontalPadding)
        .height(thickness)
        .background(PokedexTheme.colors.black.copy(alpha = 0.15f)),
    )
  }
}
