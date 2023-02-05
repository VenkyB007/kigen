package com.application.kigen

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities =  [Entity::class,ExpenseEntity::class], version = 2)
abstract class myDatabase : RoomDatabase() {
    abstract fun dao(): DAO
    abstract fun edao(): EDAO
}