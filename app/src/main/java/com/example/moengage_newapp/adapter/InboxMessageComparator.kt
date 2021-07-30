package com.example.moengage_newapp.adapter

import androidx.recyclerview.widget.DiffUtil
import com.moengage.inbox.core.model.InboxMessage

class InboxMessageComparator : DiffUtil.ItemCallback<InboxMessage>() {
    override fun areItemsTheSame(oldItem: InboxMessage, newItem: InboxMessage): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: InboxMessage, newItem: InboxMessage): Boolean {
        return oldItem == newItem
    }
}