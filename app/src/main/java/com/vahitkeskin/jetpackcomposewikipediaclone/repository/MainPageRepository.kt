package com.vahitkeskin.jetpackcomposewikipediaclone.repository

import com.vahitkeskin.jetpackcomposewikipediaclone.api.WikipediaAPI
import com.vahitkeskin.jetpackcomposewikipediaclone.model.main_page.MainPageModel
import javax.inject.Inject

/**
 * @authot: Vahit Keskin
 * creared on 3.12.2022
 */
class MainPageRepository @Inject constructor(
    private val retrofitApi: WikipediaAPI
) {
    suspend operator fun invoke(): MainPageModel {
        return retrofitApi.getMainPage()
    }
}