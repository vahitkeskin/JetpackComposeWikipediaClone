package com.vahitkeskin.jetpackcomposewikipediaclone.view.screens.search

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavHostController
import com.vahitkeskin.jetpackcomposewikipediaclone.component.WikipediaOnLifecycleEvent
import com.vahitkeskin.jetpackcomposewikipediaclone.dao.lastsearches.LastSearchRoom
import com.vahitkeskin.jetpackcomposewikipediaclone.model.searchitem.SearchItemDataClass
import com.vahitkeskin.jetpackcomposewikipediaclone.utils.PagePreview
import com.vahitkeskin.jetpackcomposewikipediaclone.utils.Screen
import com.vahitkeskin.jetpackcomposewikipediaclone.utils.ShimmerAnimation
import com.vahitkeskin.jetpackcomposewikipediaclone.utils.State
import com.vahitkeskin.jetpackcomposewikipediaclone.viewmodel.LastSearchViewModel
import com.vahitkeskin.jetpackcomposewikipediaclone.viewmodel.SearchViewModel
import kotlinx.coroutines.launch
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
    val coroutineScope = rememberCoroutineScope()

    val searchItemDataClass = remember { mutableStateListOf<SearchItemDataClass>() }
    val searchLastItem = remember { mutableStateListOf<LastSearchRoom>() }
    var stateListSize by remember { mutableStateOf(true) }
    val context = LocalContext.current
    var search by remember { mutableStateOf("Android") }
    var stateNumberPicker by remember { mutableStateOf("") }

    lastSearchViewModel.getLastSearch()
    if (lastSearchViewModel.lastSearch.isNotEmpty() && stateListSize) {
        searchLastItem.clear()
        lastSearchViewModel.lastSearch.forEach {
            searchLastItem.add(it)
            println("1. viewModel lastSearch forEach: ${it.title}")
        }
    }

    WikipediaOnLifecycleEvent { owner, event ->
        when (event) {
            Lifecycle.Event.ON_RESUME -> {
                navigateToDetail.invoke(true, "")
            }
            Lifecycle.Event.ON_PAUSE -> {
                navigateToDetail.invoke(false, "")
            }
            else -> {}
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        WikipediaSearchBar { mSearch, mStateNumberPicker, fabClick ->
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
                    modifier = Modifier.padding(start = 10.dp, top = 5.dp, bottom = 5.dp, end = 10.dp)
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
                            items = searchLastItem.stream().distinct().collect(Collectors.toList())
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
                            navHostController.navigate(Screen.CharacterDetail.route + "/$counter")
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
}