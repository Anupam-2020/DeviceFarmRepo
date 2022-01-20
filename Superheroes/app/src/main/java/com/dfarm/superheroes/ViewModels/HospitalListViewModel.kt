package com.dfarm.superheroes.ViewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dfarm.superheroes.Repository.localRepository.LocalImageLoadingRepository
import com.dfarm.superheroes.Repository.remoteRepository.RemoteImageLoadingRepository
import com.dfarm.superheroes.Sqlite.Dao.ImageDetailsDao
import com.dfarm.superheroes.Sqlite.Entities.ImageDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HospitalListViewModel
@Inject constructor(
    private val localImageLoadingRepository: LocalImageLoadingRepository,
    private val remoteImageLoadingRepository: RemoteImageLoadingRepository
): ViewModel(){

    private var daoImage: ImageDetailsDao?= null

    private val _images: MutableLiveData<List<ImageDetails>> = MutableLiveData()
    val images: LiveData<List<ImageDetails>> = _images

    fun loadImg(){
        viewModelScope.launch(Dispatchers.IO){
            val result = localImageLoadingRepository.getImageData()
            withContext(Dispatchers.Main){
                _images.value = result
            }
        }
    }

    fun getImages() = images.value ?: listOf()

    fun saveRemoteToLocalDb(){
        viewModelScope.launch {
            val result = remoteImageLoadingRepository.getImageData()
            val dbData = result.getOrThrow()
            withContext(Dispatchers.Main) {
                dbData.forEach {
                    saveToLocalDb(ImageDetails(
                        id = it.id,
                        name = it.name,
                        desc= it.desc,
                        image = it.image,
                        doctors = it.doctors,
                        location = it.location,
                        address = it.address
                    ))
                }
            }
        }
    }

    fun saveToLocalDb(img : ImageDetails){
        viewModelScope.launch(Dispatchers.IO) {
            img.let {
                val result = localImageLoadingRepository.addImageDetails(it)
                if (result.isSuccess) {
                    Log.i("@Create", "${result}")
                } else {
                    Log.i("@Create", "${result}")
                }
            }
        }
    }
}