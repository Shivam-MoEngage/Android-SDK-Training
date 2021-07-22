package com.example.moengage_newapp

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.app.NotificationCompat
import com.moengage.core.internal.utils.isNullOrEmpty
import com.moengage.pushbase.model.NotificationPayload
import com.moengage.pushbase.push.PushMessageListener


class CustomMessagePushListener() : PushMessageListener() {

    override fun onCreateNotification(
        context: Context,
        notificationPayload: NotificationPayload
    ): NotificationCompat.Builder {
        val builder = super.onCreateNotification(context, notificationPayload)
        return builder
    }


    /*
    * Should Show Notification or Not decided from KEY-VALUE PAIR present in dashboard
    * key - "showNotification"
    * value - "true/false"
    * */
    override fun isNotificationRequired(context: Context, payload: Bundle): Boolean {
        super.isNotificationRequired(context, payload)
        return payload.get("showNotification").toString().toBoolean()
    }


    override fun onHandleRedirection(activity: Activity, payload: Bundle) {

        val activityName = payload.get("redirection_activity").toString()
        val redirectIntent = if (!isNullOrEmpty(activityName)) {
            Intent(activity, Class.forName(activityName))
        } else return super.onHandleRedirection(activity, payload)

        activity.startActivity(redirectIntent)
    }
}