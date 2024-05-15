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

import android.content.res.Configuration
import androidx.annotation.FloatRange
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skydoves.pokedex.compose.core.designsystem.theme.PokedexTheme
import com.skydoves.pokedex.compose.core.designsystem.utils.pxToDp

@Composable
fun PokedexProgressBar(
  modifier: Modifier = Modifier,
  @FloatRange(0.0, 1.0) progress: Float,
  color: Color,
  label: String,
) {
  val screenWidth = LocalConfiguration.current.screenWidthDp.dp.value
  val isLocalInspectionMode = LocalInspectionMode.current
  var progressWidth by remember {
    mutableFloatStateOf(
      if (isLocalInspectionMode) {
        screenWidth
      } else {
        0f
      },
    )
  }

  Box(
    modifier = modifier
      .fillMaxWidth()
      .height(18.dp)
      .onSizeChanged { progressWidth = it.width * progress }
      .background(
        color = PokedexTheme.colors.absoluteWhite,
        shape = RoundedCornerShape(64.dp),
      )
      .clip(RoundedCornerShape(64.dp)),
  ) {
    var textWidth by remember { mutableIntStateOf(0) }
    val threshold = 16
    val isInner by remember(
      progressWidth,
      textWidth,
    ) { mutableStateOf(progressWidth > (textWidth + threshold * 2)) }

    val animation: Float by animateFloatAsState(
      targetValue = if (progressWidth == 0f) 0f else 1f,
      // Configure the animation duration and easing.
      animationSpec = tween(durationMillis = 950, easing = LinearOutSlowInEasing),
      label = "",
    )

    Box(
      modifier = Modifier
        .align(Alignment.CenterStart)
        .width(
          progressWidth
            .toInt()
            .pxToDp() * animation,
        )
        .height(18.dp)
        .background(
          color = color,
          shape = RoundedCornerShape(64.dp),
        ),
    ) {
      if (isInner) {
        Text(
          modifier = Modifier
            .onSizeChanged { textWidth = it.width }
            .align(Alignment.CenterEnd)
            .padding(end = (threshold * 2).pxToDp()),
          text = label,
          fontSize = 12.sp,
          color = PokedexTheme.colors.absoluteWhite,
        )
      }
    }

    if (!isInner) {
      Text(
        modifier = Modifier
          .onSizeChanged { textWidth = it.width }
          .align(Alignment.CenterStart)
          .padding(
            start = progressWidth
              .toInt()
              .pxToDp() + threshold.pxToDp(),
          ),
        text = label,
        fontSize = 12.sp,
        color = PokedexTheme.colors.absoluteBlack,
      )
    }
  }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PokedexProgressBarPreview1() {
  PokedexTheme {
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .height(120.dp)
        .background(PokedexTheme.colors.background),
    ) {
      PokedexProgressBar(
        modifier = Modifier.align(Alignment.Center),
        progress = 0.1f,
        color = PokedexTheme.colors.primary,
        label = "150/300",
      )
    }
  }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PokedexProgressBarPreview2() {
  PokedexTheme {
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .height(120.dp)
        .background(PokedexTheme.colors.background),
    ) {
      PokedexProgressBar(
        modifier = Modifier
          .fillMaxWidth()
          .align(Alignment.Center),
        progress = 0.5f,
        color = PokedexTheme.colors.primary,
        label = "150/300",
      )
    }
  }
}
