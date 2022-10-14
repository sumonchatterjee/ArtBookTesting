package com.example.artbooktesting.data.remote

import androidx.lifecycle.LiveData
import com.example.artbooktesting.data.local.roomdb.Art
import com.example.artbooktesting.data.local.roomdb.ArtDao
import com.example.artbooktesting.model.ImageResponse
import com.example.artbooktesting.model.Resource
import java.lang.Exception
import javax.inject.Inject

class ArtRepositoryImpl @Inject constructor(
    private val artDao: ArtDao,
    private val api:ArtService):ArtRepository {

    override suspend fun insertArt(art: Art) {
        artDao.insertArt(art)
    }

    override suspend fun deleteArt(art: Art) {
        artDao.deleteArt(art)
    }

    override fun getArt(): LiveData<List<Art>> {
        return artDao.observeArts()
    }

    override suspend fun searchImage(imageSearch: String): Resource<ImageResponse> {
        return try {
            val response = api.imageSearch(imageSearch)
            if (response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("Error", null)
            } else {
                Resource.error("Error", null)
            }
        } catch (e: Exception) {
            Resource.error("No data!", null)
        }
    }
}

