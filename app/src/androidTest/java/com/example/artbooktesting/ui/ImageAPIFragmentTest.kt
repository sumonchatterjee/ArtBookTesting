package com.example.artbooktesting.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.filters.MediumTest
import com.example.artbooktesting.R
import com.example.artbooktesting.data.remote.FakeArtRepositoryTest
import com.example.artbooktesting.ui.adapter.ImageRecyclerAdapter
import com.example.artbooktesting.ui.viewmodel.ArtViewModel
import com.example.artbooktesting.util.getOrAwaitValueTest
import com.example.artbooktesting.util.launchFragmentInHiltContainer
import com.google.common.truth.Truth
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.internal.session.MockitoSessionLoggerAdapter
import javax.inject.Inject

//integration testing
//select image test
@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class ImageAPIFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTestExecutorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var fragmentFactory:ArtFragmentFactory


    @Before
    fun setUp(){
        hiltRule.inject()
    }


    @Test
    fun `test_select_image_functionality`(){
        val navController = Mockito.mock(NavController::class.java)
        val selectedImageUrl = "'test.com"
        val testViewModel= ArtViewModel(FakeArtRepositoryTest())
        launchFragmentInHiltContainer<ImageApiFragment>(
            factory = fragmentFactory
        ){
            Navigation.setViewNavController(requireView(),navController)
            viewModel = testViewModel
            adapter.images = listOf(selectedImageUrl) //set adapter with list of fake image url

        }

        Espresso.onView(ViewMatchers.withId(R.id.imageRecyclerView)).perform(
            RecyclerViewActions.actionOnItemAtPosition<ImageRecyclerAdapter.ImageViewHolder>(
                0,click()
            ) // clicks on the item at position 0 in receylcer view
        )

        Mockito.verify(navController).popBackStack()
        Truth.assertThat(testViewModel.selectedImageUrl.getOrAwaitValueTest()).isEqualTo(selectedImageUrl)

    }
}