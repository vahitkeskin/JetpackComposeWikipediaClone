package com.vahitkeskin.jetpackcomposewikipediaclone.dao.lastsearches

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * @authot: Vahit Keskin
 * creared on 2.12.2022
 */

@Database(entities = [LastSearchRoom::class], version = 1, exportSchema = false)
abstract class LastSearchDatabase : RoomDatabase() {
    abstract fun lastSearchDao(): LastSearchDao
}