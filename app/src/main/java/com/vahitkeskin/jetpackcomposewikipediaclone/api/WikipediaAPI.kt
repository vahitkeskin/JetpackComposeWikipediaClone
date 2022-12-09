package com.vahitkeskin.jetpackcomposewikipediaclone.api

import com.vahitkeskin.jetpackcomposewikipediaclone.model.main_page.MainPageModel
import com.vahitkeskin.jetpackcomposewikipediaclone.model.search.SearchModel
import com.vahitkeskin.jetpackcomposewikipediaclone.model.searchitem.SearchItemModel
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface WikipediaAPI {

    //Main Page
    @GET("/w/api.php?action=parse&page=Anasayfa&format=json")
    suspend fun getMainPage(): MainPageModel

    //Search List
    @GET("/w/rest.php/v1/search/title")
    suspend fun getSearchItemModel(
        @Query("q") search: String,
        @Query("limit") limit: String? = "10",
    ): Response<SearchItemModel>

    //Search Detail
    @GET("/api/rest_v1/page/summary/{search}")
    suspend fun getDetailItemModel(
        @Path("search") search: String? = "Vikipedi"
    ): Response<SearchModel>
}