package com.vahitkeskin.jetpackcomposewikipediaclone.view.screens.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vahitkeskin.jetpackcomposewikipediaclone.R
import com.vahitkeskin.jetpackcomposewikipediaclone.ui.theme.WikipediaBg
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * @authot: Vahit Keskin
 * creared on 8.12.2022
 */

@Composable
fun WikipediaSearchBar(
    modifier: Modifier,
    newSearch: (String, String, Boolean) -> Unit
) {
    var search by remember { mutableStateOf("Mona Lisa") }
    val keyboard = LocalFocusManager.current
    var stateNumberPicker by remember { mutableStateOf(10) }
    var job: Job? = null
    val coroutineScope = rememberCoroutineScope()

    Box {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .weight(1f)
                    .padding(5.dp),
                value = search,
                onValueChange = { newText ->
                    search = newText
                    job?.cancel()
                    job = coroutineScope.launch {
                        delay(1000)
                        if (search.isNotEmpty()) {
                            newSearch.invoke(search, stateNumberPicker.toString(), false)
                        }
                    }
                },
                label = {
                    Text(
                        text = "Vikipedi üzerinde ara",
                        color = Color.LightGray,
                        fontSize = 14.sp
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    capitalization = KeyboardCapitalization.Sentences //Keyboard first letter uppercase
                ),
                singleLine = true,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    textColor = Color.White,
                    focusedBorderColor = Color.Gray,
                    unfocusedBorderColor = Color.LightGray
                ),
                leadingIcon = {
                    Icon(
                        Icons.Default.Search,
                        contentDescription = null,
                        tint = Color.LightGray
                    )
                },
                trailingIcon = {
                    Icon(
                        modifier = Modifier.clickable {
                            search = ""
                        },
                        painter = painterResource(id = R.drawable.ic_baseline_cancel_24),
                        contentDescription = null,
                        tint = Color.LightGray,
                    )
                }
            )

            SearchLimitNumberPicker(
                modifier = modifier
            ) { mStateNumberPicker ->
                stateNumberPicker = mStateNumberPicker
            }

            FloatingActionButton(
                backgroundColor = WikipediaBg,
                modifier = Modifier
                    .padding(end = 10.dp)
                    .size(50.dp)
                    .border(0.5.dp, Color.LightGray, CircleShape),
                onClick = {
                    keyboard.clearFocus()
                    newSearch.invoke(search, stateNumberPicker.toString(), true)
                }
            ) {
                Image(
                    modifier = Modifier.padding(10.dp),
                    painter = painterResource(id = R.drawable.wikipedia_official_logo_icon),
                    contentDescription = null
                )
            }
        }
    }
}