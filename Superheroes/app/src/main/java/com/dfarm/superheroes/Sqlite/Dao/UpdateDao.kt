package com.dfarm.superheroes.Sqlite.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.dfarm.superheroes.Sqlite.Entities.UpdateVersion


@Dao
interface UpdateDao {

    @Insert
    fun addCurrentVersion(version: UpdateVersion)

//    @Update
    @Query("Update previous_build SET version = :ver where id = :tid")
    fun updateVersion(tid: Int, ver: String)

    @Query("select version from previous_build")
    fun getVersion(): String
}