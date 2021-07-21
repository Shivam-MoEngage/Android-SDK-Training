package com.example.moengage_newapp

import android.content.Context
import com.moe.pushlibrary.MoEHelper
import com.moengage.core.Properties

class MoEngageAnalyticsHelper {

    companion object {

        fun trackEvents(context: Context, eventName: String, value: Properties) {
            MoEHelper.getInstance(context).trackEvent(eventName, value)
        }

        fun addUserAttribute(context: Context, attributeName: String, attributeValue: String) {
            MoEHelper.getInstance(context).setUserAttribute(attributeName, attributeValue)
        }

        fun addUserAttribute(context: Context, attributeName: String, attributeValue: Int) {
            MoEHelper.getInstance(context).setUserAttribute(attributeName, attributeValue)
        }


        fun addUserAttribute(context: Context, attributeName: String, attributeValue: Boolean) {
            MoEHelper.getInstance(context).setUserAttribute(attributeName, attributeValue)
        }
    }
}