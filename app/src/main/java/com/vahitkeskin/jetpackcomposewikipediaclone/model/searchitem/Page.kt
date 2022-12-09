package com.vahitkeskin.jetpackcomposewikipediaclone.model.searchitem

data class Page(
    val description: String,
    val excerpt: String,
    val id: Int,
    val key: String,
    val matched_title: Any,
    val thumbnail: Thumbnail,
    val title: String
)