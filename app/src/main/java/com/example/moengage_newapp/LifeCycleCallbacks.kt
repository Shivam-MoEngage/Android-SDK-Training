package com.example.moengage_newapp

import android.content.Context
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

class LifeCycleCallbacks(private val context: Context) : LifecycleObserver {

    private val TAG = "MainActivity LifeCycle"

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {
        Log.v(TAG, "OnStart()")
    }

}