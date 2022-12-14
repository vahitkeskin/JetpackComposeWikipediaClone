package com.vahitkeskin.jetpackcomposewikipediaclone.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
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
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
            name = this.javaClass.simpleName
        )
        val SEARCH_POPUP = intPreferencesKey("search_popup")
        val ANIMATION_TOOL_TIPS = intPreferencesKey("animation_tool_tips")
        val BOTTOM_BAR = booleanPreferencesKey("bottom_bar")
    }

    //-- Balloon --
    val getSearchPopup: Flow<Int?> = context.dataStore.data
        .map { preferences ->
            preferences[SEARCH_POPUP] ?: 1
        }

    suspend fun saveSearchPopup(name: Int) {
        context.dataStore.edit { preferences ->
            preferences[SEARCH_POPUP] = name
        }
    }

    //-- AnimationTooltips --
    val getAnimationTooltips: Flow<Int?> = context.dataStore.data
        .map { preferences ->
            preferences[ANIMATION_TOOL_TIPS] ?: 1
        }

    suspend fun saveAnimationTooltips(name: Int) {
        context.dataStore.edit { preferences ->
            preferences[ANIMATION_TOOL_TIPS] = name
        }
    }

    //-- AnimationTooltips --
    val getBottomBar: Flow<Boolean?> = context.dataStore.data
        .map { preferences ->
            preferences[BOTTOM_BAR] ?: true
        }

    suspend fun saveBottomBar(visible: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[BOTTOM_BAR] = visible
        }
    }

}