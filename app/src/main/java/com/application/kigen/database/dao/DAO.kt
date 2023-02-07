package com.application.kigen.database.dao

import androidx.room.*
import com.application.kigen.Entity
import com.application.kigen.model.ProfileInfo

@Dao
interface DAO {
    @Insert
    suspend fun insertTask(entity: Entity)

    @Delete
    suspend fun deleteTask(entity: Entity)

    @Query("Delete from profiles")
    suspend fun deleteAll()

    @Query("Select * from profiles")
    suspend fun getTasks():List<ProfileInfo>

}