package com.skydoves.pokedex.compose.core.data

import androidx.datastore.core.DataStore
import com.skydoves.pokedex.compose.core.data.repository.settings.SettingsRepositoryImpl
import com.skydoves.pokedex.compose.core.datastore.PreferencesDataSource
import com.skydoves.pokedex.compose.core.datastore.UserPreferences
import com.skydoves.pokedex.compose.core.model.UiTheme
import com.skydoves.pokedex.compose.core.model.UserData
import com.skydoves.pokedex.compose.core.test.MainCoroutinesRule
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.updateAndGet
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SettingsRepositoryTest {

  private lateinit var repository: SettingsRepositoryImpl
  private lateinit var preferencesDataSource: PreferencesDataSource

  @get:Rule
  val coroutinesRule = MainCoroutinesRule()

  @Before
  fun setup() {
    preferencesDataSource = PreferencesDataSource(
      userPreferences = InMemoryDataStore(UserPreferences.getDefaultInstance())
    )
    repository = SettingsRepositoryImpl(preferencesDataSource, coroutinesRule.testDispatcher)
  }

  @Test
  fun default_user_data_is_correct() = runTest {
      assertEquals(
        UserData(
          uiTheme = UiTheme.FOLLOW_SYSTEM,
          useDynamicColors = false
        ),
        repository.userData.first(),
      )
    }

  @Test
  fun set_dynamic_colors_to_preferences() =
    runTest {
      repository.setDynamicColors(true)

      assertEquals(
        true,
        repository.userData
          .map { it.useDynamicColors }
          .first(),
      )
      assertEquals(
        true,
        preferencesDataSource.userData
          .map { it.useDynamicColors }
          .first(),
      )
    }

  @Test
  fun set_ui_theme_to_preferences() =
    runTest {
      repository.setUiTheme(uiTheme = UiTheme.DARK)

      assertEquals(
        UiTheme.DARK,
        repository.userData
          .map { it.uiTheme }
          .first(),
      )
      assertEquals(
        UiTheme.DARK,
        preferencesDataSource.userData
          .map { it.uiTheme }
          .first(),
      )
    }
}

class InMemoryDataStore<T>(initialValue: T) : DataStore<T> {
  override val data = MutableStateFlow(initialValue)
  override suspend fun updateData(
    transform: suspend (T) -> T
  ): T = data.updateAndGet { transform(it) }
}