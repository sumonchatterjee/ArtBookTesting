package com.example.artbooktesting.di

import android.content.Context
import androidx.room.Room
import com.example.artbooktesting.data.local.roomdb.ArtDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun injectRoomDatabase(@ApplicationContext context:Context): ArtDatabase{
        return Room.databaseBuilder(
            context,
            ArtDatabase::class.java,
            "ArtBookDB"
        ).build()
    }


   @Singleton
   @Provides
    fun injectDao(database: ArtDatabase) =  database.artDao()
}