package com.application.kigen

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface EDAO {
    @Insert
    suspend fun insertExpense(entity: ExpenseEntity)

    @Query("UPDATE expense SET price = price + 1 WHERE name = :name AND price = :price")
    suspend fun increasePriceByNameAndPrice(name: String, price: String)

    @Query("UPDATE expense SET price = price - 1 WHERE name = :name AND price = :price")
    suspend fun decreasePriceByNameAndPrice(name: String, price: String)

    @Update
    suspend fun updateExpense(entity: ExpenseEntity)

    @Delete
    suspend fun deleteTask(entity: ExpenseEntity)

    @Query("Delete from expense where profileId like :profileId")
    suspend fun deleteAllProfileExpense(profileId:Int)
    @Query("Delete from expense where name like :name and price like :price")
    suspend fun deleteExpensebyNameandPrice(name: String,price: String)

    @Query("Select * from expense where profileId like :id")
    suspend fun getExpense(id:Int):List<ExpenseInfo>
    @Query("Select * from expense")
    suspend fun getAllExpenses():List<ExpenseInfo>


}