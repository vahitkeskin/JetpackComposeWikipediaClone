package com.vahitkeskin.jetpackcomposewikipediaclone.view

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.vahitkeskin.jetpackcomposewikipediaclone.utils.Screen
import com.vahitkeskin.jetpackcomposewikipediaclone.view.screens.detail.CharactersDetailScreen
import com.vahitkeskin.jetpackcomposewikipediaclone.view.screens.favorite.FavoriteScreen
import com.vahitkeskin.jetpackcomposewikipediaclone.view.screens.search.SearchScreen
import com.vahitkeskin.jetpackcomposewikipediaclone.view.screens.main.TopQuestionsScreen
import com.vahitkeskin.jetpackcomposewikipediaclone.viewmodel.*

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainScreenNavigation(
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
                    //navController.navigate(Screen.CharacterDetail.route.plus("?characterDetail=${navigateToDetail}"))
                }
            }
        }

        //favorite
        composable(Screen.Favorite.route) {
            FavoriteScreen(viewModel = favoriteViewModel)
        }
        composable(Screen.CharacterDetail.route + "/{id}",
            content = { navBackStack ->
                val counter = navBackStack.arguments?.getString("id")
                if (!counter.isNullOrBlank()) {
                    Log.d(this.javaClass.simpleName, "MainScreenNavigation: ${navBackStack.arguments}")
                    CharactersDetailScreen(
                        detailViewModel = detailViewModel,
                        nameStr = counter,
                        viewModel = favoriteViewModel
                    )
                }
            },
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentScope.SlideDirection.Left,
                    animationSpec = tween(700)
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    AnimatedContentScope.SlideDirection.Right,
                    animationSpec = tween(700)
                )
            }
        )

        //Detail
        /*
        composable(Screen.CharacterDetail.route.plus("?characterDetail={characterDetail}"),
            content = {
                CharactersDetailScreen(
                    navHostController = navController,
                    navigateToBack = {
                        navController.popBackStack()
                    }
                )
            },
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentScope.SlideDirection.Left,
                    animationSpec = tween(700)
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    AnimatedContentScope.SlideDirection.Right,
                    animationSpec = tween(700)
                )
            }
        )

         */
    }
    /*
    NavHost(navController, startDestination = Screen.MainPage.route) {

        //favorite
        composable(Screen.Favorite.route) {
            FavoriteScreen()
        }
        //topQuestions
        composable(Screen.MainPage.route) {
            TopQuestionsScreen()
        }

        //search
        composable(Screen.Search.route) {
            SearchScreen {
                fabVisible.invoke(it)
            }
        }

        //Detail
        composable(
            Screen.CharacterDetail.route.plus("?characterDetail={characterDetail}"),
            content = {
                CharactersDetailScreen(
                    navigateToBack = {
                        navController.popBackStack()
                    }
                )
            },
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentScope.SlideDirection.Left,
                    animationSpec = tween(700)
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    AnimatedContentScope.SlideDirection.Right,
                    animationSpec = tween(700)
                )
            }
        )
    }
    */
}