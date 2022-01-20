package com.dfarm.superheroes.Sqlite.Entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "image_details")
data class ImageDetails(
    @PrimaryKey(autoGenerate = true)
    val id : Int=0,
    val name : String,
    val address: String,
    val desc : String,
    val image : String,
    val doctors : String,
    val location : String
)