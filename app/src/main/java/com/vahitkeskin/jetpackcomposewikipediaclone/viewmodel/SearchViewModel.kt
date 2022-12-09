package com.vahitkeskin.jetpackcomposewikipediaclone.viewmodel

import androidx.lifecycle.ViewModel
import com.vahitkeskin.jetpackcomposewikipediaclone.repository.SearchResponse
import com.vahitkeskin.jetpackcomposewikipediaclone.utils.State
import com.vahitkeskin.jetpackcomposewikipediaclone.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * @authot: Vahit Keskin
 * creared on 4.12.2022
 */
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchResponse: SearchResponse
) : ViewModel() {

    fun searchData(search: String, limit: String) = flow {
        emit(State.LoadingState)
        try {
            delay(1000)
            emit(State.DataState(searchResponse(search,limit)))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Utils.resolveError(e))
        }
    }
}