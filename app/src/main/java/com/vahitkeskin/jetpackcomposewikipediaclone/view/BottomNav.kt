package com.vahitkeskin.jetpackcomposewikipediaclone.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.vahitkeskin.jetpackcomposewikipediaclone.ui.theme.WikipediaBg
import com.vahitkeskin.jetpackcomposewikipediaclone.utils.Screen
import com.vahitkeskin.jetpackcomposewikipediaclone.utils.items

@Composable
fun BottomNav(
    navBackStackEntry: NavBackStackEntry?,
    navController: NavController
) {
    val currentRoute = navBackStackEntry?.destination?.route

    AnimatedVisibility(
        visible = true,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it }),
        content = {
            BottomNavigation(
                modifier = Modifier
                    .padding(12.dp, 0.dp, 12.dp, 0.dp)
                    .height(100.dp),
                backgroundColor = WikipediaBg,
                elevation = 0.dp
            ) {
                items.forEach { item ->
                    if (item.route != Screen.Search.route) {
                        val selected = item.route == currentRoute
                        BottomNavigationItem(
                            selected = selected,
                            selectedContentColor = Color.LightGray,
                            unselectedContentColor = Color.Gray,
                            icon = {
                                item.icon?.let {
                                    Icon(
                                        imageVector = it,
                                        contentDescription = "",
                                        modifier = Modifier.size(35.dp)
                                    )
                                }
                            },
                            label = {
                                item.title?.let {
                                    Text(
                                        text = it
                                    )
                                }
                            },
                            onClick = {

                                if (currentRoute == item.route) {
                                    return@BottomNavigationItem
                                }

                                if (currentRoute != item.route) {
                                    item.route.let { it1 ->
                                        navController.navigate(it1) {
                                            popUpTo(navController.graph.findStartDestination().id) {
                                                saveState = true
                                            }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    }
                                }
                            }
                        )
                    }
                }
            }
        })
}