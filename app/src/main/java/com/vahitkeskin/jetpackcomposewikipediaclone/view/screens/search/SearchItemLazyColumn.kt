package com.vahitkeskin.jetpackcomposewikipediaclone.view.screens.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.vahitkeskin.jetpackcomposewikipediaclone.R
import com.vahitkeskin.jetpackcomposewikipediaclone.model.searchitem.SearchItemDataClass

/**
 * @authot: Vahit Keskin
 * creared on 8.12.2022
 */
@Composable
fun SearchItemLazyColumn(
    searchItemDataClass: SearchItemDataClass,
    clickDetail: (String?) -> Unit
) {
    Row(
        modifier = Modifier.clickable {
            clickDetail.invoke(searchItemDataClass.key)
        },
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (searchItemDataClass.url.isNullOrEmpty()) {
            Image(
                modifier = Modifier
                    .size(80.dp)
                    .padding(10.dp),
                painter = painterResource(id = R.drawable.wikipedia_official_logo_icon),
                contentDescription = null
            )
        } else {
            Image(
                modifier = Modifier
                    .size(80.dp)
                    .padding(5.dp),
                painter = rememberAsyncImagePainter(model = "https:" + searchItemDataClass.url),
                contentDescription = null
            )
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            if (!searchItemDataClass.title.isNullOrEmpty()) {
                Text(
                    text = searchItemDataClass.title.orEmpty(),
                    fontWeight = FontWeight.Bold,
                    color = Color.LightGray
                )
            }
            if (!searchItemDataClass.description.isNullOrEmpty()) {
                Text(text = searchItemDataClass.description.orEmpty(), color = Color.Gray)
            }
        }
    }
}