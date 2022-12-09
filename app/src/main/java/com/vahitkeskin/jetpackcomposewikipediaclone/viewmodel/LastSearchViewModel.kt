package com.vahitkeskin.jetpackcomposewikipediaclone.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vahitkeskin.jetpackcomposewikipediaclone.dao.lastsearches.LastSearchRoom
import com.vahitkeskin.jetpackcomposewikipediaclone.repository.LastSearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @authot: Vahit Keskin
 * creared on 2.12.2022
 */

@HiltViewModel
class LastSearchViewModel @Inject constructor(
    private val repo: LastSearchRepository
) : ViewModel() {

    var lastSearch by mutableStateOf(emptyList<LastSearchRoom>())

    fun addLastSearch(lastSearchRoom: LastSearchRoom) = viewModelScope.launch(Dispatchers.IO) {
        repo.setLastSearchRoom(lastSearchRoom)
    }

    fun getLastSearch() = viewModelScope.launch {
        repo.getLastSearchFromRoom().collect { dbLastSearch ->
            lastSearch = dbLastSearch
        }
    }

    fun deleteLastSearch(lastSearchRoom: LastSearchRoom) = viewModelScope.launch(Dispatchers.IO) {
        repo.deleteLastSearchFromRoom(lastSearchRoom)
    }

    fun deleteAllLastSearch() = viewModelScope.launch(Dispatchers.IO) {
        repo.deleteAllLastSearchFromRoom()
    }

}