package com.application.kigen.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.application.kigen.database.DataObject
import com.application.kigen.model.ProfileInfo
import com.application.kigen.R
import com.application.kigen.database.myDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class SplashScreen: AppCompatActivity() {
    private lateinit var database: myDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        database = Room.databaseBuilder(
            applicationContext, myDatabase::class.java, "Kigen"
        ).fallbackToDestructiveMigration().build()
        GlobalScope.launch {
            DataObject.listProfiles = database.dao().getTasks() as MutableList<ProfileInfo>
        }
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }, 2000)
    }
}