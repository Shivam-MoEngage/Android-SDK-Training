package com.example.moengage_newapp

import android.app.Activity
import android.app.Notification
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.app.NotificationCompat
import com.example.moengage_newapp.ui.MainActivity
import com.moengage.pushbase.model.NotificationPayload
import com.moengage.pushbase.push.PushMessageListener

class CustomMessagePushListener(private val context: Context) : PushMessageListener() {


    override fun customizeNotification(
        notification: Notification,
        context: Context,
        payload: Bundle
    ) {
        super.customizeNotification(notification, context, payload)
    }

    override fun getIntentFlags(payload: Bundle): Int {
        return super.getIntentFlags(payload)
    }

    override fun getRedirectIntent(context: Context): Intent {
        return super.getRedirectIntent(context)
    }

    override fun handleCustomAction(context: Context?, payload: String) {
        super.handleCustomAction(context, payload)
    }

    override fun isNotificationRequired(context: Context, payload: Bundle): Boolean {

        return super.isNotificationRequired(context, payload)
    }

    override fun onCreateNotification(
        context: Context,
        notificationPayload: NotificationPayload
    ): NotificationCompat.Builder {
        val builder = super.onCreateNotification(context, notificationPayload);

        val payload = notificationPayload.payload

        val newtitle = payload.getString("new_title", "New Title")
        val newmessage = payload.getString("new_message", "New Message")

        builder.setContentTitle(newtitle)
        builder.setContentText(newmessage)

        return builder;
    }

    override fun onHandleRedirection(activity: Activity, payload: Bundle) {

        if (!payload.containsKey("custom_redirection"))
            super.onHandleRedirection(activity, payload)

        val key = payload.getString("custom_redirection", null)

        if (key.isNullOrEmpty())
            super.onHandleRedirection(activity, payload)

        if (key.equals("MainActivity")) {
            val intent = Intent(activity, MainActivity::class.java)
            activity.startActivity(intent)
        } else {
            super.onHandleRedirection(activity, payload)
        }

    }

    override fun onNonMoEngageMessageReceived(context: Context, payload: Bundle) {
        super.onNonMoEngageMessageReceived(context, payload)
    }

    override fun onNotificationCleared(context: Context, payload: Bundle) {
        super.onNotificationCleared(context, payload)
    }

    override fun onNotificationNotRequired(context: Context, payload: Bundle) {
        super.onNotificationNotRequired(context, payload)
    }

    override fun onNotificationReceived(context: Context, payload: Bundle) {
        super.onNotificationReceived(context, payload)
    }

    override fun onPostNotificationReceived(context: Context, payload: Bundle) {
        super.onPostNotificationReceived(context, payload)
    }
}


