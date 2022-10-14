package com.example.artbooktesting.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.bumptech.glide.RequestManager
import com.example.artbooktesting.ui.adapter.ArtRecyclerAdapter
import com.example.artbooktesting.ui.adapter.ImageRecyclerAdapter
import javax.inject.Inject

// this fragment factory is used when we want to instantiate fragment that has some value in constructors, since we are having glide here in dependency that's why we have used this
// more details : https://proandroiddev.com/android-fragments-fragmentfactory-ceec3cf7c959
// very good to use, else there will be problem


class ArtFragmentFactory @Inject constructor(
    private val adapter:ArtRecyclerAdapter,
    private val glide: RequestManager,
    private val imageRecyclerAdapter:ImageRecyclerAdapter,
) : FragmentFactory(){


    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when(className){
            ArtFragment::class.java.name -> ArtFragment(adapter)
            ArtDetailsFragment::class.java.name -> ArtDetailsFragment(glide)
            ImageApiFragment::class.java.name -> ImageApiFragment(imageRecyclerAdapter)
            else ->super.instantiate(classLoader, className)

        }
    }
}