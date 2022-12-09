package com.vahitkeskin.jetpackcomposewikipediaclone.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vahitkeskin.jetpackcomposewikipediaclone.dao.favorites.FavoriteRoom
import com.vahitkeskin.jetpackcomposewikipediaclone.repository.FavoriteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @authot: Vahit Keskin
 * creared on 1.12.2022
 */

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val repo: FavoriteRepository
) : ViewModel() {

    var favorite by mutableStateOf(emptyList<FavoriteRoom>())

    fun addFavorite(favoriteRoom: FavoriteRoom) = viewModelScope.launch(Dispatchers.IO) {
        repo.addBookToRoom(favoriteRoom)
    }

    fun getFavorite() = viewModelScope.launch {
        repo.getFavoriteFromRoom().collect { dbFavorite ->
            favorite = dbFavorite
        }
    }

    fun deleteFavorite(lastSearchRoom: FavoriteRoom) = viewModelScope.launch(Dispatchers.IO) {
        repo.deleteFavoriteFromRoom(lastSearchRoom)
    }

    fun deleteAllFavorite() = viewModelScope.launch(Dispatchers.IO) {
        repo.deleteAllFavoriteFromRoom()
    }

}