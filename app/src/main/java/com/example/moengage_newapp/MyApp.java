package com.example.moengage_newapp;

import android.app.Application;
import android.os.Build;

//import com.moengage.core.LogLevel;
//import com.moengage.core.MoEngage;
//import com.moengage.core.config.LogConfig;
//import com.moengage.pushbase.internal.MoEngageNotificationUtils;

public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

    }

    private void createNotificationChannel() {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return;

//        try {
//
//            final String channelId = "my_channel";
//            final String channelName = "MyChannel";
//            if (MoEngageNotificationUtils.isNotificationChannelExists(getApplicationContext(), channelId))
//                return;
//            NotificationManager manager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
//
//            NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT);
//
//            channel.enableVibration(true);
//
//            AudioAttributes attributes = new AudioAttributes.Builder()
//                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
//                    .build();
//
//            Uri tone = Uri.parse(
//                    "android.resource://" + getPackageName() + "/raw/" + "tone_name");
//
//            if (tone != null && attributes != null) {
//                channel.setSound(tone, attributes);
//            }
//
//            if (manager != null) {
//                manager.createNotificationChannel(channel);
//            }
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }

    }
}
