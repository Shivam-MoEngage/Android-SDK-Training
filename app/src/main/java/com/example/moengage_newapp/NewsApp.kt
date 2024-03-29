package com.example.moengage_newapp

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.net.Uri
import android.os.Build
import android.util.Log
import com.google.firebase.messaging.RemoteMessage
import com.moe.pushlibrary.MoEHelper
import com.moengage.core.LogLevel
import com.moengage.core.MoEngage
import com.moengage.core.config.*
import com.moengage.core.model.AppStatus
import com.moengage.firebase.MoEFireBaseHelper
import com.moengage.firebase.listener.FirebaseEventListener
import com.moengage.geofence.MoEGeofenceHelper
import com.moengage.geofence.listener.OnGeofenceHitListener
import com.moengage.mi.MoEMiPushHelper
import com.xiaomi.mipush.sdk.MiPushClient
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
                    R.drawable.ic_career1,
                    R.drawable.flyo,
                    R.color.bookmarkColor,
                    tone = null,
                    isMultipleNotificationInDrawerEnabled = true,
                    isBuildingBackStackEnabled = true,
                    isLargeIconDisplayEnabled = true
                )
            )
            .configureFcm(
                FcmConfig(
                    true
                )
            ).configureInApps(
                InAppConfig(
                    false,
                    inAppSet
                )
            ).configureGeofence(
                GeofenceConfig(
                    true,
                    true
                )
            ).configureMiPush(
                MiPushConfig(
                    appId = "2882303761520017835",
                    appKey = "5242001758835",
                    isRegistrationEnabled = true
                )
            ).configurePushKit(PushKitConfig(true))
            .build()
        MoEngage.initialise(moEngage)
//        MoEPushHelper.getInstance().messageListener = CustomMessagePushListener()
//        MoEInAppHelper.getInstance().registerListener(InAppListener(this))

        MoEGeofenceHelper.getInstance().addListener(object : OnGeofenceHitListener {
            override fun geofenceHit(geoFenceHit: Intent): Boolean {
                Log.v("MoEngage_GEO", geoFenceHit.data.toString())
                return false
            }
        })

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
        createNotificationChannel()

    }

    private fun registerMoEMiToken() {
        if (MoEMiPushHelper.getInstance().hasMiUi()) {
            MiPushClient.registerPush(applicationContext, "2882303761520017835", "5242001758835")
            val xiaomiToken = MiPushClient.getRegId(applicationContext)
            if (xiaomiToken != null)
                MoEMiPushHelper.getInstance().passPushToken(applicationContext, xiaomiToken)
        }
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
                "my_channel_id",
                "my_channel_name",
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