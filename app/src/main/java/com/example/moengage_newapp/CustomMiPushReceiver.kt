package com.example.moengage_newapp

class CustomMiPushReceiver {

//    override fun onNotificationMessageArrived(p0: Context?, p1: MiPushMessage?) {
//        super.onNotificationMessageArrived(p0, p1)
//        Log.d("Moe", "FROM MI PUSH")
//
//    }
//
//    private val tag = "CustomMiPushReceiver"
//
//    override fun onReceivePassThroughMessage(context: Context?, miPushMessage: MiPushMessage?) {
//        try {
//            Logger.v("$tag onReceivePassThroughMessage() : Will try to process and show pass through message.")
//            if (miPushMessage == null || context == null) {
//                Logger.w("$tag onReceivePassThroughMessage() : Context or Mi Push object is null.")
//                return
//            }
//            MoEMiPushHelper.getInstance().passPushPayload(context, miPushMessage)
//        } catch (e: Exception) {
//            Logger.e("$tag onReceivePassThroughMessage() : ", e)
//        }
//    }
//
//    override fun onNotificationMessageClicked(context: Context?, miPushMessage: MiPushMessage?) {
//        // process notification click
//        // 1. track click
//        // 2. redirect user
//        try {
//            Logger.v("$tag onNotificationMessageClicked() : Will try to process notification click")
//            if (miPushMessage == null || context == null) {
//                Logger.w("$tag onNotificationMessageClicked() : MiPushMessage object is null")
//                return
//            }
//            MoEMiPushHelper.getInstance().onNotificationClicked(context, miPushMessage)
//        } catch (e: Exception) {
//            Logger.e("$tag onNotificationMessageClicked() : Exception: ", e)
//        }
//    }
//
//    override fun onReceiveRegisterResult(
//        context: Context?,
//        miPushCommandMessage: MiPushCommandMessage?
//    ) {
//        // save push token
//        try {
//            Logger.v("$tag onReceiveRegisterResult() : Message: $miPushCommandMessage")
//            if (miPushCommandMessage == null || context == null) return
//            if (!SdkConfig.getConfig().push.miPush.isRegistrationEnabled) {
//                Logger.w(
//                    "$tag onReceiveRegisterResult() : MoEngage SDK registration is disabled." +
//                            " Ignoring token."
//                )
//                return
//            }
//            val command = miPushCommandMessage.command
//            if (MiPushClient.COMMAND_REGISTER != command) {
//                Logger.v(
//                    "$tag onReceiveRegisterResult() : Received command is not register " +
//                            "command."
//                )
//                return
//            }
//            if (miPushCommandMessage.resultCode != ErrorCode.SUCCESS.toLong()) {
//                Logger.v("$tag onReceiveRegisterResult() : Registration failed.")
////                MiPushController.scheduleTokenRegistrationRetry(context)
//                return
//            }
//            val arguments = miPushCommandMessage.commandArguments ?: return
//            val pushToken = if (arguments.size > 0) arguments[0] else null
//            if (pushToken.isNullOrEmpty()) {
//                Logger.v("$tag onReceiveRegisterResult() : Token is null or empty")
//                return
//            }
//            MoEMiPushHelper.getInstance().passPushToken(context, pushToken)
//        } catch (e: Exception) {
//            Logger.e("$tag onReceiveRegisterResult() : Exception: ", e)
//        }
//    }
//
//    override fun onRequirePermissions(context: Context?, strings: Array<String>?) {
//        try {
//            Logger.v("$tag onRequirePermissions() : $strings")
//        } catch (e: Exception) {
//            Logger.e("$tag onRequirePermissions() : Exception: ", e)
//        }
//    }

}