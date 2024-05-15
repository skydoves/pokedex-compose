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

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Immutable
@Parcelize
@Serializable
data class Pokemon(
  var page: Int = 0,
  @SerialName(value = "name")
  val nameField: String,
  @SerialName(value = "url") val url: String,
) : Parcelable {

  val name: String
    get() = nameField.replaceFirstChar { it.uppercase() }

  val imageUrl: String
    inline get() {
      val index = url.split("/".toRegex()).dropLast(1).last()
      return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/" +
        "pokemon/other/official-artwork/$index.png"
    }
}
