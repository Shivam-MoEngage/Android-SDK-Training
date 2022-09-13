package com.example.moengage_newapp;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.moengage.firebase.MoEFireBaseHelper;
import com.moengage.pushbase.MoEPushHelper;

/**
 * @author: Shivam Agrawal
 * Date: 18/05/22 (Wednesday)
 **/
public class MoEngageFirebase extends FirebaseMessagingService {

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);

        MoEFireBaseHelper.getInstance().passPushToken(this, s);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        if (MoEPushHelper.getInstance().isFromMoEngagePlatform(remoteMessage.getData())) {
            Log.v("CustomFirebaseMessage", "Inside If Condition");
            MoEFireBaseHelper.getInstance().passPushPayload(this, remoteMessage.getData());
        }
    }
}
