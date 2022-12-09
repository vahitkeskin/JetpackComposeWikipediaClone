package com.vahitkeskin.jetpackcomposewikipediaclone.dao.lastsearches

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.vahitkeskin.jetpackcomposewikipediaclone.utils.Constants

/**
 * @authot: Vahit Keskin
 * creared on 2.12.2022
 */
@Entity(
    tableName = Constants.LAST_SEARCH_TABLE_NAME,
    indices = [Index(value = ["title", "title"], unique = true)]
)
data class LastSearchRoom(
    @PrimaryKey(autoGenerate = true) var id: Int, var title: String
)
