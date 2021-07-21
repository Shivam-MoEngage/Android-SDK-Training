package com.example.moengage_newapp.adapter

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moengage_newapp.data.Article
import com.example.moengage_newapp.databinding.ItemNewsBinding
import com.example.moengage_newapp.util.BlurTransformation


//ViewHolder for RecyclerView
class ArticleViewHolder(
    private val binding: ItemNewsBinding,
    private val onBookmarkClick: (Int) -> Unit,
    private val onImageClicked: (Int) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(article: Article) {
        binding.apply {

            Glide.with(itemView)
                .asBitmap()
                .load(article.urlToImage)
                .transform(BlurTransformation(itemView.context))
                .into(binding.newsImageBg)

            Glide.with(itemView)
                .load(article.urlToImage)
                .into(binding.newsImage)

            binding.article = article
        }
    }

    init {
        binding.apply {

            binding.newsHeadline.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onBookmarkClick(position)
                }
            }

            binding.newsImage.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onImageClicked(position)
                }
            }
        }
    }
}