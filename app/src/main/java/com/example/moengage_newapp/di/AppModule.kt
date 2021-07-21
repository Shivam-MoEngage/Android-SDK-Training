package com.example.moengage_newapp.di

import android.content.Context
import com.example.moengage_newapp.data.ArticleDao
import com.example.moengage_newapp.data.NewsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

//Dependency Injection Using HILT
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideNewDatabase(@ApplicationContext context: Context): NewsDatabase {
        return NewsDatabase.getInstance(context = context)
    }

    @Provides
    fun provideArticlesDao(db: NewsDatabase): ArticleDao {
        return db.getArticlesDao()
    }
    
}