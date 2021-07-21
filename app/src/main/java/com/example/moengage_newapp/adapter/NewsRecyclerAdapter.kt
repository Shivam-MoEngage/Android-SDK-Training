package com.example.moengage_newapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.moengage_newapp.data.Article
import com.example.moengage_newapp.databinding.ItemNewsBinding

//Adapter
class NewsRecyclerAdapter(
    private val onBookmarkClick: (Article) -> Unit,
    private val onImageClicked: (Article) -> Unit
) : ListAdapter<Article, ArticleViewHolder>(ArticleComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder(
                ItemNewsBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                ),
                onBookmarkClick = { position ->
                    val article = getItem(position)
                    if (article != null) {
                        onBookmarkClick(article)
                    }
                },
                onImageClicked = { position ->
                    val article = getItem(position)
                    if (article != null) {
                        onImageClicked(article)
                    }
                }
        )
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(getItem(position))
        }
    }

    fun getCurrentItem(position: Int): Article? =
            if (position != RecyclerView.NO_POSITION && !currentList.isNullOrEmpty()) {
                getItem(position)
            } else
                null

}