package com.vahitkeskin.jetpackcomposewikipediaclone.view.screens.favorite

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp

/**
 * @authot: Vahit Keskin
 * creared on 8.12.2022
 */
@Composable
fun TextShadow(text: String) {
    val offset = Offset(5.0f, 10.0f)
    Text(
        text = text,
        style = TextStyle(
            fontSize = 24.sp,
            shadow = Shadow(
                color = Color.Red,
                offset = offset,
                blurRadius = 3f
            )
        )
    )
}