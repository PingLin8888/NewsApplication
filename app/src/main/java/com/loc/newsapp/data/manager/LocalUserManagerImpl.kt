package com.loc.newsapp.data.manager

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.loc.newsapp.domain.manager.LocalUserManager
import com.loc.newsapp.util.Constants
import com.loc.newsapp.util.Constants.USER_SETTINGS
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocalUserManagerImpl(
    private val context: Context
) : LocalUserManager {
    override suspend fun saveAppEntry() {
        /*settings: Inside the lambda, settings represents the mutable preferences that can be modified.*/
        context.dataStore.edit { settings ->
            settings[PreferencesKeys.APP_ENTRY] = true
        }
    }

    /*This function returns a flow of boolean values representing the APP_ENTRY status.*/
    override fun readAppEntry(): Flow<Boolean> {
        return context.dataStore.data.map { preferences ->
            preferences[PreferencesKeys.APP_ENTRY] ?: false
        }
    }
}

/*This extension property defines a DataStore for Preferences associated with the Context, named USER_SETTINGS.*/
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = USER_SETTINGS)

/*Holds the keys for accessing preferences.*/
private object PreferencesKeys {
    /*Creates a key for a boolean preference named APP_ENTRY.*/
    val APP_ENTRY = booleanPreferencesKey(name = Constants.APP_ENTRY)
}