package com.vahitkeskin.jetpackcomposewikipediaclone.dao.favorites

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * @authot: Vahit Keskin
 * creared on 1.12.2022
 */

@Database(entities = [FavoriteRoom::class], version = 1, exportSchema = false)
abstract class FavoriteDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
}