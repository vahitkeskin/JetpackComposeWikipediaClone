package com.vahitkeskin.jetpackcomposewikipediaclone.view.screens.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.vahitkeskin.jetpackcomposewikipediaclone.datastore.SharedDataStore
import com.vahitkeskin.jetpackcomposewikipediaclone.model.created.MainPageTagModel
import com.vahitkeskin.jetpackcomposewikipediaclone.utils.MarqueeText
import com.vahitkeskin.jetpackcomposewikipediaclone.utils.PagePreview
import com.vahitkeskin.jetpackcomposewikipediaclone.utils.ShimmerAnimation
import com.vahitkeskin.jetpackcomposewikipediaclone.viewmodel.MainPageViewModel
import kotlinx.coroutines.launch

@Composable
fun TopQuestionsScreen(
    mainPageViewModel: MainPageViewModel = hiltViewModel()
) {
    val mainPageTagModelState = remember { mutableStateListOf<MainPageTagModel>() }
    val coroutineScope = rememberCoroutineScope()
    var state by remember { mutableStateOf(true) }
    val context = LocalContext.current
    val dataStore = SharedDataStore(context)
    coroutineScope.launch {
        dataStore.saveBottomBar(true)
    }

    if (state) {
        coroutineScope.launch {
            mainPageViewModel.mainPageStateFlow.observeForever {
                mainPageTagModelState.addAll(it)
            }
        }
        state = false
    }

    if (mainPageTagModelState.size > 0) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            MarqueeText("Herkesin katkıda bulunabildiği özgür ansiklopedi Vikipedi'ye hoş geldiniz.")
            LazyColumn {
                itemsIndexed(mainPageTagModelState) { index, item ->
                    MainPageItemScreen(item, index)
                }
            }
        }
    } else {
        LazyColumn {
            repeat(5) {
                item {
                    ShimmerAnimation(PagePreview.HOME_PAGE)
                }
            }
        }
    }
}