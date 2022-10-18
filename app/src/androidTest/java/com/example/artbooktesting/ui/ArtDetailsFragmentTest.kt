package com.example.artbooktesting.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.example.artbooktesting.R
import com.example.artbooktesting.data.local.roomdb.Art
import com.example.artbooktesting.data.remote.FakeArtRepositoryTest
import com.example.artbooktesting.ui.viewmodel.ArtViewModel
import com.example.artbooktesting.util.getOrAwaitValueTest
import com.example.artbooktesting.util.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import javax.inject.Inject

//integration testing
@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class ArtDetailsFragmentTest {

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
    fun `test_navigation_detailFragment_to_ImageApiFragment`(){
        val navController = Mockito.mock(NavController::class.java)
        launchFragmentInHiltContainer<ArtDetailsFragment>(
            factory = fragmentFactory
        ){
            Navigation.setViewNavController(requireView(),navController)
        }

        Espresso.onView(ViewMatchers.withId(R.id.artImageView)).perform(click())
        Mockito.verify(navController).navigate(
            ArtDetailsFragmentDirections.actionArtDetailsFragmentToImageApiFragment()
        )
    }

    @Test
    fun `test_on_back_pressed`(){
        val navController = Mockito.mock(NavController::class.java)
        launchFragmentInHiltContainer<ArtDetailsFragment>(
            factory = fragmentFactory
        ){
            Navigation.setViewNavController(requireView(),navController)
        }

        Espresso.pressBack()
        Mockito.verify(navController).popBackStack()

    }

    @Test
    fun `test_save_art_if_saved_in_database`(){
       val testViewModel= ArtViewModel(FakeArtRepositoryTest())
        launchFragmentInHiltContainer<ArtDetailsFragment>(
            factory = fragmentFactory
        ){
           viewModel = testViewModel
        }

        Espresso.onView(withId(R.id.nameText)).perform(replaceText("Mona Lisa"))
        Espresso.onView(withId(R.id.artistText)).perform(replaceText("Da Vinci"))
        Espresso.onView(withId(R.id.yearText)).perform(replaceText("1500"))
        Espresso.onView(withId(R.id.saveButton)).perform(click())
         assertThat(testViewModel.artList.getOrAwaitValueTest()).
         contains(Art("Mona Lisa","Da Vinci",1500,""))



    }
}