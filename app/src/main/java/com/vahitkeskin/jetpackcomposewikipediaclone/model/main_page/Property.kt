package com.vahitkeskin.jetpackcomposewikipediaclone.model.main_page

import com.google.gson.annotations.SerializedName

data class Property(
    @SerializedName("*")
    val star: String,
    val name: String
)