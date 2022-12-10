package com.vahitkeskin.jetpackcomposewikipediaclone.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.skydoves.balloon.balloon
import com.vahitkeskin.jetpackcomposewikipediaclone.component.WikipediaTopAppBar
import com.vahitkeskin.jetpackcomposewikipediaclone.ui.theme.jetpackcomposewikipediacloneTheme
import com.vahitkeskin.jetpackcomposewikipediaclone.utils.slowlyOpeningAnim
import com.vahitkeskin.jetpackcomposewikipediaclone.view.screens.search.balloon.BalloonFactory
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@ExperimentalMaterialApi
class MainActivity : ComponentActivity() {

    private val balloon by balloon<BalloonFactory>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            jetpackcomposewikipediacloneTheme {
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
                                WikipediaTopAppBar()
                            }
                        }
                    }
                ) {
                    BottomBarWithFabDem(balloon = balloon)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    jetpackcomposewikipediacloneTheme {
        BottomBarWithFabDem()
    }
}