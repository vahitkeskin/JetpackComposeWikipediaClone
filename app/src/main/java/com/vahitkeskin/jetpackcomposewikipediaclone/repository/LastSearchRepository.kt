package com.vahitkeskin.jetpackcomposewikipediaclone.repository

import com.vahitkeskin.jetpackcomposewikipediaclone.dao.lastsearches.LastSearchRoom
import kotlinx.coroutines.flow.Flow

/**
 * @authot: Vahit Keskin
 * creared on 2.12.2022
 */

interface LastSearchRepository {
    fun setLastSearchRoom(lastSearchRoom: LastSearchRoom)
    fun getLastSearchFromRoom(): Flow<List<LastSearchRoom>>
    fun deleteLastSearchFromRoom(lastSearchRoom: LastSearchRoom)
    fun deleteAllLastSearchFromRoom()
}