package com.vahitkeskin.jetpackcomposewikipediaclone.view.screens.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.vahitkeskin.jetpackcomposewikipediaclone.model.created.MainPageTagModel
import com.vahitkeskin.jetpackcomposewikipediaclone.utils.MainPageTitle
import eu.wewox.textflow.TextFlow
import eu.wewox.textflow.TextFlowObstacleAlignment

/**
 * @authot: Vahit Keskin
 * creared on 8.12.2022
 */
@Composable
fun MainPageItemScreen(
    mainPageTagModelState: MainPageTagModel,
    position: Int
) {
    if (position == 0) {
        SingleItemScreen(mainPageTagModelState)
    } else {
        Card(
            modifier = Modifier.padding(20.dp)
        ) {
            Column {
                MainPageTitle(item1Title = mainPageTagModelState.title.orEmpty(), item1BackgroundColor = mainPageTagModelState.background ?: "FF0000")
                TextFlow(
                    text = mainPageTagModelState.detail.orEmpty(),
                    modifier = Modifier.fillMaxWidth(),
                    obstacleAlignment = TextFlowObstacleAlignment.TopStart,
                    obstacleContent = {
                        Image(
                            painter = rememberAsyncImagePainter(mainPageTagModelState.image.orEmpty()),
                            contentDescription = null,
                            modifier = Modifier
                                .size(90.dp)
                                .padding(4.dp)
                        )
                    }
                )
            }
        }
    }
}