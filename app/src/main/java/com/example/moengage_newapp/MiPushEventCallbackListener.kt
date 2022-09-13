package com.example.moengage_newapp

import android.content.Context
import android.util.Log
import com.moengage.mi.listener.MiPushEventListener
import com.xiaomi.mipush.sdk.MiPushMessage

/**

@author: Shivam Agrawal
Date: 26/05/22 (Thursday)




 **/
class MiPushEventCallbackListener : MiPushEventListener() {

    override fun onNonMoEngageNotificationClicked(context: Context, message: MiPushMessage) {
        super.onNonMoEngageNotificationClicked(context, message)
    }

    override fun onNonMoEngagePassThroughMessage(context: Context, message: MiPushMessage) {
        super.onNonMoEngagePassThroughMessage(context, message)
    }

    override fun onTokenAvailable(token: String) {
        super.onTokenAvailable(token)

        Log.v("MiPushEventCallback", token)
    }
}