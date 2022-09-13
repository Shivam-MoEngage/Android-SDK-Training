package com.example.moengage_newapp

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.media.AudioAttributes
import android.net.Uri
import android.os.Build
import android.util.Log
import com.moe.pushlibrary.MoEHelper
import com.moengage.core.LogLevel
import com.moengage.core.MoEngage
import com.moengage.core.config.FcmConfig
import com.moengage.core.config.LogConfig
import com.moengage.core.config.MiPushConfig
import com.moengage.core.config.NotificationConfig
import com.moengage.core.model.AppStatus
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class NewsApp : Application(){

    override fun onCreate() {
        super.onCreate()

        val inAppSet = mutableSetOf<Class<*>>()
        inAppSet.add(OnNotificationClickActivity::class.java)

        val moEngage = MoEngage.Builder(this, "PEEGJ5X088DY40EJMYG67RVX")//enter your own app id
            .configureLogs(LogConfig(LogLevel.VERBOSE, true))
            .configureNotificationMetaData(
                NotificationConfig(
                    R.drawable.ic_baseline_bookmark_24,
                    R.drawable.ic_launcher_background,
                )
            ).configureFcm(
                FcmConfig(
                    true
                )
            ).configureMiPush(
                MiPushConfig(
                    appId = "2882303761520017835",
                    appKey = "5242001758835",
                    isRegistrationEnabled = true
                )
            )
            .build()
        MoEngage.initialiseDefaultInstance(moEngage)
//        MoEPushHelper.getInstance().messageListener = CustomMessagePushListener()


        trackInstallOrUpdate()

        createNotificationChannel()

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
        val currentVersion = preferences.getInt(versionCode, -1)

        if (currentVersion < BuildConfig.VERSION_CODE) {
            preferences.edit().putInt(versionCode, BuildConfig.VERSION_CODE).apply()
            MoEHelper.getInstance(this).setAppStatus(AppStatus.UPDATE)
            Log.v("MoEngage", "App Updated")
        }
    }

    private fun createNotificationChannel() {
        val nm =
            applicationContext.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "hello_world",
                "my_channel",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Created by Vipin"
                enableVibration(true)
            }
            val attributes = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                .build()
            channel.setSound(
                Uri.parse("android.resource://${packageName}/" + R.raw.tone_new),
                attributes
            )
            nm.createNotificationChannel(channel)
        }
    }

    private fun checkLoginStatus(): Boolean {

        val isUserLoggined = "isUserLoggined"

        //keys are just sample keys, use suitable keys for the apps
        val preferences =
            getSharedPreferences("user_preferences", Context.MODE_PRIVATE) ?: return false

        return preferences.getBoolean(isUserLoggined, false)

    }
}