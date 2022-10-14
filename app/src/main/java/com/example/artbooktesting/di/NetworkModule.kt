package com.example.artbooktesting.di

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.artbooktesting.R
import com.example.artbooktesting.data.local.roomdb.ArtDao
import com.example.artbooktesting.data.remote.ArtRepository
import com.example.artbooktesting.data.remote.ArtRepositoryImpl
import com.example.artbooktesting.data.remote.ArtService
import com.example.artbooktesting.util.Util.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun injectRetrofitAPI():ArtService{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL).build().create(ArtService::class.java)
    }

    @Provides
    @Singleton
    fun injectGlide(@ApplicationContext context:Context) = Glide .with(context)
        .setDefaultRequestOptions(
            RequestOptions().placeholder(
                R.drawable.ic_launcher_foreground
            )
                .error(R.drawable.ic_launcher_foreground)
        )


    @Provides
    @Singleton
    fun injectNormalRepo(dao:ArtDao,api:ArtService) = ArtRepositoryImpl(dao,api) as ArtRepository


}