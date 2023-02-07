package com.application.kigen.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.application.kigen.ExpenseEntity
import com.application.kigen.model.ExpenseInfo

@Dao
interface EDAO {
    @Insert
    suspend fun insertTask(entity: ExpenseEntity)

    @Update
    suspend fun updateTask(entity: ExpenseEntity)

    @Delete
    suspend fun deleteTask(entity: ExpenseEntity)

    @Query("Delete from expense where profileId like :profileId")
    suspend fun deleteAllProfileExpense(profileId:Int)

    @Query("Select * from expense where profileId like :id")
    suspend fun getExpense(id:Int):List<ExpenseInfo>
}