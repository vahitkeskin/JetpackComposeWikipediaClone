package com.vahitkeskin.jetpackcomposewikipediaclone.viewmodel

import androidx.lifecycle.ViewModel
import com.vahitkeskin.jetpackcomposewikipediaclone.repository.DetailResponse
import com.vahitkeskin.jetpackcomposewikipediaclone.utils.State
import com.vahitkeskin.jetpackcomposewikipediaclone.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * @authot: Vahit Keskin
 * creared on 1.12.2022
 */
@HiltViewModel
class DetailViewModel @Inject constructor(
    private val detailResponse: DetailResponse
) : ViewModel() {

    fun detailData(search: String?) = flow {
        emit(State.LoadingState)
        try {
            delay(1000)
            emit(State.DataState(detailResponse(search)))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Utils.resolveError(e))
        }
    }

}

