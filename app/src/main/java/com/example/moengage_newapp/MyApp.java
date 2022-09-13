package com.example.moengage_newapp;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;

import com.moe.pushlibrary.MoEHelper;
import com.moengage.core.model.AppStatus;

public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

    }

    private void createNotificationChannel() {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return;

        try {

            final String channelId = "my_channel_id";
            final String channelName = "my_channel_name";

            NotificationManager manager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

            NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT);

            channel.enableVibration(true);

            AudioAttributes attributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .build();

            Uri tone = Uri.parse(
                    "android.resource://" + getPackageName() + "/raw/" + "tone_name");

            if (tone != null && attributes != null) {
                channel.setSound(tone, attributes);
            }

            if (manager != null) {
                manager.createNotificationChannel(channel);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void trackInstallOrUpdate() {

        final String versionCode = "version_code";

        //keys are just sample keys, use suitable keys for the apps
        SharedPreferences preferences = getSharedPreferences("user_preferences", Context.MODE_PRIVATE);

        if (preferences == null)
            return;

        //Fresh Install
        if (!preferences.contains(versionCode)) {
            preferences.edit().putInt(versionCode, BuildConfig.VERSION_CODE).apply();
            MoEHelper.getInstance(this).setAppStatus(AppStatus.INSTALL);
            return;
        }

        //Update
        int currentVersion = preferences.getInt(versionCode, -1);

        if (currentVersion < BuildConfig.VERSION_CODE) {
            preferences.edit().putInt(versionCode, BuildConfig.VERSION_CODE).apply();
            MoEHelper.getInstance(this).setAppStatus(AppStatus.UPDATE);
        }

    }
}
