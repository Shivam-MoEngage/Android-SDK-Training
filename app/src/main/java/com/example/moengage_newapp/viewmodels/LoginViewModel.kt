package com.example.moengage_newapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moengage_newapp.util.PreferenceManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val preferenceManager: PreferenceManager
) : ViewModel() {

    private val userDataFlow = preferenceManager.userDataFlow
    val userData = MutableLiveData<String?>()

    init {
        shouldMoveToHomeScreen()
    }

    private fun shouldMoveToHomeScreen() {
        viewModelScope.launch {
            userDataFlow.collect {
                if (it != null && !it.equals("null")) {
                    userData.value = it
                }
            }
        }
    }

    fun moveSuccess() {
        userData.value = null
    }
}