package com.dfarm.superheroes.Repository.localRepository

import androidx.room.Update
import com.dfarm.superheroes.RequestHandling.handleRequest
import com.dfarm.superheroes.Sqlite.Dao.ImageDetailsDao
import com.dfarm.superheroes.Sqlite.Dao.UpdateDao
import com.dfarm.superheroes.Sqlite.Entities.UpdateVersion
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class LocalVersionRepository
@Inject constructor(private val daoUpdate: UpdateDao) {

    fun updateDetailsVersion(id: Int, updateVersion: String) = daoUpdate.updateVersion(id, updateVersion)

    fun getVersionDetails() = daoUpdate.getVersion()

    fun addVersionDetails(updateVersion: UpdateVersion) = daoUpdate.addCurrentVersion(updateVersion)
}