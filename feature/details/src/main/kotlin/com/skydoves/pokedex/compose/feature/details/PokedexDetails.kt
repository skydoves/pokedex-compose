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

import android.content.res.Configuration
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kmpalette.palette.graphics.Palette
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.components.rememberImageComponent
import com.skydoves.landscapist.image.LandscapistImage
import com.skydoves.landscapist.palette.PalettePlugin
import com.skydoves.landscapist.palette.rememberPaletteState
import com.skydoves.pokedex.compose.core.data.repository.details.FakeDetailsRepository
import com.skydoves.pokedex.compose.core.designsystem.component.PokedexCircularProgress
import com.skydoves.pokedex.compose.core.designsystem.component.PokedexText
import com.skydoves.pokedex.compose.core.designsystem.theme.PokedexTheme
import com.skydoves.pokedex.compose.core.designsystem.utils.getPokemonTypeColor
import com.skydoves.pokedex.compose.core.model.Pokemon
import com.skydoves.pokedex.compose.core.model.PokemonInfo
import com.skydoves.pokedex.compose.core.navigation.currentComposeNavigator
import com.skydoves.pokedex.compose.core.preview.PokedexPreviewTheme
import com.skydoves.pokedex.compose.core.preview.PreviewUtils
import com.skydoves.pokedex.compose.designsystem.R

@Composable
fun PokedexDetails(
  sharedTransitionScope: SharedTransitionScope,
  animatedContentScope: AnimatedContentScope,
  pokemon: Pokemon,
  detailsViewModel: DetailsViewModel = hiltViewModel(
    key = pokemon.name,
    creationCallback = { factory: DetailsViewModel.Factory ->
      factory.create(pokemon)
    },
  ),
) {
  val uiState by detailsViewModel.uiState.collectAsStateWithLifecycle()
  val pokemonInfo by detailsViewModel.pokemonInfo.collectAsStateWithLifecycle()

  with(sharedTransitionScope) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .sharedBounds(
          sharedContentState = rememberSharedContentState(key = "pokemon-${pokemon.name}"),
          animatedVisibilityScope = animatedContentScope,
        )
        .background(PokedexTheme.colors.background)
        .verticalScroll(rememberScrollState())
        .testTag("PokedexDetails"),
    ) {

      var palette by rememberPaletteState()
      val backgroundBrush by palette.paletteBackgroundBrush()

      DetailsHeader(
        pokemon = pokemon,
        pokemonInfo = pokemonInfo,
        onPaletteLoaded = { palette = it },
        backgroundBrush = backgroundBrush
      )

      if (uiState == DetailsUiState.Idle && pokemonInfo != null) {
        DetailsInfo(pokemonInfo = pokemonInfo!!)

        DetailsStatus(pokemonInfo = pokemonInfo!!)
      } else {
        Box(modifier = Modifier.fillMaxSize()) {
          PokedexCircularProgress()
        }
      }
    }
  }
}

