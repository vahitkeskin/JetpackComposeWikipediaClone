package com.vahitkeskin.jetpackcomposewikipediaclone.view

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.vahitkeskin.jetpackcomposewikipediaclone.R
import com.vahitkeskin.jetpackcomposewikipediaclone.component.WikipediaTopAppBar
import com.vahitkeskin.jetpackcomposewikipediaclone.ui.theme.jetpackcomposewikipediacloneTheme
import com.vahitkeskin.jetpackcomposewikipediaclone.utils.Screen
import com.vahitkeskin.jetpackcomposewikipediaclone.utils.State
import com.vahitkeskin.jetpackcomposewikipediaclone.utils.slowlyOpeningAnim
import com.vahitkeskin.jetpackcomposewikipediaclone.viewmodel.MainPageViewModel
import com.vahitkeskin.jetpackcomposewikipediaclone.viewmodel.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
@ExperimentalMaterialApi
class MainActivity : ComponentActivity() {

    private val mainPageViewModel: MainPageViewModel by viewModels()

    @OptIn(ExperimentalAnimationApi::class)
    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        /*
        installSplashScreen().apply {
            setKeepVisibleCondition {
                viewModel.isLoading.value
            }
        }
        */
        println("Start in the MainActivity...")
        setContent {
            jetpackcomposewikipediacloneTheme {
                //val mainPageViewModel: MainPageViewModel by viewModels()

                val coroutineScope = rememberCoroutineScope()
                var isLoading by remember { mutableStateOf(false) }
                /*
                coroutineScope.launch {
                    viewModel.isLoading.collect {
                        isLoading = it
                    }
                }
                */


                if (!isLoading) {
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
                        BottomBarWithFabDem()
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    jetpackcomposewikipediacloneTheme {
        //BottomBarWithFabDem(null,null,null)
    }
}