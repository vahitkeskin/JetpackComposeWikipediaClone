package com.vahitkeskin.jetpackcomposewikipediaclone.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * @authot: Vahit Keskin
 * creared on 10.12.2022
 */

class SharedDataStore(private val context: Context) {

    //Jetpack DataStore & Flow
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("searchPopup")
        val SEARCH_POPUP = intPreferencesKey("search_popup")
    }

    //get the saved search popup
    val getSearchPopup: Flow<Int?> = context.dataStore.data
        .map { preferences ->
            preferences[SEARCH_POPUP] ?: 1
        }

    //save search popup into datastore
    suspend fun saveSearchPopup(name: Int) {
        context.dataStore.edit { preferences ->
            preferences[SEARCH_POPUP] = name
        }
    }

}