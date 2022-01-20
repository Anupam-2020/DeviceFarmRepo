package com.dfarm.superheroes.Repository.localRepository

import com.dfarm.superheroes.RequestHandling.handleRequest
import com.dfarm.superheroes.Sqlite.Dao.ImageDetailsDao
import com.dfarm.superheroes.Sqlite.Entities.ImageDetails
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalImageLoadingRepository
@Inject constructor(private val daoImage: ImageDetailsDao){
    suspend fun addImageDetails(img: ImageDetails) = handleRequest { daoImage.addImageDetails(img)}

    suspend fun getImageData(): List<ImageDetails> =  daoImage.getLocalImageData()
}