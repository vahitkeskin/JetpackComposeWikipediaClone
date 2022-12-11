package com.vahitkeskin.jetpackcomposewikipediaclone.view.screens.search

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.vahitkeskin.jetpackcomposewikipediaclone.dao.lastsearches.LastSearchRoom
import com.vahitkeskin.jetpackcomposewikipediaclone.ui.theme.WikipediaSearchLastItemBG
import com.vahitkeskin.jetpackcomposewikipediaclone.ui.theme.WikipediaSearchLastItemTextBG
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * @authot: Vahit Keskin
 * creared on 8.12.2022
 */
@Composable
fun SearchLastItemLazyRow(
    lastText: LastSearchRoom,
    position: Int, viewModelItemClick: (LastSearchRoom,Boolean) -> Unit
) {
    val context = LocalContext.current
    var selectedItemPosition by remember { mutableStateOf(-1) }
    Row(
        modifier = Modifier
            .padding(start = 10.dp, top = 5.dp, end = 5.dp, bottom = 10.dp)
            .clip(RoundedCornerShape(5.dp))
            .background(WikipediaSearchLastItemBG)
            .clickable {
                viewModelItemClick.invoke(lastText,false)
                Toast
                    .makeText(context, "Select: ${lastText.title}", Toast.LENGTH_SHORT)
                    .show()
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier
                .size(20.dp)
                .padding(start = 5.dp)
                .clickable {
                    selectedItemPosition = position
                    GlobalScope.launch {
                        delay(1000)
                        viewModelItemClick.invoke(lastText,true)
                        selectedItemPosition = -1
                    }
                    Toast
                        .makeText(context, "Remove: $lastText", Toast.LENGTH_SHORT)
                        .show()
                },
            imageVector = Icons.Default.Close,
            contentDescription = null
        )
        Text(
            modifier = Modifier
                .padding(5.dp),
            text = lastText.title,
            color = WikipediaSearchLastItemTextBG,
            style = TextStyle(
                textDecoration = if (selectedItemPosition == position) TextDecoration.LineThrough else TextDecoration.None,
                color = Color.Red
            )
        )
    }
}