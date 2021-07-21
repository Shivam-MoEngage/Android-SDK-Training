package com.example.moengage_newapp.data

import com.example.moengage_newapp.data.Article

//Response for NewsAPI
data class NewsResponse(
    val status : String,
    val articles: List<Article>
)