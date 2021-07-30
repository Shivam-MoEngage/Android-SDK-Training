package com.example.moengage_newapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.moengage_newapp.R
import com.example.moengage_newapp.adapter.InboxCustomAdapter
import com.example.moengage_newapp.adapter.Page
import com.moengage.core.internal.logger.Logger
import com.moengage.inbox.core.MoEInboxHelper
import com.moengage.inbox.core.listener.OnMessagesAvailableListener
import com.moengage.inbox.core.model.InboxMessage
import kotlinx.coroutines.launch

class FragmentInbox : Fragment() {

    private lateinit var inboxListAdapter: InboxCustomAdapter
    private lateinit var inboxRecyclerView: RecyclerView
    private lateinit var inboxEmptyTextView: TextView
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

        val backButton = view.findViewById<ImageView>(R.id.backIcon)
        backButton.setOnClickListener {
            onBackButtonPressed()
        }

        inboxListAdapter = InboxCustomAdapter(
            onMessageClicked = { message, position ->
                Toast.makeText(
                    requireContext(),
                    "Message clicked at position $position",
                    Toast.LENGTH_SHORT
                ).show()
            }
        )

        inboxRecyclerView = view.findViewById<RecyclerView>(R.id.inbox_recyclerView)
        inboxEmptyTextView = view.findViewById(R.id.moeInboxEmpty)
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout)

        inboxRecyclerView.apply {
            setHasFixedSize(true)
            adapter = inboxListAdapter
        }

        swipeRefreshLayout.setOnRefreshListener {
            refresh()
        }

        ItemTouchHelper(object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val message = inboxListAdapter.currentList[position]
                MoEInboxHelper.getInstance().deleteMessage(requireContext(), message)
                refresh()
            }

        }).attachToRecyclerView(inboxRecyclerView)
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
                    inboxListAdapter.submitList(messageList)
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