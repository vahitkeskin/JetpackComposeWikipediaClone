package com.vahitkeskin.jetpackcomposewikipediaclone.view.screens.search

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavHostController
import com.vahitkeskin.jetpackcomposewikipediaclone.component.WikipediaOnLifecycleEvent
import com.vahitkeskin.jetpackcomposewikipediaclone.dao.lastsearches.LastSearchRoom
import com.vahitkeskin.jetpackcomposewikipediaclone.datastore.SharedDataStore
import com.vahitkeskin.jetpackcomposewikipediaclone.model.searchitem.SearchItemDataClass
import com.vahitkeskin.jetpackcomposewikipediaclone.ui.theme.BigCircleColor
import com.vahitkeskin.jetpackcomposewikipediaclone.ui.theme.WikipediaSearchLastItemBG
import com.vahitkeskin.jetpackcomposewikipediaclone.utils.*
import com.vahitkeskin.jetpackcomposewikipediaclone.utils.State
import com.vahitkeskin.jetpackcomposewikipediaclone.viewmodel.LastSearchViewModel
import com.vahitkeskin.jetpackcomposewikipediaclone.viewmodel.SearchViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*
import java.util.stream.Collectors

@SuppressLint("UnrememberedMutableState")
@Composable
fun SearchScreen(
    searchViewModel: SearchViewModel = hiltViewModel(),
    lastSearchViewModel: LastSearchViewModel = hiltViewModel(),
    navHostController: NavHostController,
    navigateToDetail: (Boolean, String?) -> Unit
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val searchItemDataClass = remember { mutableStateListOf<SearchItemDataClass>() }
    val searchLastItem = remember { mutableStateListOf<LastSearchRoom>() }
    var stateListSize by remember { mutableStateOf(true) }
    var search by remember { mutableStateOf("Android") }
    var stateNumberPicker by remember { mutableStateOf("") }
    val dataStore = SharedDataStore(context)

    //For NumberPicker OffSett values
    var searchIconPosition by remember { mutableStateOf(Offset.Zero) }
    var searchIconSize by remember { mutableStateOf(IntSize.Zero) }

    lastSearchViewModel.getLastSearch()
    if (lastSearchViewModel.lastSearch.isNotEmpty() && stateListSize) {
        searchLastItem.clear()
        lastSearchViewModel.lastSearch.forEach {
            searchLastItem.add(it)
        }
    }

    //Jetpack Compose LifeCycle
    WikipediaOnLifecycleEvent { _, event ->
        when (event) {
            Lifecycle.Event.ON_RESUME -> {
                navigateToDetail.invoke(true, "")
            }
            Lifecycle.Event.ON_PAUSE -> {
                coroutineScope.launch {
                    //Don't show AnimationTooltips again
                    dataStore.saveAnimationTooltips(0)
                }
                navigateToDetail.invoke(false, "")
            }
            else -> {}
        }
    }

    //While values are visible in the background with Box,
    //the AnimationTooltips value is running in the foreground.
    Box {
        //-- Background --
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            WikipediaSearchBar(
                modifier = Modifier
                    .onSizeChanged {
                        searchIconSize = it
                    }
                    .onGloballyPositioned {
                        searchIconPosition = it.positionInRoot()
                    }
            ) { mSearch, mStateNumberPicker, fabClick ->
                search = mSearch
                stateNumberPicker = mStateNumberPicker
                stateListSize = true
                searchItemDataClass.clear()
                if (fabClick) {
                    val lastSearchRoom = LastSearchRoom(0, mSearch)
                    lastSearchViewModel.addLastSearch(lastSearchRoom)
                }
            }

            if (stateListSize) {
                searchItemDataClass.clear()
                coroutineScope.launch {
                    searchViewModel.searchData(
                        search = search,
                        limit = if (stateNumberPicker.isNullOrEmpty() || stateNumberPicker.isNullOrBlank()) "10" else stateNumberPicker
                    ).collect {
                        when (it) {
                            is State.DataState -> {
                                it.data.body()?.pages?.forEach { mPage ->
                                    val model = SearchItemDataClass(
                                        title = mPage.title,
                                        description = mPage.description,
                                        url = if (mPage.thumbnail != null) mPage.thumbnail.url else "",
                                        key = mPage.key
                                    )
                                    searchItemDataClass.add(model)
                                }
                            }
                            is State.ErrorState -> {

                            }
                            is State.LoadingState -> {

                            }
                        }
                    }
                }
                stateListSize = false
            }

            if (searchLastItem.size != 0) {
                if (searchLastItem.size > 1) {
                    Row(
                        modifier = Modifier.padding(
                            start = 10.dp,
                            top = 5.dp,
                            bottom = 5.dp,
                            end = 10.dp
                        )
                    ) {
                        Text(
                            modifier = Modifier.weight(1f),
                            text = "My Past Searches",
                            textAlign = TextAlign.Start,
                            fontSize = 14.sp
                        )
                        Row(
                            modifier = Modifier
                                .clickable(
                                    indication = null,
                                    interactionSource = remember { MutableInteractionSource() } // This is mandatory
                                ) {
                                    lastSearchViewModel.deleteAllLastSearch()
                                    searchLastItem.clear()
                                    Toast.makeText(context, "Clear All", Toast.LENGTH_SHORT).show()
                                },
                        ) {
                            Text(
                                text = "Clear All",
                                textAlign = TextAlign.End,
                                fontSize = 14.sp
                            )
                            Icon(
                                modifier = Modifier
                                    .padding(start = 5.dp)
                                    .size(20.dp),
                                imageVector = Icons.Default.Delete,
                                contentDescription = null
                            )
                        }
                    }
                }
                Box {
                    if (!(searchItemDataClass.size > 0)) {
                        LazyRow { //Last Search Item Shimmer
                            repeat(10) {
                                item {
                                    ShimmerAnimation(PagePreview.SEARCH_LAST_ITEM)
                                }
                            }
                        }
                    } else {
                        LazyRow {
                            itemsIndexed(
                                items = searchLastItem.stream().distinct()
                                    .collect(Collectors.toList())
                            ) { position, mSearchLastItem ->
                                if (!mSearchLastItem.title.isNullOrEmpty()) {
                                    SearchLastItemLazyRow(
                                        lastText = mSearchLastItem,
                                        position = position
                                    ) {
                                        search = it.title
                                        lastSearchViewModel.deleteLastSearch(it)
                                    }
                                }
                            }
                        }
                    }
                }
            }
            Text(
                modifier = Modifier.padding(start = 10.dp),
                text = "\"${searchItemDataClass.size}\" Result found for $search",
                textAlign = TextAlign.Start,
                fontSize = 12.sp
            )

            if (searchItemDataClass.size > 0) {
                LazyColumn {
                    items(searchItemDataClass) {
                        SearchItemLazyColumn(
                            searchItemDataClass = it,
                            clickDetail = { counter ->
                                navHostController.navigate(Screen.Detail.route + "/$counter")
                            }
                        )
                    }
                }
            } else {
                LazyColumn { //Search Item Shimmer
                    repeat(10) {
                        item {
                            ShimmerAnimation(PagePreview.SEARCH_ITEM)
                        }
                    }
                }
            }
        }

        //-- Foreground --
        //ZOOM INFO
        val animationTooltips: List<AnimationObject> =
            listOf(
                //SearchingIcon
                AnimationObject(
                    bigCircleRadius = 400.dp,
                    bigCircleColor = BigCircleColor,
                    smallCircleRadius = 50.dp,
                    smallCircleColor = WikipediaSearchLastItemBG,
                    objectToShow = {
                        SearchLimitNumberPicker { }
                    },
                    objectOffset = searchIconPosition + Offset(10.0f, -150.0f),
                    objectSize = searchIconSize,
                    composeDescription = {
                        Column {
                            Text(
                                text = "Use NumberPicker",
                                style = MaterialTheme.typography.h6,
                                color = Color.White,
                                fontWeight = FontWeight.W500
                            )
                            Spacer(modifier = Modifier.padding(5.dp))
                            Text(
                                text = "You can set the request limit,\nso you can prevent unnecessary\ndata from coming.\uD83D\uDE09\n(Default limit 10)",
                                style = MaterialTheme.typography.subtitle1,
                                color = Color.White
                            )
                        }
                    },
                    composeDescriptionOffset = Offset(200.0f, 300.0f)
                )
            )


        var state by remember { mutableStateOf(0) }
        if (state > AnimationState.FINISHED.value &&
            dataStore.getAnimationTooltips.collectAsState(initial = "").value != 0
        ) {
            AnimationTooltips(
                modifier = Modifier
                    .background(Color.Black.copy(0.3f)),
                tooltipsList = animationTooltips,
                state = { state = it },
                bigCircleColorBeforeDisappearing = BigCircleColor
            )
        }
    }
}