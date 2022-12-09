package com.vahitkeskin.jetpackcomposewikipediaclone.utils

/**
 * @authot: Vahit Keskin
 * creared on 3.12.2022
 */

sealed class State<out T> {
    object LoadingState : State<Nothing>()
    data class ErrorState(var exception: Throwable) : State<Nothing>()
    data class DataState<T>(var data: T) : State<T>()
}