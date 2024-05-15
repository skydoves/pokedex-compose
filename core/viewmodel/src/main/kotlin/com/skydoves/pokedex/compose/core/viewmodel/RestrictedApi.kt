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

package com.skydoves.pokedex.compose.core.viewmodel

@Target(
  AnnotationTarget.CLASS,
  AnnotationTarget.PROPERTY,
  AnnotationTarget.CONSTRUCTOR,
  AnnotationTarget.FUNCTION,
  AnnotationTarget.TYPEALIAS,
)
@Retention(AnnotationRetention.BINARY)
@RequiresOptIn(
  message = "This is API has been restricted. Do not depend on this API for working properly",
  level = RequiresOptIn.Level.ERROR,
)
public annotation class RestrictedApi
