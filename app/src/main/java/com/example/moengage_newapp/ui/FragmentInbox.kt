package com.example.moengage_newapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.moengage_newapp.R
import com.example.moengage_newapp.adapter.Page
import com.moengage.core.internal.logger.Logger
import com.moengage.inbox.core.MoEInboxHelper
import com.moengage.inbox.core.listener.OnMessagesAvailableListener
import com.moengage.inbox.core.model.InboxMessage
import com.moengage.inbox.ui.MoEInboxUiHelper
import com.moengage.inbox.ui.adapter.InboxListAdapter
import com.moengage.inbox.ui.adapter.sdkdefault.DefaultInboxAdapter
import com.moengage.inbox.ui.listener.OnMessageClickListener
import kotlinx.coroutines.launch

class FragmentInbox : Fragment() {

    private lateinit var inboxListAdapter: InboxListAdapter
    private lateinit var inboxRecyclerView: RecyclerView
    private lateinit var inboxEmptyTextView: TextView
    private val inboxMessages: MutableLiveData<List<InboxMessage>> = MutableLiveData()
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_inbox, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        MoEInboxUiHelper.getInstance().setInboxAdapter(DefaultInboxAdapter())

        val backButton = view.findViewById<ImageView>(R.id.backIcon)
        backButton.setOnClickListener {
            onBackButtonPressed()
        }

        inboxRecyclerView = view.findViewById<RecyclerView>(R.id.inbox_recyclerView)
        inboxListAdapter =
            InboxListAdapter(requireContext(), MoEInboxUiHelper.getInstance().getInboxAdapter())
        inboxEmptyTextView = view.findViewById(R.id.moeInboxEmpty)
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout)

        inboxRecyclerView.apply {
            setHasFixedSize(true)
            adapter = inboxListAdapter
        }

        swipeRefreshLayout.setOnRefreshListener {
            refresh()
        }

        MoEInboxUiHelper.getInstance().setOnMessageClickListener(onMessageClickListener)
    }

    override fun onStart() {
        super.onStart()
        refresh()
    }

    private fun refresh() {
        lifecycleScope.launch {
            fetchAllMessages()
        }
    }

    private fun fetchAllMessages() {
        MoEInboxHelper.getInstance().fetchAllMessagesAsync(requireContext(), onMessageAvailable)
    }

    private val onMessageClickListener = object : OnMessageClickListener {
        override fun onMessageClick(inboxMessage: InboxMessage): Boolean {
            return true
        }

    }

    private val onMessageAvailable = object : OnMessagesAvailableListener {
        override fun onMessagesAvailable(messageList: List<InboxMessage>) {
            try {

                swipeRefreshLayout.isRefreshing = false

                if (messageList.isNullOrEmpty()) {
                    inboxRecyclerView.visibility = View.GONE
                    inboxEmptyTextView.visibility = View.VISIBLE
                } else {

                    inboxRecyclerView.visibility = View.VISIBLE
                    inboxEmptyTextView.visibility = View.GONE
                    inboxListAdapter.setInboxList(messageList.toMutableList())
                }
            } catch (e: Exception) {
                Logger.v("$tag onMessagesReceived(): ", e)
            }
        }
    }

    private fun onBackButtonPressed() {
        if (activity != null && activity is MainActivity) {
            (activity as MainActivity).moveToPage(Page.FragmentNews)
        }
    }
}