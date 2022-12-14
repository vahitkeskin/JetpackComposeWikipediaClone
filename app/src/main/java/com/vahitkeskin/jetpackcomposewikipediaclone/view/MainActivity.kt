package com.vahitkeskin.jetpackcomposewikipediaclone.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.skydoves.balloon.balloon
import com.vahitkeskin.jetpackcomposewikipediaclone.component.WikipediaTopAppBar
import com.vahitkeskin.jetpackcomposewikipediaclone.ui.theme.jetpackcomposewikipediacloneTheme
import com.vahitkeskin.jetpackcomposewikipediaclone.utils.slowlyOpeningAnim
import com.vahitkeskin.jetpackcomposewikipediaclone.view.screens.search.balloon.BalloonFactory
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@ExperimentalMaterialApi
@OptIn(ExperimentalAnimationApi::class)
class MainActivity : ComponentActivity() {

    private val balloon by balloon<BalloonFactory>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            jetpackcomposewikipediacloneTheme {
                val navController = rememberAnimatedNavController()
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize(),
                    topBar = {
                        TopAppBar {
                            Box(
                                modifier = Modifier
                                    .padding(15.dp)
                                    .alpha(slowlyOpeningAnim())
                                    .fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                WikipediaTopAppBar(navController = navController)
                            }
                        }
                    }
                ) {
                    BottomBarWithFabDem(balloon = balloon, navController = navController)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    jetpackcomposewikipediacloneTheme {
        //BottomBarWithFabDem()
    }
}