package com.example.moengage_newapp.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.moengage_newapp.databinding.ItemInboxBinding
import com.moengage.inbox.core.model.InboxMessage
import com.moengage.inbox.ui.internal.getDate

class CustomInboxViewHolder(
    private val binding: ItemInboxBinding,
    private val onMessageClicked: (Int) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(inboxMessage: InboxMessage) {

        binding.apply {
            binding.date.text = getDate(inboxMessage.receivedTime)
            binding.title.text = inboxMessage.textContent.title
            binding.newsDescription.text = inboxMessage.textContent.message

        }
    }

    init {
        binding.apply {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onMessageClicked(adapterPosition)
                }
            }
        }
    }
}