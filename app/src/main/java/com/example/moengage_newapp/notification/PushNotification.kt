package com.example.moengage_newapp.notification

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.moengage.firebase.MoEFireBaseHelper
import com.moengage.pushbase.MoEPushHelper


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
        }
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