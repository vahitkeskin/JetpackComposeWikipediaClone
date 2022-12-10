package com.vahitkeskin.jetpackcomposewikipediaclone.view

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.skydoves.balloon.Balloon
import com.skydoves.balloon.showAtCenter
import com.vahitkeskin.jetpackcomposewikipediaclone.datastore.SharedDataStore
import com.vahitkeskin.jetpackcomposewikipediaclone.utils.Screen
import com.vahitkeskin.jetpackcomposewikipediaclone.view.screens.detail.CharactersDetailScreen
import com.vahitkeskin.jetpackcomposewikipediaclone.view.screens.favorite.FavoriteScreen
import com.vahitkeskin.jetpackcomposewikipediaclone.view.screens.main.TopQuestionsScreen
import com.vahitkeskin.jetpackcomposewikipediaclone.view.screens.search.SearchScreen
import com.vahitkeskin.jetpackcomposewikipediaclone.viewmodel.*

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainScreenNavigation(
    balloon: Balloon?,
    navController: NavHostController,
    paddingValues: PaddingValues,
    fabVisible: (Boolean) -> Unit
) {
    println("Start in the MainScreenNavigation...")
    val favoriteViewModel: FavoriteViewModel = hiltViewModel()
    val lastSearchViewModel: LastSearchViewModel = hiltViewModel()
    val mainPageViewModel: MainPageViewModel = hiltViewModel()
    val searchViewModel: SearchViewModel = hiltViewModel()
    val detailViewModel: DetailViewModel = hiltViewModel()
    val context = LocalContext.current
    val dataStore = SharedDataStore(context)
    //var stateOnlyVisible by remember { mutableStateOf(true) }
    println("1. Shared data store for search popup: ${dataStore.getSearchPopup.collectAsState(initial = "").value}")

    Box {
        AnimatedNavHost(
            navController = navController,
            startDestination = Screen.MainPage.route,
            Modifier.padding(paddingValues)
        ) {

            //topQuestions
            composable(Screen.MainPage.route) {
                TopQuestionsScreen(
                    mainPageViewModel = mainPageViewModel
                )
            }

            //search
            composable(Screen.Search.route) {
                SearchScreen(
                    searchViewModel = searchViewModel,
                    lastSearchViewModel = lastSearchViewModel,
                    navHostController = navController
                ) { fab, navigateToDetail ->
                    fabVisible.invoke(fab)
                    if (navigateToDetail != "") {
                        println("composable Click CharactersScreen")
                    }
                }
            }

            //favorite
            composable(Screen.Favorite.route) {
                FavoriteScreen(viewModel = favoriteViewModel)
            }
            composable(Screen.CharacterDetail.route + "/{id}", content = { navBackStack ->
                val counter = navBackStack.arguments?.getString("id")
                if (!counter.isNullOrBlank()) {
                    Log.d(
                        this.javaClass.simpleName, "MainScreenNavigation: ${navBackStack.arguments}"
                    )
                    CharactersDetailScreen(
                        detailViewModel = detailViewModel,
                        nameStr = counter,
                        viewModel = favoriteViewModel
                    )
                }
            }, enterTransition = {
                slideIntoContainer(
                    AnimatedContentScope.SlideDirection.Left, animationSpec = tween(700)
                )
            }, popExitTransition = {
                slideOutOfContainer(
                    AnimatedContentScope.SlideDirection.Right, animationSpec = tween(700)
                )
            })
        }
        //It will be shown only once, when Dismiss is done the dataStore value 0 will be saved
        if (dataStore.getSearchPopup.collectAsState(initial = "").value != 0) { } //Development...
        AndroidView(modifier = Modifier
            .align(Alignment.BottomCenter)
            .padding(bottom = 30.dp)
            .size(height = 110.dp, width = 60.dp), factory = { context ->
            View(context).apply {
                balloon?.let {
                    showAtCenter(it)
                }
            }
        })
    }
}