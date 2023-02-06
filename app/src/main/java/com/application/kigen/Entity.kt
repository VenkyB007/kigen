package com.application.kigen

import androidx.room.PrimaryKey

@androidx.room.Entity(tableName = "profiles")
data class Entity(
    @PrimaryKey(autoGenerate = true)
    var id:Int,
    var name:String,
    var total:Int
)

@androidx.room.Entity(tableName = "expense")
data class ExpenseEntity(
    @PrimaryKey(autoGenerate = true)
    var id:Int,
    var profileId: Int,
    var name: String,
    var price: String
)