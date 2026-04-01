package com.skydoves.pokedex.compose.feature.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skydoves.pokedex.compose.core.designsystem.theme.PokedexTheme
import com.skydoves.pokedex.compose.designsystem.R

data class PokemonAbility(
  val name: String,
  val description: String,
  val isHidden: Boolean = false,
) : Displayable {
  override val displayName: String get() = name.capitalizeFirst()
  override val displayDescription: String get() = description

  companion object {
    fun normal(name: String, description: String) = PokemonAbility(name, description, isHidden = false)
    fun hidden(name: String, description: String) = PokemonAbility(name, description, isHidden = true)
    val EMPTY = PokemonAbility("Unknown", "No description available")
  }
}

sealed interface AbilityLoadState {
  data object Loading : AbilityLoadState
  data class Success(val abilities: List<PokemonAbility>) : AbilityLoadState
  data class Error(val message: String) : AbilityLoadState
}

@Composable
fun PokemonAbilityCard(
  abilityName: String,
  description: String,
  modifier: Modifier = Modifier,
  isHidden: Boolean = false,
) {
  val showDetails = androidx.compose.runtime.remember { androidx.compose.runtime.mutableStateOf(false) }
  val cardBg = when {
    isHidden -> PokedexTheme.colors.black.copy(alpha = 0.06f)
    showDetails.value -> PokedexTheme.colors.black.copy(alpha = 0.04f)
    else -> PokedexTheme.colors.background
  }
  Row(
    modifier = modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(12.dp))
      .background(cardBg)
      .padding(12.dp),
    verticalAlignment = Alignment.CenterVertically,
  ) {
    Icon(
      painter = painterResource(id = R.drawable.ic_arrow),
      contentDescription = null,
      modifier = Modifier.size(24.dp),
      tint = if (isHidden) PokedexTheme.colors.black.copy(alpha = 0.35f) else PokedexTheme.colors.black.copy(alpha = 0.8f),
    )
    Spacer(modifier = Modifier.width(12.dp))
    Column {
      Text(
        text = "${abilityName.toAbilityEmoji()} ${abilityName.capitalizeFirst()}",
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        color = PokedexTheme.colors.black,
      )
      Spacer(modifier = Modifier.height(4.dp))
      Text(
        text = description,
        fontSize = 12.sp,
        color = PokedexTheme.colors.black.copy(alpha = 0.6f),
        maxLines = 2,
      )
      Spacer(modifier = Modifier.height(4.dp))
      Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
          text = "Power",
          fontSize = 9.sp,
          color = PokedexTheme.colors.black.copy(alpha = 0.4f),
        )
        Spacer(modifier = Modifier.width(6.dp))
        Box(
          modifier = Modifier
            .weight(1f)
            .height(4.dp)
            .clip(RoundedCornerShape(2.dp))
            .background(PokedexTheme.colors.black.copy(alpha = 0.08f)),
        ) {
          Box(
            modifier = Modifier
              .fillMaxWidth(if (isHidden) 0.4f else 0.7f)
              .height(4.dp)
              .clip(RoundedCornerShape(2.dp))
              .background(PokemonUtils.getStatColor(if (isHidden) 50 else 80)),
          )
        }
      }
      Spacer(modifier = Modifier.height(6.dp))
      AbilityBadge(isHidden = false)
    }
  }
}

@Composable
private fun AbilityBadge(isHidden: Boolean) {
  val expanded = androidx.compose.runtime.remember { androidx.compose.runtime.mutableStateOf(false) }
  val label = when {
    isHidden -> "Hidden"
    else -> "Standard"
  }
  val bgColor = when {
    isHidden -> PokedexTheme.colors.black.copy(alpha = 0.12f)
    else -> PokedexTheme.colors.background
  }
  Column {
    Text(
      text = label,
      fontSize = 10.sp,
      fontWeight = FontWeight.Bold,
      color = PokedexTheme.colors.black.copy(alpha = 0.5f),
      modifier = Modifier
        .clip(RoundedCornerShape(4.dp))
        .background(bgColor)
        .padding(horizontal = 8.dp, vertical = 2.dp),
    )
    if (expanded.value) {
      Spacer(modifier = Modifier.height(4.dp))
      AbilityDetailHint()
    }
  }
}

@Composable
private fun AbilityDetailHint() {
  Row(verticalAlignment = Alignment.CenterVertically) {
    Icon(
      painter = painterResource(id = R.drawable.ic_arrow),
      contentDescription = null,
      modifier = Modifier.size(10.dp),
      tint = PokedexTheme.colors.black.copy(alpha = 0.3f),
    )
    Spacer(modifier = Modifier.width(4.dp))
    Text(
      text = "Tap to learn more",
      fontSize = 9.sp,
      color = PokedexTheme.colors.black.copy(alpha = 0.3f),
    )
  }
}
