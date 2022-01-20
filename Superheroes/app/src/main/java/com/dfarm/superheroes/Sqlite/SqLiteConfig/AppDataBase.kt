package com.dfarm.superheroes.Sqlite.SqLiteConfig

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.Update
import com.dfarm.superheroes.Sqlite.Dao.ImageDetailsDao
import com.dfarm.superheroes.Sqlite.Dao.UpdateDao
import com.dfarm.superheroes.Sqlite.Entities.ImageDetails
import com.dfarm.superheroes.Sqlite.Entities.UpdateVersion

@Database(entities = [ImageDetails::class, UpdateVersion::class],version = 3,exportSchema = false)
abstract class AppDataBase : RoomDatabase() {
    abstract fun imageDetailsDao() : ImageDetailsDao

    abstract fun updateVersionDao(): UpdateDao

}

