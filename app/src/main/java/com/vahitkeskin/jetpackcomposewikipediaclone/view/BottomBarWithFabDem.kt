package com.vahitkeskin.jetpackcomposewikipediaclone.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.vahitkeskin.jetpackcomposewikipediaclone.ui.theme.WikipediaBg
import com.vahitkeskin.jetpackcomposewikipediaclone.utils.Screen

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun BottomBarWithFabDem() {
    var fabTint by remember { mutableStateOf(false) }
    println("1. Start in the BottomBarWithFabDem...")
    val navController = rememberAnimatedNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    Scaffold(
        bottomBar = {
            AnimatedVisibility(
                visible = true,
                enter = slideInVertically(initialOffsetY = { it }),
                exit = slideOutVertically(targetOffsetY = { it }),
                content = {
                    BottomAppBar(
                        modifier = Modifier
                            .height(65.dp)
                            .clip(RoundedCornerShape(15.dp, 15.dp, 0.dp, 0.dp)),
                        cutoutShape = CircleShape,
                        backgroundColor = WikipediaBg,
                        elevation = 22.dp
                    ){
                        BottomNav(
                            navBackStackEntry = navBackStackEntry,
                            navController = navController
                        )
                    }
                })
        },
        floatingActionButtonPosition = FabPosition.Center,
        isFloatingActionButtonDocked = true,
        floatingActionButton = {
            AnimatedVisibility(
                visible = true,
                enter = slideInVertically(initialOffsetY = { it }),
                exit = slideOutVertically(targetOffsetY = { it }),
                content = {
                    FloatingActionButton(
                        shape = CircleShape,
                        onClick = {
                            navController.navigate(Screen.Search.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                            navController.navigate(Screen.Search.route)
                        },
                        contentColor = Color.Gray,
                        backgroundColor = WikipediaBg
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "Add icon",
                            tint = if (fabTint) Color.LightGray else Color.Gray
                        )
                    }
                })
        }
    ){ innerPadding ->
        println("2. Start in the BottomBarWithFabDem...")
        MainScreenNavigation(navController,innerPadding) { fabTint = it }
        /*
        Box(modifier = Modifier.padding(innerPadding)) {
            MainScreenNavigation(navController, innerPadding) { fabTint = it }
        }
        */
    }
}