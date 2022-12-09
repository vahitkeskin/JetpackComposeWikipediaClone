package com.vahitkeskin.jetpackcomposewikipediaclone.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MainPageTitle(
    item1Title: String,
    item1BackgroundColor: String? = "FF0000"
) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .background("#$item1BackgroundColor".color)
            .padding(start = 10.dp),
        text = item1Title, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.Black
    )
}