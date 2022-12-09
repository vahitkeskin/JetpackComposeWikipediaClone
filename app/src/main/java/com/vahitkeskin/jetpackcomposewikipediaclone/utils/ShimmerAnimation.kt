package com.vahitkeskin.jetpackcomposewikipediaclone.utils

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ShimmerAnimation(
    pagePreview: PagePreview ?= null
) {
    val transition = rememberInfiniteTransition()
    val translateAnim by transition.animateFloat(
        initialValue = 0f, targetValue = 1000f, animationSpec = infiniteRepeatable(
            tween(durationMillis = 1200, easing = FastOutSlowInEasing), RepeatMode.Reverse
        )
    )

    val brush = Brush.linearGradient(
        colors = ShimmerColorShades,
        start = Offset(10f, 10f),
        end = Offset(translateAnim, translateAnim)
    )

    when(pagePreview) {
        PagePreview.HOME_PAGE -> { ShimmerHomePage(brush = brush) }
        PagePreview.SEARCH_ITEM -> { ShimmerSearchItem(brush = brush) }
        PagePreview.SEARCH_LAST_ITEM -> { ShimmerSearchLastItem(brush = brush) }
        else -> { ShimmerHomePage(brush = brush) }
    }
}


@Composable
fun ShimmerHomePage(
    brush: Brush
) {
    Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp)) {
        Spacer(
            modifier = Modifier
                .padding(vertical = 8.dp)
                .clip(RoundedCornerShape(8.dp))
                .fillMaxWidth()
                .height(40.dp)
                .background(brush = brush)
        )
        Spacer(
            modifier = Modifier
                .padding(vertical = 5.dp)
                .clip(RoundedCornerShape(8.dp))
                .fillMaxWidth()
                .size(200.dp)
                .background(brush = brush)
        )
    }
}
@Composable
fun ShimmerSearchItem(
    brush: Brush
) {
    Box(modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp)) {
        Row {
            Spacer(
                modifier = Modifier
                    .padding(horizontal = 5.dp, vertical = 5.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .size(70.dp)
                    .background(brush = brush)
            )
            Column(
                verticalArrangement = Arrangement.Center
            ) {
                Spacer(
                    modifier = Modifier
                        .padding(horizontal = 5.dp, vertical = 5.dp)
                        .clip(RoundedCornerShape(5.dp))
                        .fillMaxWidth()
                        .height(30.dp)
                        .background(brush = brush)
                )
                Spacer(
                    modifier = Modifier
                        .padding(horizontal = 5.dp, vertical = 5.dp)
                        .clip(RoundedCornerShape(5.dp))
                        .fillMaxWidth()
                        .height(30.dp)
                        .background(brush = brush)
                )
            }
        }
    }
}
@Composable
fun ShimmerSearchLastItem(
    brush: Brush
) {
    Box(modifier = Modifier.padding(start = 5.dp, end = 5.dp, top = 5.dp, bottom = 10.dp)) {
        Spacer(
            modifier = Modifier
                .padding(horizontal = 0.dp, vertical = 0.dp)
                .clip(RoundedCornerShape(5.dp))
                .height(30.dp)
                .width(80.dp)
                .background(brush = brush)
        )
    }
}

val ShimmerColorShades = listOf(

    Color.LightGray.copy(0.9f),

    Color.LightGray.copy(0.2f),

    Color.LightGray.copy(0.9f)

)