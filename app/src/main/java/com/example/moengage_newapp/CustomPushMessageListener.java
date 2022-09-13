package com.example.moengage_newapp;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.moengage.pushbase.model.NotificationPayload;
import com.moengage.pushbase.push.PushMessageListener;

/**
 * @author: Shivam Agrawal
 * Date: 18/08/22 (Thursday)
 **/
public class CustomPushMessageListener extends PushMessageListener {

    @NonNull
    @Override
    public NotificationCompat.Builder onCreateNotification(@NonNull Context context, @NonNull NotificationPayload notificationPayload) {
        return super.onCreateNotification(context, notificationPayload);
    }
}
