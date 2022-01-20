package com.dfarm.superheroes.Sqlite.Entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "previous_build")
data class UpdateVersion(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val version: String
)
