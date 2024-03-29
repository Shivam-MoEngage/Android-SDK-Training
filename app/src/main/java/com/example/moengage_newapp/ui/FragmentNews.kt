package com.example.moengage_newapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.moengage_newapp.MoEngageAnalyticsHelper
import com.example.moengage_newapp.R
import com.example.moengage_newapp.adapter.NewsRecyclerAdapter
import com.example.moengage_newapp.adapter.Page
import com.example.moengage_newapp.data.Article
import com.example.moengage_newapp.databinding.FragmentNewsBinding
import com.example.moengage_newapp.util.Resource
import com.example.moengage_newapp.util.exhaustive
import com.example.moengage_newapp.util.showSnackbar
import com.example.moengage_newapp.viewmodels.NewsViewModel
import com.moe.pushlibrary.MoEHelper
import com.moengage.cards.ui.CardActivity
import com.moengage.core.Properties
import com.moengage.inapp.MoEInAppHelper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect


@AndroidEntryPoint
class FragmentNews() : Fragment() {

    private lateinit var binding: FragmentNewsBinding
    private val viewModel: NewsViewModel by viewModels()
    private lateinit var newsArticleAdapter: NewsRecyclerAdapter
    private val currentAdapterPos = MutableLiveData<Int>(0)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewsBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        newsArticleAdapter = NewsRecyclerAdapter(
            onBookmarkClick = { article ->
                onBookMark(article)
            },
            onImageClicked = { article ->
                openNews(article)
            }
        )

        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(binding.newsRecycler)
        binding.newsRecycler.apply {
            this.adapter = newsArticleAdapter
            setHasFixedSize(true)
            itemAnimator = null
            itemAnimator?.changeDuration = 0
        }

        val lm = binding.newsRecycler.layoutManager as LinearLayoutManager

        binding.newsRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    currentAdapterPos.value = lm.findFirstCompletelyVisibleItemPosition()
                }
            }
        })

        //Start Fetching news
        viewModel.onStart()

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.recentNews.collect {
                val result = it ?: return@collect

                binding.swipeRefreshLayout.isRefreshing = result is Resource.Loading
                binding.loadingImage.isVisible = result is Resource.Loading
                binding.newsRecycler.isVisible = !result.data.isNullOrEmpty()
                binding.badNetWork.isVisible = result.data.isNullOrEmpty() && result !is Resource.Loading

                newsArticleAdapter.submitList(result.data) {
                    if (viewModel.pendingScrollToTopAfterRefresh) {
                        binding.newsRecycler.smoothScrollToPosition(0)
                        viewModel.pendingScrollToTopAfterRefresh = false
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.events.collect { event ->
                when (event) {
                    is NewsViewModel.Event.ShowErrorMessage -> {
                        showSnackbar(
                                getString(
                                        R.string.could_not_refresh,
                                        event.error.localizedMessage
                                                ?: getString(R.string.unknown_error_occurred)
                                )
                        )

                        binding.badNetWork.visibility = VISIBLE
                    }
                }.exhaustive
            }
        }


        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.onManualRefresh()
        }

        //Observing the messages
        viewModel.bookMarkMsg.observe(viewLifecycleOwner){
            if (!it.isNullOrEmpty()){
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            }
        }


        //Adapting the action bar icon based on adapter current position
        currentAdapterPos.observe(viewLifecycleOwner){ position ->
//            if (position == 1 ){
//                binding.actionIcon.setImageResource(R.drawable.ic_baseline_vertical_align_top_24)
//            }else if (position == 0){
//                binding.actionIcon.setImageResource(R.drawable.ic_baseline_refresh_24)
//            }

            val adapter = newsArticleAdapter.getCurrentItem(position)
            adapter?.let { setBookMarkIcon(it.isBookMarked) }
        }

        //Adapting the action bar action based on current adapter position
//        binding.actionIcon.setOnClickListener{
//            if (currentAdapterPos.value!! > 0){
//                binding.newsRecycler.smoothScrollToPosition(0)
//            }else{
//                viewModel.onManualRefresh()
//            }
//        }

        //On ActionBar
        binding.backIcon.setOnClickListener {
            if (activity != null && activity is MainActivity) {
                (activity as MainActivity).moveToPage(Page.FragmentBookMark)
            }
        }

        //Update BookMark Icon
        binding.bookmarkParent.setOnClickListener {
            newsArticleAdapter.getCurrentItem(currentAdapterPos.value!!)?.let { article ->
                onBookMark(article)
            }
        }

        binding.shareParent.setOnClickListener {
            shareNews()
        }

        binding.sortParent.setOnClickListener {
            sortNews()
        }

        binding.logoutText.setOnClickListener {
            logoutUser()
        }
        binding.cards.setOnClickListener {
            startActivity(Intent(requireContext(), CardActivity::class.java))
        }
    }

    //Share News
    private fun shareNews() {
        if (activity != null && activity is MainActivity) {
            val article = newsArticleAdapter.getCurrentItem(currentAdapterPos.value!!)
            article?.let { (activity as MainActivity).shareNews(article) }
        }
    }

    //Open News
    private fun openNews(article: Article) {
        if (activity != null && activity is MainActivity) {
            (activity as MainActivity).openNews(article)
        }
    }

    override fun onResume() {
        super.onResume()

        MoEHelper.getInstance(requireContext()).appContext = listOf("NewsFragment")
        MoEInAppHelper.getInstance().showInApp(requireContext())
    }

    override fun onStop() {
        super.onStop()
        MoEHelper.getInstance(requireContext()).resetAppContext()
    }

    //Sort News
    private fun sortNews() {
        viewModel.updateSortOrder()

        //Refreshing the adapter
        newsArticleAdapter.submitList(arrayListOf())
    }

    //On Bookmark Clicked
    private fun onBookMark(article: Article) {
        viewModel.onBookmarkClick(article)
        setBookMarkIcon(!article.isBookMarked)

        requireContext().let {

            val properties = Properties().apply {
                addAttribute("articleUrl", article.url)
                addAttribute("articleTitle", article.title)
            }
            MoEngageAnalyticsHelper.trackEvents(it, "articleSaved", properties)
        }
    }

    //Update Bookmark Icon
    private fun setBookMarkIcon(isBookMarked: Boolean) {
        binding.bkIcon.setImageResource(
            when (isBookMarked) {
                true -> R.drawable.ic_baseline_bookmark_24
                else -> R.drawable.ic_baseline_bookmark_border_24
            }
        )
    }

    private fun logoutUser() {
        if (activity is MainActivity) {
            (activity as MainActivity).logoutUser()
        }
    }
}