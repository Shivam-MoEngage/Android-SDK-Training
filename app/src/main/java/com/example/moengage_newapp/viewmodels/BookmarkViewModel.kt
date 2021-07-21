package com.example.moengage_newapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moengage_newapp.data.NewsRepository
import com.example.moengage_newapp.data.Article
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val repository: NewsRepository
) : ViewModel() {

    val bookmarks = repository.getAllBookmarkedArticles()
        .stateIn(viewModelScope, SharingStarted.Lazily, null)

    fun onBookmarkClick(article: Article) {
        val currentlyBookmarked = article.isBookMarked
        val updatedArticle = article.copy(isBookMarked = !currentlyBookmarked)
        viewModelScope.launch {
            repository.updateArticle(updatedArticle)
        }

        _bookMarkMsg.value = if(updatedArticle.isBookMarked) "Bookmark Added" else "Removed Bookmark"
    }

    private val _bookMarkMsg = MutableLiveData<String>()
    val bookMarkMsg : LiveData<String>
        get() = _bookMarkMsg

    fun onDeleteAllBookmarks() {
        viewModelScope.launch {
            repository.resetAllBookmarks()
            _bookMarkMsg.value = "Removed All Bookmark"
        }
    }
}