package com.vahitkeskin.jetpackcomposewikipediaclone.view.screens.search

import android.widget.NumberPicker
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.vahitkeskin.jetpackcomposewikipediaclone.R
import com.vahitkeskin.jetpackcomposewikipediaclone.ui.theme.WikipediaBg
import com.vahitkeskin.jetpackcomposewikipediaclone.ui.theme.WikipediaSearchLastItemBG
import com.vahitkeskin.jetpackcomposewikipediaclone.utils.AnimationObject
import com.vahitkeskin.jetpackcomposewikipediaclone.utils.AnimationState
import com.vahitkeskin.jetpackcomposewikipediaclone.utils.AnimationTooltips
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * @authot: Vahit Keskin
 * creared on 8.12.2022
 */

@Composable
fun WikipediaSearchBar(
    newSearch: (String, String, Boolean) -> Unit
) {
    var search by remember { mutableStateOf("Mona Lisa") }
    val keyboard = LocalFocusManager.current
    var stateNumberPicker by remember { mutableStateOf(10) }
    var job: Job? = null
    val coroutineScope = rememberCoroutineScope()

    var personIconPosition by remember { mutableStateOf(Offset.Zero) }
    var personIconIntSize by remember { mutableStateOf(IntSize.Zero) }
    var searchIconPosition by remember { mutableStateOf(Offset.Zero) }
    var searchIconSize by remember { mutableStateOf(IntSize.Zero) }
    val color1 = Color(0xff15a8a6)
    val color4 = Color(0xffe4335d)
    val iconAnimationColor = Color(0xff2c3a8c)

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

            AndroidView(
                modifier = Modifier
                    .onSizeChanged {
                        searchIconSize = it
                    }
                    .onGloballyPositioned {
                        searchIconPosition = it.positionInRoot()
                    }
                    .width(50.dp)
                    .height(80.dp)
                    .padding(5.dp),
                factory = { context ->
                    NumberPicker(context).apply { //For scroll request limit value, default 10
                        textColor = ContextCompat.getColor(context, android.R.color.white)
                        setOnValueChangedListener { numberPicker, _, _ ->
                            stateNumberPicker =
                                if (numberPicker.value > 0) numberPicker.value else 1
                        }
                        minValue = 0
                        maxValue = 20
                        value = stateNumberPicker
                    }
                }
            )

            FloatingActionButton(
                backgroundColor = WikipediaBg,
                modifier = Modifier
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
        println("searchIconPosition and searchIconSize size: $searchIconPosition AND $searchIconSize")
        //ZOOM INFO
        var animationTooltips: List<AnimationObject> =
            listOf(
                //SearchingIcon
                AnimationObject(
                    bigCircleRadius = 400.dp,
                    bigCircleColor = color4,
                    smallCircleRadius = 50.dp,
                    smallCircleColor = WikipediaSearchLastItemBG,
                    objectToShow = {
                        SearchIcon()
                    },
                    objectOffset = searchIconPosition,
                    objectSize = searchIconSize,
                    composeDescription = {
                        Column(
                            modifier = Modifier.background(Color.Red)
                        ) {
                            Text(
                                text = "Use NumberPicker",
                                style = MaterialTheme.typography.h6,
                                color = Color.White,
                                fontWeight = FontWeight.W500
                            )
                            Text(
                                text = "You can set the request limit,\nso you can prevent\nunnecessary data from coming.\n(Default limit 10)",
                                style = MaterialTheme.typography.subtitle1,
                                color = Color.White
                            )
                        }
                    },
                    composeDescriptionOffset = Offset(
                        personIconPosition.x.plus(personIconIntSize.width.times(2)),
                        personIconPosition.y.plus(personIconIntSize.height.times(2)),
                    )
                )
            )

        var state by remember { mutableStateOf(0) }
        if (state > AnimationState.FINISHED.value) {
            AnimationTooltips(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(0.3f)),
                tooltipsList = animationTooltips,
                state = { state = it },
                bigCircleColorBeforeDisappearing = color1
            )
        }
    }
}

@Composable
fun SearchIcon() {
    AndroidView(
        modifier = Modifier
            .width(50.dp)
            .height(80.dp)
            .padding(5.dp),
        factory = { context ->
            NumberPicker(context).apply { //For scroll request limit value, default 10
                textColor = ContextCompat.getColor(context, android.R.color.white)
                setOnValueChangedListener { numberPicker, _, _ ->
                }
                minValue = 0
                maxValue = 20
                value = 10
            }
        }
    )
}