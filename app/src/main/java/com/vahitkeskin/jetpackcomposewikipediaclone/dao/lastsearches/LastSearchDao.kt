package com.vahitkeskin.jetpackcomposewikipediaclone.dao.lastsearches

import androidx.room.*
import com.vahitkeskin.jetpackcomposewikipediaclone.utils.Constants.Companion.LAST_SEARCH_TABLE_NAME
import kotlinx.coroutines.flow.Flow

/**
 * @authot: Vahit Keskin
 * creared on 2.12.2022
 */
@Dao
interface LastSearchDao {

    @Query("SELECT * FROM $LAST_SEARCH_TABLE_NAME ORDER BY id DESC")
    fun getLastSearch(): Flow<List<LastSearchRoom>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun setLastSearch(lastSearchRoom: LastSearchRoom)

    @Delete
    fun deleteLastSearch(lastSearchRoom: LastSearchRoom)

    @Query("DELETE FROM $LAST_SEARCH_TABLE_NAME")
    fun deleteAllLastSearch()

}