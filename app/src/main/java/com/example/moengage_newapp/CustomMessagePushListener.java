package com.example.moengage_newapp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.moengage.pushbase.model.NotificationPayload;
import com.moengage.pushbase.push.PushMessageListener;

public class CustomMessagePushListener extends PushMessageListener {

    @NonNull
    @Override
    public NotificationCompat.Builder onCreateNotification(@NonNull Context context, @NonNull NotificationPayload notificationPayload) {
        NotificationCompat.Builder builder = super.onCreateNotification(context, notificationPayload);


        builder.setOngoing(true);

        return builder;
    }

    @Override
    public void onNotificationReceived(@NonNull Context context, @NonNull Bundle payload) {
    }

    @Override
    public boolean isNotificationRequired(@NonNull Context context, @NonNull Bundle payload) {
        return false;
    }
}
