package com.example.moengage_newapp.notification;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.moengage.firebase.MoEFireBaseHelper;
import com.moengage.pushbase.MoEPushHelper;

/**
 * @author: Shivam Agrawal
 * Date: 03/06/22 (Friday)
 **/
public class CustomFirebaseService extends FirebaseMessagingService {

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        MoEFireBaseHelper.getInstance().passPushToken(this, s);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        if (MoEPushHelper.getInstance().isFromMoEngagePlatform(remoteMessage.getData())) {
            MoEFireBaseHelper.getInstance().passPushPayload(this, remoteMessage.getData());
        } else {

            //handle your implementation

        }
    }
}
