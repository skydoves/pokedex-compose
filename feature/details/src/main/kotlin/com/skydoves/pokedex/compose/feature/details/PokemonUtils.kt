package com.skydoves.pokedex.compose.feature.details

import androidx.compose.ui.graphics.Color

typealias StatValue = Int
typealias PokemonId = Int

const val MAX_STAT = 245
const val MAX_POKEMON_ID = 1025
const val EVOLUTION_LEVEL_THRESHOLD = 16
const val DEFAULT_ABILITIES_COUNT = 3

object PokemonUtils {
  fun formatStatValue(value: Int, maxValue: Int = 255): Float {
    return (value.toFloat() / maxValue).coerceIn(0f, 1f)
  }

  fun getStatColor(value: Int): Color {
    return when {
      value >= 100 -> Color(0xFF4CAF50)
      value >= 60 -> Color(0xFFFFC107)
      else -> Color(0xFFF44336)
    }
  }

  fun formatPokemonId(id: Int): String {
    return "#${id.toString().padStart(3, '0')}"
  }

  fun getEvolutionArrow(fromLevel: Int?): String {
    return if (fromLevel != null) "Lv.$fromLevel →" else "→"
  }

  val DEFAULT_EVOLUTION = listOf("Bulbasaur", "Ivysaur", "Venusaur")
}

fun String.capitalizeFirst(): String {
  return this.replaceFirstChar { it.uppercase() }
}

inline fun <T> List<T>.filterAndMap(
  predicate: (T) -> Boolean,
  transform: (T) -> String,
): List<String> = filter(predicate).map(transform)

interface Displayable {
  val displayName: String
  val displayDescription: String
}

fun Int.toStatPercentage(max: Int = 255): Int {
  return ((this.toFloat() / max) * 100).toInt().coerceIn(0, 100)
}

fun List<String>.toCommaSeparated(): String {
  return this.joinToString(", ")
}

fun String.toAbilityEmoji(): String {
  return when (this.lowercase()) {
    "overgrow" -> "🌿"
    "chlorophyll" -> "☀️"
    "blaze" -> "🔥"
    "torrent" -> "💧"
    "shield dust" -> "🛡️"
    else -> "⚡"
  }
}
