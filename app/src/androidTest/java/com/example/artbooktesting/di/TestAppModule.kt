package com.example.artbooktesting.di

import android.content.Context
import androidx.room.Room
import com.example.artbooktesting.data.local.roomdb.ArtDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {

    //build room databse with hilt
    @Provides
    @Named("testDatabase")
    fun injectInMemoryRoom(@ApplicationContext context:Context)=
        Room.inMemoryDatabaseBuilder(context,ArtDatabase::class.java).allowMainThreadQueries().build()

}