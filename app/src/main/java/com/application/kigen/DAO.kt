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
    @Query("Delete from profiles where name like :name")
    suspend fun deleteProfilebyName(name: String)

    @Query("Select * from profiles")
    suspend fun getTasks():List<ProfileInfo>
    @Query("Select SUM(price) from expense where profileId like :pos")
    suspend fun getProfileTotalExpense(pos: Int) :Int

}