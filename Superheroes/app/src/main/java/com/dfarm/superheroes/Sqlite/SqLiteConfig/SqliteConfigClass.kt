package com.dfarm.superheroes.Sqlite.SqLiteConfig

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SqliteConfigModule  {

    @Singleton
    @Provides
    fun database(@ApplicationContext context : Context) : AppDataBase{
        return Room.databaseBuilder(
            context,
            AppDataBase::class.java,
            "image_db"
        ).fallbackToDestructiveMigration().build()
    }

    @Singleton
    @Provides
    fun ImageDetailsDao(appDb : AppDataBase) = appDb.imageDetailsDao()

    @Singleton
    @Provides
    fun UpdateVersionDao(appDb: AppDataBase) = appDb.updateVersionDao()
}