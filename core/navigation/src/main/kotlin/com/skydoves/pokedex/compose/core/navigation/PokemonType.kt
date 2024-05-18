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

package com.skydoves.pokedex.compose.core.navigation

import android.net.Uri
import android.os.Bundle
import androidx.core.os.BundleCompat
import androidx.navigation.NavType
import com.skydoves.pokedex.compose.core.model.Pokemon
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object PokemonType : NavType<Pokemon>(isNullableAllowed = false) {

  override fun put(bundle: Bundle, key: String, value: Pokemon) {
    bundle.putParcelable(key, value)
  }

  override fun get(bundle: Bundle, key: String): Pokemon? =
    BundleCompat.getParcelable(bundle, key, Pokemon::class.java)

  override fun parseValue(value: String): Pokemon {
    return Json.decodeFromString(Uri.decode(value))
  }

  override fun serializeAsValue(value: Pokemon): String = Uri.encode(Json.encodeToString(value))
}
