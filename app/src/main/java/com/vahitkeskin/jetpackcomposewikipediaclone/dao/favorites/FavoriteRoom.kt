package com.vahitkeskin.jetpackcomposewikipediaclone.dao.favorites

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.vahitkeskin.jetpackcomposewikipediaclone.utils.Constants.Companion.FAVORITE_SCREEN_TABLE_NAME

/**
 * @authot: Vahit Keskin
 * creared on 1.12.2022
 */
@Entity(tableName = FAVORITE_SCREEN_TABLE_NAME, indices = [Index(value = ["itemTitle", "itemTitle"], unique = true)])
data class FavoriteRoom(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var itemTitle: String,
    var itemDetail: String,
    var itemImage: String,
    var itemSaveTime: String,
)