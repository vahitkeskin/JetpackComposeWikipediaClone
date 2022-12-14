package com.vahitkeskin.jetpackcomposewikipediaclone.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val title: String?, val icon: ImageVector?) {
    object MainPage : Screen("main_page", "Main Page", Icons.Default.Home)
    object Favorite : Screen("favorite", "Favorite", Icons.Default.Favorite)
    object Search : Screen("search", null, null)
    object Detail : Screen("detail",null,null)
}