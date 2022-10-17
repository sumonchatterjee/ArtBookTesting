package com.example.artbooktesting.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.example.artbooktesting.data.local.roomdb.Art
import com.example.artbooktesting.data.local.roomdb.ArtDao
import com.example.artbooktesting.data.local.roomdb.ArtDatabase
import com.example.artbooktesting.util.getOrAwaitValueTest
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@SmallTest
@ExperimentalCoroutinesApi
@HiltAndroidTest
class ArtDAOTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var dao:ArtDao

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    @Named("testDatabase")
    lateinit var artDatabase: ArtDatabase

    @Before
    fun setUp(){
        //injecting via hilt
      /*  val context = ApplicationProvider.getApplicationContext<Context>()
        artDatabase = Room
            .inMemoryDatabaseBuilder(context, ArtDatabase::class.java) //this is only used in tests
            .allowMainThreadQueries() //this allows to run the test in main thread, not used in production
            .build()*/
        hiltRule.inject()
        dao = artDatabase.artDao()
    }

    @Test
    fun insertArtTest() = runBlocking {
        val exampleArt = Art("Mona Lisa","Da Vinci",1700,"test.com",1)
        dao.insertArt(exampleArt)
        val list = dao.observeArts().getOrAwaitValueTest()  //converts livedata to normal data
        assertThat(list).contains(exampleArt)

    }



    @Test
    fun deleteArtTest() = runBlocking {
        val exampleArt = Art("Mona Lisa","Da Vinci",1700,"test.com",1)
        dao.insertArt(exampleArt)
        dao.deleteArt(exampleArt)

        val list = dao.observeArts().getOrAwaitValueTest()  //converts livedata to normal data
        assertThat(list).doesNotContain(exampleArt)
    }



    @After
    fun tearDown(){
        artDatabase.close()
    }

}