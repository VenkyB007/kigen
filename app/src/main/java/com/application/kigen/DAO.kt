package com.application.kigen

import androidx.room.*

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