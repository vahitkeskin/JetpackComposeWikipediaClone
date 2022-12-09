package com.vahitkeskin.jetpackcomposewikipediaclone.view.screens.detail

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState

/**
 * @authot: Vahit Keskin
 * creared on 8.12.2022
 */
@OptIn(ExperimentalPagerApi::class)
@Composable
fun DetailText(result: String) {
    val content = listOf(listOf(result))
    val pagerState = rememberPagerState(pageCount = content.size)
    HorizontalPager(
        state = pagerState,
        modifier = Modifier.fillMaxWidth()
    ) { page ->
        LazyColumn {
            val pageText = content[page]
            item {
                for (index in pageText.indices) {
                    Text(
                        text = buildAnnotatedString {
                            append(pageText[index])
                            if (index == 0) {
                                addStyle(
                                    style = SpanStyle(
                                        color = Color.White,
                                        fontSize = 52.sp,
                                        fontFamily = FontFamily.Cursive
                                    ),
                                    start = 0,
                                    end = 1
                                )
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        fontSize = 14.sp,
                        style = TextStyle(
                            textAlign = TextAlign.Justify
                        )
                    )
                }
            }
        }
    }
}