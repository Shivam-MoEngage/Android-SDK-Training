package com.example.moengage_newapp.util

import android.content.Context
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.emptyPreferences
import androidx.datastore.preferences.preferencesKey
import com.example.moengage_newapp.data.User
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class PreferenceManager @Inject constructor(@ApplicationContext context: Context) {
    private val dataStore = context.createDataStore("user_preferences");

    val preferenceFlow = dataStore.data
        .catch {
            emit(emptyPreferences())
        }
        .map { preference ->
            //default to false
            preference[sort_order] ?: false
        }

    val userDataFlow: Flow<String> = dataStore.data
        .catch {
            emit(emptyPreferences())
        }
        .map { preference ->
            val userData = preference[userData]
            if (userData.isNullOrEmpty())
                ""
            else
                userData
        }


    suspend fun updateSortOrder(sortOrder: Boolean) {
        dataStore.edit {
            it[sort_order] = sortOrder
        }
    }

    suspend fun setUserData(user: User?) {
        dataStore.edit {
            it[userData] = Gson().toJson(user, User::class.java)
        }
    }

    //Default to false - which means show result in descending order  - latest first
    val sort_order = preferencesKey<Boolean>("sort_order")
    val userData = preferencesKey<String>("userData")

}