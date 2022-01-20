package com.dfarm.superheroes.Repository.remoteRepository

import com.dfarm.superheroes.Api.LoadingImageApi
import com.dfarm.superheroes.Dto.ImageDetailsDto
import com.dfarm.superheroes.RequestHandling.handleRequest
import com.dfarm.superheroes.Sqlite.Entities.ImageDetails
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteImageLoadingRepository
@Inject constructor(
    private val loadingImageApi: LoadingImageApi
) {

    suspend fun getImageData() : Result<List<ImageDetails>> = handleRequest {
        loadingImageApi.getImageData().map {
                imageDetailsDto -> ImageDetails(
            id = imageDetailsDto.id,
            address = imageDetailsDto.address,
            desc = imageDetailsDto.desc,
            name = imageDetailsDto.name,
            doctors = imageDetailsDto.doctors,
            image = imageDetailsDto.image,
            location = imageDetailsDto.location
        )  }
    }

}