package com.vahitkeskin.jetpackcomposewikipediaclone.api

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vahitkeskin.jetpackcomposewikipediaclone.dao.lastsearches.LastSearchDao
import com.vahitkeskin.jetpackcomposewikipediaclone.dao.lastsearches.LastSearchRoom

/**
 * @authot: Vahit Keskin
 * creared on 2.12.2022
 */

@Database(entities = [LastSearchRoom::class], version = 1, exportSchema = false)
abstract class LastSearchDatabase : RoomDatabase() {
    abstract fun lastSearchDao(): LastSearchDao
}