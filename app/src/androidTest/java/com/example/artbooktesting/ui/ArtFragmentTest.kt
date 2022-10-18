package com.example.artbooktesting.ui

import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.filters.MediumTest
import com.example.artbooktesting.R
import com.example.artbooktesting.util.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import javax.inject.Inject

@MediumTest
@HiltAndroidTest
class ArtFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var fragmentFactory:ArtFragmentFactory


    @Before
    fun setUp(){
        hiltRule.inject()
    }


    @Test
    fun `testNavigation-from-art-fragment-to-art-details-fragment`(){

        val navController = Mockito.mock(NavController::class.java)
        launchFragmentInHiltContainer<ArtFragment>(
             factory = fragmentFactory
        ){
              Navigation.setViewNavController(requireView(),navController)
        }

        Espresso.onView(ViewMatchers.withId(R.id.fav)).perform(click())
        Mockito.verify(navController).navigate(
            ArtFragmentDirections.actionArtFragmentToArtDetailsFragment()
        )
    }


}