package com.example.artbooktesting.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.artbooktesting.data.remote.repo.FakeArtRepository
import com.example.artbooktesting.model.Status
import com.example.artbooktesting.ui.viewmodel.ArtViewModel
import com.example.artbooktesting.util.MainCoroutineRule
import com.example.artbooktesting.util.getOrAwaitValueTest
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ArtViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule() //makes sure this works in main thread only

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()


    private lateinit var viewModel:ArtViewModel

    @Before
    fun setUp(){
        //make fake repository
        //called as test doubles
        viewModel = ArtViewModel(FakeArtRepository())
    }

    @Test
    fun `insert art without year return error`(){
        viewModel.makeArt("Mona Lisa","Da Vinci","")
        val value = viewModel.insertArtMessage.getOrAwaitValueTest()
        assertThat(value.status).isEqualTo(Status.ERROR)

    }

    @Test
    fun `insert art without name return error`(){
        viewModel.makeArt("","Da Vinci","1986")
        val value = viewModel.insertArtMessage.getOrAwaitValueTest()
        assertThat(value.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert art without artist name return error`(){
        viewModel.makeArt("mona lisa", "","1986")
        val value = viewModel.insertArtMessage.getOrAwaitValueTest()
        assertThat(value.status).isEqualTo(Status.ERROR)
    }
}