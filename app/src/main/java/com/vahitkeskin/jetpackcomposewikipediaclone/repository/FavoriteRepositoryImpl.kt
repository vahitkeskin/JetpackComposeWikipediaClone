package com.vahitkeskin.jetpackcomposewikipediaclone.repository

import com.vahitkeskin.jetpackcomposewikipediaclone.dao.favorites.FavoriteDao
import com.vahitkeskin.jetpackcomposewikipediaclone.dao.favorites.FavoriteRoom
import kotlinx.coroutines.flow.Flow

/**
 * @authot: Vahit Keskin
 * creared on 1.12.2022
 */

class FavoriteRepositoryImpl(
    private val favoriteDao: FavoriteDao
) : FavoriteRepository{

    override fun addBookToRoom(lastSearchRoom: FavoriteRoom) = favoriteDao.setFavorite(lastSearchRoom)

    override fun getFavoriteFromRoom(): Flow<List<FavoriteRoom>> = favoriteDao.getFavorite()

    override fun deleteFavoriteFromRoom(lastSearchRoom: FavoriteRoom) = favoriteDao.deleteFavorite(lastSearchRoom)

    override fun deleteAllFavoriteFromRoom() = favoriteDao.deleteAllFavorite()

}