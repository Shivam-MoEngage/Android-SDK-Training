package com.example.moengage_newapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.moengage_newapp.data.User
import com.example.moengage_newapp.databinding.ActivityLoginBinding
import com.example.moengage_newapp.ui.MainActivity
import com.example.moengage_newapp.util.Helper
import com.example.moengage_newapp.viewmodels.LoginViewModel
import com.google.firebase.messaging.FirebaseMessaging
import com.google.gson.Gson
import com.moengage.core.Properties
import com.moengage.firebase.MoEFireBaseHelper
import com.moengage.inapp.MoEInAppHelper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var activityBinding: ActivityLoginBinding
    private val loginViewModel: LoginViewModel by viewModels()
    private var shouldObserve = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        activityBinding.lifecycleOwner = this

        MoEngageAnalyticsHelper.trackEvents(this, "appOpen", Properties().apply {
            addAttribute("appOpened", true)
        })

        loginViewModel.userData.observe(this) {
            if (!it.isNullOrEmpty() && shouldObserve) {
                moveToNextScreen(it, true)
            }
        }

        activityBinding.btLogin.setOnClickListener {
            val etName = activityBinding.etUsername.text.trim().toString()
            val etPassword = activityBinding.etPassword.text.trim().toString()
            shouldObserve = false

            if (etName.isEmpty() || etPassword.isEmpty()) {
                Toast.makeText(this, "Username and Password Cannot be Null", Toast.LENGTH_LONG)
                    .show()
                return@setOnClickListener
            }

            if (Helper.userPassMap.containsKey(etName)) {
                val pass = Helper.userPassMap.getValue(etName)
                if (pass.equals(etPassword.trim())) {
                    val userData = User(
                        userName = etName, passWord = etPassword,
                        uId = Helper.getUid()
                    )

                    moveToNextScreen(Gson().toJson(userData, User::class.java), false)

                } else {
                    Toast.makeText(this, " Wrong Password", Toast.LENGTH_LONG).show()
                }
            }

        }

        FirebaseMessaging.getInstance().token.addOnCompleteListener { p0 ->
            if (p0.isSuccessful) {
                val token = p0.result ?: return@addOnCompleteListener
                MoEFireBaseHelper.getInstance().passPushToken(applicationContext, token)
            }
        }
        activityBinding.showInApps.setOnClickListener {
            MoEInAppHelper.getInstance().showInApp(this)
        }

        activityBinding.showSelfHandledInApps.setOnClickListener {
            MoEInAppHelper.getInstance().getSelfHandledInApp(this)
        }

        activityBinding.nudge.initialiseNudgeView(this)

    }

    private fun moveToNextScreen(user: String?, isFromSavedSession: Boolean) {

        if (user.isNullOrEmpty())
            return

        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("user_data", user)
        intent.putExtra("isFresh", !isFromSavedSession)
//        MoEngage.enableSdk(this)
//        updateLoginStatus(true)
        startActivity(intent)
        finish()
    }

    override fun onStart() {
        super.onStart()
        InAppController.getInstance().registerActivity(this)
    }

    override fun onStop() {
        super.onStop()

        InAppController.getInstance().unRegisterActivity(this)
    }

    fun updateLoginStatus(value: Boolean) {

        val isUserLoggined = "isUserLoggined"

        //keys are just sample keys, use suitable keys for the apps
        val preferences = getSharedPreferences("user_preferences", Context.MODE_PRIVATE) ?: return

        preferences.edit().putBoolean(isUserLoggined, value).apply();
    }
}