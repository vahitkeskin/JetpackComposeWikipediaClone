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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.skydoves.balloon.Balloon
import com.vahitkeskin.jetpackcomposewikipediaclone.datastore.SharedDataStore
import com.vahitkeskin.jetpackcomposewikipediaclone.ui.theme.WikipediaBg
import com.vahitkeskin.jetpackcomposewikipediaclone.utils.Screen

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun BottomBarWithFabDem(
    balloon: Balloon? = null,
    navController: NavHostController
) {
    var fabTint by remember { mutableStateOf(false) }
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val context = LocalContext.current
    val dataStore = SharedDataStore(context)
    val getBottomBar = dataStore.getBottomBar.collectAsState(initial = "").value


    Scaffold(
        bottomBar = {
            AnimatedVisibility(
                visible = getBottomBar == true,
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
                    ) {
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
                visible = getBottomBar == true,
                enter = slideInVertically(initialOffsetY = { it }),
                exit = slideOutVertically(targetOffsetY = { it }),
                content = {
                    FloatingActionButton(
                        shape = CircleShape,
                        onClick = {
                            Screen.Search.route.let { it1 ->
                                navController.navigate(it1) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
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
    ) { innerPadding ->
        MainScreenNavigation(
            balloon,
            navController,
            innerPadding
        ) {
            fabTint = it
        }
    }
}