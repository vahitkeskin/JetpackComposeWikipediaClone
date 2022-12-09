package com.vahitkeskin.jetpackcomposewikipediaclone.dao.favorites

import androidx.room.*
import com.vahitkeskin.jetpackcomposewikipediaclone.utils.Constants.Companion.FAVORITE_SCREEN_TABLE_NAME
import kotlinx.coroutines.flow.Flow

/**
 * @authot: Vahit Keskin
 * creared on 1.12.2022
 */
@Dao
interface FavoriteDao {

    @Query("SELECT * FROM $FAVORITE_SCREEN_TABLE_NAME ORDER BY id DESC")
    fun getFavorite(): Flow<List<FavoriteRoom>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun setFavorite(favoriteRoom: FavoriteRoom)

    @Delete
    fun deleteFavorite(favoriteRoom: FavoriteRoom)

    @Query("DELETE FROM $FAVORITE_SCREEN_TABLE_NAME")
    fun deleteAllFavorite()

}