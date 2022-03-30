package com.example.moengage_newapp

import android.content.Context

class InAppListener(private val context: Context) {
//    :
//    InAppMessageListener() {
//
//    override fun onClosed(inAppCampaign: MoEInAppCampaign) {
//        super.onClosed(inAppCampaign)
//    }
//
//    override fun onCustomAction(inAppCampaign: MoEInAppCampaign) {
//        super.onCustomAction(inAppCampaign)
//    }
//
//    override fun onNavigation(inAppCampaign: MoEInAppCampaign): Boolean {
//        return super.onNavigation(inAppCampaign)
//    }
//
//    override fun onSelfHandledAvailable(inAppCampaign: MoEInAppCampaign) {
//        super.onSelfHandledAvailable(inAppCampaign)
//        val currentActivity = InAppController.getInstance().getCurrentActivity() ?: return
//
//        val customView =
//            LayoutInflater.from(currentActivity).inflate(R.layout.self_handled_campaign, null)
//        val textView = customView.findViewById<TextView>(R.id.payload)
//        textView.text = inAppCampaign.selfHandledCampaign?.payload
//        val dialogBuilder = AlertDialog.Builder(currentActivity)
//        dialogBuilder.setView(customView)
//        val dialog = dialogBuilder.create()
//        val dismiss = customView.findViewById<Button>(R.id.dismiss)
//        dismiss.setOnClickListener {
//            MoEInAppHelper.getInstance().selfHandledDismissed(currentActivity, inAppCampaign)
//            dialog.dismiss()
//        }
//        val click = customView.findViewById<Button>(R.id.click)
//        click.setOnClickListener {
//            MoEInAppHelper.getInstance().selfHandledClicked(currentActivity, inAppCampaign)
//            dialog.dismiss()
//        }
//        val widgetClick = customView.findViewById<Button>(R.id.widgetClick)
//        widgetClick.setOnClickListener {
//
//            //Log the event to the server
//            MoEInAppHelper.getInstance().selfHandledClicked(currentActivity, inAppCampaign, 12345)
//
//            //Will not log event - only update the db as widget clicked
//            MoEInAppHelper.getInstance().selfHandledPrimaryClicked(currentActivity, inAppCampaign)
//            dialog.dismiss()
//        }
//        dialog.show()
//        Log.v("MoEngage", inAppCampaign.selfHandledCampaign?.payload.toString())
//        MoEInAppHelper.getInstance().selfHandledShown(currentActivity, inAppCampaign)
//    }
//
//    override fun onShown(inAppCampaign: MoEInAppCampaign) {
//        super.onShown(inAppCampaign)
//    }
}