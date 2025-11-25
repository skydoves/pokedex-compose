package com.skydoves.pokedex.compose.feature.settings

import android.content.res.Configuration
import android.os.Build
import androidx.annotation.ChecksSdkIntAtLeast
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.skydoves.landscapist.palette.rememberPaletteState
import com.skydoves.pokedex.compose.core.designsystem.component.PokedexCircularProgress
import com.skydoves.pokedex.compose.core.model.UiTheme
import com.skydoves.pokedex.compose.core.model.UserData
import com.skydoves.pokedex.compose.core.navigation.currentComposeNavigator
import com.skydoves.pokedex.compose.core.preview.PokedexPreviewTheme

@Composable
fun PokedexSettings(
  settingsViewModel: SettingsViewModel = hiltViewModel()
) {
  val uiState by settingsViewModel.uiState.collectAsStateWithLifecycle()
  val userData by settingsViewModel.userData.collectAsStateWithLifecycle()

  SettingsDialog(
    settingsUiState = uiState,
    userData = userData,
    supportDynamicColor = supportsDynamicTheming(),
    onDynamicColorsPreferenceChange = settingsViewModel::setDynamicColors,
    onChangeUiTheme = settingsViewModel::setUiTheme
  )
}

@Composable
fun SettingsDialog(
  settingsUiState: SettingsUiState,
  userData: UserData?,
  supportDynamicColor: Boolean = supportsDynamicTheming(),
  onDynamicColorsPreferenceChange: (useDynamicColor: Boolean) -> Unit,
  onChangeUiTheme: (uiTheme: UiTheme) -> Unit,
) {
  val windowInfo = LocalWindowInfo.current

  var palette by rememberPaletteState()
  val backgroundColor by palette.paletteBackgroundColor()

  val composeNavigator = currentComposeNavigator

  Box(
    modifier = Modifier
      .widthIn(max = (windowInfo.containerSize.width - 80).dp)
      .background(
        color = backgroundColor,
        shape = RoundedCornerShape(size = 30.dp)
      ),
    contentAlignment = Alignment.Center,
    content = {

      Column(
        modifier = Modifier
          .wrapContentSize(align = Alignment.TopCenter)
          .padding(all = 32.dp)
          .verticalScroll(state = rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(
          space = 24.dp,
          alignment = Alignment.Top
        ),
        content = {

          Text(
            text = stringResource(R.string.feature_settings_title),
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.titleLarge,
            color = LocalContentColor.current,
            modifier = Modifier.fillMaxWidth()
          )

          HorizontalDivider()

          if (settingsUiState == SettingsUiState.Idle && userData != null) {
            SettingsDialogContent(
              uiTheme = userData.uiTheme,
              onChangeUiTheme = onChangeUiTheme,
              useDynamicColors = userData.useDynamicColors,
              onChangeDynamicColors = onDynamicColorsPreferenceChange,
              supportDynamicTheme = supportDynamicColor
            )
          } else {
            Box(modifier = Modifier.fillMaxSize()) {
              PokedexCircularProgress()
            }
          }

          HorizontalDivider()

          Button(
            onClick = composeNavigator::navigateUp,
            content = {
              Text(
                text = stringResource(id = R.string.feature_settings_dismiss_dialog_button_text),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary
              )
            },
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
  onChangeUiTheme: (UiTheme) -> Unit,
  useDynamicColors: Boolean,
  onChangeDynamicColors: (Boolean) -> Unit,
  supportDynamicTheme: Boolean,
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

      AnimatedVisibility(visible = supportDynamicTheme) {
        SettingsDialogThemeSection(
          useDynamicUiTheme = useDynamicColors,
          onChangeDynamicUiTheme = onChangeDynamicColors
        )
      }
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

      Text(
        text = stringResource(id = R.string.feature_settings_theme),
        textAlign = TextAlign.Start,
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.fillMaxWidth()
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

          UiTheme.entries.forEach { theme ->
            Row(
              Modifier
                .fillMaxWidth()
                .selectable(
                  selected = uiTheme == UiTheme.FOLLOW_SYSTEM,
                  role = Role.RadioButton,
                  onClick = { onChangeUiTheme(UiTheme.FOLLOW_SYSTEM) },
                ),
              horizontalArrangement = Arrangement.spacedBy(
                space = 8.dp,
                alignment = Alignment.Start
              ),
              verticalAlignment = Alignment.CenterVertically,
              content = {
                RadioButton(
                  selected = uiTheme == UiTheme.FOLLOW_SYSTEM,
                  onClick = null
                )
                Text(text = stringResource(id = R.string.feature_settings_theme_follow_system))
              }
            )
          }


        }
      )
    }
  )
}

@Composable
private fun SettingsDialogThemeSection(
  useDynamicUiTheme: Boolean,
  onChangeDynamicUiTheme: (Boolean) -> Unit,
) {

  Row(
    modifier = Modifier
      .fillMaxWidth()
      .selectable(
        selected = useDynamicUiTheme,
        role = Role.Switch,
        onClick = {
          onChangeDynamicUiTheme(!useDynamicUiTheme)
        }
      ),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.SpaceBetween,
    content = {
      Text(
        text = stringResource(id = R.string.feature_settings_dynamic_theme),
        style = MaterialTheme.typography.titleMedium,
        color = LocalContentColor.current
      )

      Switch(
        checked = useDynamicUiTheme,
        onCheckedChange = null
      )
    }
  )
}

@ChecksSdkIntAtLeast(api = Build.VERSION_CODES.S)
fun supportsDynamicTheming() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun SettingsDialogPreview() {
  PokedexPreviewTheme { animatedContentScope ->
    SettingsDialog(
      settingsUiState = SettingsUiState.Idle,
      userData = null,
      supportDynamicColor = true,
      onDynamicColorsPreferenceChange = {},
      onChangeUiTheme = {}
    )
  }
}