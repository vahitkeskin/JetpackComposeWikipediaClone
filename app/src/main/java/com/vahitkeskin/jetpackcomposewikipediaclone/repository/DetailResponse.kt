package com.vahitkeskin.jetpackcomposewikipediaclone.repository

import com.vahitkeskin.jetpackcomposewikipediaclone.api.WikipediaAPI
import com.vahitkeskin.jetpackcomposewikipediaclone.model.search.SearchModel
import retrofit2.Response
import javax.inject.Inject

/**
 * @authot: Vahit Keskin
 * creared on 5.12.2022
 */
class DetailResponse @Inject constructor(
    private var wikipediaAPI: WikipediaAPI
) {
    suspend operator fun invoke(search: String?): Response<SearchModel> {
        return wikipediaAPI.getDetailItemModel(search)
    }
}