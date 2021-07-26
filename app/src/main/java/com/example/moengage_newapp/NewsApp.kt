package com.example.moengage_newapp

import android.app.Application
import android.content.Context
import android.util.Log
import com.google.firebase.messaging.RemoteMessage
import com.moe.pushlibrary.MoEHelper
import com.moengage.core.LogLevel
import com.moengage.core.MoEngage
import com.moengage.core.config.FcmConfig
import com.moengage.core.config.LogConfig
import com.moengage.core.config.NotificationConfig
import com.moengage.core.model.AppStatus
import com.moengage.firebase.MoEFireBaseHelper
import com.moengage.firebase.listener.FirebaseEventListener
import com.moengage.pushbase.MoEPushHelper
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class NewsApp : Application(){

    override fun onCreate() {
        super.onCreate()

        val moEngage = MoEngage.Builder(this, "PEEGJ5X088DY40EJMYG67RVX")//enter your own app id
            .configureLogs(LogConfig(LogLevel.VERBOSE, true))
            .configureNotificationMetaData(
                NotificationConfig(
                    R.drawable.ic_baseline_bookmark_24,
                    R.drawable.ic_launcher_background,
                    R.color.design_default_color_error,
                    tone = null,
                    isMultipleNotificationInDrawerEnabled = true,
                    isBuildingBackStackEnabled = true,
                    isLargeIconDisplayEnabled = true
                )
            ).configureFcm(
                FcmConfig(
                    false
                )
            ).build()
        MoEngage.initialise(moEngage)
        MoEPushHelper.getInstance().messageListener = CustomMessagePushListener()
        trackInstallOrUpdate()

        MoEFireBaseHelper.getInstance().addEventListener(object : FirebaseEventListener() {
            override fun onNonMoEngageMessageReceived(remoteMessage: RemoteMessage) {
                super.onNonMoEngageMessageReceived(remoteMessage)
            }

            override fun onTokenAvailable(token: String) {
                super.onTokenAvailable(token)
                Log.v("MoEngage", "OnTokenAvailable: $token")
            }
        })
    }

    private fun trackInstallOrUpdate() {

        val versionCode = "version_code"

        //keys are just sample keys, use suitable keys for the apps
        val preferences = getSharedPreferences("user_preferences", Context.MODE_PRIVATE) ?: return

        //Fresh Install
        if (!preferences.contains(versionCode)) {
            preferences.edit().putInt(versionCode, BuildConfig.VERSION_CODE).apply()
            MoEHelper.getInstance(this).setAppStatus(AppStatus.INSTALL)
            Log.v("MoEngage", "Fresh Install")
            return
        }

        //Update
        val currentVersion = preferences.getInt(versionCode, 1)
        if (currentVersion < BuildConfig.VERSION_CODE) {
            preferences.edit().putInt(versionCode, BuildConfig.VERSION_CODE).apply()
            MoEHelper.getInstance(this).setAppStatus(AppStatus.UPDATE)
            Log.v("MoEngage", "App Updated")
        }
    }
}