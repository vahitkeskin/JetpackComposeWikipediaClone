package com.vahitkeskin.jetpackcomposewikipediaclone.repository

import com.vahitkeskin.jetpackcomposewikipediaclone.api.WikipediaAPI
import com.vahitkeskin.jetpackcomposewikipediaclone.model.searchitem.SearchItemModel
import retrofit2.Response
import javax.inject.Inject

/**
 * @authot: Vahit Keskin
 * creared on 4.12.2022
 */
class SearchResponse @Inject constructor(
    private val wikipediaAPI: WikipediaAPI
) {
    suspend operator fun invoke(search: String, limit: String): Response<SearchItemModel> {
        return wikipediaAPI.getSearchItemModel(search,limit)
    }
}