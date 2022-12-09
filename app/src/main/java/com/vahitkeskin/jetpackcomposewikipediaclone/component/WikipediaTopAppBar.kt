package com.vahitkeskin.jetpackcomposewikipediaclone.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.vahitkeskin.jetpackcomposewikipediaclone.R

@Composable
fun WikipediaTopAppBar() {
    Box {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.wikipedia_logo),
                contentDescription = "",
                modifier = Modifier.size(30.dp)
            )
            Column {
                Icon(
                    painter = painterResource(id = R.drawable.wikipedia_wordmark_tr),
                    contentDescription = "",
                    tint = Color.White
                )
                Icon(
                    painter = painterResource(id = R.drawable.wikipedia_tagline_tr),
                    contentDescription = "",
                    tint = Color.White
                )
            }
        }
        /*
            Icon(
                modifier = Modifier.clickable {
                    navHostController.popBackStack()
                },
                imageVector = Icons.Default.ArrowBack,
                contentDescription = null
            )
        */
    }
}