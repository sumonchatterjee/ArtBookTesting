package com.example.artbooktesting.data.remote

import com.example.artbooktesting.model.ImageResponse
import com.example.artbooktesting.util.Util
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ArtService {

    @GET("/api/")
    suspend fun imageSearch(
        @Query("q") searchQuery:String,
        @Query("key") apiKey:String = Util.API_KEY
    ):Response<ImageResponse>
}