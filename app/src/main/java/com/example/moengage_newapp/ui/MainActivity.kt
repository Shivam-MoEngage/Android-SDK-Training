package com.example.moengage_newapp.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.viewpager2.widget.ViewPager2
import com.example.moengage_newapp.LifeCycleCallbacks
import com.example.moengage_newapp.LoginActivity
import com.example.moengage_newapp.MoEngageAnalyticsHelper
import com.example.moengage_newapp.R
import com.example.moengage_newapp.adapter.Page
import com.example.moengage_newapp.adapter.ViewPagerAdapter
import com.example.moengage_newapp.data.Article
import com.example.moengage_newapp.data.User
import com.example.moengage_newapp.databinding.ActivityMainBinding
import com.example.moengage_newapp.util.ViewPagerTransformer
import com.example.moengage_newapp.viewmodels.HomeViewModel
import com.google.gson.Gson
import com.moe.pushlibrary.MoEHelper
import com.moengage.core.Properties
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this


        val bundle = intent.extras
        val userData = bundle?.getString("user_data") ?: ""
        val isFresh = bundle?.getBoolean("isFresh") ?: false

        if (isFresh) {
            saveSession(userData)
        }

        viewPagerAdapter = ViewPagerAdapter(this)
        binding.viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding.viewPager.setPageTransformer(ViewPagerTransformer())
        binding.viewPager.adapter = viewPagerAdapter
        binding.viewPager.setCurrentItem(1, false)
        lifecycle.addObserver(LifeCycleCallbacks(applicationContext))
    }

    override fun onBackPressed() {
        if (binding.viewPager.currentItem != 1) {
            binding.viewPager.currentItem = 1
        } else {
            MoEngageAnalyticsHelper.trackEvents(this, "appClose", Properties().apply {
                addAttribute("appClosed", true)
            })
            super.onBackPressed()
        }
    }


    //Change Page
    fun moveToPage(page: Page){
        when (page) {
            Page.FragmentBookMark -> binding.viewPager.currentItem = 0
            Page.FragmentNews -> binding.viewPager.currentItem = 1
        }
    }

    //Share News
    fun shareNews(article: Article) {

        val properties = Properties().apply {
            addAttribute("articleUrl", article.url)
            addAttribute("articleTitle", article.title)
        }

        MoEngageAnalyticsHelper.trackEvents(this, "articleShared", properties)


        val sendIntent = Intent()
        sendIntent.action = Intent.ACTION_SEND

        sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Share News")
        sendIntent.putExtra(Intent.EXTRA_TEXT, article.url)

        sendIntent.type = "text/plain"
        startActivity(Intent.createChooser(sendIntent, "Share News"))
    }

    //Open News Intent
    fun openNews(article: Article) {

        val properties = Properties().apply {
            addAttribute("articleUrl", article.url)
            addAttribute("articleTitle", article.title)
        }

        MoEngageAnalyticsHelper.trackEvents(this, "articleViewed", properties)

        val uri = Uri.parse(article.url)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }

    private fun saveSession(user: String) {
        if (user.isEmpty())
            return

        viewModel.loginUser(user)

        val _user = Gson().fromJson(user, User::class.java)
        MoEHelper.getInstance(this).setUniqueId(_user.uId)
        MoEHelper.getInstance(this).setFirstName(_user.userName)
        MoEHelper.getInstance(this).setFullName(_user.userName + _user.passWord)

    }

    fun logoutUser() {
        viewModel.logOut()
        MoEHelper.getInstance(this).logoutUser()
        moveToLoginScreen()
    }

    fun moveToLoginScreen() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.addFlags(
            Intent.FLAG_ACTIVITY_CLEAR_TOP or
                    Intent.FLAG_ACTIVITY_CLEAR_TASK or
                    Intent.FLAG_ACTIVITY_NEW_TASK
        )
        startActivity(intent)
    }
}