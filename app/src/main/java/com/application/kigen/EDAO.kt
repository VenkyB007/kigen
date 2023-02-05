package com.application.kigen

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface EDAO {
    @Insert
    suspend fun insertTask(entity: ExpenseEntity)

    @Update
    suspend fun updateTask(entity: ExpenseEntity)

    @Delete
    suspend fun deleteTask(entity: ExpenseEntity)

    @Query("Delete from expense")
    suspend fun deleteAll()

    @Query("Select * from expense where id like :id")
    suspend fun getExpense(id:Int):List<ExpenseInfo>
}