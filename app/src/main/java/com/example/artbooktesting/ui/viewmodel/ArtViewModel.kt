package com.example.artbooktesting.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.artbooktesting.data.local.roomdb.Art
import com.example.artbooktesting.data.remote.ArtRepository
import com.example.artbooktesting.model.ImageResponse
import com.example.artbooktesting.model.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArtViewModel@Inject constructor(
    private val artRepo:ArtRepository
):ViewModel() {

    val artList = artRepo.getArt()

    private val images = MutableLiveData<Resource<ImageResponse>>()
    val imageList :LiveData<Resource<ImageResponse>>
      get() = images

   // the images which are selected/ tapped by user
    private val selectedImages = MutableLiveData<String>()
    val selectedImageUrl : LiveData<String>
    get() = selectedImages //will be used in art details fragment


    //art details fragment
    private var insertArtMsg = MutableLiveData<Resource<Art>>()
    val insertArtMessage :LiveData<Resource<Art>>
    get() = insertArtMsg


    fun resetInsertArtMsg(){
        insertArtMsg = MutableLiveData<Resource<Art>>()
    }

    fun setSelectedImage(url:String){
        selectedImages.postValue(url)
    }


    fun deleteArt(art:Art){
        viewModelScope.launch {
            artRepo.deleteArt(art)
        }
    }

    fun insertArt(art:Art) = viewModelScope.launch {
        artRepo.insertArt(art)
    }

    fun searchForImage(searchString:String){
        if(searchString.isEmpty()){
            return
        }
        images.value = Resource.loading(null)
        viewModelScope.launch {
       val response = artRepo.searchImage(searchString)
            images.value = response
        }

    }

    fun makeArt(name:String,artistName:String,year:String){
        if(name.isEmpty() || artistName.isEmpty() || year.isEmpty()){
            insertArtMsg.postValue(Resource.error("Enter name, Artist, year",null))
            return
        }

        val yearInt = try{
            year.toInt()
        }catch (e:Exception){
            insertArtMsg.postValue(Resource.error("Couldn't convert date",null))
            return
        }

        val art = Art(name,artistName,yearInt,selectedImages.value ?: "")
        insertArt(art)
        setSelectedImage("")
        insertArtMsg.postValue(Resource.success(art))

    }


}