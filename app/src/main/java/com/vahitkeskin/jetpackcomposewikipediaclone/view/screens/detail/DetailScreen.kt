package com.vahitkeskin.jetpackcomposewikipediaclone.view.screens.detail

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.vahitkeskin.jetpackcomposewikipediaclone.component.WikipediaFavoriteButton
import com.vahitkeskin.jetpackcomposewikipediaclone.component.WikipediaParallax
import com.vahitkeskin.jetpackcomposewikipediaclone.dao.favorites.FavoriteRoom
import com.vahitkeskin.jetpackcomposewikipediaclone.model.created.DetailItemModel
import com.vahitkeskin.jetpackcomposewikipediaclone.utils.State
import com.vahitkeskin.jetpackcomposewikipediaclone.utils.detailTimestamp
import com.vahitkeskin.jetpackcomposewikipediaclone.utils.instantTime
import com.vahitkeskin.jetpackcomposewikipediaclone.viewmodel.DetailViewModel
import com.vahitkeskin.jetpackcomposewikipediaclone.viewmodel.FavoriteViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber

@OptIn(ExperimentalAnimationApi::class, ExperimentalComposeUiApi::class)
@Composable
fun DetailScreen(
    detailViewModel: DetailViewModel = hiltViewModel(),
    nameStr: String?,
    viewModel: FavoriteViewModel = hiltViewModel()
) {
    var state by remember { mutableStateOf(true) }
    val coroutineScope = rememberCoroutineScope()
    val detailItemModelList = remember { mutableStateListOf<DetailItemModel>() }

    if (state) {
        coroutineScope.launch {
            detailViewModel.detailData(nameStr).collect {
                when (it) {
                    is State.DataState -> {
                        delay(1000)
                        detailItemModelList.add(
                            DetailItemModel(
                                description = it.data.body()?.description.orEmpty(),
                                extract = it.data.body()?.extract.orEmpty(),
                                originalimage = it.data.body()?.thumbnail?.source.orEmpty(),
                                timestamp = it.data.body()?.timestamp
                            )
                        )
                    }
                    is State.ErrorState -> {
                        Timber.d("4. Select for detail screen item name: ErrorState")
                    }
                    is State.LoadingState -> {
                        Timber.d("5. Select for detail screen item name: LoadingState")
                    }
                }
            }
        }

        state = false
    }

    if (detailItemModelList.size > 0) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {
            if (!detailItemModelList[0].extract.isNullOrBlank() && !detailItemModelList[0].originalimage.isNullOrBlank()) {
                Box(
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Text(
                        text = detailItemModelList[0].description.orEmpty(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        textAlign = TextAlign.Center,
                        fontFamily = FontFamily.Cursive,
                        fontSize = 24.sp
                    )
                    WikipediaFavoriteButton { selected ->
                        if (selected) {
                            val favoriteRoom = FavoriteRoom(
                                id = 0,
                                itemTitle = detailItemModelList[0].description.orEmpty(),
                                itemDetail = detailItemModelList[0].extract.orEmpty(),
                                itemImage = detailItemModelList[0].originalimage.orEmpty(),
                                itemSaveTime = instantTime()
                            )
                            viewModel.addFavorite(favoriteRoom)
                        }
                    }
                }
                Box(
                    modifier = Modifier
                        .size(height = 190.dp, width = 400.dp)
                        .padding(10.dp)
                ) {
                    WikipediaParallax(imageUrl = detailItemModelList[0].originalimage)
                }

                DetailText(detailItemModelList[0].extract.orEmpty())

                Text(
                    text = detailItemModelList[0].timestamp?.detailTimestamp().orEmpty(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    textAlign = TextAlign.End,
                    fontFamily = FontFamily.SansSerif,
                    fontSize = 12.sp
                )
            }
        }
    }
}

@Composable
@Preview
fun DetailScreenPreview() {
    DetailScreen(nameStr = "Developer")
}
