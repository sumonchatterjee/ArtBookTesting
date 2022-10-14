package com.example.artbooktesting.data.remote

import androidx.lifecycle.LiveData
import com.example.artbooktesting.data.local.roomdb.Art
import com.example.artbooktesting.model.ImageResponse
import com.example.artbooktesting.model.Resource

interface ArtRepository {
    suspend fun insertArt(art: Art)

    suspend fun deleteArt(art:Art)

    fun getArt():LiveData<List<Art>>

    suspend fun searchImage(mageString:String):Resource<ImageResponse>

}