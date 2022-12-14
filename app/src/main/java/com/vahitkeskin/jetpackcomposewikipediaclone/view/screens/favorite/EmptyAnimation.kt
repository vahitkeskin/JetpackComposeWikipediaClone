package com.vahitkeskin.jetpackcomposewikipediaclone.view.screens.favorite

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.vahitkeskin.jetpackcomposewikipediaclone.utils.PagePreview

/**
 * @authot: Vahit Keskin
 * creared on 8.12.2022
 */
@Composable
fun EmptyAnimation(
    pagePreview: PagePreview
) {
    val infiniteTransition = rememberInfiniteTransition()
    val dy by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    val travelDistance = with(LocalDensity.current) { 30.dp.toPx() }

    Column(
        modifier = Modifier
            .width(200.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = if (pagePreview == PagePreview.SEARCH_SCREEN) Icons.Filled.Search else Icons.Filled.Favorite,
            "",
            tint = Color.Red,
            modifier = Modifier
                .size(100.dp)
                .align(Alignment.CenterHorizontally)
                .graphicsLayer {
                    translationY = dy * travelDistance
                },
        )

        Spacer(modifier = Modifier.height(30.dp))

        Box(
            modifier = Modifier
                .width(40.dp)
                .height(10.dp)
                .align(Alignment.CenterHorizontally)
                .graphicsLayer {
                    scaleX = 0.5f + dy / 2
                    alpha = 0.3f + dy / 2
                }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.Red, shape = CircleShape)
            )
        }
    }

}