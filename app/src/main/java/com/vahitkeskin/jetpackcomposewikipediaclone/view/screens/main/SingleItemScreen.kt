package com.vahitkeskin.jetpackcomposewikipediaclone.view.screens.main

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vahitkeskin.jetpackcomposewikipediaclone.component.WikipediaParallax
import com.vahitkeskin.jetpackcomposewikipediaclone.model.created.MainPageTagModel
import com.vahitkeskin.jetpackcomposewikipediaclone.utils.MainPageTitle
import com.vahitkeskin.jetpackcomposewikipediaclone.utils.PagePreview

/**
 * @authot: Vahit Keskin
 * creared on 8.12.2022
 */
@OptIn(ExperimentalAnimationApi::class, ExperimentalComposeUiApi::class)
@Composable
fun SingleItemScreen(
    mainPageTagModelState: MainPageTagModel
) {
    Card(
        modifier = Modifier.padding(20.dp)
    ) {
        Column {
            MainPageTitle(item1Title = mainPageTagModelState.title.orEmpty(), item1BackgroundColor = mainPageTagModelState.background ?: "FF0000")
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(height = 190.dp, width = 400.dp)
                        .padding(10.dp)
                ) {
                    WikipediaParallax(mainPageTagModelState.image.orEmpty(), PagePreview.HOME_PAGE)
                }
                /*
                Image(
                    painter = rememberAsyncImagePainter(item1Image),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(10.dp)
                        .size(height = 200.dp, width = 400.dp),
                    contentScale = ContentScale.FillBounds
                )
                */
                Text(
                    modifier = Modifier.padding(bottom = 10.dp),
                    text = mainPageTagModelState.detail.orEmpty()
                )
            }
        }
    }
}