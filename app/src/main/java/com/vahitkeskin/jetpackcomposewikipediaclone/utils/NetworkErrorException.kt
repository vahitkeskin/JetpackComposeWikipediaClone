package com.vahitkeskin.jetpackcomposewikipediaclone.utils

import org.json.JSONObject
import retrofit2.HttpException

/**
 * @authot: Vahit Keskin
 * creared on 3.12.2022
 */

open class NetworkErrorException(
    val errorMessage: String
) : Exception() {
    override val message: String
        get() = localizedMessage

    override fun getLocalizedMessage(): String {
        return errorMessage
    }

    companion object {
        fun parseException(e: HttpException): NetworkErrorException {
            val errorBody = e.response()?.errorBody()?.string()

            return try {
                NetworkErrorException(JSONObject(errorBody!!).getString("message"))
            } catch (_: Exception) {
                NetworkErrorException("unexpected error!!ً")
            }
        }
    }
}

class AuthenticationException(authMessage: String) :
    NetworkErrorException(errorMessage = authMessage)