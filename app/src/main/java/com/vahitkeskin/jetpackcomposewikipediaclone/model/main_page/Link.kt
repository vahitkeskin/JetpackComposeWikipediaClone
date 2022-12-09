package com.vahitkeskin.jetpackcomposewikipediaclone.model.main_page

import com.google.gson.annotations.SerializedName

data class Link(
    @SerializedName("*")
    val star: String,
    val exists: String,
    val ns: Int
)