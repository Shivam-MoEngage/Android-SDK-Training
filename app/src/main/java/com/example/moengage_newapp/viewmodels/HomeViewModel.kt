package com.example.moengage_newapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moengage_newapp.data.User
import com.example.moengage_newapp.util.PreferenceManager
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val preferenceManager: PreferenceManager
) : ViewModel() {

    fun loginUser(user: String) {

        if (user.isEmpty())
            return

        viewModelScope.launch {
            preferenceManager.setUserData(Gson().fromJson(user, User::class.java))
        }
    }

    fun logOut() {
        viewModelScope.launch {
            preferenceManager.setUserData(null)
        }
    }
}