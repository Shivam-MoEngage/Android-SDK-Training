package com.example.moengage_newapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.moengage_newapp.databinding.ItemInboxBinding
import com.moengage.inbox.core.model.InboxMessage

class InboxCustomAdapter(
    private val onMessageClicked: (InboxMessage, Int) -> Unit,
) : ListAdapter<InboxMessage, CustomInboxViewHolder>(InboxMessageComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomInboxViewHolder {

        return CustomInboxViewHolder(
            ItemInboxBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onMessageClicked = { position ->
                val msg = getItem(position) ?: return@CustomInboxViewHolder
                onMessageClicked(msg, position)
            }
        )

    }

    override fun onBindViewHolder(holder: CustomInboxViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }
}