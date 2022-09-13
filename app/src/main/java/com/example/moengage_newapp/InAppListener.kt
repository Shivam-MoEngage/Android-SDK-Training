package com.example.moengage_newapp

import com.moengage.inapp.listeners.InAppMessageListener
import com.moengage.inapp.model.MoEInAppCampaign

class InAppListener() : InAppMessageListener() {

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

    }

    override fun onShown(inAppCampaign: MoEInAppCampaign) {
        super.onShown(inAppCampaign)
    }

}