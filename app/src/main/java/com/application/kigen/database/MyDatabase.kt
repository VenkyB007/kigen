package com.application.kigen.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.application.kigen.Entity
import com.application.kigen.ExpenseEntity
import com.application.kigen.database.dao.DAO
import com.application.kigen.database.dao.EDAO

@Database(entities =  [Entity::class, ExpenseEntity::class], version = 3)
abstract class myDatabase : RoomDatabase() {
    abstract fun dao(): DAO
    abstract fun edao(): EDAO
}