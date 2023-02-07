package com.application.kigen.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.application.kigen.*
import com.application.kigen.adapter.ProfileAdapter
import com.application.kigen.database.DataObject
import com.application.kigen.database.myDatabase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val database: myDatabase = Room.databaseBuilder(
            applicationContext, myDatabase::class.java, "Kigen"
        ).build()
        add.setOnClickListener {
            val intent = Intent(this, CreateProfile::class.java)
            startActivity(intent)
        }
        deleteAllProfiles.setOnClickListener{
            DataObject.deleteAllProfiles()
            GlobalScope.launch {
                database.dao().deleteAll()
            }
            setRecycler()
        }
        setRecycler()

    }
    fun setRecycler() {
        profile_view.adapter = ProfileAdapter(DataObject.getAllData())
        profile_view.layoutManager = LinearLayoutManager(this)
    }
}