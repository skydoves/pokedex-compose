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

package com.skydoves.pokedex.compose.core.model

enum class PokemonGeneration(val label: String, val range: IntRange) {
  GEN_1("Generation I", 1..151),
  GEN_2("Generation II", 152..251),
  GEN_3("Generation III", 252..386),
  GEN_4("Generation IV", 387..493),
  GEN_5("Generation V", 494..649),
  GEN_6("Generation VI", 650..721),
  GEN_7("Generation VII", 722..809),
  GEN_8("Generation VIII", 810..905),
  GEN_9("Generation IX", 906..1025),
  ;

  companion object {
    fun fromId(id: Int): PokemonGeneration {
      return entries.firstOrNull { id in it.range } ?: GEN_1
    }
  }
}

data class EvolutionStep(
  val name: String,
  val id: Int,
  val minLevel: Int? = null,
)

data class EvolutionChain(
  val steps: List<EvolutionStep>,
) {
  val isFullyEvolved: Boolean get() = steps.size >= 3
  val currentStage: Int get() = steps.size
  val maxLevel: Int get() = steps.mapNotNull { it.minLevel }.maxOrNull() ?: 0
  val summary: String get() = steps.joinToString(" → ") { it.name }
}
