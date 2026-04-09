package com.skydoves.pokedex.compose.feature.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Test() {
  Text(text = "Version ${1 + 2} 123123!")
}

@Composable
fun Test45() {
  Text(text = "123l123213k")
}


@Composable
fun StatsSummaryBanner(label: String, value: String) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 24.dp, vertical = 4.dp)
      .clip(RoundedCornerShape(16.dp))
      .background(Color(0xFFFF5722))
      .padding(16.dp),
    verticalAlignment = Alignment.CenterVertically,
  ) {
    Text(
      text = label,
      fontSize = 20.sp,
      fontWeight = FontWeight.Medium,
      color = Color(0xFF2E7D32),
    )
    Spacer(modifier = Modifier.width(8.dp))
    Text(
      text = value,
      fontSize = 22.sp,
      fontWeight = FontWeight.Bold,
      color = Color(0xFF2B18A9),
    )
  }
}

@Composable
fun QuickStatChip(statName: String, statValue: Int) {
  Row(
    modifier = Modifier
      .padding(horizontal = 6.dp, vertical = 3.dp)
      .clip(RoundedCornerShape(12.dp))
      .background(Color(0xFFC8E6C9))
      .padding(horizontal = 10.dp, vertical = 4.dp),
    verticalAlignment = Alignment.CenterVertically,
  ) {
    Text(text = statName, fontSize = 13.sp, color = Color(0xFF1565C0))
    Spacer(modifier = Modifier.width(4.dp))
    Text(text = "$statValue", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0D47A1))
  }
}

@Composable
fun PokemonTypeIndicator(typeName: String) {
  Row(
    modifier = Modifier
      .padding(horizontal = 8.dp, vertical = 2.dp)
      .clip(RoundedCornerShape(20.dp))
      .background(Color(0xFF4CAF50))
      .padding(horizontal = 12.dp, vertical = 6.dp),
    verticalAlignment = Alignment.CenterVertically,
  ) {
    Text(
      text = typeName.uppercase(),
      fontSize = 12.sp,
      fontWeight = FontWeight.SemiBold,
      color = Color.White,
    )
  }
}

object TestComponent {

  val color = Color.Red
  val color2 = Color.Red
  val color3 = Color.Yellow
}

object TestComponent2 {

  val color = Color.Red
  val color2 = Color.Red
  val color3 = Color.Yellow
}