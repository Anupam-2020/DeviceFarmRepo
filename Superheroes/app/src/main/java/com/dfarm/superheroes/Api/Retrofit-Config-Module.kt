package com.dfarm.superheroes.Api

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RetrofitConfigure(){

    @Singleton
    @Provides
    fun retrofit(): Retrofit {
        val client =  OkHttpClient.Builder()
            .build()

        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://mocki.io/v1/")
            .client(client)
            .build()
    }

    @Singleton
    @Provides
    fun loadingImageApi(retrofit: Retrofit) = retrofit.create(LoadingImageApi::class.java)

}