package com.example.moengage_newapp

import android.content.Context
import com.moengage.inapp.listeners.InAppMessageListener
import com.moengage.inapp.model.MoEInAppCampaign

class InAppListener(private val context: Context) : InAppMessageListener() {

    override fun onClosed(inAppCampaign: MoEInAppCampaign) {
        super.onClosed(inAppCampaign)
    }

    override fun onCustomAction(inAppCampaign: MoEInAppCampaign) {
        super.onCustomAction(inAppCampaign)
    }

    override fun onNavigation(inAppCampaign: MoEInAppCampaign): Boolean {
        return super.onNavigation(inAppCampaign)
    }

    override fun onSelfHandledAvailable(inAppCampaign: MoEInAppCampaign) {
        super.onSelfHandledAvailable(inAppCampaign)
    }

    override fun onShown(inAppCampaign: MoEInAppCampaign) {
        super.onShown(inAppCampaign)
    }
}