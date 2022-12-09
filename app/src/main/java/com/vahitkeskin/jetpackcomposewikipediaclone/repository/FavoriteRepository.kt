package com.vahitkeskin.jetpackcomposewikipediaclone.repository

import com.vahitkeskin.jetpackcomposewikipediaclone.dao.favorites.FavoriteRoom
import kotlinx.coroutines.flow.Flow

/**
 * @authot: Vahit Keskin
 * creared on 1.12.2022
 */
interface FavoriteRepository {
    fun addBookToRoom(lastSearchRoom: FavoriteRoom)
    fun getFavoriteFromRoom(): Flow<List<FavoriteRoom>>
    fun deleteFavoriteFromRoom(lastSearchRoom: FavoriteRoom)
    fun deleteAllFavoriteFromRoom()
}