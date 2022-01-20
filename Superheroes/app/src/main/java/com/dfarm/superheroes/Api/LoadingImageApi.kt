package com.dfarm.superheroes.Api

import com.dfarm.superheroes.Dto.ImageDetailsDto
import retrofit2.http.GET

interface LoadingImageApi {

    @GET("e5040754-88bb-4f3c-b9f1-ad68e2abde26")
    suspend fun getImageData() : List<ImageDetailsDto>
}