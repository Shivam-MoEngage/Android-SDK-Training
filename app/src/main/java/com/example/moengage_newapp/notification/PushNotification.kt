package com.example.moengage_newapp.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.moengage_newapp.R
import com.example.moengage_newapp.ui.MainActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.moengage.firebase.MoEFireBaseHelper
import com.moengage.pushbase.MoEPushHelper
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL


class PushNotification : FirebaseMessagingService() {

    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
        Log.i("FCM Token", p0)
        MoEFireBaseHelper.getInstance().passPushToken(applicationContext, p0)
        //Whenever a new token is generated, and backend server is resposible for send fcm
        // token should also be updated their
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        val pushPayload = remoteMessage.data
        if (MoEPushHelper.getInstance().isFromMoEngagePlatform(pushPayload)) {
            MoEFireBaseHelper.getInstance().passPushPayload(applicationContext, pushPayload)
        } else {
            //your own logic
        }
//
//        if (remoteMessage.notification  != null){
//            handleNotification(remoteMessage.notification)
//        }else if (remoteMessage.data.isNotEmpty()) {
//            Log.i("FCM with Payload", remoteMessage.data.toString());
//
//
//            /*
//            *
//            * {gcm_activityName=com.example.moengage_newapp.ui.MainActivity, gcm_notificationType=normal notification, moe_cid_attr={"moe_campaign_id":"000000000000000078026512"}, push_from=moengage, gcm_alert=How are you !!, gcm_title=Hello World, FallBackFlagAndroid=false, gcm_campaign_id=000000000000000078026512_L_0, moe_channel_id=moe_default_channel}
//            *
//            *
//            *
//            *
//            * */
//
//            //message will contain the Push Message
//            val title = remoteMessage.data["gcm_title"]
//
//            //message will contain the Push Message
//            val messageBody = remoteMessage.data["gcm_alert"]
//
//            //Image Uri
//            val imageUri = remoteMessage.data["image"]
//            //To get a Bitmap image from the URL received
//            val bitmap = getBitmapfromUrl(imageUri);
//            handleNotificationPayload(title, messageBody, bitmap)
//        }
    }

    private fun handleNotificationPayload(message:String?,messageBody:String?,image:Bitmap?){

        if (message == null || messageBody == null)
            return

        val channelId = "default"
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)
        val defaultSoundUri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(image)
                .setContentTitle(message)
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, "Moengage", NotificationManager.IMPORTANCE_DEFAULT)
            channel.description = "FCM Notification"
            notificationManager.createNotificationChannel(channel)
        }
        notificationManager.notify(0, notificationBuilder.build())

    }
    private fun handleNotification(notification: RemoteMessage.Notification?) {
        val channelId = "default"
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)
        val defaultSoundUri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(notification?.title)
                .setContentText(notification?.body)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, "Moengage", NotificationManager.IMPORTANCE_DEFAULT)
            channel.description = "FCM Notification"
            notificationManager.createNotificationChannel(channel)
        }
        notificationManager.notify(0, notificationBuilder.build())
    }

    fun getBitmapfromUrl(imageUrl: String?): Bitmap? {
        return try {
            val url = URL(imageUrl)
            val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
            connection.setDoInput(true)
            connection.connect()
            val input: InputStream = connection.getInputStream()
            BitmapFactory.decodeStream(input)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}