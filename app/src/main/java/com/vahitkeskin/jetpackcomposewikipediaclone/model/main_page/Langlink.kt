package com.vahitkeskin.jetpackcomposewikipediaclone.model.main_page

import com.google.gson.annotations.SerializedName

data class Langlink(
    @SerializedName("*")
    val star: String,
    val autonym: String,
    val lang: String,
    val langname: String,
    val url: String
)