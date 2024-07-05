package com.loc.newsapp.data.remote

import com.loc.newsapp.data.remote.dto.NewsResponse
import retrofit2.http.Query

interface NewsApi {
    suspend fun getNews(
        @Query("page") page: Int,
        @Query("sources") sources: String,
        @Query("apiKey") apiKey: String
    ): NewsResponse
}