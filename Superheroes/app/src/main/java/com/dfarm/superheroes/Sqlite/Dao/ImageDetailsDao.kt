package com.dfarm.superheroes.Sqlite.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.dfarm.superheroes.Sqlite.Entities.ImageDetails

@Dao
interface ImageDetailsDao{
    @Insert
    fun addImageDetails(image: ImageDetails)

    @Query("select * from image_details")
    fun getLocalImageData() : List<ImageDetails>
}