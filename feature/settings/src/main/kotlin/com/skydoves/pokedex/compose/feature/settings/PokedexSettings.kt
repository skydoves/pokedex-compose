package com.skydoves.pokedex.compose.feature.settings

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.skydoves.pokedex.compose.core.data.repository.userdata.FakeUserDataRepository
import com.skydoves.pokedex.compose.core.designsystem.component.PokedexCircularProgress
import com.skydoves.pokedex.compose.core.designsystem.component.PokedexText
import com.skydoves.pokedex.compose.core.designsystem.theme.PokedexTheme
import com.skydoves.pokedex.compose.core.model.UiTheme
import com.skydoves.pokedex.compose.core.navigation.currentComposeNavigator
import com.skydoves.pokedex.compose.core.preview.PokedexPreviewTheme

@Composable
fun PokedexSettings(
  settingsViewModel: SettingsViewModel = hiltViewModel()
) {
  val uiState by settingsViewModel.uiState.collectAsStateWithLifecycle()

  val windowInfo = LocalWindowInfo.current

  Box(
    modifier = Modifier
      .widthIn(max = (windowInfo.containerSize.width - 80).dp)
      .background(
        color = PokedexTheme.colors.background,
        shape = RoundedCornerShape(size = 32.dp)
      )
  ) {
    SettingsDialog(
      settingsUiState = uiState,
      onChangeUiTheme = settingsViewModel::setUiTheme
    )
  }
}

@Composable
fun SettingsDialog(
  settingsUiState: SettingsUiState,
  onChangeUiTheme: (uiTheme: UiTheme) -> Unit,
) {

  val composeNavigator = currentComposeNavigator

  Box(
    contentAlignment = Alignment.Center,
    content = {

      Column(
        modifier = Modifier
            .wrapContentSize(align = Alignment.Center)
            .padding(all = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(
          space = 24.dp,
          alignment = Alignment.Top
        ),
        content = {

          PokedexText(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.feature_settings_title),
            color = PokedexTheme.colors.black,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Start,
            fontSize = 22.sp,
          )

          HorizontalDivider()

          if (settingsUiState is SettingsUiState.Success) {
            SettingsDialogContent(
              uiTheme = settingsUiState.userData.uiTheme,
              onChangeUiTheme = onChangeUiTheme
            )
          }

          if (settingsUiState is SettingsUiState.Loading) {
            Box(modifier = Modifier.fillMaxSize()) {
              PokedexCircularProgress()
            }
          }

          HorizontalDivider()

          TextButton(
            onClick = composeNavigator::navigateUp,
            content = {
              Text(text = stringResource(id = R.string.feature_settings_dismiss_dialog_button_text))
            },
            colors = ButtonDefaults.textButtonColors(contentColor = PokedexTheme.colors.primary),
            modifier = Modifier.align(alignment = Alignment.End)
          )
        }
      )
    }
  )
}

@Composable
private fun SettingsDialogContent(
  uiTheme: UiTheme,
  onChangeUiTheme: (UiTheme) -> Unit
) {
  Column(
    modifier = Modifier.fillMaxWidth(),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.spacedBy(
      space = 16.dp,
      alignment = Alignment.CenterVertically
    ),
    content = {

      SettingsDialogThemeSection(
        uiTheme = uiTheme,
        onChangeUiTheme = onChangeUiTheme
      )
    }
  )
}

@Composable
private fun SettingsDialogThemeSection(
  uiTheme: UiTheme,
  onChangeUiTheme: (UiTheme) -> Unit,
  modifier: Modifier = Modifier
) {

  Column(
    modifier = modifier.fillMaxWidth(),
    horizontalAlignment = Alignment.Start,
    verticalArrangement = Arrangement.spacedBy(
      space = 16.dp,
      alignment = Alignment.CenterVertically
    ),
    content = {

      PokedexText(
        modifier = Modifier.fillMaxWidth(),
        text = stringResource(id = R.string.feature_settings_theme),
        color = PokedexTheme.colors.black,
        fontWeight = FontWeight.Medium,
        textAlign = TextAlign.Start,
        fontSize = 16.sp,
      )

      Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .selectableGroup(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(
          space = 16.dp,
          alignment = Alignment.CenterVertically
        ),
        content = {

          UiTheme.entries.forEach {
            Row(
              Modifier
                  .selectable(
                      selected = uiTheme == it,
                      role = Role.RadioButton,
                      onClick = { onChangeUiTheme(it) },
                  ),
              horizontalArrangement = Arrangement.spacedBy(
                space = 8.dp,
                alignment = Alignment.Start
              ),
              verticalAlignment = Alignment.CenterVertically,
              content = {
                RadioButton(
                  selected = uiTheme == it,
                  onClick = null,
                  colors = RadioButtonDefaults.colors(selectedColor = PokedexTheme.colors.primary)
                )

                PokedexText(
                  text = stringResource(
                    id = when (it) {
                      UiTheme.FOLLOW_SYSTEM -> R.string.feature_settings_theme_follow_system
                      UiTheme.LIGHT -> R.string.feature_settings_theme_light
                      UiTheme.DARK -> R.string.feature_settings_theme_dark
                    }
                  ),
                  color = PokedexTheme.colors.black,
                  fontWeight = FontWeight.Normal,
                  textAlign = TextAlign.Start,
                  fontSize = 14.sp,
                )
              }
            )
          }
        }
      )
    }
  )
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun SettingsDialogPreview() {
  PokedexPreviewTheme {
    PokedexSettings(
      settingsViewModel = SettingsViewModel(userDataRepository = FakeUserDataRepository())
    )
  }
}