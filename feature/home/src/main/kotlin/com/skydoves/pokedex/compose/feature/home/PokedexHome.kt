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

package com.skydoves.pokedex.compose.feature.home

import android.content.res.Configuration
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
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
import com.skydoves.pokedex.compose.core.data.repository.home.FakeHomeRepository
import com.skydoves.pokedex.compose.core.designsystem.component.PokedexAppBar
import com.skydoves.pokedex.compose.core.designsystem.component.PokedexCircularProgress
import com.skydoves.pokedex.compose.core.designsystem.theme.PokedexTheme
import com.skydoves.pokedex.compose.core.model.Pokemon
import com.skydoves.pokedex.compose.core.navigation.PokedexScreen
import com.skydoves.pokedex.compose.core.navigation.currentComposeNavigator
import com.skydoves.pokedex.compose.core.preview.PokedexPreviewTheme
import com.skydoves.pokedex.compose.core.preview.PreviewUtils
import com.skydoves.pokedex.compose.designsystem.R
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

@Composable
fun PokedexHome(
  sharedTransitionScope: SharedTransitionScope,
  animatedContentScope: AnimatedContentScope,
  homeViewModel: HomeViewModel = hiltViewModel(),
) {
  val uiState by homeViewModel.uiState.collectAsStateWithLifecycle()
  val pokemonList by homeViewModel.pokemonList.collectAsStateWithLifecycle()

  val composeNavigator = currentComposeNavigator

  Column(modifier = Modifier.fillMaxSize()) {
    PokedexAppBar(
      onActionClick = { composeNavigator.navigate(PokedexScreen.Settings) }
    )

    HomeContent(
      sharedTransitionScope = sharedTransitionScope,
      animatedContentScope = animatedContentScope,
      uiState = uiState,
      pokemonList = pokemonList.toImmutableList(),
      fetchNextPokemonList = homeViewModel::fetchNextPokemonList,
      navigateToDetails = { composeNavigator.navigate(PokedexScreen.Details(pokemon = it)) }
    )
  }
}

@Composable
private fun HomeContent(
  sharedTransitionScope: SharedTransitionScope,
  animatedContentScope: AnimatedContentScope,
  uiState: HomeUiState,
  pokemonList: ImmutableList<Pokemon>,
  fetchNextPokemonList: () -> Unit,
  navigateToDetails: (Pokemon) -> Unit
) {
  Box(modifier = Modifier.fillMaxSize()) {
    val threadHold = 8
    LazyVerticalGrid(
      modifier = Modifier.testTag("PokedexList"),
      columns = GridCells.Fixed(2),
      contentPadding = PaddingValues(6.dp),
    ) {
      itemsIndexed(items = pokemonList, key = { _, pokemon -> pokemon.name }) { index, pokemon ->
        if ((index + threadHold) >= pokemonList.size && uiState != HomeUiState.Loading) {
          fetchNextPokemonList()
        }

        var palette by rememberPaletteState()
        val backgroundColor by palette.paletteBackgroundColor()

        PokemonCard(
          sharedTransitionScope = sharedTransitionScope,
          animatedContentScope = animatedContentScope,
          pokemon = pokemon,
          onPaletteLoaded = { palette = it },
          backgroundColor = backgroundColor,
          onCardClick = { navigateToDetails(pokemon) }
        )
      }
    }

    if (uiState == HomeUiState.Loading) {
      PokedexCircularProgress()
    }
  }
}

@Composable
private fun PokemonCard(
  sharedTransitionScope: SharedTransitionScope,
  animatedContentScope: AnimatedContentScope,
  onPaletteLoaded: (Palette) -> Unit,
  backgroundColor: Color,
  pokemon: Pokemon,
  onCardClick: () -> Unit,
) {

  with(sharedTransitionScope) {
    Card(
      modifier = Modifier
        .padding(6.dp)
        .fillMaxWidth()
        .testTag("Pokemon")
        .sharedBounds(
          sharedContentState = rememberSharedContentState(key = "pokemon-${pokemon.name}"),
          animatedVisibilityScope = animatedContentScope,
        )
        .clickable { onCardClick() },
      shape = RoundedCornerShape(14.dp),
      colors = CardColors(
        containerColor = backgroundColor,
        contentColor = backgroundColor,
        disabledContainerColor = backgroundColor,
        disabledContentColor = backgroundColor,
      ),
      elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    ) {
      LandscapistImage(
        modifier = Modifier
          .align(Alignment.CenterHorizontally)
          .padding(top = 20.dp)
          .size(120.dp),
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

      Text(
        modifier = Modifier
          .align(Alignment.CenterHorizontally)
          .fillMaxWidth()
          .padding(12.dp),
        text = pokemon.name,
        color = PokedexTheme.colors.black,
        textAlign = TextAlign.Center,
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
      )
    }
  }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PokedexHomePreview() {
  PokedexPreviewTheme { animatedContentScope ->
    PokedexHome(
      sharedTransitionScope = this@PokedexPreviewTheme,
      animatedContentScope = animatedContentScope,
      homeViewModel = HomeViewModel(homeRepository = FakeHomeRepository()),
    )
  }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun HomeContentPreview() {
  PokedexPreviewTheme { animatedContentScope ->
    HomeContent(
      sharedTransitionScope = this@PokedexPreviewTheme,
      animatedContentScope = animatedContentScope,
      uiState = HomeUiState.Idle,
      pokemonList = PreviewUtils.mockPokemonList().toImmutableList(),
      fetchNextPokemonList = { },
      navigateToDetails = { }
    )
  }
}
