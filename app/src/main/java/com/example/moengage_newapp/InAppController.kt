package com.example.moengage_newapp

import android.app.Activity
import java.lang.ref.WeakReference

class InAppController {

    private var currentActivity: WeakReference<Activity>? = null

    companion object {
        @Volatile
        private var instance: InAppController? = null

        fun getInstance(): InAppController {
            synchronized(this) {
                var instance = InAppController.instance
                if (instance == null) {
                    instance = InAppController()
                    InAppController.instance = instance
                }
                return instance
            }
        }
    }

    fun registerActivity(activity: Activity?) {
        updateCurrentActivityContext(activity)
    }

    private fun updateCurrentActivityContext(activity: Activity?) {
        currentActivity = if (activity == null) null else WeakReference(activity)
    }

    fun unRegisterActivity(activity: Activity) {
        try {
            if (currentActivity != null && (currentActivity!!.javaClass.name == activity.javaClass.name)) {
                updateCurrentActivityContext(null)
            }
        } catch (e: Exception) {
        }
    }

    fun getCurrentActivity(): Activity? {
        return if (currentActivity == null) null else currentActivity!!.get()
    }

}