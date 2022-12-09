package com.vahitkeskin.jetpackcomposewikipediaclone.model.main_page

import com.google.gson.annotations.SerializedName

data class Iwlink(
    @SerializedName("*")
    val star: String,
    val prefix: String,
    val url: String
)