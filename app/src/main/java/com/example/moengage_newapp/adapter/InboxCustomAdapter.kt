package com.example.moengage_newapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moengage_newapp.databinding.ItemInboxBinding
import com.moengage.inbox.core.model.InboxMessage
import com.moengage.inbox.ui.adapter.InboxAdapter
import com.moengage.inbox.ui.adapter.InboxListAdapter
import com.moengage.inbox.ui.adapter.ViewHolder

class InboxCustomAdapter(
    private val onMessageClicked: (InboxMessage, Int) -> Unit,
) : InboxAdapter() {
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int, inboxMessage: InboxMessage): Int {
        return 1
    }

    override fun onBindViewHolder(
        viewHolder: ViewHolder,
        position: Int,
        inboxMessage: InboxMessage,
        inboxListAdapter: InboxListAdapter
    ) {
        (viewHolder as CustomInboxViewHolder).bind(position, inboxMessage, inboxListAdapter)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        return CustomInboxViewHolder(
            ItemInboxBinding.inflate(
                LayoutInflater.from(viewGroup.context),
                viewGroup,
                false
            ),
            onMessageClicked = { position, message ->
                if (position != RecyclerView.NO_POSITION) {
                    onMessageClicked(message, position)
                }
            }
        )
    }

}