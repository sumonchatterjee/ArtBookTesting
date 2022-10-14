package com.example.artbooktesting.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.artbooktesting.R
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity:AppCompatActivity() {


    @Inject
    lateinit var fragmentFactory:ArtFragmentFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.fragmentFactory = fragmentFactory
        setContentView(R.layout.main_activity)
    }
}