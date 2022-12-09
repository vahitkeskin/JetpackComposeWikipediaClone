package com.vahitkeskin.jetpackcomposewikipediaclone.repository

import com.vahitkeskin.jetpackcomposewikipediaclone.dao.lastsearches.LastSearchDao
import com.vahitkeskin.jetpackcomposewikipediaclone.dao.lastsearches.LastSearchRoom
import kotlinx.coroutines.flow.Flow

/**
 * @authot: Vahit Keskin
 * creared on 2.12.2022
 */

class LastSearchRepositoryImpl(
    private val lastSearchDao: LastSearchDao
) : LastSearchRepository{

    override fun setLastSearchRoom(lastSearchRoom: LastSearchRoom) = lastSearchDao.setLastSearch(lastSearchRoom)

    override fun getLastSearchFromRoom(): Flow<List<LastSearchRoom>> = lastSearchDao.getLastSearch()

    override fun deleteLastSearchFromRoom(lastSearchRoom: LastSearchRoom) = lastSearchDao.deleteLastSearch(lastSearchRoom)

    override fun deleteAllLastSearchFromRoom() = lastSearchDao.deleteAllLastSearch()
}