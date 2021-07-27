package com.example.moengage_newapp

import android.app.Activity
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
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

        val activityName = notificationPayload.payload.getString("gcm_activityName", "")

        val redirectIntent = if (!isNullOrEmpty(activityName)) {
            Intent(context, Class.forName(activityName))
        } else null

        val pendingIntent =
            PendingIntent.getActivity(context, 0, redirectIntent, PendingIntent.FLAG_ONE_SHOT)

        return NotificationCompat.Builder(context, "moe_default_channel")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(notificationPayload.payload.getString("gcm_title", ""))
            .setContentText(notificationPayload.payload.getString("gcm_alert", ""))
            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
            .setContentIntent(pendingIntent)
    }


    /*
    * Should Show Notification or Not decided from KEY-VALUE PAIR present in dashboard
    * key - "showNotification"
    * value - "true/false"
    * */
    override fun isNotificationRequired(context: Context, payload: Bundle): Boolean {
        if (super.isNotificationRequired(context, payload)) {
            return payload.getBoolean("showNotification", true)
        }
        return super.isNotificationRequired(context, payload)
    }


    override fun onHandleRedirection(activity: Activity, payload: Bundle) {

        val activityName = payload.get("redirection_activity").toString()
        val redirectIntent = if (!isNullOrEmpty(activityName)) {
            Intent(activity, Class.forName(activityName))
        } else return super.onHandleRedirection(activity, payload)

        activity.startActivity(redirectIntent)
    }
}