@Composable
private fun DetailsHeader(
  pokemon: Pokemon,
  pokemonInfo: PokemonInfo?,
  onPaletteLoaded: (Palette) -> Unit,
  backgroundBrush: Brush,
) {
  val composeNavigator = currentComposeNavigator
  val shape = RoundedCornerShape(
    topStart = 0.dp,
    topEnd = 0.dp,
    bottomStart = 64.dp,
    bottomEnd = 64.dp,
  )

  Box(
    modifier = Modifier
      .fillMaxWidth()
      .height(290.dp)
      .shadow(elevation = 9.dp, shape = shape)
      .background(brush = backgroundBrush, shape = shape),
  ) {
    Row(
      modifier = Modifier
        .padding(12.dp)
        .statusBarsPadding(),
      verticalAlignment = Alignment.CenterVertically,
    ) {
      Icon(
        modifier = Modifier
          .padding(end = 6.dp)
          .clickable { composeNavigator.navigateUp() },
        painter = painterResource(id = R.drawable.ic_arrow),
        tint = PokedexTheme.colors.absoluteWhite,
        contentDescription = null,
      )

      Text(
        modifier = Modifier.padding(horizontal = 10.dp),
        text = pokemon.name,
        color = PokedexTheme.colors.absoluteWhite,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
      )
    }

    PokedexText(
      modifier = Modifier
        .align(Alignment.TopEnd)
        .padding(12.dp)
        .statusBarsPadding(),
      text = pokemonInfo?.getIdString().orEmpty(),
      previewText = "#001",
      color = PokedexTheme.colors.absoluteWhite,
      fontWeight = FontWeight.Bold,
      fontSize = 18.sp,
    )

    LandscapistImage(
      modifier = Modifier
        .align(Alignment.BottomCenter)
        .padding(bottom = 20.dp)
        .size(190.dp),
      imageModel = { pokemon.imageUrl },
      imageOptions = ImageOptions(contentScale = ContentScale.Inside),
      component = rememberImageComponent {
        if (!LocalInspectionMode.current) {
          +PalettePlugin(
            imageModel = pokemon.imageUrl,
            useCache = true,
            paletteLoadedListener = { onPaletteLoaded.invoke(it) },
          )
        }
      },
    )
  }

  PokedexText(
    modifier = Modifier
      .padding(top = 24.dp)
      .fillMaxWidth(),
    text = pokemon.name,
    previewText = "skydoves",
    color = PokedexTheme.colors.black,
    fontWeight = FontWeight.Bold,
    textAlign = TextAlign.Center,
    fontSize = 36.sp,
  )
}

@Composable
private fun DetailsInfo(pokemonInfo: PokemonInfo) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .padding(top = 14.dp),
    horizontalArrangement = Arrangement.spacedBy(22.dp, Alignment.CenterHorizontally),
  ) {
    pokemonInfo.types.forEach { typeInfo ->
      Text(
        modifier = Modifier
          .background(
            color = getPokemonTypeColor(type = typeInfo.type.name),
            shape = RoundedCornerShape(64.dp),
          )
          .padding(horizontal = 40.dp, vertical = 4.dp),
        text = typeInfo.type.name,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
        color = PokedexTheme.colors.absoluteWhite,
        maxLines = 1,
        fontSize = 16.sp,
      )
    }
  }

  Row(
    modifier = Modifier
      .fillMaxWidth()
      .padding(top = 24.dp),
    horizontalArrangement = Arrangement.SpaceEvenly,
  ) {
    PokemonInfoItem(
      title = pokemonInfo.getWeightString(),
      content = stringResource(id = R.string.weight),
    )

    PokemonInfoItem(
      title = pokemonInfo.getHeightString(),
      content = stringResource(id = R.string.height),
    )
  }
}

@Composable
private fun DetailsStatus(
  pokemonInfo: PokemonInfo,
) {
  Text(
    modifier = Modifier
      .fillMaxWidth()
      .padding(top = 22.dp, bottom = 16.dp),
    text = stringResource(id = R.string.base_stats),
    textAlign = TextAlign.Center,
    color = PokedexTheme.colors.black,
    fontWeight = FontWeight.Bold,
    fontSize = 21.sp,
  )

  Column {
    pokemonInfo.toPokedexStatusList().forEach { pokemonStatus ->
      PokemonStatusItem(
        modifier = Modifier.padding(bottom = 12.dp),
        pokedexStatus = pokemonStatus,
      )
    }
  }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PokedexDetailsPreview() {
  val pokemon = PreviewUtils.mockPokemon()
  PokedexPreviewTheme { animatedContentScope ->
    PokedexDetails(
      sharedTransitionScope = this@PokedexPreviewTheme,
      animatedContentScope = animatedContentScope,
      pokemon = pokemon,
      detailsViewModel = DetailsViewModel(
        pokemon = pokemon,
        detailsRepository = FakeDetailsRepository(),
      ),
    )
  }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PokedexDetailsInfoPreview() {
  PokedexTheme {
    DetailsInfo(pokemonInfo = PreviewUtils.mockPokemonInfo())
  }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PokedexDetailsStatusPreview() {
  PokedexTheme {
    DetailsStatus(
      pokemonInfo = PreviewUtils.mockPokemonInfo(),
    )
  }
}
