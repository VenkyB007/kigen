package com.application.kigen

import androidx.room.*

@Dao
interface DAO {
    @Insert
    suspend fun insertProfile(entity: Entity)

    @Delete
    suspend fun deleteTask(entity: Entity)

    @Query("Delete from profiles")
    suspend fun deleteAll()
    @Query("Delete from profiles where name like :name")
    suspend fun deleteProfilebyName(name: String)

    @Query("Select * from profiles")
    suspend fun getTasks():List<ProfileInfo>
    //@Query("Update expense SET SUM(price) where profileId like :pos")
    @Query("UPDATE expense SET price = (SELECT SUM(price) FROM expense WHERE profileId LIKE :pos) WHERE profileId LIKE :pos")
    suspend fun getProfileTotalExpense(pos: Int)-

}