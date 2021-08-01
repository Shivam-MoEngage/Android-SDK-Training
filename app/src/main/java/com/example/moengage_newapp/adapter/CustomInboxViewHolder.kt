package com.example.moengage_newapp.adapter

import android.view.View
import androidx.core.content.ContextCompat
import com.example.moengage_newapp.databinding.ItemInboxBinding
import com.moengage.inbox.core.MoEInboxHelper
import com.moengage.inbox.core.model.InboxMessage
import com.moengage.inbox.ui.R
import com.moengage.inbox.ui.adapter.InboxListAdapter
import com.moengage.inbox.ui.adapter.ViewHolder
import com.moengage.inbox.ui.internal.getDate

//class CustomInboxViewHolder(
//    private val binding: ItemInboxBinding,
//    private val onMessageClicked: (Int) -> Unit,
//) : RecyclerView.ViewHolder(binding.root) {
//
//    fun bind(inboxMessage: InboxMessage) {
//
//        binding.apply {
//            binding.date.text = getDate(inboxMessage.receivedTime)
//            binding.title.text = inboxMessage.textContent.title
//            binding.newsDescription.text = inboxMessage.textContent.message
//
//        }
//    }
//
//    init {
//        binding.apply {
//            itemView.setOnClickListener {
//                val position = adapterPosition
//                if (position != RecyclerView.NO_POSITION) {
//                    onMessageClicked(adapterPosition)
//                }
//            }
//        }
//    }
//}

class CustomInboxViewHolder(
    private val itemInboxBinding: ItemInboxBinding,
    private val onMessageClicked: (Int, InboxMessage) -> Unit,
) : ViewHolder(itemInboxBinding.root) {

    fun bind(position: Int, inboxMessage: InboxMessage, inboxListAdapter: InboxListAdapter) {
        itemInboxBinding.apply {

            itemView.setBackgroundColor(
                ContextCompat.getColor(
                    itemView.context,
                    if (inboxMessage.isClicked) R.color.moe_inbox_item_clicked else R.color.moe_inbox_item_unclicked
                )
            )

            date.text = getDate(inboxMessage.receivedTime)
            title.text = inboxMessage.textContent.title
            newsDescription.text = inboxMessage.textContent.message

            deleteMessage.setOnClickListener {
                inboxListAdapter.deleteItem(position, inboxMessage)
            }
            itemView.setOnClickListener {

                itemView.setBackgroundColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.moe_inbox_item_clicked
                    )
                )
                onMessageClicked(position, inboxMessage)
            }

            if (MoEInboxHelper.getInstance().hasCouponCode(inboxMessage)) {
                couponCode.visibility = View.VISIBLE
                couponCode.text = MoEInboxHelper.getInstance().getCouponCode(inboxMessage)
            } else {
                couponCode.visibility = View.GONE
            }
        }
    }

}